import { Component, inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Employee } from '../employee';
import { UserService } from '../user.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-update-user-details',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './update-user-details.component.html',
  styleUrl: './update-user-details.component.css'
})
export class UpdateUserDetailsComponent implements OnInit {

  updateForm: FormGroup = new FormGroup({});
  employee: Employee = new Employee();
  successMessage: string = "";

  private userService = inject(UserService);
  private formBuilder = inject(FormBuilder);
  private router = inject(Router);

  ngOnInit(): void {
    this.userService.getUserDetails().subscribe(data => {
      this.employee = data;
      this.updateForm.patchValue({
        name: this.employee.name,
        mobileNo: this.employee.mobileNo,
        address: this.employee.address
      });
    });

    this.updateForm = this.formBuilder.group({
      name: ['', Validators.required],
      mobileNo: ['', [Validators.required, Validators.pattern('^[0-9]{10}$')]],
      address: ['', Validators.required]
    })
  }

  onSubmit():void{
    if(this.updateForm.valid){
      const updatedEmployee:Employee = this.updateForm.value;
      this.userService.updateDetails(updatedEmployee).subscribe({
        next: (response: any) => {
          this.successMessage = response.message;
          setTimeout(() => {
            this.router.navigate(['/user/user-details']);
          }, 2000);
        },
        error: (error: any) => {
          console.log(error);
        }
      });
    }
    else{
      this.makeFormDirty(this.updateForm);
    }
  }

  makeFormDirty(form:FormGroup){
    Object.keys(form.controls).forEach(key => {
      form.controls[key].markAsDirty();
      form.controls[key].markAsTouched();
    });
  }
}
