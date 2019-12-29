import { Component, OnInit } from '@angular/core';
import { TransactionService } from 'src/app/_services/transaction.service';
import { AuthService } from 'src/app/_services/auth.service';
import { Router, ActivatedRoute } from '@angular/router';
import { Transaction } from 'src/app/_models/transaction';

@Component({
	selector: 'transaction-card',
	templateUrl: './transaction-card.component.html',
	styleUrls: ['./transaction-card.component.css']
})
export class TransactionCardComponent implements OnInit {

	transaction: Transaction;
	id: number;
	isReadyToDisplayHtml: boolean = false;

	constructor(private transactionService: TransactionService, private authService: AuthService, private router: Router, private route: ActivatedRoute) { }

	ngOnInit() {

		if (!this.authService.loggedIn()) {
			this.router.navigate(['/login']);
		}

		this.route.params.subscribe(params => {
			if (params['id'] != null) {
			  this.id = +params['id'];
			  this.transactionService.getById(this.id).subscribe(transaction => {
				if (transaction == null) {
				  this.router.navigate(['/transactions']);
				} else {
				  this.transaction = transaction;
				  this.isReadyToDisplayHtml = true;
				}	  
			  }, error => {
				this.router.navigate(['/']);
			});
			}
		  });		

	}

	navigateBack() {
		this.router.navigate(['/transactions']);
	}

}
