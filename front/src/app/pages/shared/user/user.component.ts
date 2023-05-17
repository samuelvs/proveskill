import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.scss']
})
export class UserComponent implements OnInit {

  userTypes ={
    ADMIN: "Administrador",
    TEACHER: "Docente",
    STUDENT: "Aluno"
  };
  users = [];
  hideToggle = false;
  userToEdit = {};

  constructor(private userService: UserService){}

  ngOnInit(): void {
    this.loadUsers();
  }

  loadUsers() {
    this.userService.getUsers().subscribe((data: any) => {
      this.users = data;
    }, rej => {
      this.userService._snackBar.open(
        `Houve algum erro, verifique as informações e tente novamente.`,
        '',
        {
          duration: 5000
        }
      );
    });
  }

  toggleUser(){
    this.hideToggle = !this.hideToggle;
  }

  edit(user) {
    this.hideToggle = true;
    this.userToEdit = { ...user };
  }

  clearUser() {
    this.userToEdit = {}
  }

  deleteUser(id) {
    this.userService.delete(id).subscribe(res => {
      this.loadUsers();
    }, rej => {
      this.userService._snackBar.open(
        `Houve algum erro, verifique as informações e tente novamente.`,
        '',
        {
          duration: 5000
        }
      );
    });
  }

}
