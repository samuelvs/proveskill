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
export class ExamService {

  private url = `${env.apiBaseUrl}/exams`;
  headers = new HttpHeaders().set('Content-Type', 'application/json');
  currentUser = {};

  constructor(private http: HttpClient, public router: Router, public _snackBar: MatSnackBar) {
  }

  getExams() {
      return this.http.get(this.url)
      .pipe(
        map((res) => {
          return res;
        }));
  }

  public startExam(examId) {
      return this.http.get(`${this.url}/start/${examId}`)
      .pipe(
        map((res) => {
          return res;
        }));
  }

  public answer(data) {
      return this.http.put(`${this.url}/answer`, data)
      .pipe(
        map((res) => {
          return res;
        }));
  }

  public putExams(data) {
    return this.http.put(`${this.url}`, data)
      .pipe(
        map((res) => {
          return res;
        }));
  }

  public delete(id: number) {
    return this.http.delete(this.url + `/${id}`)
    .pipe(
      map((res) => {
        return res;
      }));
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

