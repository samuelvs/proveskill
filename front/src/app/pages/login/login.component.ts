import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { ForgotPasswordComponent } from 'src/app/components/forgot-password/forgot-password.component';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  signinForm: FormGroup;

  constructor(
    public fb: FormBuilder,
    public authService: AuthService,
    public router: Router,
    public dialog: MatDialog
  ) {
    this.signinForm = this.fb.group({
      email: [''],
      password: [''],
    });
  }

  ngOnInit() {}

  loginUser() {
    this.authService.signIn(this.signinForm.value.email, this.signinForm.value.password);
  }

  forgotPassword() {
    let dialogRef = this.dialog.open(ForgotPasswordComponent,  {width: '500px'});
    dialogRef.afterClosed().subscribe((result) => {
      if (result?.event === 'forgot-password') {
        this.authService.changePassword(result?.data?.email, result?.data?.password);
      }
    });
  }
}
