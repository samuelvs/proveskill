import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';

import { environment as env } from 'src/environments/environment';

export class UserService {

  private url = `${env.apiBaseUrl}/user`;

  constructor(private http: HttpClient) {}

  public getUsers(data) {
    return this.http.get(`${this.url}`, data)
      .pipe(
        map((res) => {
          return res;
        }));
  }

  public postUsers(data) {
    return this.http.post(`${this.url}`, data)
      .pipe(
        map((res) => {
          return res;
        }));
  }

}
