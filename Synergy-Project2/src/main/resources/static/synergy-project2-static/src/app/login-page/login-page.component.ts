import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { AccountService } from '../services/account.service';
import { Track } from '../models/track';

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.css']
})
export class LoginPageComponent implements OnInit {

  public token: string = '';
  public newReleases: string = '';
  public songSearch: string = '';
  public artistSearch: string = '';
  public songResult: string = '';
  public songId: string = '';
  public albumImageUrl: string = '';
  public track:Track|null = null;


  constructor(private accountService: AccountService) { }

  ngOnInit(): void {
  }

  connectAccount() {
    this.accountService.getTokenServ().subscribe(
      (data: Object) => {
        this.token = Object.values(data)[0]
      });
  }

  getNewReleases() {
    this.accountService.getnewReleasesServ(this.token).subscribe(
      (data: Object) => {
        this.newReleases = JSON.stringify(data);
      }
    )

  }

  clearResults() {
    this.newReleases = '';
  }

  getSong():Track {
    this.accountService.searchSongServ(this.token, this.artistSearch, this.songSearch).subscribe(
      (data: Object) => {
        let innerData: any[] = Object.values(data);
        let innerInfo: any[] = Object.values(innerData[0]);
        let innerSongs: any[] = Object.values(innerInfo[1]);
        let innerSongsInfo: any[] = Object.values(innerSongs[0]);
        console.log(innerInfo);
        let innerSongsInfoUrl: any[] = Object.values(innerSongsInfo[6]);
        let finalUrl = innerSongsInfoUrl[0];
        this.songId = finalUrl.substring(31, finalUrl.length);
        
        this.accountService.getSongServ(this.token, this.songId).subscribe(
          (data: Object) => {
            let innerData = Object.values(data);
            let innerArtistandAlbum: any[] = Object.values(innerData[0]);
            let innerArtistInfo: any[] = Object.values(innerArtistandAlbum[1]);
            let innerArtistDetails: any[] = Object.values(innerArtistInfo[0]);
            let artistName = innerArtistDetails[3];
            let albumName = innerArtistandAlbum[6];
            let songName = innerData[11]; 
            let innerAlbumImageInfo: any[] = Object.values(innerArtistandAlbum[5]);
            let innerAlbumImageDetails: any[] = Object.values(innerAlbumImageInfo[0]);
            let albumImageUrl = innerAlbumImageDetails[1];
            this.albumImageUrl = albumImageUrl;
            let track = new Track(this.songId, songName, artistName, albumName, albumImageUrl);
            this.track = track;
            return track;
          }
        )
      }

    )
  return new Track('','','','','');
  }


}
