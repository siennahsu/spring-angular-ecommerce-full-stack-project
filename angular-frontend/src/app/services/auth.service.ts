import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { tap } from 'rxjs/operators';
import { jwtDecode } from 'jwt-decode';

interface JwtPayload {
  sub: string; // Assuming 'sub' holds the username
  exp: number;
  iat: number;
  //email: string 
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private baseUrl = 'http://localhost:8080/auth';

  constructor(private http: HttpClient) { }

  register(user: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/register`, user);
  }

  login(credentials: any): Observable<any> {
    return this.http.post<any>(`${this.baseUrl}/login`, credentials, { responseType: 'text' as 'json' })
      .pipe(
        tap(token => {
          localStorage.setItem('jwt', token); // Store token
          const userEmail = this.getUserEmail();
          if (userEmail) {
            this.setUserEmail(userEmail);
          } else {
            console.log("User email is null")
          }
         
        })
      );
  }

  getToken(): string | null {
    return localStorage.getItem('jwt');
  }

  getHeaders(): HttpHeaders {
    const token = this.getToken();
    return new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
  }


  // Check if the user is authenticated
  isAuthenticated(): boolean {
    const token = this.getToken();
    return token != null; // Returns true if token exists
  }

  setUserEmail(email: string): void {
    sessionStorage.setItem('userEmail', email);
  }

  // Get the user email from the JWT token
  getUserEmail(): string | null {
    const token = this.getToken();
    if (token) {
      const decodedToken: JwtPayload = jwtDecode(token);
      return decodedToken.sub; 
    }
    return null;
  }

  getUserName(): Observable<{ firstName: string, lastName: string }> | null {
    const email = this.getUserEmail();
    if (!email) {
      console.error('No email found for the authenticated user.');
      return null;
    }
    const headers = this.getHeaders();
    return this.http.get<{ firstName: string, lastName: string }>(
      `http://localhost:8080/api/users/details?email=${email}`,
      { headers }
    );
  }

}