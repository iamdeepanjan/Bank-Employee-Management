import { Component, inject } from '@angular/core';
import { Router, RouterOutlet } from '@angular/router';
import { AuthService } from './authentication/auth.service';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'frontend-bank-employee-mng';

  private router = inject(Router);
  private authService = inject(AuthService);

  logout(){
    this.authService.clearUserFromLocalStorage();
    this.router.navigate(['/login']);
  }
}
