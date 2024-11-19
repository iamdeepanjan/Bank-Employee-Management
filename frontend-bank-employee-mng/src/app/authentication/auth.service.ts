import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { LoginRequest } from './login-request';
import { Observable } from 'rxjs';
import { JwtResponse } from './jwt-response';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private url = "http://localhost:8080/auth/login";
  private http = inject(HttpClient);

  login(loginRequest:LoginRequest):Observable<JwtResponse> {
    return this.http.post<JwtResponse>(this.url, loginRequest);
  }

  saveUserInLocalStorage(jwtResponse:JwtResponse) : void {
    localStorage.setItem("token", jwtResponse.accessToken);
    localStorage.setItem("id", jwtResponse.id.toString());
    localStorage.setItem("username", jwtResponse.username);
    localStorage.setItem("email", jwtResponse.email);
    localStorage.setItem("roles", jwtResponse.roles.toString());
  }

  clearUserFromLocalStorage() : void {
    localStorage.clear();
  }

  isLoggedIn() : boolean {
    return localStorage.getItem("token") !== null;
  }
}
