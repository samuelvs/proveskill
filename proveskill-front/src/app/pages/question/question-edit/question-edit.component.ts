import { Component, OnInit } from '@angular/core';
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
  title = 'Criar questão';
  formQuestion: FormGroup;
  private routeSub: Subscription;

  constructor(private router: Router, private actRoute: ActivatedRoute) { }

  ngOnInit(): void {
    this.routeSub = this.actRoute.params.subscribe(params => {
      if(params['id']) {
        this.title = 'Editar questão';
        this.createForm({id: 1,
          question: "Quantos paus se faz uma canoa",
          difficulty: 2,
          tags: ["sdf", "ewqre"],
          type: 1});
      } else {
        this.createForm(new Question);
      }
    });
  }

  ngOnDestroy() {
    this.routeSub.unsubscribe();
  }

  createForm(question: Question) {
    this.formQuestion = new FormGroup({
      question: new FormControl(question.question, [Validators.required]),
      difficulty: new FormControl(question.difficulty, [Validators.required]),
      tags: new FormControl(question.tags, [Validators.required]),
      type: new FormControl(question.type, [Validators.required]),
    });
  }

  back() {
    this.router.navigate(['questao']);
  }
}
