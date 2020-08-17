import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {Person} from "../model/person.model";
import {Observable} from "rxjs/index";

@Injectable()
export class ApiService {

  constructor(private http: HttpClient) { }
  baseUrl: string = 'http://localhost:8080/person/';

  login(loginPayload) : Observable<any> {
    return this.http.post<any>('http://localhost:8080/' + 'user/auth', loginPayload);
  }

  getPersons() : Observable<any> {
    return this.http.get<any>(this.baseUrl);
  }

  getPersonById(cpf: string): Observable<any> {
    return this.http.get<any>(this.baseUrl + cpf);
  }

  createPerson(person: Person): Observable<any> {
    return this.http.post<any>(this.baseUrl, person);
  }

  updatePerson(person: Person): Observable<any> {
    return this.http.put<any>(this.baseUrl + person.cpf, person);
  }

  deletePerson(cpf: string): Observable<any> {
    return this.http.delete<any>(this.baseUrl + cpf);
  }
}
