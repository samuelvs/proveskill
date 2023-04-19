import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';

import { environment as env } from 'src/environments/environment';
@Injectable({
  providedIn: 'root',
})
export class UserService {

  private url = `${env.apiBaseUrl}/users`;

  constructor(private http: HttpClient) {}

  public getUsers() {
    return this.http.get(`${this.url}`)
      .pipe(
        map((res) => {
          return res;
        }));
  }

  public putUsers(data) {
    return this.http.put(`${this.url}`, data)
      .pipe(
        map((res) => {
          return res;
        }));
  }

}
