import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login-status',
  templateUrl: './login-status.component.html',
  styleUrls: ['./login-status.component.css']
})
export class LoginStatusComponent implements OnInit {

  isAuthenticated: boolean = false;
  userEmail: string | null = null;
  firstName: string | null = null;
  lastName: string | null = null;

  constructor(private authService: AuthService, private router: Router) { }

  ngOnInit(): void {
    // Check if the user is authenticated when the component loads
    this.isAuthenticated = this.authService.isAuthenticated();
    this.userEmail = this.authService.getUserEmail();

    const userNameObservable = this.authService.getUserName();
    if (userNameObservable) {
      userNameObservable.subscribe({
        next: (data) => {
          this.firstName = data.firstName;
          this.lastName = data.lastName;
        },
        error: (err) => {
          console.error('Error fetching user details:', err);
        }
      });
    } else {
      console.error('getUserName() returned null');
    }
  }
  

  // Logout method to clear the token and redirect
  logout(): void {
    localStorage.removeItem('jwt'); // Remove JWT from localStorage
    sessionStorage.clear(); 
    this.isAuthenticated = false;
    this.userEmail = null;
    this.router.navigate(['/products']).then(() => {
      window.location.reload();  // Forces the page to reload
    });
  }

}
