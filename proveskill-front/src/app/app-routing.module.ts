import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { UserComponent } from './pages/user/user.component';
import { QuestionComponent } from './pages/question/question.component';
import { UserEditComponent } from './pages/user/user-edit/user-edit.component';
import { QuestionEditComponent } from './pages/question/question-edit/question-edit.component';


const routes: Routes = [
  { path: '',  component: HomeComponent, pathMatch: 'full' },
  { path: 'usuario',  component: UserComponent, pathMatch: 'full' },
  { path: 'usuario/editar',  component: UserEditComponent, pathMatch: 'full' },
  { path: 'usuario/editar/:id',  component: UserEditComponent, pathMatch: 'full' },
  { path: 'questao',  component: QuestionComponent, pathMatch: 'full' },
  { path: 'questao/editar',  component: QuestionEditComponent, pathMatch: 'full' },
  { path: 'questao/editar/:id',  component: QuestionEditComponent, pathMatch: 'full' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
