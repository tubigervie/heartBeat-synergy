import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { AccountService } from '../services/account.service';

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.css']
})
export class LoginPageComponent implements OnInit {

  public token:string = '';
  public newReleases:string = '';
  public song:string = '';
  public songResult:string = '';

  constructor(private accountService:AccountService) { }

  ngOnInit(): void {
  }

  connectAccount() {
  this.accountService.getTokenServ().subscribe(
    (data:Object)=> {
      console.log(data)
    console.log(Object.values(data))
      this.token = Object.values(data)[0]
    console.log(this.token);} );     
  }

  getNewReleases() {
    this.accountService.getnewReleasesServ(this.token).subscribe(
      (data:Object)=> {
        this.newReleases = JSON.stringify(data);
      }
    )

   }

   searchSong() {
     this.accountService.searchSongServ(this.token, this.song).subscribe(
      (data:Object)=> {
        this.songResult = JSON.stringify(data);
        console.log(this.songResult);
      }
     )
   }

   clearResults() {
     this.songResult = '';
     this.newReleases = '';
   }
   

}
