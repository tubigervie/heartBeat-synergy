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
  public songId:string = '';
  public songResultArray: any[] = [];

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
        this.songResultArray = Object.entries(data); 
        console.log(Object.keys(data));
        console.log(Object.values(data));
        let innerData:any[]=Object.values(data);
        console.log(innerData);
        let innerInfo:any[]=Object.values(innerData[0]);
        console.log(innerInfo);
        let innerSongs:any[]=Object.values(innerInfo[1]);
        console.log(innerSongs);
        let innerSongsInfo:any[]=Object.values(innerSongs[0]);
        console.log(innerSongsInfo);
        let innerSongsInfoUrl:any[]=Object.values(innerSongsInfo[6]);
        console.log(innerSongsInfoUrl);
        let finalUrl = innerSongsInfoUrl[0];
        console.log(finalUrl);
        this.songResult = (finalUrl);
       
      }
     )
   }

   clearResults() {
     this.songResult = '';
     this.newReleases = '';
   }

   getSong() {
     this.accountService.getSongServ(this.token, this.songId).subscribe(
      (data:Object)=> {
        this.songResult = JSON.stringify(data);
        console.log(this.songResult);
      }
     )
   }
   

}
