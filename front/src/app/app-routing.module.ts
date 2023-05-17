import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HomeComponent } from './pages/shared/home/home.component';
import { UserComponent } from './pages/shared/user/user.component';
import { QuestionComponent } from './pages/shared/question/question.component';
import { UserEditComponent } from './pages/shared/user/user-edit/user-edit.component';
import { QuestionEditComponent } from './pages/shared/question/question-edit/question-edit.component';
import { LoginComponent } from './pages/login/login.component';
import { AuthGuard } from './auth/auth.guard';
import { ExamComponent } from './pages/shared/exam/exam.component';
import { AdminComponent } from './pages/admin/admin.component';
import { AdminRoutingModule } from './pages/admin/admin-routing.module';
import { TeacherComponent } from './pages/teacher/teacher.component';
import { TeacherRoutingModule } from './pages/teacher/teacher-routing.module';
import { RoleGuard } from './auth/role.guard';
import { MatDialogModule } from '@angular/material/dialog';
import { PageNotFoundComponent } from './pages/shared/page-not-found/page-not-found.component';
import { StudentComponent } from './pages/student/student.component';
import { StudentRoutingModule } from './pages/student/student-routing.module';


const routes: Routes = [
  { path: '',  redirectTo: "login", pathMatch: 'full' },
  { path: 'login',  component: LoginComponent, pathMatch: 'full' },
  { path: 'admin',  component: AdminComponent, pathMatch: 'full', canActivate: [AuthGuard, RoleGuard] },
  { path: 'docente',  component: TeacherComponent, pathMatch: 'full', canActivate: [AuthGuard, RoleGuard] },
  { path: 'estudante',  component: StudentComponent, pathMatch: 'full', canActivate: [AuthGuard, RoleGuard] },
  { path: '**', pathMatch: 'full', component: PageNotFoundComponent },
  // { path: 'usuario',  component: UserComponent, pathMatch: 'full', canActivate: [AuthGuard] },
  // { path: 'questao',  component: QuestionComponent, pathMatch: 'full', canActivate: [AuthGuard]  },
  // { path: 'exame',  component: ExamComponent, pathMatch: 'full', canActivate: [AuthGuard]  },
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes),
    AdminRoutingModule,
    TeacherRoutingModule,
    StudentRoutingModule,
  ],
  entryComponents:[MatDialogModule],
  exports: [RouterModule]
})
export class AppRoutingModule { }
