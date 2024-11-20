import { Component, inject, OnInit } from '@angular/core';
import { Employee } from '../employee';
import { UserService } from '../user.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-user-details',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './user-details.component.html',
  styleUrl: './user-details.component.css'
})
export class UserDetailsComponent implements OnInit {

  employee!:Employee;
  private userService = inject(UserService);

  ngOnInit(): void {
    this.userService.getUserDetails().subscribe(data=>{
      this.employee = data;
    });
  }

}
