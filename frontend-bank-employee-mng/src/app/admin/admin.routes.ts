import { Routes } from '@angular/router';
import { BankListComponent } from './bank-list/bank-list.component';
import { AddBankComponent } from './add-bank/add-bank.component';
import { EmployeeListComponent } from './employee-list/employee-list.component';
import { AddEmployeeComponent } from './add-employee/add-employee.component';

export const routes: Routes = [
    { path: 'bank-list', component: BankListComponent },
    { path: 'add-bank', component: AddBankComponent },
    { path: 'employees-list', component: EmployeeListComponent },
    { path: 'add-employees', component: AddEmployeeComponent },
    { path: '', redirectTo: 'employees-list', pathMatch: 'full'},
];
