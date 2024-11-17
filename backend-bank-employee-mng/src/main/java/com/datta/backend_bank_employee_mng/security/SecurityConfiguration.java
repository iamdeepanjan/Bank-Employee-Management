package com.datta.backend_bank_employee_mng.security;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

import com.datta.backend_bank_employee_mng.security.services.UserDetailsServiceImpl;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

@Configuration
@EnableMethodSecurity
public class SecurityConfiguration {

	@Autowired
	UserDetailsServiceImpl userDetailsService;
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception{
		return authConfig.getAuthenticationManager();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(passwordEncoder());
		
		return authProvider;
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		http.authorizeHttpRequests(auth -> auth.requestMatchers("/auth/**").permitAll().anyRequest().authenticated());
		http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		http.csrf(csrf -> csrf.disable());
		http.headers(header -> header.frameOptions(frameOption -> frameOption.disable()));
		http.authenticationProvider(authenticationProvider());
//		http.oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));
		http.oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> 
			jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())
		));
		return http.build();
	}
	
	//For Jwt Authentication
	@Bean
	public KeyPair keyPair() {
		try {
			var keyPairGen = KeyPairGenerator.getInstance("RSA");
			keyPairGen.initialize(2048);
			return keyPairGen.generateKeyPair();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	@Bean
	public RSAKey rsakey(KeyPair keyPair) {
		return new RSAKey
				.Builder((RSAPublicKey)keyPair.getPublic())
				.privateKey(keyPair.getPrivate())
				.keyID(UUID.randomUUID().toString())
				.build();
	}
	
	@Bean
	public JWKSource<SecurityContext> jwkSource(RSAKey rsaKey){
		var jwtSet = new JWKSet(rsaKey);
		return (jwkSelector, context) -> jwkSelector.select(jwtSet);
	}
	
	@Bean
	public JwtDecoder jwtDecoder(RSAKey rsaKey) throws JOSEException{
		return NimbusJwtDecoder
				.withPublicKey(rsaKey.toRSAPublicKey())
				.build();
	}
	
	@Bean
	public JwtEncoder jwtEncoder(JWKSource<SecurityContext> jwkSource) {
		return new NimbusJwtEncoder(jwkSource);
	}
	
	//For authorization with JWT authentication
	@Bean
	public JwtAuthenticationConverter jwtAuthenticationConverter() {
	    JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
	    grantedAuthoritiesConverter.setAuthorityPrefix(""); // Prefix for roles
	    grantedAuthoritiesConverter.setAuthoritiesClaimName("roles"); // Map "roles" claim

	    JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
	    converter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
	    return converter;
	}

}
