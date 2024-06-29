import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatIconModule } from '@angular/material/icon';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatButtonModule } from '@angular/material/button';
import { MatDividerModule } from '@angular/material/divider';
import { UserModule } from './pages/shared/user/user.module';
import { ReactiveFormsModule } from '@angular/forms';
import { QuestionModule } from './pages/shared/question/question.module';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { LoginComponent } from './pages/login/login.component';
import { AuthInterceptor } from './auth/authconfig.interceptor';
import { ExamComponent } from './pages/shared/exam/exam.component';
import { ExamModule } from './pages/shared/exam/exam.module';
import { AdminModule } from './pages/admin/admin.module';
import { TeacherModule } from './pages/teacher/teacher.module';
import { ChangePasswordComponent } from './components/change-password/change-password.component';
import { PageNotFoundComponent } from './pages/shared/page-not-found/page-not-found.component';
import { StudentModule } from './pages/student/student.module';
import { ChangePasswordModule } from './components/change-password/change-password.module';
import { ForgotPasswordModule } from './components/forgot-password/forgot-password.module';
import { ExamAnsweredComponent } from './pages/shared/exam-answered/exam-answered.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    PageNotFoundComponent,
    ExamAnsweredComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserModule,
    BrowserAnimationsModule,
    MatToolbarModule,
    MatSidenavModule,
    MatButtonModule,
    MatIconModule,
    MatDividerModule,
    ReactiveFormsModule,
    HttpClientModule,
    UserModule,
    QuestionModule,
    ExamModule,
    AdminModule,
    TeacherModule,
    StudentModule,
    ChangePasswordModule,
    ForgotPasswordModule
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
