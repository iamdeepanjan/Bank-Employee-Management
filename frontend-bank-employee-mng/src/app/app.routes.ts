import { Routes } from '@angular/router';
import { LoginComponent } from './authentication/login/login.component';
import { AdminNavigatorComponent } from './admin/admin-navigator/admin-navigator.component';
import { routes as adminRoutes } from './admin/admin.routes';
import { UserNavigatorComponent } from './user/user-navigator/user-navigator.component';
import { routes as userRoutes } from './user/user.routes';
import { NotFoundComponent } from './not-found/not-found.component';

export const routes: Routes = [
    { path: 'login', component: LoginComponent},  
    { path: 'admin', 
        component: AdminNavigatorComponent,
        children: adminRoutes
    },    
    { path: 'user', 
        component: UserNavigatorComponent,
        children: userRoutes
    },     
    { path: '', redirectTo: 'login', pathMatch: 'full'},
    { path: '**', component: NotFoundComponent}
];
