import { HttpClient, HttpHeaders } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Bank } from './bank';
import { Employee } from './employee';

@Injectable({
  providedIn: 'root'
})
export class AdminService {

  private http = inject(HttpClient);
  private url:string = 'http://localhost:8080/api/v1';

  private getHeaders(): HttpHeaders{
    let headers = new HttpHeaders();
    const authToken = localStorage.getItem('token');
    headers = headers.set('Authorization', 'Bearer ' + authToken);
    return headers;
  }

  //Bank Details
  getAllBranches(): Observable<Bank[]>{
    return this.http.get<Bank[]>(this.url + '/banks', {headers: this.getHeaders()});
  }

  addNewBank(bank:Bank): Observable<Bank>{
    return this.http.post<Bank>(this.url + '/banks', bank, {headers: this.getHeaders()});
  }
  
  //Employee Details
  getAllEmployees(): Observable<Employee[]>{
    return this.http.get<Employee[]>(this.url + '/employees', {headers: this.getHeaders()});
  }

  addNewEmployee(employee:Employee): Observable<Object>{
    return this.http.post(this.url + '/employees', employee, {headers: this.getHeaders()});
  }

  viewEmployeeById(id:number): Observable<Employee>{
    return this.http.get<Employee>(this.url + '/employees/' + id, {headers: this.getHeaders()});
  }

  updateEmployeeById(id:number, employee:Employee): Observable<Object>{
    return this.http.put(this.url + '/employees/' + id, employee, {headers: this.getHeaders()});
  }

  approveEmployeeById(id:number): Observable<Object>{
    return this.http.patch(this.url + '/employees/approve/' + id , {}, {headers: this.getHeaders()});
  }

  deleteEmployeeById(id:number): Observable<Object>{
    return this.http.delete(this.url + '/employees/' + id, {headers: this.getHeaders()});
  }  
}
