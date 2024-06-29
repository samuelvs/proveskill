import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { Question } from '../question';
import { QuestionService } from 'src/app/services/question.service';
@Component({
  selector: 'app-question-edit',
  templateUrl: './question-edit.component.html',
  styleUrls: ['./question-edit.component.scss']
})
export class QuestionEditComponent implements OnInit {
  @Input() question?;
  @Output() clear = new EventEmitter();
  @Output() submit = new EventEmitter();

  formQuestion: FormGroup;
  private routeSub: Subscription;
  alternatives: string[] = [];
  answers: string[] = [];

  constructor(private questionService: QuestionService) { }

  ngOnInit(): void {
    const question = new Question;
    this.formQuestion = new FormGroup({
      id: new FormControl(question.id, [Validators.required]),
      name: new FormControl(question.name, [Validators.required]),
      level: new FormControl(question.level, [Validators.required]),
      tags: new FormControl(question.tags, [Validators.required]),
      type: new FormControl(question.type, [Validators.required]),
      alternatives: new FormControl(question.alternatives, [Validators.required]),
      answer: new FormControl(question.answer, [Validators.required]),
    });

    this.formQuestion.get('type').valueChanges.subscribe((value) => {
      this.alternatives = [];
      this.answers = [];
      this.formQuestion.controls['alternatives'].reset();
      this.formQuestion.controls['answer'].reset();
    });
  }

  ngOnChanges(change) {
    if (Object.keys(change.question.currentValue).length > 0) {
      this.formQuestion.patchValue(change.question.currentValue);
      this.alternatives = change.question.currentValue.alternatives;
      this.answers = change.question.currentValue.answer;
    }
  }

  addAlternative() {
    let alternative = (<HTMLInputElement>document.getElementById('alternative')).value;
    this.alternatives.push(alternative);
    (document.getElementById('alternative') as HTMLInputElement).value = '';
  }

  removeAlternative(index) {
    this.alternatives = this.alternatives.filter((el, i) => i !== index);
  }

  changeBox(item) {
    if (this.answers.includes(item)) {
      this.answers = this.answers.filter(el => el !== item);
    } else {
      this.answers.push(item);
    }

    this.formQuestion.controls['answer'].setValue(this.answers);
  }

  addAnswer(answer) {
    this.formQuestion.controls['answer'].setValue([answer]);
  }

  saveQuestion() {
    let values = this.formQuestion.value;
    console.log(values);

    values.tags = [...values.tags];
    values.alternatives = this.alternatives;
    this.formQuestion.controls['type'].value == 3 ? values.answer = [ ...this.answers ] : values.answer = [values.answer];


    console.log(values);


    this.questionService.putQuestions(values).subscribe(res => {
      this.formQuestion.reset();
      this.submit.emit();
    }, rej => {
      this.questionService._snackBar.open(
        `Houve algum erro, verifique as informações e tente novamente.`,
        '',
        {
          duration: 5000
        }
      );
    })
  }

  clearQuestion() {
    this.formQuestion.reset();
    this.alternatives = [];
    this.answers = [];
    this.clear.emit();
  }
}
