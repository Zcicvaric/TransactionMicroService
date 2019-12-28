import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from '../../environments/environment';

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

	getAll() {
		return this.http.get(this.baseUrl + 'transactions/all', this.httpOptions);
	}

}
