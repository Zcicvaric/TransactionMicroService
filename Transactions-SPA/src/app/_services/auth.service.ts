import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { map } from 'rxjs/operators';

@Injectable({
	providedIn: 'root'
})
export class AuthService {

	baseUrl = environment.apiUrl;

	private httpOptions = {
		headers: new HttpHeaders({
			'Authorisation': "Token " + localStorage.getItem('token')
		})
	};

	constructor(private http: HttpClient) { }

	login(credentials) {
		return this.http.post(this.baseUrl + 'token', credentials).pipe(
			map((response: any) => {
				if (response) {
					localStorage.setItem('token', response.token);
				}
			})
		);
	}

	loggedIn() {
		const token = localStorage.getItem('token');
		return token != null;
	}

	logout() {
		localStorage.removeItem('token');
	}

}
