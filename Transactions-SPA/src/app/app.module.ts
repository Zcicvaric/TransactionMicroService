import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './_components/login/login.component';

// primeng
import { InputTextModule } from 'primeng/inputtext';
import { PasswordModule } from 'primeng/password';
import { ButtonModule } from 'primeng/button';
import { TransactionListComponent } from './_components/transaction/transaction-list/transaction-list.component';

@NgModule({
   declarations: [
      AppComponent,
	  LoginComponent,
	  TransactionListComponent
   ],
   imports: [
      BrowserModule,
	  AppRoutingModule,
	  HttpClientModule,
	  FormsModule,
	  InputTextModule,
	  PasswordModule,
	  ButtonModule
   ],
   providers: [],
   bootstrap: [
      AppComponent
   ]
})
export class AppModule { }
