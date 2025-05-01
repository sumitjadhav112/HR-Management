import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators, AbstractControl, ValidationErrors } from '@angular/forms';
import { GeniusServiceService } from 'src/app/genius-service.service';
import { ToastrService } from 'ngx-toastr';
import { getResponse } from 'src/app/Model/clientModel';
import { Router } from '@angular/router';
import { Location } from '@angular/common';

@Component({
  selector: 'app-client-registration',
  templateUrl: './client-registration.component.html',
  styleUrls: ['./client-registration.component.css'],
})
export class ClientRegistrationComponent {
  ClientRegisterData: FormGroup;
  currentDate: string;

  constructor(
    private fb: FormBuilder,
    private service: GeniusServiceService,
    private toastr: ToastrService,
    private router: Router,
    private location: Location
  ) {
    this.ClientRegisterData = this.fb.group({
      client_name: ['', [Validators.required, Validators.pattern('^[a-zA-Z\\s]+$'), Validators.maxLength(50)]],
      mobile_number: ['', [Validators.required, Validators.pattern('^\\d{10}$')]],
      address: ['', [Validators.required, Validators.maxLength(100)]],
      startDate:['',[Validators.required]],
      timeline: ['', [Validators.required, this.futureDateValidator]],
      totalAmount: ['', Validators.required],
      projectDetails: ['', Validators.required],
      client_email: ['', [Validators.required, Validators.email]],
      total_discount: [''],
    });

    this.currentDate = new Date().toISOString().split('T')[0];
  }

  futureDateValidator(control: AbstractControl): ValidationErrors | null {
    const selectedDate = new Date(control.value);
    const currentDate = new Date();
    selectedDate.setHours(0, 0, 0, 0);
    currentDate.setHours(0, 0, 0, 0);
    return selectedDate >= currentDate ? null : { notFutureDate: true };
  }

  onSubmitReactiveForm() {
    console.log("html values ",this.ClientRegisterData.value);
    if (this.ClientRegisterData.valid) {
      const formattedTimeline = new Date(this.ClientRegisterData.value.timeline).toISOString();
      const formatteedstrtDate = new Date(this.ClientRegisterData.value.startDate).toISOString();
      this.ClientRegisterData.patchValue({ timeline: formattedTimeline });
      this.ClientRegisterData.patchValue({ startDate: formatteedstrtDate });
      this.service.Client_Register_Fun(this.ClientRegisterData.value).subscribe(
        (res: getResponse) => {
          if (res?.message === 'Client addedd Successfully') {
            this.toastr.success(res.message);
            sessionStorage.setItem('clientId', res.response.client_id);
            this.router.navigate(['/installments', res.response.client_id]);
            this.ClientRegisterData.reset();
          } else {
            this.toastr.error(res.message);
          }
        },
        (error) => {
          this.toastr.error('An error occurred while registering the client.');
        }
      );
    } else {
      this.markFormGroupTouched(this.ClientRegisterData);
    }
  }

  markFormGroupTouched(formGroup: FormGroup) {
    Object.values(formGroup.controls).forEach(control => {
      control.markAsTouched();

      if (control instanceof FormGroup) {
        this.markFormGroupTouched(control);
      }
    });
  }

  goBack() {
    this.location.back();
  }

  // Helper methods for template
  isFieldInvalid(fieldName: string): boolean {
    const control = this.ClientRegisterData.get(fieldName);
    return control ? control.invalid && (control.dirty || control.touched) : false;
  }

  getErrorMessage(fieldName: string): string {
    const control = this.ClientRegisterData.get(fieldName);
    if (control) {
      if (control.errors?.['required']) {
        return `${this.capitalizeFirstLetter(fieldName)} is required.`;
      }
      if (control.errors?.['pattern']) {
        if (fieldName === 'client_name') {
          return 'Name must contain only letters (a-z, A-Z).';
        }
        if (fieldName === 'mobile_number') {
          return 'Please enter a valid mobile number (10 digits).';
        }
        if (fieldName === 'address') {
          return 'Address must not exceed 100 characters.';
        }
      }
      if (control.errors?.['email']) {
        return 'Please enter a valid email address.';
      }
      if (control.errors?.['startDate']) {
        return 'Please enter the start date of the project.';
      }
      if (control.errors?.['notFutureDate']) {
        return 'Date must be current or in the future.';
      }
    }
    return '';
  }

  capitalizeFirstLetter(string: string): string {
    return string.charAt(0).toUpperCase() + string.slice(1);
  }
}