import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ApiService {

  apiUrl = 'http://3.19.171.39:8080/';

  constructor(private http: HttpClient) { }

  getUserList(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}`);
  }

  associateBoss(idChefe: number, idSubordinado: number): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}associateBoss`, { "idBoss": idChefe, "idSubordinate": idSubordinado });
  }

  addCadastro(user: string, password: string): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}`, { "nome": user, "senha": password });
  }
}
