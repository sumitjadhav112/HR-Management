import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { ClientRegistrationComponent } from './Client/client-registration/client-registration.component';
import { HomepageComponent } from './homepage/homepage.component';
import { InstallmentsComponent } from './Client/installments/installments.component';
import { AllClientComponent } from './Client/all-client/all-client.component';
import { InvoiceComponent } from './Client/invoice/invoice.component';
import { AddExpenseComponent } from './Expense/add-expense/add-expense.component';
import { ViewExpensesComponent } from './Expense/view-expenses/view-expenses.component';
import { SummaryComponent } from './summary/summary/summary.component';
import { authGuard } from './guards/auth.guard';
import { canDeactivateGuard } from './guards/can-deactivate.guard';
import { NotificationsComponent } from './Client/notifications/notifications.component';
import { RevenueComponent } from './revenue/revenue/revenue.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { EmployeesComponent } from './Employee/employees/employees.component';
import { EmployeeSummaryComponent } from './Employee/employee-summary/employee-summary.component';
import { InternStudentComponent } from './intern/intern-student/intern-student.component';


const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  {
    path: '',
    canActivate: [authGuard],
    children: [
      { path: 'homepage', component: HomepageComponent },
      { path: 'installments/:clientId', component: InstallmentsComponent },
      { path: 'view-clients', component: AllClientComponent },
      { path: 'invoice', component: InvoiceComponent },
      { path: 'add-expense', component: AddExpenseComponent },
      { path: 'view-expenses', component: ViewExpensesComponent },
      { path: 'summary', component: SummaryComponent },
      {path:'notifications', component:NotificationsComponent},
      {path:'revenue',component:RevenueComponent},
      { path: 'CLientRegister', component: ClientRegistrationComponent ,canDeactivate: [canDeactivateGuard] },
      { path: 'employees', component: EmployeesComponent },
      {path:'employee-summary', component:EmployeeSummaryComponent},
      {path:'intern-student',component:InternStudentComponent}

    ]
  },
  { path: '**', component: PageNotFoundComponent }
];


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
