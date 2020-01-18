import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/_services/auth.service';
import { Router, ActivatedRoute } from '@angular/router';
import { TransactionService } from 'src/app/_services/transaction.service';
import { Transaction } from 'src/app/_models/transaction';
import { SelectItem } from 'primeng/api/selectitem';

@Component({
	selector: 'transaction-list',
	templateUrl: './transaction-list.component.html',
	styleUrls: ['./transaction-list.component.css']
})
export class TransactionListComponent implements OnInit {

	transactions: Transaction[] = [];
	cols: any[];
	readyToDisplayHtml: boolean = false;
    states: SelectItem[];

	constructor(private authService: AuthService, private router: Router, private transactionService: TransactionService, private route: ActivatedRoute) { }

	ngOnInit() {
		if (!this.authService.loggedIn()) {
			this.router.navigate(['/login']);
		}

		this.route.data.subscribe( params => {
			if ('state' in params) {
				if (params['state'] == 'completed') {
					this.transactionService.getAllCompleted().subscribe(
						success => {
							this.transactions = success;
							console.log("transaction service get all response", success);
							this.readyToDisplayHtml = true;
						},
						error => console.log("transaction service get all response error", error)
					);
				}
				if (params['state'] == 'cancelled') {
					this.transactionService.getAllCancelled().subscribe(
						success => {
							this.transactions = success;
							console.log("transaction service get all response", success);
							this.readyToDisplayHtml = true;
						},
						error => console.log("transaction service get all response error", error)
					);
				}
			}			
			else {
				this.transactionService.getAll().subscribe(
					success => {
						this.transactions = success;
						console.log("transaction service get all response", success);
						this.readyToDisplayHtml = true;
					},
					error => console.log("transaction service get all response error", error)
				);
			}			
	
			this.states = [
				{ label: '', value: '' },
				{ label: 'Izvršeno', value: 1 },
				{ label: 'Stornirano', value: 2 }            
			];
			
			this.cols = [
				{ field: 'uid', header: 'Uid' },
				{ field: 'transactionAmount', header: 'Amount' },
				{ field: 'payerCurrency', header: 'Currency' },
				{ field: 'receiverExchangeRate', header: 'Exchange Rate' },
				{ field: 'description', header: 'Description' },
				{ field: 'date', header: 'Date' },				
				{ field: 'typeOfExpense', header: 'Type of expense' },				
				{ field: 'edit', header: 'Actions' }
			];        

		});
	}	

	isIcon(fieldName: string) {
		return fieldName == "typeOfExpense";
	}

	isField(fieldName: string) {
		return fieldName != "edit" && fieldName != "typeOfExpense";
	}

	isAction(fieldName: string) {
		return fieldName == "edit";
	}

	edit(id: number) {
		this.router.navigate(['/transaction/'+id])
	}
	
	getImage(fieldValue: string) {
		if (fieldValue == "Food")
			return "https://cdn.iconscout.com/icon/free/png-256/fast-food-1851561-1569286.png";
		else 
			return "https://image.flaticon.com/icons/png/512/619/619043.png";
	}

}
