import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { Employee } from '../employee';
import { AdminService } from '../admin.service';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Bank } from '../bank';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-employee-dialog',
  standalone: true,
  imports: [ReactiveFormsModule, MatDialogModule],
  templateUrl: './employee-dialog.component.html',
  styleUrl: './employee-dialog.component.css'
})
export class EmployeeDialogComponent implements OnInit{

  employee: Employee = new Employee;
  updateForm: FormGroup = new FormGroup({});
  id: number = 0;
  banks:Bank[] = [];
  errorMessage: string = '';
  successMessage: string = '';
  emailValueChangeSubscription:Subscription = new Subscription();

  constructor(
    public dialogRef: MatDialogRef<EmployeeDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private adminService: AdminService,
    private formBuilder: FormBuilder
  ) {
    this.employee = data.employee || {};
  }

  onCancel(): void {
    this.dialogRef.close();
  }

  loadEmployeeDetails(): void {
    if (this.data.action === 'view') {
      this.adminService.viewEmployeeById(this.data.employee.id).subscribe({
        next: (employee) => {
          this.employee = employee;
        },
        error: (err) => {
          alert('Error loading employee details: ' + err.message);
          console.error(err);
        }
      });
    }
  }

  loadForUpdate(): void {
    if (this.data.action === 'update') {
      this.adminService.viewEmployeeById(this.data.employee.id).subscribe({
        next: (employee) => {
          this.employee = employee;
          this.updateForm.patchValue({
            empId: this.employee.empId,
            name: this.employee.name,
            email: this.employee.email,
            job: this.employee.job,
            banks:{
              id: this.employee.bank.id
            }
          });
        },
        error: (err) => {
          alert('Error loading employee details: ' + err.message);
          console.error(err);
        }
      });
      this.updateForm = this.formBuilder.group({
        empId: ['', [Validators.required]],
        name: ['', [Validators.required]],
        email: ['', [Validators.required, Validators.email]],
        job: ['', [Validators.required]],
        bank: this.formBuilder.group({
          id: ['', [Validators.required]]
        })
      });
      this.adminService.getAllBranches().subscribe(data => this.banks = data);
      this.emailValueChangeSubscription = this.updateForm.get('email')!.valueChanges.subscribe(() => {
        this.errorMessage = '';
      });
    }
  }

  approveEmployee(id:number): void {
    this.adminService.approveEmployeeById(id).subscribe({
      next: () => {
        this.dialogRef.close();
      },
      error: (err: any) => {
        alert('Error approving employee: ' + err.message);
      }
    });
  }

  onSubmitUpdate(){
    if(this.updateForm.valid){
      const updatedEmployee:Employee = this.updateForm.value;
      this.adminService.updateEmployeeById(this.data.employee.id, updatedEmployee).subscribe({
        next: (res:any) => {
          this.successMessage = res.message; 
          setTimeout(() => {
            this.dialogRef.close();
          }, 2000);
        },
        error: (error: any) => {
          this.errorMessage = error.error.message;
        }
      });
    }
  }

  onConfirmDelete(id:number): void {
    this.adminService.deleteEmployeeById(id).subscribe({
      next: () => {
        this.dialogRef.close();
      },
      error: (err: any) => {
        alert('Error deleting employee: ' + err.message);
      }
    });
  }

  ngOnInit(): void {
    this.loadEmployeeDetails();
    this.loadForUpdate();
  }
}
