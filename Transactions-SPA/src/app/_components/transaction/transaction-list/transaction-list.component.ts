import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/_services/auth.service';
import { Router } from '@angular/router';

@Component({
	selector: 'transaction-list',
	templateUrl: './transaction-list.component.html',
	styleUrls: ['./transaction-list.component.css']
})
export class TransactionListComponent implements OnInit {

	constructor(private authService: AuthService, private router: Router) { }

	ngOnInit() {
		if (!this.authService.loggedIn()) {
			this.router.navigate(['/login']);
		}
	}

}
