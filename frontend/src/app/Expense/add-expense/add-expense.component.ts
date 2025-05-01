import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { GeniusServiceService } from 'src/app/genius-service.service';

@Component({
  selector: 'app-add-expense',
  templateUrl: './add-expense.component.html',
  styleUrls: ['./add-expense.component.css']
})
export class AddExpenseComponent {

  expenseForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    private toastr: ToastrService,
    private geniusService: GeniusServiceService
  ) {
    this.expenseForm = this.fb.group({
      type: ['', Validators.required],
      amount: ['', [Validators.required, Validators.min(0.01)]],
      date: ['', Validators.required]
    });
  }

  ngOnInit(): void {}

  onSubmit(): void {
    if (this.expenseForm.valid) {
      this.geniusService.addExpense(this.expenseForm.value).subscribe(
        (res: any) => {
          this.toastr.success('Expense added successfully');
          console.log("added expance",res);
          
          this.expenseForm.reset();
        },
        (error) => {
          this.toastr.error('Error adding expense');
        }
      );
    }
  }

}
