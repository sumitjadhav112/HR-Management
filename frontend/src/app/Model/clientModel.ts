export class RegistreClientObjClass {
    
        name: string='';
        contractDetails:string='';
        taskOrProject:string='';
        timeline:any; 
        totalAmount:number = 0;
     }


     export class getResponse {
        message: string='';
       response : any;
       status!: boolean ;
       statusCode: String ='';
     }

     export class LoginObjClass {
        mobile_number: string='';
        password : any;
       
     }