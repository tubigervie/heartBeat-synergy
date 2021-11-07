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
  public songUrl:string = '';
  public songResult:string = '';
  public songId:string = '';
  public artistName:string = '';
  public albumName:string = '';
  public songName:string = '';
  public albumImageUrl:string = '';
 

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
        let innerData:any[]=Object.values(data);
        let innerInfo:any[]=Object.values(innerData[0]);
        let innerSongs:any[]=Object.values(innerInfo[1]);
        let innerSongsInfo:any[]=Object.values(innerSongs[0]);
        let innerSongsInfoUrl:any[]=Object.values(innerSongsInfo[6]);
        let finalUrl = innerSongsInfoUrl[0];
        this.songId= finalUrl.substring(31, finalUrl.length);
        this.songUrl =finalUrl;
       
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
        let innerData = Object.values(data);
        console.log(innerData);
        console.log(JSON.stringify(innerData));
        let innerArtistandAlbum:any[] = Object.values(innerData[0]);
        console.log(innerArtistandAlbum);
        let innerArtistInfo:any[] = Object.values(innerArtistandAlbum[1]);
        console.log(innerArtistInfo);
        let innerArtistDetails:any[] = Object.values(innerArtistInfo[0]);
        console.log(innerArtistDetails);
        let artistName = innerArtistDetails[3];
        console.log(artistName);
        this.artistName = artistName;
        let albumName = innerArtistandAlbum[6];
        this.albumName = albumName;
        let songName = innerData[11];
        this.songName = songName;
        let innerAlbumImageInfo:any[] = Object.values(innerArtistandAlbum[5]);
        console.log(innerAlbumImageInfo);
        let innerAlbumImageDetails:any[] = Object.values(innerAlbumImageInfo[0]);
        console.log(innerAlbumImageDetails);
        let albumImageUrl = innerAlbumImageDetails[1];
        console.log(albumImageUrl);
        this.albumImageUrl = albumImageUrl;

      }
     )
   }
   

}
