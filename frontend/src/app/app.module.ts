import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ClientRegistrationComponent } from './Client/client-registration/client-registration.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { ToastrModule } from 'ngx-toastr';
import { LoginComponent } from './login/login.component';
import { HomepageComponent } from './homepage/homepage.component';
import { InstallmentsComponent } from './Client/installments/installments.component';
import { InvoiceComponent } from './Client/invoice/invoice.component';
import { AllClientComponent } from './Client/all-client/all-client.component';
import { AddExpenseComponent } from './Expense/add-expense/add-expense.component';
import { ViewExpensesComponent } from './Expense/view-expenses/view-expenses.component';
import { DialogModule } from 'primeng/dialog';
import { ButtonModule } from 'primeng/button';
import { TableModule } from 'primeng/table';
import { TagModule } from 'primeng/tag';
import { CheckboxModule } from 'primeng/checkbox';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { ConfirmationService, MessageService } from 'primeng/api';
import { ToastModule } from 'primeng/toast';
import { SidebarModule } from 'primeng/sidebar';
import { DropdownModule } from 'primeng/dropdown';
import { DialogService, DynamicDialogRef } from 'primeng/dynamicdialog';
import { CalendarModule } from 'primeng/calendar';
import { InputNumberModule } from 'primeng/inputnumber';
import { InputTextModule } from 'primeng/inputtext';
import { SummaryComponent } from './summary/summary/summary.component';
import { CommonModule, DatePipe } from '@angular/common';
import { NotificationsComponent } from './Client/notifications/notifications.component';
import { BadgeModule } from 'primeng/badge';
import { RevenueComponent } from './revenue/revenue/revenue.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { EmployeesComponent } from './Employee/employees/employees.component';
import { EmployeeSummaryComponent } from './Employee/employee-summary/employee-summary.component';
import { InternStudentComponent } from './intern/intern-student/intern-student.component';
import { SalaryReceptComponent } from './Employee/salary-recept/salary-recept.component';
// import { NgxUiLoaderHttpModule, NgxUiLoaderModule } from 'ngx-ui-loader';



@NgModule({
  declarations: [
    AppComponent,
    ClientRegistrationComponent,
    LoginComponent,
    HomepageComponent,
    InstallmentsComponent,
    InvoiceComponent,
    AllClientComponent,
    AddExpenseComponent,
    ViewExpensesComponent,
    SummaryComponent,
    NotificationsComponent,
    RevenueComponent,
    PageNotFoundComponent,
    EmployeesComponent,
    EmployeeSummaryComponent,
    InternStudentComponent,
    SalaryReceptComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    BrowserAnimationsModule, 
    ToastrModule.forRoot(), // ToastrModule added
    DialogModule,
    ButtonModule,
    TableModule,
    TagModule,
    ConfirmDialogModule,
    CheckboxModule,
    InputTextModule,
    ToastModule,
    SidebarModule,
    DropdownModule,
    CalendarModule,
    InputNumberModule,
    BadgeModule,
    CommonModule,
    // NgxUiLoaderModule,
    // NgxUiLoaderHttpModule.forRoot({
    //   showForeground: true, // Show loader for HTTP requests
    // }),
    
  ],
  providers: [ConfirmationService,MessageService,DialogService,DynamicDialogRef,DatePipe
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
