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
				{ field: 'id', header: 'Id' },
				{ field: 'transactionAmount', header: 'transactionAmount' },
				{ field: 'description', header: 'description' },
				{ field: 'paymentInstrumentId', header: 'paymentInstrumentId' },
				// { field: 'uid', header: 'Uid' },
				// { field: 'number', header: 'Number' },
				{ field: 'date', header: 'Date' },				
				{ field: 'statusId', header: 'statusId' },
				// { field: 'paymentTypeId', header: 'paymentTypeId' },
				
				// { field: 'payerIBAN', header: 'payerIBAN' },
				// { field: 'payerCurrency', header: 'payerCurrency' },
				// { field: 'receiverIBAN', header: 'receiverIBAN' },
				// { field: 'receiverCurrency', header: 'receiverCurrency' },
				// { field: 'receiverExchangeRate', header: 'receiverExchangeRate' },
				// { field: 'swiftCode', header: 'swiftCode' },
				// { field: 'modelId', header: 'modelId' },
				// { field: 'referenceNumber', header: 'referenceNumber' },
				// { field: 'usageCode', header: 'usageCode' },
				// { field: 'stornoID', header: 'stornoID' }
				{ field: 'edit', header: 'Actions' }
			];        

		  });

		

	}	

	isField(fieldName: string) {
		return fieldName != "edit";
	}

	isAction(fieldName: string) {
		return fieldName == "edit";
	}

	edit(id: number) {
		this.router.navigate(['/transaction/'+id])
	}
	
}
