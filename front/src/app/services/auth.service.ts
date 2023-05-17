import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import {
  HttpClient,
  HttpHeaders,
  HttpErrorResponse,
} from '@angular/common/http';
import { Router } from '@angular/router';
import { environment as env } from 'src/environments/environment';
import { MatSnackBar } from '@angular/material/snack-bar';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  // private url = `${env.apiBaseUrl}/auth`;
  private url = `/api/api/auth`;
  headers = new HttpHeaders().set('Content-Type', 'application/json');

  constructor(private http: HttpClient, public router: Router, public _snackBar: MatSnackBar) {
  }

  signIn(email:string, password:string ) {
      return this.http.post(this.url + '/authenticate', {email, password})
              .subscribe((res: any) => {
                localStorage.setItem('access_token', res.token);
                localStorage.setItem('name', res.name);
                localStorage.setItem('email', res.email);
                localStorage.setItem('school', res.school);
                localStorage.setItem('role', res.role);
                localStorage.setItem('first_access', res.first_acsess);
                console.log(res.role);

                switch (res?.role) {
                  case "ADMIN":
                    this.router.navigate(['admin']);
                    break;
                  case "TEACHER":
                    this.router.navigate(['docente']);
                    break;
                  case "STUDENT":
                    this.router.navigate(['estudante']);
                    break;
                  default:
                    break;
                }
              }, (rej) => {
                this._snackBar.open(
                  `Houve algum erro, verifique as informações e tente novamente.`,
                  '',
                  {
                    duration: 5000
                  }
                );
              })
  }

  changePassword(email:string, password:string ) {
      return this.http.post(this.url + '/change-password', {email, password})
              .subscribe((res: any) => {}, (rej) => {
                console.log(rej);

                this._snackBar.open(
                  `Houve algum erro, verifique as informações e tente novamente.`,
                  '',
                  {
                    duration: 5000
                  }
                );
              })
  }

  getToken() {
    return localStorage.getItem('access_token');
  }

  get isLoggedIn(): boolean {
    let authToken = localStorage.getItem('access_token');
    return authToken !== null ? true : false;
  }

  get currentUser() {
    let user = {
      access_token: localStorage.getItem('access_token'),
      name: localStorage.getItem('name'),
      email: localStorage.getItem('email'),
      school: localStorage.getItem('school'),
      role: localStorage.getItem('role'),
      first_access: localStorage.getItem('first_access'),
    }
    return user;
  }

  doLogout() {
    let removeToken = localStorage.removeItem('access_token');
    localStorage.clear();
    if (removeToken == null) {
      this.router.navigate(['login']);
    }
  }

  handleError(error: HttpErrorResponse) {
    let msg = '';
    if (error.error instanceof ErrorEvent) {
      // client-side error
      msg = error.error.message;
    } else {
      // server-side error
      msg = `Error Code: ${error.status}\nMessage: ${error.message}`;
    }
    return throwError(msg);
  }
}

