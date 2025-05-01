import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InternStudentComponent } from './intern-student.component';

describe('InternStudentComponent', () => {
  let component: InternStudentComponent;
  let fixture: ComponentFixture<InternStudentComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [InternStudentComponent]
    });
    fixture = TestBed.createComponent(InternStudentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
