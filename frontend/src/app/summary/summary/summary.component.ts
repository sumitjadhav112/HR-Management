import { Component, OnInit } from '@angular/core';
import { Location } from '@angular/common';
import { InstallmentService } from 'src/app/services/installment.service';
import { trigger, transition, style, animate, query, stagger } from '@angular/animations';

@Component({
  selector: 'app-summary',
  templateUrl: './summary.component.html',
  styleUrls: ['./summary.component.css'],
  animations: [
    trigger('tableAnimation', [
      transition(':enter', [
        query('tbody tr', [
          style({ opacity: 0, transform: 'translateY(-20px)' }),
          stagger(50, [
            animate('300ms ease-out', style({ opacity: 1, transform: 'translateY(0)' }))
          ])
        ], { optional: true })
      ])
    ]),
    trigger('modalAnimation', [
      transition(':enter', [
        style({ opacity: 0 }),
        animate('300ms ease-out', style({ opacity: 1 }))
      ]),
      transition(':leave', [
        animate('300ms ease-in', style({ opacity: 0 }))
      ])
    ]),
    trigger('listAnimation', [
      transition(':enter', [
        style({ opacity: 0, transform: 'translateY(-10px)' }),
        animate('200ms ease-out', style({ opacity: 1, transform: 'translateY(0)' }))
      ])
    ])
  ]
})
export class SummaryComponent implements OnInit {
  year: number = new Date().getFullYear();
  monthlySummary: any;
  monthlyDetails: any;
  selectedMonth: number | null = null;
  loading: boolean = false;
  error: string | null = null;
  showModal: boolean = false;

  constructor(private service: InstallmentService, private location: Location) {}

  ngOnInit() {
    this.getMonthlySummary();
  }

  goBack() {
    this.location.back();
  }

  getMonthlySummary() {
    this.loading = true;
    this.error = null;
    this.service.getMonthlySummary(this.year).subscribe(
      (response) => {
        this.monthlySummary = response.response;
        console.log(this.monthlySummary); 
        this.loading = false;
      },
      (error) => {
        this.error = 'An error occurred while fetching the summary.';
        this.loading = false;
      }
    );
  }

  onYearChange() {
    this.getMonthlySummary();
    this.monthlyDetails = null;
    this.selectedMonth = null;
    this.showModal = false;
  }

  getMonthName(monthNumber: number): string {
    const monthNames = [
      'January', 'February', 'March', 'April', 'May', 'June',
      'July', 'August', 'September', 'October', 'November', 'December'
    ];
    return monthNames[monthNumber - 1] || 'Unknown Month';
  }

  getMonthlyDetails(month: number) {
    this.loading = true;
    this.error = null;
    this.selectedMonth = month;
    this.service.getMonthlyDetails(this.year, month).subscribe(
      (response) => {
        this.monthlyDetails = response.response;
        console.log("Monthely details: ",this.monthlyDetails);
        
        this.loading = false;
        this.showModal = true;
      },
      (error) => {
        this.error = 'An error occurred while fetching the details.';
        this.loading = false;
      }
    );
  }
 
  closeModal() {
    this.showModal = false;
  }

  getTotalInvoices(): number {
    return this.monthlyDetails?.invoices?.reduce((total, invoice) => total + invoice.paidAmount, 0) || 0;
  }
  
  getTotalExpenses(): number {
    return this.monthlyDetails?.expenses?.reduce((total, expense) => total + expense.amount, 0) || 0;
  }
  
  getTotalRevenues(): number {
    return this.monthlyDetails?.revenues?.reduce((total, revenue) => total + revenue.amount, 0) || 0;
  }
}