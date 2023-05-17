import { Component, OnInit } from '@angular/core';
import { ExamService } from 'src/app/services/exam.service';
import {
  MatSnackBar
} from '@angular/material/snack-bar';

@Component({
  selector: 'app-exams',
  templateUrl: './exams.component.html',
  styleUrls: ['./exams.component.scss']
})
export class ExamsComponent implements OnInit {
  exams: any = [];


  constructor(private examService: ExamService, private _snackBar: MatSnackBar) { }

  ngOnInit(): void {
    this.loadExams();
  }

  loadExams() {
    this.examService.getExams().subscribe((res) => {

      this.exams = res;
      console.log(this.exams);
    }, rej => {
      this.examService._snackBar.open(
        `Houve algum erro, verifique as informações e tente novamente.`,
        '',
        {
          duration: 5000
        }
      );
    })
  }

  start(exam) {
    if (!this.canStart(exam)) {
      this._snackBar.open(
        `Tente novamente entre ${this.getDate(exam.startDateTime)} horas e ${this.getDate(exam.endDateTime)}.`,
        '',
        {
          duration: 5000
        }
      );
    } else {

    }
  }

  canStart(exam) {
    const dateNow = new Date();
    return dateNow >= new Date(exam.startDateTime) && dateNow <= new Date(exam.endDateTime);
  }

  getQuestionsQuantity(questions) {
    return questions.length;
  }

  getDate(startDateTime) {
    return new Date(startDateTime).toLocaleString();
  }

}
