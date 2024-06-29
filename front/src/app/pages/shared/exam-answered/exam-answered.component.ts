import { Component, OnInit } from '@angular/core';
import { ExamService } from 'src/app/services/exam.service';

@Component({
  selector: 'app-exam-answered',
  templateUrl: './exam-answered.component.html',
  styleUrls: ['./exam-answered.component.scss']
})
export class ExamAnsweredComponent implements OnInit {

  constructor(private service: ExamService,) { }

  ngOnInit(): void {
    this.loadExams();
  }

  loadExams() {
    this.service.getExamsAnswered().subscribe((res) => {

      console.log(res);
    }, rej => {
      this.service._snackBar.open(
        `Houve algum erro, verifique as informações e tente novamente.`,
        '',
        {
          duration: 5000
        }
      );
    })
  }

}
