import { Component, inject, OnInit } from '@angular/core';
import { Employee } from '../employee';
import { Bank } from '../bank';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { AdminService } from '../admin.service';
import { MatDialog } from '@angular/material/dialog';
import { EmployeeDialogComponent } from '../employee-dialog/employee-dialog.component';
import { MatCommonModule } from '@angular/material/core';

@Component({
  selector: 'app-employee-list',
  standalone: true,
  imports: [FormsModule,CommonModule, MatCommonModule],
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
  private dialog = inject(MatDialog);

  ngOnInit(): void {
    this.loadAllEmployees()
    this.adminService.getAllBranches().subscribe(data => {
      this.banks = data;
    })
  }

  loadAllEmployees(): void {
    this.adminService.getAllEmployees().subscribe(data => {
      this.employees = data;
      this.filteredEmployees = data;
      console.log(this.employees);
    });
  }
  openDialog(action: string, employee: Employee): void {
    const dialogRef = this.dialog.open(EmployeeDialogComponent, {
      data: {
        action: action,
        employee: employee
      }, 
      maxHeight: '500px',
      maxWidth: '600px',
    });
    dialogRef.afterClosed().subscribe(result => {
      if(result){
        console.log('Dialog result:', result);
      }
      this.loadAllEmployees();
    })
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
