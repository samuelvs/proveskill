import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { UserComponent } from '../shared/user/user.component';
import { QuestionComponent } from '../shared/question/question.component';
import { ExamComponent } from '../shared/exam/exam.component';
import { AuthGuard } from 'src/app/auth/auth.guard';
import { AdminComponent } from './admin.component';
import { HomeComponent } from '../shared/home/home.component';
import { RoleGuard } from 'src/app/auth/role.guard';


const routes: Routes = [{
    path: 'admin',
    component: AdminComponent,
    children:[
    { path: '',  component: HomeComponent, pathMatch: 'full', canActivate: [AuthGuard, RoleGuard] },
    { path: 'usuario',  component: UserComponent, pathMatch: 'full', canActivate: [AuthGuard, RoleGuard] },
    { path: 'questao',  component: QuestionComponent, pathMatch: 'full', canActivate: [AuthGuard, RoleGuard] },
    { path: 'exame',  component: ExamComponent, pathMatch: 'full', canActivate: [AuthGuard, RoleGuard] },
  ]
}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdminRoutingModule { }
