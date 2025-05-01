import { Component, OnInit } from '@angular/core';
import { ClientService } from 'src/app/services/client.service';
import html2canvas from 'html2canvas';
import jsPDF from 'jspdf';
import { Location } from '@angular/common';
import { Router } from '@angular/router';

@Component({
  selector: 'app-invoice',
  templateUrl: './invoice.component.html',
  styleUrls: ['./invoice.component.css']
})
export class InvoiceComponent implements OnInit {

  invoice: any;
  installmentArray: any[] = [];

  constructor(private clientService: ClientService, private location:Location,private router:Router) {}

  ngOnInit(): void {
    this.getInvoiceByClient();
  }

  private getInvoiceByClient(): void {
    const clientId = sessionStorage.getItem('clientId');
    if (clientId) {
      this.clientService.getInvoiceByClient(clientId).subscribe(
        (data: any) => {
          this.invoice = data.response;
          console.log('Invoice data:', this.invoice);
          this.installmentArray = this.invoice[0].installments;
        },
        (error) => {
          console.error('Error fetching invoice:', error);
        }
      );
    } else {
      console.error('Client ID not found in session storage');
    }
  }

  public downloadPDF(): void {
    const invoiceElement = document.querySelector('.invoice-container') as HTMLElement;
    if (invoiceElement) {
      html2canvas(invoiceElement).then(canvas => {
        const imgData = canvas.toDataURL('image/png');
        const pdf = new jsPDF('p', 'mm', 'a4');
        const imgProps = pdf.getImageProperties(imgData);
        const pdfWidth = pdf.internal.pageSize.getWidth();
        const pdfHeight = (imgProps.height * pdfWidth) / imgProps.width;
        pdf.addImage(imgData, 'PNG', 0, 0, pdfWidth, pdfHeight);
        pdf.save('invoice.pdf');
      });
    }
  }

  goBack(){
    this.router.navigate(['/homepage'])
  }
}
