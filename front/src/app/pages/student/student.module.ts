import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { StudentRoutingModule } from './student-routing.module';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from 'src/app/app-routing.module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatDividerModule } from '@angular/material/divider';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { StudentComponent } from './student.component';
import { QuestionModule } from '../shared/question/question.module';
import { ExamModule } from '../shared/exam/exam.module';
import { ExamsComponent } from './exams/exams.component';
import { MatDialogModule } from '@angular/material/dialog';
import { ExamsModule } from './exams/exams.module';
import { AnswerExamComponent } from './answer-exam/answer-exam.component';
import { AnswerExamModule } from './answer-exam/answer-exam.module';


@NgModule({
  declarations: [StudentComponent],
  imports: [
    CommonModule,
    StudentRoutingModule,
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
    QuestionModule,
    ExamModule,
    MatDialogModule,
    ExamsModule,
    AnswerExamModule
  ],
  bootstrap: [StudentComponent]
})
export class StudentModule { }
