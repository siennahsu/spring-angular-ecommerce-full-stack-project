import { Component } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  user = {
    firstName: '',
    lastName: '',
    email: '',
    password: ''
  };
  confirmPassword: string = '';
  passwordMismatch: boolean = false;

  constructor(private authService: AuthService, private router: Router) { }


  // Method to check password mismatch
  validatePasswords() {
    this.passwordMismatch = this.user.password !== this.confirmPassword;
  }

  // Trigger validation when the user changes password fields
  onPasswordChange() {
    this.validatePasswords();
  }


  register() {
    this.authService.register(this.user).subscribe(() => {
      this.router.navigate(['/login']);
    }, error => {
      console.error('Registration error: ', error);
    });
  }
}