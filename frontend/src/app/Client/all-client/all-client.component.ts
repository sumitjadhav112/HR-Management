import { DatePipe, Location } from '@angular/common';
import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { ConfirmationService, MessageService } from 'primeng/api';
import { ClientService } from 'src/app/services/client.service';
import { InstallmentService } from 'src/app/services/installment.service';

@Component({
  selector: 'app-all-client',
  templateUrl: './all-client.component.html',
  styleUrls: ['./all-client.component.css'],
})
export class AllClientComponent implements OnInit {
  clientsArray: any[] = [];
  filteredClients: any[] = [];
  clientColors: { [key: number]: string } = {};
  searchTerm: string = '';
  installmentArray: any[] = [];
  sidebarVisible: boolean = false;
  editInstallmentData: any = {};
  editInstallmentVisible: boolean = false;
  isLoading: boolean = false;
  errorMessage: string | null = null;
  projectAmount: any;
  visible: boolean = false;
  editClientData: any = {};
  editClientVisible: boolean = false;
  anyInstallmentPaid:boolean = false;
  clientId: any;
  installment_clientid:number;
  startDateString: string;
  timelineString: string;
  
  navigateToAddInstallment() {
    // console.log("client id in navigate router : ",this.clientId);
    const id = sessionStorage.getItem('clientId')
      this.router.navigate(['/installments', id]);
  }

  


  goBack() {
    this.location.back();
  }

  constructor(
    private clientService: ClientService,
    private installmentService: InstallmentService,
    private location: Location,
    private cdRef: ChangeDetectorRef,
    private confirmationService: ConfirmationService,
    private messageService: MessageService,
    private toastr: ToastrService,
    private router:Router,
    private datePipe: DatePipe


  ) {}

  ngOnInit(): void {
    this.allClients();
  }


  saveEditedClient() {
    this.clientService.updateClientData(this.editClientData).subscribe(
      (response: any) => {
        console.log('Client updated successfully:', response);
        this.messageService.add({
          severity: 'success',
          summary: 'Success',
          detail: 'Client updated successfully',
          life: 3000,
        });
        this.editClientVisible = false;
        this.allClients(); // Refresh the client list
      },
      (error) => {
        console.error('Error updating client:', error);
        this.messageService.add({
          severity: 'error',
          summary: 'Error',
          detail: 'Failed to update client: ' + (error.message || 'Unknown error'),
          life: 5000,
        });
      }
    );
  }

  viewInvoiceForClient(client_id:any) {
    sessionStorage.setItem("clientId",client_id);
    this.router.navigate(['/invoice']);
  }


  generateInvoice() {
    this.installmentService
      .generateInvoice(this.clientId)
      .subscribe(
        (data) => {
        },
        (error) => {
          this.toastr.error('Error generating invoice');
        }
      );
  }

  allClients() {
    this.clientService.getAllClients().subscribe((data: any) => {
      this.clientsArray = data.response;
      this.filteredClients = [...this.clientsArray];
      this.generateClientColors();
      this.clientsArray.forEach(client => {
        this.installmentService.viewClientInstallment(client.client_id).subscribe(
          (installmentData: any) => {
            client.installments = installmentData.response || [];
            this.cdRef.detectChanges(); // Trigger change detection
          },
          (error) => {
            console.error(`Error fetching installments for client ${client.client_id}:`, error);
            client.installments = [];
          }
        );
      });
  
      console.log('array data :', this.clientsArray);
      console.log('array data :', this.clientsArray);
    });
    (error) => {
      console.error('Failed to load clients', error);
      this.clientsArray = [];  // Fallback to an empty array
    }
  }
 
  getClientStatusClass(client: any): { [key: string]: boolean } {
    const allPaid = this.isAllInstallmentsPaid(client);
    return {
      'all-paid': allPaid,
      'pending': !allPaid
    };
  }
  getClientStatusText(client: any): string {
    return this.isAllInstallmentsPaid(client) ? 'ALL PAID' : 'PENDING';
  }

  isAllInstallmentsPaid(client: any): boolean {
    return client.installments && 
           client.installments.length > 0 && 
           client.installments.every((installment: any) => installment.status === 'PAID');
  }

  viewInstallments(client: any) {
    sessionStorage.setItem('clientId',client.client_id);
    console.log('View installments for client:', client);
    this.installment_clientid = client.client_id;
    this.clientId = client.client_id;
    this.projectAmount = client.totalAmount-client.total_discount; // Set the project amount
    this.installmentService
      .viewClientInstallment(client.client_id)
      .subscribe(
        (data:any) => {
          console.log('data', data);
          this.installmentArray = data.response || []; // Ensure installmentArray is an array
          console.log('array response', this.installmentArray);
          this.visible = true;
        },
        (error) => {
          console.error('Error fetching installments:', error);
          this.installmentArray = []; // Ensure installmentArray is an array
          this.visible = true;
        }
      );
  }

  get totalAmount(): number {
    return this.installmentArray.reduce(
      (total, installment) => total + installment.amount,
      0
    );
  }

