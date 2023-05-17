import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';

export interface DialogData {}

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrls: ['./forgot-password.component.scss']
})

export class ForgotPasswordComponent implements OnInit {

  constructor(public dialogRef: MatDialogRef<ForgotPasswordComponent>, @Inject(MAT_DIALOG_DATA) public data: DialogData) {}

  ngOnInit(): void {
  }

  cancel(){
    this.dialogRef.close({event:"cancel"});
  }

  closeDialog(){
    const mail = document.getElementById('mail') as HTMLInputElement;;
    const password = document.getElementById('password') as HTMLInputElement;;
    this.dialogRef.close({event:'forgot-password', data: { email: mail?.value, password: password?.value}});
  }

}
