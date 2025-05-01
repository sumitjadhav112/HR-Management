import { Component, OnInit, ViewChild, ElementRef, Renderer2 } from '@angular/core';
import { Router } from '@angular/router';
import { GeniusServiceService } from '../genius-service.service';

@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.css']
})
export class HomepageComponent implements OnInit {
  showLogoutConfirm: boolean = false;
  notificationCount: number = 0;

  @ViewChild('notificationIcon') notificationIcon!: ElementRef;

  constructor(
    private router: Router,
    private geniusService: GeniusServiceService,
    private renderer: Renderer2
  ) {}

  ngOnInit() {
    this.getNotificationCount();
  }

  toggleLogoutConfirm() {
    this.showLogoutConfirm = !this.showLogoutConfirm;
  }

  logout() {
    localStorage.removeItem('isLoggedIn');
    this.router.navigate(['/login']);
  }

  toggleNotifications() {
    console.log('Viewing notifications...');
    this.router.navigate(['/notifications']);
    this.notificationCount = 0;
    this.renderer.removeClass(this.notificationIcon.nativeElement, 'has-unread');
  }

  getNotificationCount() {
    this.geniusService.getNotifications().subscribe((data: any) => {
      const newCount = data.response.filter((notification: any) => !notification.read).length;
      if (newCount > this.notificationCount) {
        this.notificationCount = newCount;
        // this.triggerNotificationAnimation();
      }
    });
  }

  // triggerNotificationAnimation() {
  //   // Remove the class
  //   this.renderer.removeClass(this.notificationIcon.nativeElement, 'has-unread');
    
  //   // Force a reflow
  //   void this.notificationIcon.nativeElement.offsetWidth;
    
  //   // Re-add the class to trigger the animation
  //   this.renderer.addClass(this.notificationIcon.nativeElement, 'has-unread');
  // }
}