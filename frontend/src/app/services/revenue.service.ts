import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class RevenueService {
  constructor(private http: HttpClient) {}

  private revenue = 'http://localhost:8088/management/v1/revenue';

  addRevenue(revenue: any) {
    console.log('revenue data in service ', revenue);
    return this.http.post(`${this.revenue}/saveOrUpdateRevenue`, revenue);
  }
  getAllRevenue() {
    return this.http.get(`${this.revenue}/getAllRevenue`);
  }
  deleteRevenue(id: number) {
    return this.http.delete(`${this.revenue}/deleteRevenue/${id}`);
  }
}
