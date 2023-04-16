import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { User } from '../user';

@Component({
  selector: 'app-user-edit',
  templateUrl: './user-edit.component.html',
  styleUrls: ['./user-edit.component.scss']
})
export class UserEditComponent implements OnInit {

  title = 'Criar usuário';
  formUser: FormGroup;
  private routeSub: Subscription;

  constructor(private router: Router, private actRoute: ActivatedRoute) { }

  ngOnInit(): void {
    this.routeSub = this.actRoute.params.subscribe(params => {
      if(params['id']) {
        this.title = 'Editar usuário';
        this.createForm({name: 'asdf', email: 'qerq', school: 'kafd', type: 1});
      } else {
        this.createForm(new User);
      }
    });
  }

  ngOnDestroy() {
    this.routeSub.unsubscribe();
  }

  createForm(user: User) {
    this.formUser = new FormGroup({
      name: new FormControl(user.name, [Validators.required]),
      email: new FormControl(user.email, [Validators.required, Validators.email]),
      school: new FormControl(user.school, [Validators.required]),
      type: new FormControl(user.type, [Validators.required]),
    });
  }

  back() {
    this.router.navigate(['usuario']);
  }
}
