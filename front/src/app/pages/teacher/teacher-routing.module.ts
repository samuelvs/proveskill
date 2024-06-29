import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { UserComponent } from '../shared/user/user.component';
import { QuestionComponent } from '../shared/question/question.component';
import { ExamComponent } from '../shared/exam/exam.component';
import { AuthGuard } from 'src/app/auth/auth.guard';
import { TeacherComponent } from './teacher.component';
import { HomeComponent } from '../shared/home/home.component';
import { RoleGuard } from 'src/app/auth/role.guard';
import { ExamAnsweredComponent } from '../shared/exam-answered/exam-answered.component';


const routes: Routes = [{
    path: 'docente',
    component: TeacherComponent,
    children:[
    { path: '',  component: HomeComponent, pathMatch: 'full', canActivate: [AuthGuard, RoleGuard] },
    { path: 'questao',  component: QuestionComponent, pathMatch: 'full', canActivate: [AuthGuard, RoleGuard] },
    { path: 'exame',  component: ExamComponent, pathMatch: 'full', canActivate: [AuthGuard, RoleGuard] },
    { path: 'exames-respondidos',  component: ExamAnsweredComponent, pathMatch: 'full', canActivate: [AuthGuard, RoleGuard] },
  ]
}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class TeacherRoutingModule { }
