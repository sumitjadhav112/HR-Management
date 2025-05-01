import { Location } from '@angular/common';
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { EmployeeSummaryDTO } from 'src/app/Model/EmployeeSummaryDTO';
import { EmployeeService } from 'src/app/services/employee.service';

@Component({
  selector: 'app-employee-summary',
  templateUrl: './employee-summary.component.html',
  styleUrls: ['./employee-summary.component.css']
})
export class EmployeeSummaryComponent {
  employeeId: number;
  employeeName: string;

  summaryForm: FormGroup;
  employeeSummary: EmployeeSummaryDTO;
  isLoading: boolean = false;
  error: string | null = null;


  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private employeeService: EmployeeService,
    private fb: FormBuilder,
    private location:Location
  ) {
    const navigation = this.router.getCurrentNavigation();
    const state = navigation?.extras.state as { employeeId: number,employeeName:string};
    this.employeeId = state?.employeeId;
    this.employeeName = state?.employeeName;
    console.log("Employee id",this.employeeId);
  }

  ngOnInit(): void {
    this.initForm();
  }


  
  initForm(): void {
    this.summaryForm = this.fb.group({
      date: ['', Validators.required]
    });
  }

//   onSubmit(): void {
//     if (this.summaryForm.valid) {
//         this.isLoading = true;
//         this.error = null;
//         const salaryRequest = { date: this.summaryForm.value.date };
//         this.employeeService.getEmpSummary(this.employeeId, salaryRequest).subscribe(
//             (response: any) => {
//               console.log("Summary response : ",response);
              
//                 this.employeeSummary = response.response;
//                 this.isLoading = false;
//             },
//             (error) => {
//                 console.error('Error fetching employee summary:', error);
//                 this.error = 'Failed to fetch employee summary. Please try again.';
//                 this.isLoading = false;
//             }
//         );
//     }
// }


onSubmit(): void {
  

  const date = this.summaryForm.get('date')?.value;
    if (date) {
      const formattedDate = new Date(date).toISOString().split('T')[0];
      if (this.summaryForm.valid) {
        this.isLoading = true;
        this.error = null;
        const month = this.summaryForm.value.date;
        const formattedDate = this.convertMonthToFirstDate(month);
        const salaryRequest = { date: formattedDate };
        this.employeeService.getEmpSummary(this.employeeId, salaryRequest).subscribe(
          (response: any) => {
            console.log("Summary response: ", response);
            this.employeeSummary = response.response;
            this.isLoading = false;
          },
          (error) => {
            console.error('Error fetching employee summary:', error);
            this.error = 'Failed to fetch employee summary. Please try again.';
            this.isLoading = false;
          }
        );
      }      console.log(formattedDate); // "yyyy-MM-dd"
    }
}


convertMonthToFirstDate(month: string): string {
  const [year, monthPart] = month.split('-');
  return `${year}-${monthPart}-01`;
}

goBack(){
  this.location.back();
}
 
}
