import { HttpClient, HttpHeaders } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Employee } from './employee';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private http = inject(HttpClient);
  private url:string = 'http://localhost:8080/api/v1';

  private getHeaders(): HttpHeaders{
    let headers = new HttpHeaders();
    const authToken = localStorage.getItem('token');
    headers = headers.set('Authorization', 'Bearer ' + authToken);
    return headers;
  }

  getUserDetails(): Observable<Employee>{
    return this.http.get<Employee>(this.url + '/current-employee', {headers: this.getHeaders()});
  }

  getOtherEmployees(): Observable<Employee[]>{
    return this.http.get<Employee[]>(this.url + '/other-employee', {headers: this.getHeaders()});
  }

  updatePassword(currentPassword: string, newPassword: string): Observable<Object> {
    const body = { currentPassword, newPassword };
    return this.http.patch(this.url + '/update-password', body, {headers: this.getHeaders()});
  }

  updateDetails(employee: Employee): Observable<Object> {
    return this.http.put(this.url + '/update-details', employee, {headers: this.getHeaders()});
  }
}
