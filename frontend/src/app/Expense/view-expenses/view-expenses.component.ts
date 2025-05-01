import { DatePipe, Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { MessageService } from 'primeng/api';
import { DialogService, DynamicDialogRef } from 'primeng/dynamicdialog';
import { GeniusServiceService } from 'src/app/genius-service.service';

@Component({
  selector: 'app-view-expenses',
  templateUrl: './view-expenses.component.html',
  styleUrls: ['./view-expenses.component.css'],
})
export class ViewExpensesComponent implements OnInit {
  constructor(
    private geniusServ: GeniusServiceService,
    private toastr: ToastrService,
    private datePipe: DatePipe,
    private location: Location,



  ) {}

  goBack() {
    this.location.back();
  }

  ngOnInit(): void {
    this.getAllExpense();
    this.expenseArray;
  }

  expenseArray: any[] = [];
  displayDialog: boolean = false;
  selectedExpense: any = {};


  getAllExpense() {
    this.geniusServ.getAllExpense().subscribe((data: any) => {
      console.log('data of the all expanses', data);
      this.expenseArray = data.response;
    });
  }

  editExpense(expense: any) {
    this.selectedExpense = { ...expense };
    this.selectedExpense.date = this.datePipe.transform(this.selectedExpense.date, 'yyyy-MM-dd');
    this.displayDialog = true;
  }
 
  onSave() {
    this.selectedExpense.date = new Date(this.selectedExpense.date);
    this.geniusServ.editExpense(this.selectedExpense).subscribe(
      (updatedExpense: any) => {
        console.log(updatedExpense);
        this.toastr.success('Expense Updated');
        const index = this.expenseArray.findIndex(e => e.id === updatedExpense.id);
        if (index !== -1) {
          this.expenseArray[index] = updatedExpense;
        }
        this.displayDialog = false;
      },
      (error) => {
        console.error('Error updating expense:', error);
        this.toastr.error('Error while Updating..!');

      }
    );
  }

  onCancel() {
    this.displayDialog = false;
  }
}
