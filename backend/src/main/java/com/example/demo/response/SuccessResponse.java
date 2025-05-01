package com.example.demo.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import com.example.demo.DTOs.InvoiceDTO;
import com.example.demo.model.Messages;
import com.example.demo.model.Users;

public class SuccessResponse {

	private Object response;
	private Boolean status;
	private String message;
	private HttpStatusCode statusCode;

	public Object getResponse() {
		return response;
	}

	public void setResponse(Object response) {
		this.response = response;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public HttpStatusCode getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(HttpStatusCode statusCode) {
		this.statusCode = statusCode;
	}

	// -----------------------------------|| Client
	// ||----------------------------------------//

	public void nullData() {
		this.response = null;
		this.status = true;
		this.statusCode = HttpStatus.NOT_ACCEPTABLE;
		this.message = Messages.null_filed;
	}

	public void clientAdded(Object client) {
		this.response = client;
		this.status = true;
		this.statusCode = HttpStatus.OK;
		this.message = Messages.client_added;
	}

	public void clientNotFound() {
		this.response = null;
		this.status = true;
		this.statusCode = HttpStatus.NOT_FOUND;
		this.message = Messages.no_client_found;
	}

	public void clients(Object findAll) {
		this.response = findAll;
		this.status = true;
		this.statusCode = HttpStatus.OK;
		this.message = Messages.clientsFound;
	}

	public void clientUpdated(Object clientDTO) {
		this.response = clientDTO;
		this.status = true;
		this.statusCode = HttpStatus.OK;
		this.message = Messages.clientUpdated;
	}

	public void userAdded(Object save) {
		this.response = save;
		this.status = true;
		this.statusCode = HttpStatus.OK;
		this.message = Messages.userAdded;
	}

	public void invalidMobile() {
		this.response = null;
		this.status = true;
		this.statusCode = HttpStatus.NOT_FOUND;
		this.message = Messages.invalid_mobile;
	}

	public void loginSuccesfully(Object map) {
		this.response = map;
		this.status = true;
		this.statusCode = HttpStatus.OK;
		this.message = Messages.loginSuccessfully;
	}

	public void invalidPassword() {
		this.response = null;
		this.status = true;
		this.statusCode = HttpStatus.NOT_ACCEPTABLE;
		this.message = Messages.invalid_pass;
	}

	public void duplicateEmail() {
		this.response = null;
		this.status = true;
		this.statusCode = HttpStatus.NOT_ACCEPTABLE;
		this.message = Messages.duplicateEmail;
	}

	public void duplicateMobile() {
		this.response = null;
		this.status = true;
		this.statusCode = HttpStatus.NOT_ACCEPTABLE;
		this.message = Messages.duplicateMobile;
	}

	public void invoiceAdded(Object invoiceDTO) {
		this.response = invoiceDTO;
		this.status = true;
		this.statusCode = HttpStatus.OK;
		this.message = Messages.invoiceAdded;
	}

	public void invoiceNotFound() {
		this.response = null;
		this.status = true;
		this.statusCode = HttpStatus.NOT_FOUND;
		this.message = Messages.invoice_not_found;
	}

	public void invoiceFound(Object findAll) {
		this.response = findAll;
		this.status = true;
		this.statusCode = HttpStatus.FOUND;
		this.message = Messages.invoice_found;
	}

	public void expenseAdded(Object expenseDTO) {
		this.response = expenseDTO;
		this.status = true;
		this.statusCode = HttpStatus.FOUND;
		this.message = Messages.expenseAdded;
	}

	public void expenseNotFound() {
		this.response = null;
		this.status = true;
		this.statusCode = HttpStatus.NOT_FOUND;
		this.message = Messages.expense_not_found;
	}

	public void expenseFound(Object expenseDTOs) {
		this.response = expenseDTOs;
		this.status = true;
		this.statusCode = HttpStatus.FOUND;
		this.message = Messages.expense_found;
	}

	public void emailSent() {
		this.response = null;
		this.status = true;
		this.statusCode = HttpStatus.FOUND;
		this.message = Messages.emailSend;
	}

	public void instalmentCreated(Object savedInstallment) {
		this.response = savedInstallment;
		this.status = true;
		this.statusCode = HttpStatus.FOUND;
		this.message = Messages.created_installment;
	}

	public void retriveInstallment(Object installments) {
		this.response = installments;
		this.status = true;
		this.statusCode = HttpStatus.FOUND;
		this.message = Messages.found_installment;
	}

	public void clientDeleted() {
		this.response = null;
		this.status = true;
		this.statusCode = HttpStatus.FOUND;
		this.message = Messages.client_deleted;
	}

	public void monthlySummery(Object monthlySummaries) {
		this.response = monthlySummaries;
		this.status = true;
		this.statusCode = HttpStatus.FOUND;
		this.message = "SUMMERY.";
	}

	public void userNotFound() {
		this.response = null;
		this.status = true;
		this.statusCode = HttpStatus.NOT_FOUND;
		this.message = "User not found";
	}

	public void users(Object userDTO) {
		this.response = userDTO;
		this.status = true;
		this.statusCode = HttpStatus.FOUND;
		this.message = "Retrived user";

	}

	public void userDeleted(Object map) {
		this.response = map;
		this.status = true;
		this.statusCode = HttpStatus.FOUND;
		this.message = "Account deleted Sucessfully";
	}

	public void passwordUpdated() {
		this.response = null;
		this.status = true;
		this.statusCode = HttpStatus.FOUND;
		this.message = "Password Updated Successfully.";
	}

	public void sendEmailSuccessfully(int randomNumber) {
		this.response = randomNumber;
		this.status = true;
		this.statusCode = HttpStatus.FOUND;
		this.message = "Email Send Successfully.";
	}

	public void emailNotSend() {
		this.response = null;
		this.status = true;
		this.statusCode = HttpStatus.FOUND;
		this.message = "Please Enter the correct Email";
	}

	public void installmentNotFound() {
		this.response = null;
		this.status = true;
		this.statusCode = HttpStatus.FOUND;
		this.message = "Installment Not Found";
	}

	public void markAsPaid(Object installment) {
		this.response = installment;
		this.status = true;
		this.statusCode = HttpStatus.FOUND;
		this.message = "Mark As Paid Successfully";
	}

	public InvoiceDTO getData(InvoiceDTO data) {
		this.response = data;
		this.status = true;
		this.statusCode = HttpStatus.FOUND;
		this.message = "generate PDF Successfull.";
		return data;
	}

	public void instalmentUpdated(Object updatedInstallmentDTO) {
		this.response = updatedInstallmentDTO;
		this.status = true;
		this.statusCode = HttpStatus.FOUND;
		this.message = "Installment updated Successfull.";
	}

	public void expenseUpdated(Object map) {
		this.response = map;
		this.status = true;
		this.statusCode = HttpStatus.FOUND;
		this.message = "Expense Updated Successfully.";
	}

	public void notificationsAreEmpty() {
		this.response = null;
		this.status = true;
		this.statusCode = HttpStatus.NOT_FOUND;
		this.message = "Notifications Are Empty";
	}

	public void notificationRetrive(Object findAll) {
		this.response = findAll;
		this.status = true;
		this.statusCode = HttpStatus.FOUND;
		this.message = "Notifications Found.";
	}

	public void notificationNotFound() {
		this.response = null;
		this.status = true;
		this.statusCode = HttpStatus.NOT_FOUND;
		this.message = "Notifications Not Found..!";
	}

	public void readSuccessfuly(Object notifications) {
		this.response = notifications;
		this.status = true;
		this.statusCode = HttpStatus.FOUND;
		this.message = "Read Successfully.";
	}

	public void revenueAdded(Object map2) {
		this.response = map2;
		this.status = true;
		this.statusCode = HttpStatus.FOUND;
		this.message = "Revenue Added.";
	}

	public void revenueRetrived(Object findAll) {
		this.response = findAll;
		this.status = true;
		this.statusCode = HttpStatus.FOUND;
		this.message = "revenue found.";
	}

	public void revenueUpdated(Object existingRevenue) {
		this.response = existingRevenue;
		this.status = true;
		this.statusCode = HttpStatus.FOUND;
		this.message = "revenue Updated.";
	}

	public void revenueNotFound() {
		this.response = null;
		this.status = true;
		this.statusCode = HttpStatus.NOT_FOUND;
		this.message = "revenue Not Found..!";
	}

	public void revenueDeleted(Object findById) {
		this.response = findById;
		this.status = true;
		this.statusCode = HttpStatus.FOUND;
		this.message = "revenue deleted successfully.";

	}

	public void setMonthlySummaries(Object monthlySummaries) {
		this.response = monthlySummaries;
		this.status = true;
		this.statusCode = HttpStatus.FOUND;
		this.message = "Summary found";
	}

	public void MonthlyData(Object response) {
		this.response = response;
		this.status = true;
		this.statusCode = HttpStatus.FOUND;
		this.message = "Monthelly summery found";
		this.statusCode = HttpStatus.NOT_FOUND;
	}

	public void employeeNotFound() {
		this.message = "Employee not found.";
		this.status = false;
	}

	public void employeeAdded(Object response) {
		this.message = "Employee added successfully.";
		this.response = response;
		this.status = true;
		this.statusCode = HttpStatus.FOUND;
		;
	}

	public void employeeUpdated(Object response) {
		this.message = "Employee updated successfully.";
		this.response = response;
		this.status = true;
		this.statusCode = HttpStatus.FOUND;
	}

	public void retriveEmployees(Object collect) {
		this.message = "Employees Found";
		this.response = collect;
		this.status = true;
		this.statusCode = HttpStatus.FOUND;
	}

	public void leavesRetrived(Object empLeave) {
		this.message = "Leaves Found";
		this.response = empLeave;
		this.status = true;
		this.statusCode = HttpStatus.FOUND;
	}

	public void noLeavesPresent() {
		this.message = "No leaves present";
		this.response = null;
		this.status = false;
		this.statusCode = HttpStatus.NOT_FOUND;
	}

	public void InternNotFound() {
		this.message = "Intern not found";
		this.response = null;
		this.status = false;
		this.statusCode = HttpStatus.NOT_FOUND;

	}

	public void interUpdated(Object map) {
		this.message = "Intern Updated Successfully.";
		this.response = map;
		this.status = false;
		this.statusCode = HttpStatus.FOUND;
	}

	public void internSave(Object map) {
		this.message = "Intern Saved Successfully.";
		this.response = map;
		this.status = false;
		this.statusCode = HttpStatus.FOUND;
	}

	public void intersFound(Object collect) {
		this.message = "Interns Retrived.";
		this.response = collect;
		this.status = false;
		this.statusCode = HttpStatus.FOUND;
	}

	public void userUpdated(Object userToUpdate) {
		this.message = "User Updated Successfully";
		this.response = userToUpdate;
		this.status = true;
		this.statusCode = HttpStatus.FOUND;
	}

	public void duplicateMobileNumber() {
		this.message = "Mobile number already exist";
		this.response = null;
		this.status = true;
		this.statusCode = HttpStatus.FOUND;
	}

	public void maxUserLimitReached() {
		this.message = "Maximum user limit reached. Cannot register more users";
		this.response = null;
		this.status = true;
		this.statusCode = HttpStatus.NOT_ACCEPTABLE;

	}

	public void expenseDeleted() {
		this.message = "Expense Deleted Successfully..";
		this.response = null;
		this.status = true;
		this.statusCode = HttpStatus.FOUND;
	}

}
