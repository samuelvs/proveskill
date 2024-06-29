import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { ExamService } from 'src/app/services/exam.service';
import { Exam } from '../exam';
import { QuestionService } from 'src/app/services/question.service';
import { Question } from '../../question/question';
import { $ } from 'protractor';

@Component({
  selector: 'app-exam-edit',
  templateUrl: './exam-edit.component.html',
  styleUrls: ['./exam-edit.component.scss']
})
export class ExamEditComponent implements OnInit {
  @Input() exam?;
  @Output() clear = new EventEmitter();
  @Output() submit = new EventEmitter();

  formExam: FormGroup;
  questions: Question[] = [];
  questionsSelected = [];
  questionTypes: string[] = ["Aberta", "Escolha única", "Multipla Escolha", "Verdadeiro ou falso"];
  difficultyTypes: string[] = ["Muito fácil", "Fácil", "Médio", "Dificil", "Muito dificil"];

  constructor(private examService: ExamService, private questionService: QuestionService) { }

  ngOnInit(): void {
    const exam = new Exam;
    this.questionsSelected = [];
    this.formExam = new FormGroup({
      id: new FormControl(exam.id, [Validators.required]),
      title: new FormControl(exam.title, [Validators.required]),
      startDateTime: new FormControl(exam.startDateTime, [Validators.required]),
      endDateTime: new FormControl(exam.endDateTime, [Validators.required]),
      duration: new FormControl(exam.duration, [Validators.required]),
      questions: new FormControl(exam.questions, [Validators.required]),
    });
    this.loadQuestions();
  }

  ngOnChanges(change) {
    if (Object.keys(change.exam.currentValue).length > 0) {
      this.formExam.patchValue(change.exam.currentValue);
      this.questionsSelected = change.exam.currentValue.questions;
      this.questionsSelected.filter(el => el.user = null)
    }
  }

  searchQuestion() {
    var search = document.getElementById("a") as HTMLInputElement;
    if (search.value.length > 0) {
      this.questionService.getQuestionsBySearch(search.value).subscribe((res: Question[]) => {
        this.questions = res;
      }, rej => {
        this.examService._snackBar.open(
          `Houve algum erro, verifique as informações e tente novamente.`,
          '',
          {
            duration: 5000
          }
          );
        });
    } else {
      this.loadQuestions();
    }
  }

  loadQuestions() {
    this.questionService.getQuestions().subscribe((res: Question[]) => {
      this.questions = res;
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

  questionChecked(question){
    let questionExist;
    if (question.id) {
      questionExist = this.questionsSelected.find(el => el.id == question.id);
    } else {
      questionExist = this.questionsSelected.find(el => el == question);
    }

    return questionExist ? true : false;
  }

  adjustAlternatives(alternatives) {
    return alternatives.toString().replace(',', '; ');
  }

  changeBox(question: Question) {
    const questionExist = this.questionChecked(question);

    if (questionExist) {
      if (question.id) {
        this.questionsSelected = this.questionsSelected.filter(el => el.id !== question.id);
      } else {
        this.questionsSelected = this.questionsSelected.filter(el => el !== question);
      }
    } else {
      this.questionsSelected.push(question);
    }
  }

  save() {
    let values = this.formExam.value;
    values.questions = this.questionsSelected;

    console.log(values);


    this.examService.putExams(values).subscribe(res => {
      this.formExam.reset();
      this.questionsSelected = [];
      this.submit.emit();
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

  clearForm() {
    this.formExam.reset();
    this.questionsSelected = [];
    this.clear.emit();
  }
}
