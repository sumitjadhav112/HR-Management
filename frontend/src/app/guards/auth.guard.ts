import { inject } from '@angular/core';
import { CanActivateFn } from '@angular/router';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';

export const authGuard: CanActivateFn = (route, state) => {
  const router = inject(Router);
  const toastr = inject(ToastrService);
  
  let token = localStorage.getItem('isLoggedIn');
  
  if (token == 'false') {
    toastr.error('Not authorized', 'Access Denied');
    router.navigate(['/login']);
    console.log("not authorized..!");
    return false;
  } else {
    
    return true;
  }
};