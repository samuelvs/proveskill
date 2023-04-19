import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from 'src/services/user.service';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.scss']
})
export class UserComponent implements OnInit {

  userTypes: string[] = ["Estudant", "Professor"];
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

  }

}
