import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-question',
  templateUrl: './question.component.html',
  styleUrls: ['./question.component.scss']
})
export class QuestionComponent implements OnInit {

  questionTypes: string[] = ["Aberta", "Escolha única", "Multipla Escolha", "Verdadeiro ou falso"];
  difficultyTypes: string[] = ["Muito fácil", "Fácil", "Médio", "Dificil", "Muito dificil"];
  questions = [
    {
      id: 1,
      question: "Quantos paus se faz uma canoa",
      difficulty: 2,
      tags: ["sdf", "ewqre"],
      type: 1
    },
    {
      id: 2,
      question: "Quantos paus se faz uma canoa",
      difficulty: 2,
      tags: ["sdf", "ewqre"],
      type: 1
    },

  ];

  constructor(private router: Router) { }

  ngOnInit(): void {
  }

  createQuestion(){
    this.router.navigate(['/questao/editar']);
  }

  deleteQuestion(id) {

  }


}
