import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { BehaviorSubject, Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';

import { environment as env } from 'src/environments/environment';
@Injectable({
  providedIn: 'root',
})
export class UserService {

  private url = `${env.apiBaseUrl}`;

  constructor(private http: HttpClient, public _snackBar: MatSnackBar) {}

  public getUsers() {
    return this.http.get(`${this.url}/users`)
      .pipe(
        map((res) => {
          return res;
        }));
  }

  public putUsers(data) {
    if (data.id) {
      return this.http.put(`${this.url}/users`, data)
        .pipe(
          map((res) => {
            return res;
          }));
    }

    return this.http.post(`${this.url}/auth/register`, data)
      .pipe(
        map((res) => {
          return res;
        }));
  }

  public delete(id: number) {
      return this.http.delete(this.url + `/users/${id}`)
      .pipe(
        map((res) => {
          return res;
        }));
  }

}
