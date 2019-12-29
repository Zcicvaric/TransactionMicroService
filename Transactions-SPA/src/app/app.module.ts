import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './_components/login/login.component';
import { NavbarComponent } from './_components/navbar/navbar.component';
import { TransactionListComponent } from './_components/transaction/transaction-list/transaction-list.component';

// primeng
import { InputTextModule } from 'primeng/inputtext';
import { PasswordModule } from 'primeng/password';
import { ButtonModule } from 'primeng/button';
import { TableModule } from 'primeng/table';
import { DropdownModule } from 'primeng/dropdown';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { TransactionCardComponent } from './_components/transaction/transaction-card/transaction-card.component';
import { TabMenuModule } from 'primeng/tabmenu';
import {PanelModule} from 'primeng/panel';


@NgModule({
   declarations: [
      AppComponent,
	  LoginComponent,
	  NavbarComponent,
	  TransactionListComponent,
	  TransactionCardComponent
   ],
   imports: [
      BrowserModule,
	  AppRoutingModule,
	  HttpClientModule,
	  FormsModule,
	  InputTextModule,
	  PasswordModule,
	  ButtonModule,
	  TableModule,
	  DropdownModule,
	  BrowserAnimationsModule,
	  TabMenuModule,
	  PanelModule
   ],
   providers: [],
   bootstrap: [
      AppComponent
   ]
})
export class AppModule { }
