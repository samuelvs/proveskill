import { Component, ViewChild, TemplateRef  } from '@angular/core';
import { BreakpointObserver } from '@angular/cdk/layout';
import { MatSidenav } from '@angular/material/sidenav';
import {MatDialog} from '@angular/material/dialog';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';
import { ChangePasswordComponent } from 'src/app/components/change-password/change-password.component';

@Component({
  selector: 'teacher',
  templateUrl: './teacher.component.html',
  styleUrls: ['./teacher.component.scss']
})

export class TeacherComponent {
  @ViewChild(MatSidenav)
  sidenav!: MatSidenav;

  title = 'ProveSkill';
  public menu = [
    { name: 'Inicio', link: '/docente', icon: 'home' },
    { name: 'Questões', link: '/docente/questao', icon: 'info' },
    { name: 'Exames', link: '/docente/exame', icon: 'task' },
    { name: 'Exames Respondidos', link: '/docente/exames-respondidos', icon: 'task' },
  ];
  @ViewChild('myDialog') changePassword: TemplateRef<any>;

  constructor(
    private observer: BreakpointObserver,
    public router: Router,
    private authService: AuthService,
    public dialog: MatDialog) {}

  ngOnInit() {
    if (this.authService?.currentUser?.first_access === 'true') {
      let dialogRef = this.dialog.open(ChangePasswordComponent,  {width: '500px'});
      dialogRef.afterClosed().subscribe((result) => {
        if (result?.event === 'change-password') {
          this.authService.changePassword(this.authService.currentUser.email, result?.data);
        }
      });
    }
  }

  ngAfterViewInit() {
    this.observer.observe(['(max-width: 800px)']).subscribe((res) => {
      if (res.matches) {
        this.sidenav.mode = 'over';
        this.sidenav.close();
      } else {
        this.sidenav.mode = 'side';
        this.sidenav.open();
      }
    });
  }

  logout() {
    this.authService.doLogout();
  }
}
