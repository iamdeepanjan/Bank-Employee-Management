import { Component, inject } from '@angular/core';
import { Employee } from '../employee';
import { UserService } from '../user.service';
import { Router } from '@angular/router';
import { TitleCasePipe } from '@angular/common';
import { AvatarDirective } from '../../avatar.directive';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [TitleCasePipe, AvatarDirective],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent {

  employee!: Employee;
  otherEmployees: Employee[] = [];

  private userService = inject(UserService);
  private router = inject(Router);

  ngOnInit(): void {
    this.userService.getUserDetails().subscribe(data => this.employee = data);

    this.userService.getOtherEmployees().subscribe(data => this.otherEmployees = data);
  }

  goToChangePassword(){
    this.router.navigate(['user/change-password']);
  }

  goToAddDetails(){
    this.router.navigate(['user/update-details']);
  }
}
