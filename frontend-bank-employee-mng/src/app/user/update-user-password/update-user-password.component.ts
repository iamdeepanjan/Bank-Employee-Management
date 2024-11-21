import { Component, inject, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Subscription } from 'rxjs';
import { UserService } from '../user.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-update-user-password',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './update-user-password.component.html',
  styleUrl: './update-user-password.component.css'
})
export class UpdateUserPasswordComponent implements OnInit, OnDestroy{

  passwordForm: FormGroup = new FormGroup({});
  errorMessageForCurrent:string = "";
  errorMessageForNew: string = "";
  errorMessageForConfirm:string = "";
  successMessage:string = "";
  passwordValueChangesSubscription: Subscription = new Subscription();

  private userService = inject(UserService);
  private formBuilder = inject(FormBuilder);
  private router = inject(Router);

  ngOnInit(): void {
    this.passwordForm = this.formBuilder.group({
      currentPassword: ['', Validators.required],
      newPassword: ['', [Validators.required, Validators.minLength(6)]],
      confirmPassword: ['', [Validators.required, Validators.minLength(6)]]
    });
    this.passwordValueChangesSubscription = this.passwordForm.get('currentPassword')!.valueChanges.subscribe(() => {
      this.errorMessageForCurrent = '';
    });
    this.passwordValueChangesSubscription = this.passwordForm.get('newPassword')!.valueChanges.subscribe(() => {
      this.errorMessageForNew = '';
    });
    this.passwordValueChangesSubscription = this.passwordForm.get('confirmPassword')!.valueChanges.subscribe(() => {
      this.errorMessageForConfirm = '';
    });
  }

  onSubmit():void{
    if(this.passwordForm.value){
      const currentPassword = this.passwordForm.get('currentPassword')?.value;
      const newPassword = this.passwordForm.get('newPassword')?.value;
      const confirmPassword = this.passwordForm.get('confirmPassword')?.value;

      if (newPassword !== confirmPassword) {
        this.errorMessageForConfirm = "Passwords do not match";
        return;
      }

      if (newPassword == currentPassword) {
        this.errorMessageForNew = "Current Password and New Password are same";
        return;
      }

      this.userService.updatePassword(currentPassword, newPassword).subscribe({
        next: (response: any) => {
          this.successMessage = response.message;
          setTimeout(() => {
            this.router.navigate(['/user/user-details']);
          }, 2000);
        },
        error: (error: any) => {
          this.errorMessageForCurrent = error.error.message;
        }
      });
    }
    else{
      this.makeFormDirty(this.passwordForm);
    }
  }

  makeFormDirty(form:FormGroup){
    Object.keys(form.controls).forEach(key => {
      form.controls[key].markAsDirty();
      form.controls[key].markAsTouched();
    });
  }

  ngOnDestroy(): void {
    this.passwordValueChangesSubscription.unsubscribe();
  }
}
