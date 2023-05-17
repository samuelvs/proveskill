import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ExamService } from 'src/app/services/exam.service';

@Component({
  selector: 'app-exam',
  templateUrl: './exam.component.html',
  styleUrls: ['./exam.component.scss']
})
export class ExamComponent implements OnInit {

  exams: any = [];
  hideToggle = false;
  examToEdit = {};

  constructor(private router: Router, private examService: ExamService) { }

  ngOnInit(): void {
    this.loadExams();
  }

  loadExams() {
    this.examService.getExams().subscribe((res) => {
      this.exams = res;
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

  getQuestionsQuantity(questions) {
    return questions.length;
  }

  toggleExam(){
    this.hideToggle = !this.hideToggle;
  }

  edit(exam) {
    this.hideToggle = true;
    this.examToEdit = { ...exam };
  }

  getDate(startDateTime) {
    return new Date(startDateTime).toLocaleString();
  }

  clear() {
    this.examToEdit = {}
  }

  delete(id) {
    this.examService.delete(id).subscribe(res => {
      this.loadExams();
    }, rej => {
      this.examService._snackBar.open(
        `Houve algum erro, verifique as informações e tente novamente.`,
        '',
        {
          duration: 5000
        }
      );
    });
  }
}
