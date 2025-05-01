import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class EmployeeService {

  constructor(private http: HttpClient) {}

  private apiUrl = `http://localhost:8088/management/v1/employees`;


  addOrUpdateEmployee(employee:any) {
    return this.http.post(`${this.apiUrl}/addOrUpdateEmployee`, employee);
  }

  getAllEmployees() {
    return this.http.get(`${this.apiUrl}/getAllActiveEmployees`);
  }

  getEmployeeById(id: number) {
    return this.http.get(`${this.apiUrl}/getEmployeeById/${id}`);
  }

  deleteEmployee(id: number) {
    return this.http.delete(`${this.apiUrl}/deleteEmployee/${id}`);
  }
  getAllLeaves(empId: number) {
    console.log("get leaves works",empId);
    return this.http.get(`${this.apiUrl}/getAllLeavesByEmpId/`+empId);
  } 

  addLeave(employeeId: number, leave: any) {
    return this.http.post(`${this.apiUrl}/addLeaves/${employeeId}`, leave);
  }

  calculateSalary(employeeId: number, salaryRequest: { date: string }): Observable<any> {
    return this.http.post(`${this.apiUrl}/calculateSalary/${employeeId}`, salaryRequest);
  }

  getEmpSummary(employeeId:any , salaryRequest : {date:string}){
    return this.http.post(`${this.apiUrl}/calculateEmployeeSummary/${employeeId}`,salaryRequest);
  }

  // addAdvanceForEmp(empId:any,advnceAmount:{advanceAmount:string}){
  //   console.log("service data : ",advnceAmount);
  //   return this.http.post(`${this.apiUrl}/payAdvance/${empId}`,advnceAmount)
  // }
  addAdvanceForEmp(empId: any, advnceDetails: { advanceAmount: string, month: string }) {
    console.log("service data: ", advnceDetails);
    return this.http.post(`${this.apiUrl}/payAdvance/${empId}`, advnceDetails);
  }
  
}
