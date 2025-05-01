import { Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { MessageService } from 'primeng/api';
import { RevenueService } from 'src/app/services/revenue.service';

interface Revenue {
  id?: number;
  amount: number;
  date: Date | null;
  description: string;
}

@Component({
  selector: 'app-revenue',
  templateUrl: './revenue.component.html',
  styleUrls: ['./revenue.component.css'],
})
export class RevenueComponent implements OnInit {
  revenueArray: Revenue[] = [];
  displayAddRevenueModal: boolean = false;
  displayEditRevenueModal: boolean = false;
  revenueColors: string[] = [];
  newRevenue: Revenue = { amount: 0, date: null, description: '' };
  editingRevenue: Revenue = { amount: 0, date: null, description: '' };
  displayDeleteModal: boolean = false;
  deletingRevenue: Revenue = { id: 0, amount: 0, date: null, description: '' };

  constructor(
    private revenueService: RevenueService,
    private messageService: MessageService,
    private location: Location
  ) {}

  ngOnInit() {
    this.loadRevenues();
  }

  goBack(): void {
    this.location.back();
  }

  loadRevenues() {
    this.revenueService.getAllRevenue().subscribe((data: any) => {
      this.revenueArray = data.response;
      this.generateRevenueColors();
    });
  }

  generateRevenueColors() {
    const colors = [
      '#3498db',
      '#2ecc71',
      '#e74c3c',
      '#f39c12',
      '#9b59b6',
      '#1abc9c',
    ];
    this.revenueColors = this.revenueArray.map(() =>
      this.getRandomColor(colors)
    );
  }

  confirmDelete() {
    if (this.deletingRevenue.id) {
      console.log(this.deletingRevenue.id);

      this.revenueService.deleteRevenue(this.deletingRevenue.id).subscribe(
        (data: any) => {
          console.log(data);
          
          if (data.message === 'revenue deleted successfully.') {
            this.messageService.add({
              severity: 'success',
              summary: 'Success',
              detail: 'Revenue deleted successfully',
              life: 3000,
            });
            this.closeDeleteModal();
            this.loadRevenues();
          } else {
            this.messageService.add({
              severity: 'error',
              summary: 'Error',
              detail: 'Revenue not deleted',
              life: 3000,
            });
          }
        },
        (error) => {
          console.error('Error deleting revenue:', error);
          this.messageService.add({
            severity: 'error',
            summary: 'Error',
            detail: 'An error occurred while deleting revenue',
            life: 3000,
          });
        }
      );
    }
  }

  getRandomColor(colors: string[]): string {
    return colors[Math.floor(Math.random() * colors.length)];
  }

  openAddRevenueModal() {
    this.displayAddRevenueModal = true;
  }

  closeAddRevenueModal() {
    this.displayAddRevenueModal = false;
    this.resetNewRevenue();
  }

  saveNewRevenue() {
    this.revenueService.addRevenue(this.newRevenue).subscribe(
      (data: any) => {
        console.log('revenue response for add:', data);
        if (data.message === 'Revenue Added.') {
          this.messageService.add({
            severity: 'success',
            summary: 'Success',
            detail: 'Revenue added successfully',
            life: 2000,
          });
          this.closeAddRevenueModal();
          this.loadRevenues();
        } else {
          this.messageService.add({
            severity: 'error',
            summary: 'Error',
            detail: 'Revenue not added',
            life: 3000,
          });
        }
      },
      (error) => {
        console.error('Error adding revenue:', error);
        this.messageService.add({
          severity: 'error',
          summary: 'Error',
          detail: 'An error occurred while adding revenue',
          life: 3000,
        });
      }
    );
  }

  openEditRevenueModal(revenue: Revenue) {
    this.editingRevenue = { ...revenue };
    this.displayEditRevenueModal = true;
  }

  closeEditRevenueModal() {
    this.displayEditRevenueModal = false;
    this.editingRevenue = { amount: 0, date: null, description: '' };
  }

  saveEditedRevenue() {
    if (this.editingRevenue.id) {
      this.revenueService.addRevenue(this.editingRevenue).subscribe(
        (data: any) => {
          console.log('revenue response for edit:', data);
          if (data.message === 'revenue Updated.') {
            this.messageService.add({
              severity: 'success',
              summary: 'Success',
              detail: 'Revenue updated successfully',
              life: 3000,
            });
            this.closeEditRevenueModal();
            this.loadRevenues();
          } else {
            this.messageService.add({
              severity: 'error',
              summary: 'Error',
              detail: 'Revenue not updated',
              life: 3000,
            });
          }
        },
        (error) => {
          console.error('Error updating revenue:', error);
          this.messageService.add({
            severity: 'error',
            summary: 'Error',
            detail: 'An error occurred while updating revenue',
            life: 3000,
          });
        }
      );
    }
  }

  openDeleteConfirmation(revenue: Revenue) {
    this.deletingRevenue = { ...revenue };
    this.displayDeleteModal = true;
  }

  closeDeleteModal() {
    this.displayDeleteModal = false;
  }

  resetNewRevenue() {
    this.newRevenue = { amount: 0, date: null, description: '' };
  }

  getTotalRevenue(): number {
    if (!this.revenueArray || this.revenueArray.length === 0) return 0;
    return this.revenueArray.reduce((total, revenue) => total + revenue.amount, 0);
  }

  getAverageRevenue(): number {
    if (!this.revenueArray || this.revenueArray.length === 0) return 0;
    return this.getTotalRevenue() / this.revenueArray.length;
  }
}
