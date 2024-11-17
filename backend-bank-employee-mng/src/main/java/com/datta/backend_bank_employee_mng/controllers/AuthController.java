package com.datta.backend_bank_employee_mng.controllers;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.datta.backend_bank_employee_mng.models.ERole;
import com.datta.backend_bank_employee_mng.models.Role;
import com.datta.backend_bank_employee_mng.models.User;
import com.datta.backend_bank_employee_mng.repositories.RoleRepository;
import com.datta.backend_bank_employee_mng.repositories.UserRepository;
import com.datta.backend_bank_employee_mng.request.LoginRequest;
import com.datta.backend_bank_employee_mng.request.SignupRequest;
import com.datta.backend_bank_employee_mng.response.JwtResponse;
import com.datta.backend_bank_employee_mng.response.MessageResponse;
import com.datta.backend_bank_employee_mng.security.services.UserDetailsImpl;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private JwtEncoder jwtEncoder;
	
	@Autowired
	PasswordEncoder encoder;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	private Object createScope(Authentication auth) {
		return auth.getAuthorities().stream()
				.map(a -> a.getAuthority())
				.collect(Collectors.joining(" "));
	}
	
	private String createToken(Authentication auth) {
		String roles = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
		var claims = JwtClaimsSet.builder()
				.issuer("self")
				.issuedAt(Instant.now())
				.expiresAt(Instant.now().plusSeconds(60*30))
				.subject(auth.getName())
				.claim("scope", createScope(auth))
				.claim("roles", roles)
				.claim("userId", ((UserDetailsImpl) auth.getPrincipal()).getId())
				.build();
		return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
	}
	
	@PostMapping("/login")
	public JwtResponse authenticateUser(@RequestBody LoginRequest loginRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = createToken(authentication);
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());
		
		return new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles);
	}
	
	@PostMapping("/signup")
	public ResponseEntity<MessageResponse> registerUser(@RequestBody SignupRequest signupRequest) {
		if(userRepository.existsByUsername(signupRequest.getUsername())) {
			return ResponseEntity.badRequest().body(new MessageResponse("username is already taken"));
		}
		if(userRepository.existsByEmail(signupRequest.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponse("email is already taken"));
		}
		User user = new User(signupRequest.getUsername(),
							signupRequest.getEmail(),
							encoder.encode(signupRequest.getPassword()));
		Set<String> strRoles = signupRequest.getRoles();
		Set<Role> roles = new HashSet<>();
		
		if (strRoles == null) {
			Role userRole = roleRepository.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Role is not found"));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch(role) {
				case "admin":
					Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
						.orElseThrow(() -> new RuntimeException("Role is not found"));
					roles.add(adminRole);
					break;
				default:
					Role userRole = roleRepository.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Role is not found"));
				roles.add(userRole);
				}
			});
		}
		user.setRoles(roles);
		userRepository.save(user);
		return ResponseEntity.ok(new MessageResponse("User registered succesfully"));
	}
	
	@PostMapping("/roles")
	public ResponseEntity<Role> addRoles(@RequestBody Role role){
		return ResponseEntity.ok(roleRepository.save(role));
	}
	
	@GetMapping("/roles")
	public ResponseEntity<List<Role>> getRoles(){
		return ResponseEntity.ok(roleRepository.findAll());
	}
	
	 @GetMapping("/test")
	 public String test(Authentication auth) {
		 System.out.println("Authorities: " + auth.getAuthorities());
	     return "Authorities logged in console!";
	 }
	
}
