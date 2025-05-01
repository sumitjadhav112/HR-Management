import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { getResponse } from '../Model/clientModel';
import { GeniusServiceService } from '../genius-service.service';
import { ToastrService } from 'ngx-toastr';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent {
  LoginData: FormGroup;
  showPassword: boolean = false;

  togglePasswordVisibility() {
    this.showPassword = !this.showPassword;
  }

  constructor(
    private fb: FormBuilder,
    private service: GeniusServiceService,
    private toastr: ToastrService,
    private route: Router
  ) {
    this.LoginData = this.fb.group({
      mobile_number: [
        '',
        [
          Validators.required,
          Validators.maxLength(10),
          Validators.pattern('^[0-9]{1,10}$'),
        ],
      ],
      password: ['', [Validators.required]],
    });
  }

  LoginFormSubmit() {
    console.log('inserteddata', this.LoginData.value);
    this.service
      .Login_Fun(this.LoginData.value)
      .subscribe((res: getResponse) => {
        console.log('registerRes', res);
        if (res?.message === 'Login Successfully.') {
          localStorage.setItem('isLoggedIn','true')
          this.toastr.success(res.message);
          this.LoginData.reset();
          this.route.navigate(['/homepage']);
        } else {
          this.toastr.error(res.message);
          localStorage.setItem('isLoggedIn','false')
        }
      });
  }

  validateMobileNumber(event: KeyboardEvent) {
    const input = event.target as HTMLInputElement;
    const value = input.value;

    if (!/^\d*$/.test(value + event.key)) {
      event.preventDefault();
    }

    if (value.length >= 10 && event.key !== 'Backspace') {
      event.preventDefault();
    }
  }
}
