import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatIconModule } from '@angular/material/icon';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatButtonModule } from '@angular/material/button';
import { MatDividerModule } from '@angular/material/divider';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatRadioModule } from '@angular/material/radio';
import { MatCheckboxModule} from '@angular/material/checkbox';
import { MatExpansionModule } from '@angular/material/expansion';
import { AppRoutingModule } from 'src/app/app-routing.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ExamService } from 'src/app/services/exam.service';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { AnswerExamComponent } from './answer-exam.component';

@NgModule({
  declarations: [
    AnswerExamComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatIconModule,
    MatSnackBarModule,
    MatButtonModule,
    MatToolbarModule,
    MatSidenavModule,
    MatDividerModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatRadioModule,
    MatCheckboxModule,
    MatExpansionModule,
    FormsModule
  ],
  providers: [
    ExamService
  ],
})
export class AnswerExamModule { }
