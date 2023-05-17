import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { QuestionService } from 'src/app/services/question.service';

@Component({
  selector: 'app-question',
  templateUrl: './question.component.html',
  styleUrls: ['./question.component.scss']
})
export class QuestionComponent implements OnInit {

  questionTypes: string[] = ["Aberta", "Escolha única", "Multipla Escolha", "Verdadeiro ou falso"];
  difficultyTypes: string[] = ["Muito fácil", "Fácil", "Médio", "Dificil", "Muito dificil"];
  questions: any = [];

  hideToggle = false;
  questionToEdit = {};


  constructor(private router: Router, private questionService: QuestionService ) { }

  ngOnInit(): void {
    this.loadQuestions();
  }

  loadQuestions() {
    this.questionService.getQuestions().subscribe((res) => {
      this.questions = res;
    }, rej => {
      this.questionService._snackBar.open(
        `Houve algum erro, verifique as informações e tente novamente.`,
        '',
        {
          duration: 5000
        }
      );
    });
  }

  toggleQuestion(){
    this.hideToggle = !this.hideToggle;
  }

  edit(question) {
    this.hideToggle = true;
    this.questionToEdit = { ...question };
  }

  clear() {
    this.questionToEdit = {}
  }

  deleteQuestion(id) {
    this.questionService.delete(id).subscribe(res => {
      this.loadQuestions();
    }, rej => {
      this.questionService._snackBar.open(
        `Houve algum erro, verifique as informações e tente novamente.`,
        '',
        {
          duration: 5000
        }
      );
    });
  }
}
