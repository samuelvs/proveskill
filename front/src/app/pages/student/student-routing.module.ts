import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { UserComponent } from '../shared/user/user.component';
import { QuestionComponent } from '../shared/question/question.component';
import { ExamComponent } from '../shared/exam/exam.component';
import { AuthGuard } from 'src/app/auth/auth.guard';
import { StudentComponent } from './student.component';
import { HomeComponent } from '../shared/home/home.component';
import { RoleGuard } from 'src/app/auth/role.guard';
import { ExamsComponent } from './exams/exams.component';


const routes: Routes = [{
    path: 'estudante',
    component: StudentComponent,
    children:[
    { path: '',  component: HomeComponent, pathMatch: 'full', canActivate: [AuthGuard] },
    { path: 'exames',  component: ExamsComponent, pathMatch: 'full', canActivate: [AuthGuard] },
    // { path: 'exame',  component: ExamComponent, pathMatch: 'full', canActivate: [AuthGuard, RoleGuard] },
  ]
}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class StudentRoutingModule { }
