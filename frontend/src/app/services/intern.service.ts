import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class InternService {

  private baseUrl = 'http://localhost:8088/management/v1/interns';

  constructor(private http:HttpClient) { }

  getAllInterns() {
    return this.http.get(`${this.baseUrl}/getAllInterns`);
  }

  addOrUpdateIntern(intern: any) {
    return this.http.post(`${this.baseUrl}/saveOrUpdateIntern`, intern);
  }

  deleteIntern(id: number) {
    return this.http.delete(`${this.baseUrl}/deleteById/${id}`);
  }
}
