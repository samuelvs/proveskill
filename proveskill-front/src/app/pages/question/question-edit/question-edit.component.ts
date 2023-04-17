import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { Question } from '../question';
@Component({
  selector: 'app-question-edit',
  templateUrl: './question-edit.component.html',
  styleUrls: ['./question-edit.component.scss']
})
export class QuestionEditComponent implements OnInit {
  @Input() question?;
  @Output() clear = new EventEmitter();

  formQuestion: FormGroup;
  private routeSub: Subscription;
  alternatives: string[] = [];
  answers: number[] = [];

  constructor(private router: Router, private actRoute: ActivatedRoute) { }

  ngOnInit(): void {
    const question = new Question;
    this.formQuestion = new FormGroup({
      question: new FormControl(question.question, [Validators.required]),
      difficulty: new FormControl(question.difficulty, [Validators.required]),
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

  changeBox(index) {
    if (this.answers.includes(index)) {
      this.answers = this.answers.filter(el => el !== index);
    } else {
      this.answers.push(index);
    }

    this.formQuestion.controls['answer'].setValue(this.answers);
  }

  clearQuestion() {
    this.formQuestion.reset();
    this.alternatives = [];
    this.answers = [];
    this.clear.emit();
  }
}
