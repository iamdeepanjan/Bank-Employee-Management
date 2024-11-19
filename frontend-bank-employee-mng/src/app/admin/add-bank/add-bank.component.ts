import { Component, inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AdminService } from '../admin.service';
import { Subscription } from 'rxjs';
import { Bank } from '../bank';

@Component({
  selector: 'app-add-bank',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './add-bank.component.html',
  styleUrl: './add-bank.component.css'
})
export class AddBankComponent implements OnInit{
  
  addBankForm: FormGroup = new FormGroup({});
  successMessage:string = '';
  errorMessage:string = '';
  valueChange:Subscription = new Subscription();

  private adminService = inject(AdminService);
  private formBuilder = inject(FormBuilder);
  private router = inject(Router);

  ngOnInit(): void {
    this.addBankForm = this.formBuilder.group({
      bankName: ['HDFC', Validators.required],
      branchName: ['', [Validators.required]],
      branchCode: ['', [Validators.required]]
    });
    this.valueChange = this.addBankForm.get('branchName')!.valueChanges.subscribe(() => {
      this.errorMessage = '';
    })
    this.valueChange = this.addBankForm.get('branchCode')!.valueChanges.subscribe(() => {
      this.errorMessage = '';
    })
  }
  onSubmit() {
    if(this.addBankForm.valid){
      const bank:Bank = this.addBankForm.value;
      this.adminService.addNewBank(bank).subscribe({
        next: (response: any) => {
          this.successMessage = response.message;
          setTimeout(() => {
            this.router.navigate(['/admin/bank-list']);
          }, 2000);
        },
        error: (error: any) => {
          this.errorMessage = error.error.message;
        }
      });
    }
    else{
      this.makeFormDirty(this.addBankForm);
    }
  }

  makeFormDirty(form:FormGroup){
    Object.keys(form.controls).forEach(key => {
      form.controls[key].markAsDirty();
      form.controls[key].markAsTouched();
    });
  }
}
