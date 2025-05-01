import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SalaryReceptComponent } from './salary-recept.component';

describe('SalaryReceptComponent', () => {
  let component: SalaryReceptComponent;
  let fixture: ComponentFixture<SalaryReceptComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SalaryReceptComponent]
    });
    fixture = TestBed.createComponent(SalaryReceptComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
