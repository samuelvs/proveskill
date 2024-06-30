import { Component, ViewChild } from '@angular/core';
import { BreakpointObserver } from '@angular/cdk/layout';
import { MatSidenav } from '@angular/material/sidenav';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';
import {MatDialog} from '@angular/material/dialog';
import { ChangePasswordComponent } from 'src/app/components/change-password/change-password.component';


@Component({
  selector: 'admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.scss']
})

export class AdminComponent {
  @ViewChild(MatSidenav)
  sidenav!: MatSidenav;

  title = 'ProveSkill';
  public menu = [
    { name: 'Inicio', link: '/admin', icon: 'home' },
    { name: 'Usuários', link: '/admin/usuario', icon: 'person' },
    { name: 'Questões', link: '/admin/questao', icon: 'info' },
    // { name: 'Exames', link: '/admin/exame', icon: 'task' },
  ];
  constructor(private observer: BreakpointObserver,
    public router: Router,
    private authService: AuthService,
    public dialog: MatDialog) {}

  ngOnInit() {
    if (this.authService?.currentUser?.firstAccess === 'true') {
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
