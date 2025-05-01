import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { GeniusServiceService } from 'src/app/genius-service.service';
import { InstallmentService } from 'src/app/services/installment.service';

@Component({
  selector: 'app-notifications',
  templateUrl: './notifications.component.html',
  styleUrls: ['./notifications.component.css']
})
export class NotificationsComponent implements OnInit {
notificationArray:any[]= [];
displayDialog: boolean = false;
  selectedClient: any;

  constructor(private geniusService: GeniusServiceService,
    private installmentService: InstallmentService,
    private router:Router
  
  ){}
  ngOnInit() {
    this.getAllNotifications();
  }
  navigateToHome() {
    this.router.navigate(['/homepage']); // Adjust this route as needed
  }
  getAllNotifications(){
    this.geniusService.getNotifications().subscribe((data:any)=>{
      this.notificationArray=data.response;
      console.log("array response : ",this.notificationArray);
      
      console.log(data);
  });
}
showDetails(client: any) {
  this.installmentService.getClientDetails(client.client_id).subscribe(
    (clientDetails: any) => {
      console.log("client details :",clientDetails.response);
      
      this.selectedClient = clientDetails.response;
      this.displayDialog = true;
    },
    (error) => {
      console.error('Error fetching client details:', error);
      // Handle error (e.g., show an error message)
    }
  );
}
getIconClass(type: string): string {
  switch(type) {
    case 'info': return 'info';
    case 'success': return 'success';
    case 'warning': return 'warning';
    case 'danger': return 'danger';
    default: return 'info';
  }
}

getIconName(type: string): string {
  switch(type) {
    case 'info': return 'fa-info-circle';
    case 'success': return 'fa-check-circle';
    case 'warning': return 'fa-exclamation-triangle';
    case 'danger': return 'fa-times-circle';
    default: return 'fa-bell';
  }
}

viewed(notification:any){
  console.log("viewed data :",notification.id);
  this.geniusService.markAsViewed(notification.id).subscribe((data=>{
    console.log("mark as viewed response : ",data);
  }))
}

}
