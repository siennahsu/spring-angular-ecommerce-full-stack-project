import { Component } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  credentials: any = {};

  constructor(private authService: AuthService, private router: Router) { }

  login() {
    const body = {
      email: this.credentials.email,
      password: this.credentials.password
    };
  
    this.authService.login(body).subscribe(
      (response) => {
        console.log('Login successful:', response);
        // Navigate to '/products' and refresh the page
        this.router.navigate(['/products']).then(() => {
          window.location.reload();  // Forces the page to reload
        });
      },
      (error) => {
        console.error('Login error:', error);
        if (error.status === 400) {
          // Check for more specific error messages from the server
          if (error.error && error.error.message) {
            alert(error.error.message); // Display server's specific error message
          } else {
            alert('Invalid email or password or other error occurred.'); // Generic message
          }
        } else if (error.status === 401) { // Unauthorized
          alert('Invalid email or password.');
        } else {
          alert('An unexpected error occurred.');
        }
      }
    );
  }
}

