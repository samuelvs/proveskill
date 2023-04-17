import { Component, EventEmitter, Input, OnInit, Output, SimpleChange } from '@angular/core';
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

  @Input() user?;
  @Output() clear = new EventEmitter();

  formUser: FormGroup;

  constructor(private router: Router, private actRoute: ActivatedRoute) { }

  ngOnInit(): void {
    const user = new User;
    this.formUser = new FormGroup({
      id: new FormControl(user.id, [Validators.required]),
      name: new FormControl(user.name, [Validators.required]),
      email: new FormControl(user.email, [Validators.required, Validators.email]),
      school: new FormControl(user.school, [Validators.required]),
      type: new FormControl(user.type, [Validators.required]),
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
}
