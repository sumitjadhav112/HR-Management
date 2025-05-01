import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ClientService {

  constructor(private http:HttpClient) { }
 private getAllClientURL = 'http://localhost:8088/management/v1/client/getAllClients'
 private getInvoiceByClientId = 'http://localhost:8088/management/v1/invoice/getinvoiceByClient/'

 private updateClient = 'http://localhost:8088/management/v1/client/addOrUpdateClient';

  getAllClients(){
  return this.http.get(this.getAllClientURL)
 }

 getInvoiceByClient(clientId:any){
  return this.http.get(this.getInvoiceByClientId+clientId)
 }
 updateClientData(clientData: any): Observable<any> {
  return this.http.post(`${this.updateClient}`,clientData);
}


}
