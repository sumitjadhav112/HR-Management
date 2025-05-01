import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class InstallmentService {

 

  private baseUrl = 'http://localhost:8088/management/v1/installment';

  private clientURL =  'http://localhost:8088/management/v1/client/getClientById/';

  private getInstallmentByIdURL = 'http://localhost:8088/management/v1/installment/getAllInstallmentForClients/'

  private invoiceUrl = 'http://localhost:8088/management/v1/invoice/generate/'

  private markAsPaidUrl = 'http://localhost:8088/management/v1/installment/markAsPaid';

  private editInstallment = 'http://localhost:8088/management/v1/installment/editInstallment';


  constructor(private http: HttpClient) { }
  
  createInstallment(installment: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/createInstallment`, installment);
  }

  getClientDetails(clientId: number){
    console.log("client id in service",clientId);
    
    return this.http.get(this.clientURL+clientId);
  } 
  viewClientInstallment(clientId:any){
    console.log("client id in service for installment :",clientId);
    return this.http.get(this.getInstallmentByIdURL+clientId)
   }
  generateInvoice(clientId: any) {
    return this.http.get(this.invoiceUrl + clientId);
  }

  markPaid(installmentId: number, status: string): Observable<any> {
    return this.http.post(this.markAsPaidUrl, { installment_id: installmentId, status });
  }

  edit(installment:any){
    return this.http.post(this.editInstallment,installment);
  }

  private apiUrl = 'http://localhost:8088/invoiceGenerator/v1/financial';

  getMonthlySummary(year: number): Observable<any> {
    return this.http.get(`${this.apiUrl}/monthly-summary`, {
      params: { year: year.toString() }
    });
  }

  getMonthlyDetails(year: number, month: number): Observable<any> {
    return this.http.get(`${this.apiUrl}/monthly-details`, {
      params: { year: year.toString(), month: month.toString() }
    });
  }


}