  getInitials(name: string): string {
    return name
      .split(' ')
      .map((n) => n[0])
      .join('')
      .toUpperCase();
  }

  getClientColor(clientId: number): string {
    return this.clientColors[clientId] || '#000000';
  }

  private generateClientColors() {
    this.clientsArray.forEach((client) => {
      this.clientColors[client.client_id] = this.getRandomColor();
    });
  }

  private getRandomColor(): string {
    const letters = '0123456789ABCDEF';
    let color = '#';
    for (let i = 0; i < 6; i++) {
      color += letters[Math.floor(Math.random() * 16)];
    }
    return color;
  }

  // searchClients() {
  //   if (!this.searchTerm.trim()) {
  //     this.filteredClients = [...this.clientsArray];
  //   } else {
  //     const searchTermLower = this.searchTerm.toLowerCase().trim();
  //     this.filteredClients = this.clientsArray.filter(
  //       (client) =>
  //         client.client_name.toLowerCase().includes(searchTermLower) ||
  //         client.clinet_email.toLowerCase().includes(searchTermLower) ||
  //         client.projectDetails.toLowerCase().includes(searchTermLower) ||
  //         client.mobile_number.toLowerCase().includes(searchTermLower)
  //     );
  //   }
  // }

  searchClients() {
    if (!this.searchTerm.trim()) {
      this.filteredClients = [...this.clientsArray];
    } else {
      const searchTermLower = this.searchTerm.toLowerCase().trim();
      this.filteredClients = this.clientsArray.filter((client) => {
        return (
          (client.client_name && client.client_name.toLowerCase().includes(searchTermLower)) ||
          (client.client_email && client.client_email.toLowerCase().includes(searchTermLower)) ||
          (client.projectDetails && client.projectDetails.toLowerCase().includes(searchTermLower)) ||
          (client.mobile_number && client.mobile_number.toString().toLowerCase().includes(searchTermLower))
        );
      });
    }
  }

  markAsPaid(installmentId: number, status: string): void {
    this.confirmationService.confirm({
      message: 'Are you sure you want to change the status of this installment?',
      accept: () => {
        this.installmentService.markPaid(installmentId, status).subscribe(
          (response) => {
            if (response.message === 'Null data not applicable') {
              this.messageService.add({
                severity: 'warning',
                summary: 'Warning',
                detail: 'Some error occurred',
              });
            } else {
              this.anyInstallmentPaid = true;
              console.log('Installment status changed:', response);
              this.messageService.add({
                severity: 'success',
                summary: 'Success',
                detail: 'Installment status updated',
              });
              const installment = this.installmentArray.find(
                (i) => i.installment_id === installmentId
              );
              if (installment) {
                installment.status = status;
              }
              this.cdRef.detectChanges();
            }
          },
          (error) => {
            console.error('Error changing installment status:', error);
            const installment = this.installmentArray.find(
              (i) => i.installment_id === installmentId
            );
            if (installment) {
              installment.status = status === 'PAID' ? 'PENDING' : 'PAID';
            }
            this.cdRef.detectChanges();
          }
        );
      },
      reject: () => {
        const installment = this.installmentArray.find(
          (i) => i.installment_id === installmentId
        );
        if (installment) {
          installment.status = status === 'PAID' ? 'PENDING' : 'PAID';
        }
        this.cdRef.detectChanges();
      },
    });
  }

  editInstallment(installment: any) {
    this.editInstallmentData = {
      installment_id: installment.installment_id,
      amount: installment.amount,
      client_id: installment.client_id,
      dueDate: installment.dueDate,
    };
    this.editInstallmentVisible = true;
  }

  saveEditedInstallment() {
    this.installmentService.edit(this.editInstallmentData).subscribe(
      (response: any) => {
        if (response.message === 'Installment amount exceeds remaining total amount') {
          this.messageService.add({
            severity: 'error',
            summary: 'Error',
            detail: 'Installment amount exceeds remaining total amount',
          });
        } else {
          console.log('Installment updated successfully:', response);
          this.messageService.add({
            severity: 'success',
            summary: 'Success',
            detail: 'Installment updated successfully',
            life: 3000,
          });
          this.editInstallmentVisible = false;
          this.viewInstallments({ client_id: this.editInstallmentData.client_id });
        }
      },
      (error) => {
        console.error('Error updating installment:', error);
        this.messageService.add({
          severity: 'error',
          summary: 'Error',
          detail: 'Failed to update installment: ' + (error.message || 'Unknown error'),
          life: 5000,
        });
      }
    );
  }
  checkAndCloseDialog() {
    if (this.totalAmount === this.projectAmount) {
      this.visible = false;
    } else {
      this.messageService.add({
        severity: 'error',
        summary: 'Error',
        detail: 'The total installment amount does not match the project total amount.',
        life: 5000,
      });
    }
  }

  editClient(client: any) {
    console.log('Editing client:', client);
    this.editClientData = { ...client }; // Create a copy of the client data
    this.editClientVisible = true;
 
  }
}
