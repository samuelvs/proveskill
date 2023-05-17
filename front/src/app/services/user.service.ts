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

  // private url = `${env.apiBaseUrl}`;
  private url = `/api/api`;

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

  delete(id: number) {
      return this.http.delete(this.url + `/${id}`)
              .subscribe((res: any) => {
                return res;
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

}
