import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { Transaction } from '../_models/transaction';
import { Observable } from 'rxjs';

@Injectable({
	providedIn: 'root'
})
export class TransactionService {

	baseUrl = environment.apiUrl;

	private httpOptions = {
		headers: new HttpHeaders({
			'Authorisation': "Token " + localStorage.getItem('token')
		})
	};

	constructor(private http: HttpClient) { }

	getById(id) : Observable<Transaction> {
		return this.http.get<Transaction>(this.baseUrl + 'transactions/'+id, this.httpOptions);
	}

	getAll() : Observable<Transaction[]> {
		return this.http.get<Transaction[]>(this.baseUrl + 'transactions/', this.httpOptions);
	}

	getAllCancelled() : Observable<Transaction[]> {
		return this.http.get<Transaction[]>(this.baseUrl + 'transactions/canceled', this.httpOptions);
	}

	getAllCompleted() : Observable<Transaction[]> {
		return this.http.get<Transaction[]>(this.baseUrl + 'transactions/completed', this.httpOptions);
	}

}
