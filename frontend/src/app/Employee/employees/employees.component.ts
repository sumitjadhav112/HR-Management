import { animate, style, transition, trigger } from '@angular/animations';
import { Component, HostListener, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { MessageService } from 'primeng/api';
import { EmployeeService } from 'src/app/services/employee.service';

@Component({
  selector: 'app-employees',
  templateUrl: './employees.component.html',
  styleUrls: ['./employees.component.css']
})
export class EmployeesComponent implements OnInit {
  employeeForm: FormGroup;
  employees: any[] = [];
  displayDialog = false;
  successMessage: string;
  visible: boolean = false;
  errorMessage: string;
  isEditing = false;
  showDeleteAlert = false;
  employeeToDelete: number | null = null;
  displayLeaveDialog: boolean = false;
  leaveForm: FormGroup;
  addAdvanceForm:FormGroup;
  leaves: any[] = [];
  salaryForm: FormGroup;
  currentEmployee: any;
  calculatedSalary: number | null = null;
  displayAdvanceDialouge:boolean = false;


  constructor(
    private fb: FormBuilder,
    private employeeService: EmployeeService,
    private messageService: MessageService,
    private router:Router
  ) {
    this.initForm();
    this.leaveForm = this.fb.group({
      leaveDate: ['', Validators.required],
      leaveType: ['', Validators.required]
    });
    this.initSalaryForm();
    this.addAdvanceForm = this.fb.group({
      advanceAmount: ['', [Validators.required, Validators.min(1)]],
      month:['',[Validators.required]]
    });
  }


  ngOnInit(): void {
    this.getAllEmployees();
  }


  initSalaryForm(): void {
    this.salaryForm = this.fb.group({
      date: ['', Validators.required]
    });
  }


  manageSalary(employee: any): void {
    this.currentEmployee = employee;
    this.initSalaryForm(); // Reset the form
    this.calculatedSalary = null; // Reset calculated salary
    this.visible = true;
  }
  
  onSubmitSalary(): void {
    if (this.salaryForm.valid) {
      const salaryRequest = { date: this.salaryForm.value.date };
      this.employeeService.calculateSalary(this.currentEmployee.employee_id, salaryRequest).subscribe(
        (response: any) => {
          console.log("salary response : ",response);
          
          this.calculatedSalary = response.calculatedSalary;
          this.messageService.add({
            severity: 'success',
            summary: 'Success',
            detail: 'Salary calculated successfully.',
          });
          this.visible = false;
        },
        (error) => {
          this.messageService.add({
            severity: 'error',
            summary: 'Error',
            detail: 'An error occurred while calculating salary.',
          });
        }
      );
    }
  }


  initForm(): void {
    this.employeeForm = this.fb.group({
      employee_id: [null],
      name: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      phone: ['', [Validators.required, Validators.pattern('^[0-9]{10}$')]],
      status: [true],
      joiningDate:['',[Validators.required]],
      baseSalary: ['', [Validators.required, Validators.min(0)]],
    });
  }

  showAddDialog(): void {
    this.isEditing = false;
    this.initForm(); // Reset the form
    this.displayDialog = true;
  }

  onSubmit(): void {
    if (this.employeeForm.invalid) {
      this.messageService.add({severity:'error', summary: 'Error', detail: 'Please fill in all required fields correctly.'});
      return;
    }
    console.log("employee form details",this.employeeForm.value);
    
    this.employeeService.addOrUpdateEmployee(this.employeeForm.value).subscribe(
      (response: any) => {        
        this.messageService.add({severity:'success', summary: 'Success', detail: response.message});
        this.employeeForm.reset();
        this.hideDialog();
        this.getAllEmployees();
      },
      (error) => {
        this.messageService.add({severity:'error', summary: 'Error', detail: 'An error occurred while processing your request.'});
      }
    );
  }
  
  getFormControl(name: string) {
    return this.employeeForm.get(name);
  }

  getErrorMessage(controlName: string): string {
    const control = this.getFormControl(controlName);
    if (control?.hasError('required')) {
      return `${this.capitalizeFirstLetter(controlName)} is required.`;
    } else if (control?.hasError('email')) {
      return 'Invalid email format.';
    } else if (control?.hasError('pattern')) {
      return 'Invalid phone number format.';

    }
    else if (control?.hasError('joiningDate')) {
      return 'select the joining date of employee.';
      
    } else if (control?.hasError('min')) {
      return 'Value must be a positive number.';
    }
    return '';
  }

  private capitalizeFirstLetter(string: string) {
    return string.charAt(0).toUpperCase() + string.slice(1);
  }

  getAllEmployees() {
    this.employeeService.getAllEmployees().subscribe((data:any) => {
      this.employees = data.response;
      console.log("emp",data.response);
      
    });
  }

  editEmployee(employee: any): void {
    this.isEditing = true;
    this.employeeForm.patchValue(employee);
    this.displayDialog = true;
  }

  deleteEmployee(employeeId: number): void {
    this.showDeleteAlert = true;
    this.employeeToDelete = employeeId;
  }

  cancelDelete(): void {
    this.showDeleteAlert = false;
    this.employeeToDelete = null;
  }

  confirmDelete(): void {
    if (this.employeeToDelete) {
      this.employeeService.deleteEmployee(this.employeeToDelete).subscribe(
        () => {
          this.messageService.add({severity:'success', summary: 'Success', detail: 'Employee deleted successfully.'});
          this.getAllEmployees();
          this.showDeleteAlert = false;
          this.employeeToDelete = null;
        },
        (error) => {
          this.messageService.add({severity:'error', summary: 'Error', detail: 'An error occurred while deleting the employee.'});
          this.showDeleteAlert = false;
          this.employeeToDelete = null;
        }
      );
    }
  }
  
  hideDialog() {
    this.displayDialog = false;
    this.isEditing = false;
    this.employeeForm.reset();
  }

  toggleDropdown(employee: any): void {
    employee.showDropdown = !employee.showDropdown;
    this.employees.forEach(emp => {
      if (emp !== employee) {
        emp.showDropdown = false;
      }
    });
  }



  @HostListener('document:click', ['$event'])
  closeDropdowns(event: Event): void {
    if (!(event.target as HTMLElement).closest('.dropdown')) {
      this.employees.forEach(employee => employee.showDropdown = false);
    }
  }

  addLeaves(employee: any): void {
    this.currentEmployee = employee;
    this.displayLeaveDialog = true;
    this.leaveForm.reset // Reset leaves array
    this.getAllLeavesForEmployee(employee.employee_id);
  }

  onAddLeave(): void {
    if (this.leaveForm.valid) {
      this.leaves.push(this.leaveForm.value);
      this.leaveForm.reset();
    }
  }

  onSubmitLeaves(): void {
    if (this.leaveForm.valid) {
      this.employeeService.addLeave(this.currentEmployee.employee_id, this.leaveForm.value).subscribe(
        (response: any) => {
          console.log("leave response : ", response);
          this.messageService.add({severity:'success', summary: 'Success', detail: 'Leaves added successfully.'});
          this.getAllLeavesForEmployee(this.currentEmployee.employee_id);
        },
        (error) => {
          this.messageService.add({severity:'error', summary: 'Error', detail: 'Failed to add leaves.'});
        }
      );
    }
  }

  getAllLeavesForEmployee(employeeId: number): void {
    this.employeeService.getAllLeaves(employeeId).subscribe(
      (response: any) => {
        console.log(response);
        this.leaves = response.response || [];
      },
      (error) => {
        this.messageService.add({severity:'error', summary: 'Error', detail: 'Failed to fetch leaves.'});
      }
    );
  }

  bakcToHome(){
    this.router.navigate(['/homepage']);
  }



  goToEmployeeSummary(employee: any): void {
    this.router.navigate(['/employee-summary'], { 
      state: { employeeId: employee.employee_id, employeeName: employee.name } 
    });
  }

  addAdvance(employee:any):void{
    this.currentEmployee = employee;
    this.displayAdvanceDialouge= true
    this.addAdvanceForm.reset // Reset leaves array
  }

addAdvanceForEmployees() {
  const advnceDetails = { 
    advanceAmount: this.addAdvanceForm.value.advanceAmount, 
    month: this.addAdvanceForm.value.month 
  };
  this.employeeService.addAdvanceForEmp(this.currentEmployee.employee_id, advnceDetails)
    .subscribe(data => {
      console.log(data);
      this.displayAdvanceDialouge = false;
    });
}
}
