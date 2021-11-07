import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { AccountService } from '../services/account.service';

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.css']
})
export class LoginPageComponent implements OnInit {

  constructor(private accountService:AccountService) { }

  ngOnInit(): void {
  }

  connectAccount() {
  this.accountService.getToken().subscribe((data:Observable<any>)=> console.log(data));
    
  
  }

}
