import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { TransactionListComponent } from './_components/transaction/transaction-list/transaction-list.component';
import { LoginComponent } from './_components/login/login.component';
import { TransactionCardComponent } from './_components/transaction/transaction-card/transaction-card.component';


const routes: Routes = [
	{ path: '', component: TransactionListComponent },
	{ path: 'transactions', component: TransactionListComponent },
	{ path: 'transactions/completed', component: TransactionListComponent, data : {state : 'completed'} },
	{ path: 'transactions/cancelled', component: TransactionListComponent, data : {state : 'cancelled'} },
	{ path: 'login', component: LoginComponent },
	{ path: 'transaction/:id', component: TransactionCardComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
