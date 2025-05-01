import { Component, OnInit } from '@angular/core';
import { Intern } from 'src/app/Model/Intern';
import { InternService } from 'src/app/services/intern.service';
import { MessageService } from 'primeng/api';
import { CalendarModule } from 'primeng/calendar';
import { FormsModule } from '@angular/forms';
import { Location } from '@angular/common';

@Component({
  selector: 'app-intern-student',
  templateUrl: './intern-student.component.html',
  styleUrls: ['./intern-student.component.css'],
  providers: [MessageService],
})
export class InternStudentComponent implements OnInit {
  interns: Intern[] = [];
  filteredInterns: Intern[] = [];
  newIntern: Intern = {
    name: '',
    time_period: '',
    total_Amount: 0,
    startDate: '',
    paidAmount: 0,
    status: true,
  };
  isEdit: boolean = false;
  displayDialog: boolean = false;
  date: any;
  searchTerm: string = '';
  showConfirmation: boolean = false;
  internToDeactivate: number | null = null;

  constructor(
    private internService: InternService,
    private messageService: MessageService,
    private location: Location
  ) {}

  ngOnInit(): void {
    this.loadInterns();
  }

  goBack() {
    this.location.back();
  }

  loadInterns(): void {
    this.internService.getAllInterns().subscribe(
      (data: any) => {
        this.interns = data.response;
        this.filteredInterns = [...this.interns];
      },
      (error) => {
        this.messageService.add({
          severity: 'error',
          summary: 'Error',
          detail: 'Failed to load interns',
        });
      }
    );
  }

  saveIntern(): void {
    if (this.isEdit) {
      this.updateIntern();
    } else {
      this.addIntern();
    }
  }

  addIntern(): void {
    const internToAdd = { ...this.newIntern };
    delete internToAdd.id;

    this.internService.addOrUpdateIntern(internToAdd).subscribe(
      (data: any) => {
        this.loadInterns();
        this.hideDialog();
        this.messageService.add({
          severity: 'success',
          summary: 'Success',
          detail: 'Intern added successfully',
        });
      },
      (error) => {
        this.messageService.add({
          severity: 'error',
          summary: 'Error',
          detail: 'Failed to add intern',
        });
      }
    );
  }

  updateIntern(): void {
    this.internService.addOrUpdateIntern(this.newIntern).subscribe(
      (data: any) => {
        this.loadInterns();
        this.hideDialog();
        this.messageService.add({
          severity: 'success',
          summary: 'Success',
          detail: 'Intern updated successfully',
        });
      },
      (error) => {
        this.messageService.add({
          severity: 'error',
          summary: 'Error',
          detail: 'Failed to update intern',
        });
      }
    );
  }

  editIntern(intern: Intern): void {
    this.newIntern = { ...intern };
    this.isEdit = true;
    this.displayDialog = true;
  }

  confirmDeactivation(id: number): void {
    this.internToDeactivate = id;
    this.showConfirmation = true;
  }

  cancelDeactivation(): void {
    this.showConfirmation = false;
    this.internToDeactivate = null;
  }

  proceedDeactivation(): void {
    if (this.internToDeactivate) {
      this.deactivateIntern(this.internToDeactivate);
      this.showConfirmation = false;
      this.internToDeactivate = null;
    }
  }

  deactivateIntern(id: number): void {
    const intern = this.interns.find(i => i.id === id);
    if (intern) {
      intern.status = false;
      this.internService.addOrUpdateIntern(intern).subscribe(
        (data: any) => {
          this.loadInterns();
          this.messageService.add({
            severity: 'success',
            summary: 'Success',
            detail: 'Intern deactivated successfully',
          });
        },
        (error) => {
          this.messageService.add({
            severity: 'error',
            summary: 'Error',
            detail: 'Failed to deactivate intern',
          });
        }
      );
    }
  }

  showDialog(): void {
    this.isEdit = false;
    this.newIntern = {
      name: '',
      time_period: '',
      total_Amount: 0,
      startDate: '',
      paidAmount: 0,
      status: true,
    };
    this.displayDialog = true;
  }

  hideDialog(): void {
    this.displayDialog = false;
  }

  searchInterns() {
    if (!this.searchTerm.trim()) {
      this.filteredInterns = [...this.interns];
    } else {
      this.filteredInterns = this.interns.filter(intern =>
        intern.name.toLowerCase().includes(this.searchTerm.toLowerCase()) ||
        intern.time_period.toLowerCase().includes(this.searchTerm.toLowerCase()) ||
        intern.total_Amount.toString().includes(this.searchTerm) ||
        intern.startDate.includes(this.searchTerm) ||
        intern.paidAmount.toString().includes(this.searchTerm) ||
        (intern.status ? 'active' : 'inactive').includes(this.searchTerm.toLowerCase())
      );
    }
  }
}