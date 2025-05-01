import { Component, OnInit } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  Validators,
  AbstractControl,
  ValidationErrors,
} from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { InstallmentService } from 'src/app/services/installment.service';

@Component({
  selector: 'app-installments',
  templateUrl: './installments.component.html',
  styleUrls: ['./installments.component.css'],
})
export class InstallmentsComponent implements OnInit {
  installmentForm: FormGroup;
  clientId: any;
  clientName: string = '';
  totalAmount: number = 0;
  totalDiscount: number = 0;
  amountAfterDiscount: number = 0;
  installments: number[] = [0];
  isInstallmentSubmitted: boolean[] = [false];
  invoiceGenerated: boolean = false;

  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private installmentService: InstallmentService,
    private toastr: ToastrService
  ) {
    this.installmentForm = this.fb.group({});
    this.clientId = sessionStorage.getItem('clientId');
  }

  ngOnInit() {
    this.createForm();
    this.getClientDetails();
  }

  futureDateValidator(control: AbstractControl): ValidationErrors | null {
    const selectedDate = new Date(control.value);
    const currentDate = new Date();
    selectedDate.setHours(0, 0, 0, 0);
    currentDate.setHours(0, 0, 0, 0);
    return selectedDate > currentDate ? null : { notFutureDate: true };
  }

  viewInvoiceForClient() {
    this.router.navigate(['/invoice']);
  }

  createForm() {
    this.installmentForm = this.fb.group({
      amount0: ['', [Validators.required, Validators.min(1)]],
      dueDate0: ['', [Validators.required, this.futureDateValidator]],
      isPaid0: [false],
    });
  }

  getClientDetails() {
    this.installmentService.getClientDetails(this.clientId).subscribe(
      (res: any) => {
        this.clientName = res.response.client_name;
        this.totalAmount = res.response.totalAmount;
        this.totalDiscount = res.response.total_discount || 0;
        this.amountAfterDiscount = this.totalAmount - this.totalDiscount;
        console.log('Client details:', res);
      },
      (error) => {
        this.toastr.error('Error fetching client details');
      }
    );
  }

  addInstallment() {
    if (this.installments.length < 3) {
      const newIndex = this.installments.length;
      let remainingAmount = this.amountAfterDiscount;

      for (let i = 0; i < this.installments.length; i++) {
        const amountControl = this.installmentForm.get(`amount${i}`);
        if (amountControl?.value) {
          remainingAmount -= amountControl.value;
        }
      }

      this.installments.push(newIndex);
      this.isInstallmentSubmitted.push(false);

      this.installmentForm.addControl(
        `amount${newIndex}`,
        this.fb.control(newIndex === 2 ? remainingAmount : '', [
          Validators.required,
          Validators.min(1),
        ])
      );
      this.installmentForm.addControl(
        `dueDate${newIndex}`,
        this.fb.control('', [Validators.required, this.futureDateValidator])
      );
      this.installmentForm.addControl(
        `isPaid${newIndex}`,
        this.fb.control(false)
      );
    }
  }

  submitInstallment(index: number) {
    if (
      this.installmentForm.get(`amount${index}`)?.valid &&
      this.installmentForm.get(`dueDate${index}`)?.valid
    ) {
      const installment = {
        client_id: this.clientId,
        amount: this.installmentForm.get(`amount${index}`)?.value,
        dueDate: this.installmentForm.get(`dueDate${index}`)?.value,
        isPaid: this.installmentForm.get(`isPaid${index}`)?.value,
      };

      this.installmentService.createInstallment(installment).subscribe(
        (res: any) => {
          console.log(res);
          if (res.message == 'Cannot add more than 3 installments for a single client') {
            this.toastr.error(`You cannot add more than three installments.`);
          } else {
            this.toastr.success(`Installment ${index + 1} added successfully`);
            this.installmentForm.get(`amount${index}`)?.disable();
            this.installmentForm.get(`dueDate${index}`)?.disable();
            this.isInstallmentSubmitted[index] = true;
          }
        },
        (error) => {
          this.toastr.error(`Error adding installment ${index + 1}`);
        }
      );
    }
  }

  generateInvoice() {
    this.installmentService.generateInvoice(sessionStorage.getItem('clientId')).subscribe(
      (data) => {
        this.invoiceGenerated = true;
        console.log(data);
        
      },
      (error) => {
        this.toastr.error('Error generating invoice');
      }
    );
  }
}

