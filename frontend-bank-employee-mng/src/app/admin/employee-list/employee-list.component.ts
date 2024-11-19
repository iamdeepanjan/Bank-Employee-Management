import { Component, inject, OnInit } from '@angular/core';
import { Employee } from '../employee';
import { Bank } from '../bank';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { AdminService } from '../admin.service';

@Component({
  selector: 'app-employee-list',
  standalone: true,
  imports: [FormsModule,CommonModule],
  templateUrl: './employee-list.component.html',
  styleUrl: './employee-list.component.css'
})
export class EmployeeListComponent implements OnInit {

  employees: Employee[] = [];
  banks: Bank[] = [];
  branchId: number = 0;
  filteredEmployees: Employee[] = [];

  private router = inject(Router);
  private adminService = inject(AdminService);

  ngOnInit(): void {
    this.adminService.getAllEmployees().subscribe(data => {
      this.employees = data;
      this.filteredEmployees = data;
      console.log(this.employees);
    });
    this.adminService.getAllBranches().subscribe(data => {
      this.banks = data;
    })
  }

  viewEmployeeById(id:number):void {
    this.router.navigate(['/admin/employee-detail',id]);
  }
  
  updateEmployeeById(id:number):void {
    this.router.navigate(['/admin/update-employee',id]);
  }

  deleteEmployeeById(id: number): void {
    if (confirm('Are you sure you want to delete this employee?')) {
      // this.adminService.deleteEmployee(id).subscribe(data => {
      //   console.log(data);
      //   this.getAllEmployees();
      // });
    }
  }
  
  
  filterEmployees(): void {
    if (!this.branchId) {
      this.filteredEmployees = this.employees; // Show all employees if no branch selected
    } 
    else {
      this.filteredEmployees = this.employees.filter(employee =>
        employee.bank?.id=== Number(this.branchId)
      );
    }
  }
}
