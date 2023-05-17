import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AnswerExamComponent } from './answer-exam.component';

describe('AnswerExamComponent', () => {
  let component: AnswerExamComponent;
  let fixture: ComponentFixture<AnswerExamComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AnswerExamComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AnswerExamComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
