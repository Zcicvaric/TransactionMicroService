import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/_services/auth.service';
import { TransactionService } from 'src/app/_services/transaction.service';
import { Router } from '@angular/router';

@Component({
	selector: 'login',
	templateUrl: './login.component.html',
	styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

	username: string;
	password: string;

	constructor(private authService: AuthService, private transactionService: TransactionService, private router: Router) { }

	ngOnInit() {
	}

	login() {
		// var credentials = {
		// 	username: this.username,
		// 	password: this.password
		// }

		// TODO: this is hardcoded
		var credentials = {
			// id: Math.floor(Math.random() * 150),
			// id: 6,
  			
			userName: "admin",
			password: "admin"
		};

		this.authService.login(credentials).subscribe(
			success => {
				this.router.navigate(['/transactions']);
				console.log("success");				
			},
			error => console.log("login error", error)
		);

	}

}
