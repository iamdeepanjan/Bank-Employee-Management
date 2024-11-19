import { Routes } from '@angular/router';
import { LoginComponent } from './authentication/login/login.component';
import { AdminNavigatorComponent } from './admin/admin-navigator/admin-navigator.component';
import { BankListComponent } from './admin/bank-list/bank-list.component';
import { AddBankComponent } from './admin/add-bank/add-bank.component';
import { EmployeeListComponent } from './admin/employee-list/employee-list.component';
import { AddEmployeeComponent } from './admin/add-employee/add-employee.component';

export const routes: Routes = [
    { path: 'login', component: LoginComponent},  
    { 
        path: 'admin', 
        component: AdminNavigatorComponent,
        children: [
            { path: 'bank-list', component: BankListComponent },
            { path: 'add-bank', component: AddBankComponent },
            { path: 'employees-list', component: EmployeeListComponent },
            { path: 'add-employees', component: AddEmployeeComponent },
            { path: '', redirectTo: 'bank-list', pathMatch: 'full'}
        ]
    },     
    { path: '', redirectTo: 'login', pathMatch: 'full'}
];
