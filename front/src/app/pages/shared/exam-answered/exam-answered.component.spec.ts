import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ExamAnsweredComponent } from './exam-answered.component';

describe('ExamAnsweredComponent', () => {
  let component: ExamAnsweredComponent;
  let fixture: ComponentFixture<ExamAnsweredComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ExamAnsweredComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ExamAnsweredComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
