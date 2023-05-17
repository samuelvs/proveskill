import { Component, EventEmitter, Input, OnInit, Output, SimpleChange } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { User } from '../user';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-user-edit',
  templateUrl: './user-edit.component.html',
  styleUrls: ['./user-edit.component.scss']
})
export class UserEditComponent implements OnInit {

  @Input() user?;
  @Output() clear = new EventEmitter();
  @Output() submit = new EventEmitter();

  formUser: FormGroup;

  constructor(private userService: UserService) { }

  ngOnInit(): void {
    const user = new User;
    this.formUser = new FormGroup({
      id: new FormControl(user.id, [Validators.required]),
      name: new FormControl(user.name, [Validators.required]),
      email: new FormControl(user.email, [Validators.required, Validators.email]),
      school: new FormControl(user.school, [Validators.required]),
      role: new FormControl(user.type, [Validators.required]),
    });
  }

  ngOnChanges(change) {
    if (Object.keys(change.user.currentValue).length > 0) {
      this.formUser.patchValue(change.user.currentValue);
    }
  }

  clearUser() {
    this.formUser.reset();
    this.clear.emit();
  }

  save() {
    this.userService.putUsers(this.formUser.value).subscribe((res: any) => {
      this.formUser.reset();
      this.submit.emit();
      if (res?.password) {
        this.userService._snackBar.open(`Usuário cadastrado com a senha: ${res?.password}`, '', {duration:10000});
      }
    });
  }
}
