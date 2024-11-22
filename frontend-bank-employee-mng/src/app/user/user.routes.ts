import { Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { UserDetailsComponent } from './user-details/user-details.component';
import { UpdateUserDetailsComponent } from './update-user-details/update-user-details.component';
import { UpdateUserPasswordComponent } from './update-user-password/update-user-password.component';

export const routes: Routes = [
    {path: 'user-details', component: UserDetailsComponent},
    {path: 'change-password', component: UpdateUserPasswordComponent},
    {path: 'update-details', component: UpdateUserDetailsComponent},
    {path: 'home', component: HomeComponent},
    {path: '', redirectTo: 'home', pathMatch: 'full'}
];