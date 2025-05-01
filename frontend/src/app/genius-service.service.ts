import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {
  LoginObjClass,
  RegistreClientObjClass,
  getResponse,
} from './Model/clientModel';

@Injectable({
  providedIn: 'root',
})
export class GeniusServiceService {
  Client_Register_Url =
    'http://localhost:8088/management/v1/client/addOrUpdateClient';
  Login_Url = 'http://localhost:8088/management/v1/user/login';
  private expense = 'http://localhost:8088/management/v1/expense';

   private notificationURL = 'http://localhost:8088/management/v1/notifications'

  constructor(private http: HttpClient) {}

  Client_Register_Fun(RegistreClientObj: RegistreClientObjClass) {
    return this.http.post<getResponse>(
      this.Client_Register_Url,
      RegistreClientObj
    );
  }
  Login_Fun(RegistreClientObj: LoginObjClass) {
    console.log('service login data', RegistreClientObj);

    return this.http.post<getResponse>(this.Login_Url, RegistreClientObj);
  }

  addExpense(expense: any) {
    return this.http.post(`${this.expense}/addExpenses`, expense);
  }

  getAllExpense(){
    return this.http.get(`${this.expense}/getAllExpense`);
  }

  editExpense(expense:any){
    return this.http.post(`${this.expense}/updateExpense`,expense);
  }

  getNotifications(){
    return this.http.get(`${this.notificationURL}/getAllNotifications`);
  }

  markAsViewed(id: any) {
    console.log('id for mark as viewed:', id);
    return this.http.post(`${this.notificationURL}/read/${id}`, {});
  }
}
