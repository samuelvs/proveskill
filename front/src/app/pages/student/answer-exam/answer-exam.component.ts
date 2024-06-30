import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';
import { ExamService } from 'src/app/services/exam.service';

@Component({
  selector: 'app-answer-exam',
  templateUrl: './answer-exam.component.html',
  styleUrls: ['./answer-exam.component.scss']
})
export class AnswerExamComponent implements OnInit {

  examId;
  startedExam: any = [];
  currentQuestion: any = {};
  formAnswer;
  isLoading = true;
  initialValue: any = [];

  constructor(private router: Router, private examService: ExamService, private authService: AuthService) {
    const currentExamId = localStorage.getItem('examId');
    if (currentExamId) {
      this.examId = currentExamId;
    } else {
      this.examId = this.router.getCurrentNavigation().extras.state?.examId;
    }
  }

  ngOnInit(): void {
    this.loadExam();
    this.formAnswer = new FormGroup({
      answer: new FormControl(this.initialValue, [Validators.required]),
    });

    this.formAnswer.get('answer').valueChanges.subscribe((value) => {
      this.answerQuestion(typeof(value) === 'string' ? [value] : value);
    });
  }

  calculateTimeRemaining(dateTime: string, durationInMinutes: number) {
    setInterval(function () {
    const targetDate = new Date(dateTime);
    const currentDate = new Date();
      const display = document.querySelector("#time")
      const remainingTime = targetDate.getTime() - currentDate.getTime() + durationInMinutes * 60 * 1000;

      const hours = Math.floor(remainingTime / (60 * 60 * 1000));
      const minutes = Math.floor((remainingTime % (60 * 60 * 1000)) / (60 * 1000));
      const seconds = Math.floor((remainingTime % (60 * 1000)) / 1000);

      const formattedTime = `${padZero(hours)}:${padZero(minutes)}:${padZero(seconds)}`;

      if (remainingTime <= 0) {
        this.router.navigate(['estudante/exames']);
      } else {
        display.textContent = formattedTime;
      }

      function padZero(number: number): string {
        return number.toString().padStart(2, "0");
      }
    }, 1000);
  }

  loadExam() {
    this.examService.startExam(this.examId).subscribe(
      (res: any) => {
        this.startedExam = res;
        localStorage.setItem('startedExamId', res.id);
        localStorage.setItem('examId', res.exam.id);
        this.currentQuestion = this.startedExam.exam.questions[0];
        if (this.currentQuestion.answer) {
          this.currentQuestion.type === 3 ? this.formAnswer.get('answer').setValue(this.currentQuestion.answer) : this.formAnswer.get('answer').setValue(...this.currentQuestion.answer);
        }
        this.isLoading = false;
        this.calculateTimeRemaining(res.startedAt, res.exam.duration);
      },
      (rej) => {
        this.examService._snackBar.open(
          `Algo deu errado com a solicitação, verifique as informações e tente novamente.`,
          '',
          {
            duration: 5000
          }
        );
      }
    );
  }

  getQuestion(i) {
    if (this.currentQuestion.answer && this.currentQuestion.answer.length  > 0) {
      this.currentQuestion = this.startedExam.exam.questions[i];
      if (this.currentQuestion.answer && this.currentQuestion.answer.length > 0) {
        this.currentQuestion.type === 3 ? this.formAnswer.get('answer').setValue(this.currentQuestion.answer) : this.formAnswer.get('answer').setValue(...this.currentQuestion.answer);
      } else {
        this.formAnswer.get('answer').clear();
      }
    } else {
      this.examService._snackBar.open(
        `Responda a questão antes de solicitar uma nova.`,
        '',
        {
          duration: 5000
        }
      );
    }
  }

  answerQuestion(answer) {
    if(this.currentQuestion && answer.length > 0) {
      const data = {
        startedExamId: parseInt(this.startedExam.id),
        questionId: this.currentQuestion?.id,
        answer: answer
      };

      this.examService.answer(data).subscribe(res => {
        this.startedExam.exam.questions.find(el => el.id === this.currentQuestion.id).answer = answer;
        this.currentQuestion.answer = answer;
      },
        rej => {
        this.examService._snackBar.open(
          `Algo deu errado com a solicitação, verifique as informações e tente novamente.`,
          '',
          {
            duration: 5000
          }
        );
      })
    }
  }

  changeBox(item) {
    let answers: any = [];
    answers = this.formAnswer.get("answer").value;
    answers = typeof(answers) == 'string' ? [answers] : answers;

    if (this.formAnswer.get("answer").value.includes(item)) {
      answers = answers.filter(el => el !== item);
    } else {
      answers.push(item);
    }
    this.formAnswer.get("answer").setValue(answers);
  }

  canFinalize() {
    return this.startedExam.exam.questions.filter(el => el.answer === null).length == 0;
  }

  finalize() {
    this.router.navigate(['estudante'])
  }
}
