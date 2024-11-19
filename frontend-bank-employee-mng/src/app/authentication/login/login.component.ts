import { Component, inject, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { LoginRequest } from '../login-request';
import { AuthService } from '../auth.service';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JwtResponse } from '../jwt-response';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent implements OnInit, OnDestroy{

  loginForm:FormGroup = new FormGroup({});
  loginRequest:LoginRequest = new LoginRequest();
  error:string = '';
  passwordValueChange:Subscription = new Subscription();

  private authService = inject(AuthService);
  private router = inject(Router);
  private formBuilder = inject(FormBuilder);

  ngOnInit(): void {
    this.loginForm = this.formBuilder.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });
    this.passwordValueChange = this.loginForm.get('password')!.valueChanges.subscribe(() => {
      this.error = '';
    })
  }


  onSubmit() :void{
    if(this.loginForm.valid){
      this.loginRequest = this.loginForm.value;
      this.authService.login(this.loginRequest).subscribe({
        next: (response: JwtResponse) => {
          this.authService.saveUserInLocalStorage(response);
          const roles = response.roles[0];
          if (roles && roles === 'ROLE_ADMIN') {
            this.router.navigate(['/admin']);
          } else if (roles && roles === 'ROLE_USER') {
            this.router.navigate(['/user']);
          }
        },
        error: (error: any) => {
          this.error = 'Invalid login credentials, please try again';
        }
      });  
    }
    else{
      this.makeFormDirty(this.loginForm);
    }
  }

  makeFormDirty(form:FormGroup){
    Object.keys(form.controls).forEach(key => {
      form.controls[key].markAsDirty();
      form.controls[key].markAsTouched();
    });
  }

  ngOnDestroy(): void {
    this.passwordValueChange.unsubscribe();
  }
}
