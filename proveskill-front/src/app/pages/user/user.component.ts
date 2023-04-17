import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.scss']
})
export class UserComponent implements OnInit {

  userTypes: string[] = ["Estudant", "Professor"];
  users = [
    {
      id: 1,
      name: "João da Silva",
      email: "joao@gmail.com",
      school: "Instituto Federal de Alagoas",
      type: 1
    },
    {
      id: 2,
      name: "Gabriel Ferr",
      email: "joao@gmail.com",
      school: "Instituto Federal de Alagoas",
      type: 1
    }
  ];
  hideToggle = false;
  userToEdit = {};

  constructor(public router: Router){}

  ngOnInit(): void {
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

  }

}
