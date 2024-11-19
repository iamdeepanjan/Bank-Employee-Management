import { Component, inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { map, Observable, Subscription } from 'rxjs';
import { AdminService } from '../admin.service';
import { Router } from '@angular/router';
import { Bank } from '../bank';
import { Employee } from '../employee';

@Component({
  selector: 'app-add-employee',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './add-employee.component.html',
  styleUrl: './add-employee.component.css'
})
export class AddEmployeeComponent implements OnInit {

  addEmployeeForm:FormGroup = new FormGroup({});
  banks: Bank[] = [];
  successMessage:string = '';
  errorMessage:string = '';
  valueChange:Subscription = new Subscription();

  private adminService = inject(AdminService);
  private formBuilder = inject(FormBuilder);
  private router = inject(Router);

  ngOnInit(): void {
    this.addEmployeeForm = this.formBuilder.group({
      empId: ['', Validators.required],
      name: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      job: ['', Validators.required],
      password: ['password', Validators.required],
      bank: this.formBuilder.group({
        id: ['', Validators.required]
      })
    })
    this.getLastEmpId().subscribe(data => this.addEmployeeForm.get('empId')!.setValue(data));
    this.adminService.getAllBranches().subscribe(data => this.banks = data);
    this.valueChange = this.addEmployeeForm.get('email')!.valueChanges.subscribe(() => {
      this.errorMessage = '';
    })
  }

  onSubmit() {
    if(this.addEmployeeForm.valid){
      const employee:Employee = this.addEmployeeForm.value;
      this.adminService.addNewEmployee(employee).subscribe({
        next: (response: any) => {
          this.successMessage = response.message;
          setTimeout(() => {
            this.router.navigate(['/admin/employees-list']);
          }, 2000);
        },
        error: (error: any) => {
          this.errorMessage = error.error.message;
        }
      });
    }
    else{
      this.makeFormDirty(this.addEmployeeForm);
    }
  }

  makeFormDirty(form:FormGroup){
    Object.keys(form.controls).forEach(key => {
      form.controls[key].markAsDirty();
      form.controls[key].markAsTouched();
    });
  }

  getLastEmpId(): Observable<number> {
    return this.adminService.getAllEmployees().pipe(
      map(data => {
        let lastEmp = data.pop();
        return lastEmp ? Number(lastEmp?.empId) + 1 : 10000;
      })
    );
  }
}
