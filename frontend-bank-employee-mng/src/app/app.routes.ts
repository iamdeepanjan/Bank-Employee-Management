import { Routes } from '@angular/router';
import { LoginComponent } from './authentication/login/login.component';
import { AdminNavigatorComponent } from './admin/admin-navigator/admin-navigator.component';
import { BankListComponent } from './admin/bank-list/bank-list.component';
import { AddBankComponent } from './admin/add-bank/add-bank.component';
import { EmployeeListComponent } from './admin/employee-list/employee-list.component';
import { AddEmployeeComponent } from './admin/add-employee/add-employee.component';
import { UserNavigatorComponent } from './user/user-navigator/user-navigator.component';
import { HomeComponent } from './user/home/home.component';
import { UserDetailsComponent } from './user/user-details/user-details.component';
import { UpdateUserDetailsComponent } from './user/update-user-details/update-user-details.component';
import { UpdateUserPasswordComponent } from './user/update-user-password/update-user-password.component';

export const routes: Routes = [
    { path: 'login', component: LoginComponent},  
    { path: 'admin', 
        component: AdminNavigatorComponent,
        children: [
            { path: 'bank-list', component: BankListComponent },
            { path: 'add-bank', component: AddBankComponent },
            { path: 'employees-list', component: EmployeeListComponent },
            { path: 'add-employees', component: AddEmployeeComponent },
            { path: '', redirectTo: 'bank-list', pathMatch: 'full'}
        ]},    
    { path: 'user', 
        component: UserNavigatorComponent,
        children:[
            {path: 'user-details', component: UserDetailsComponent},
            {path: 'change-password', component: UpdateUserPasswordComponent},
            {path: 'update-details', component: UpdateUserDetailsComponent},
            {path: 'home', component: HomeComponent},
            {path: '', redirectTo: 'home', pathMatch: 'full'}
        ]},     
    { path: '', redirectTo: 'login', pathMatch: 'full'}
];
