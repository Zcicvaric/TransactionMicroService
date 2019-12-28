import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { TransactionListComponent } from './_components/transaction/transaction-list/transaction-list.component';
import { LoginComponent } from './_components/login/login.component';


const routes: Routes = [
	{ path: '', component: TransactionListComponent },
	{ path: 'transactions', component: TransactionListComponent },
	{ path: 'login', component: LoginComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
