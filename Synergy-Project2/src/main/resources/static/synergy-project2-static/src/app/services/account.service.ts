import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';


@Injectable({
  providedIn: 'root'
})
export class AccountService {

  
  clientId:string = 'b652928c89cc4c72b3c12c1c255bb631';
  clientSecret:string = '3ba7579a1a7c4f09bc20a5cee2ff1849';

  requestUrl:string = 'https://api.spotify.com/v1/';
  authUrl:string = 'https://accounts.spotify.com/authorize';
  tokenUrl:string = 'https://accounts.spotify.com/api/token';

  
  base64Credentials = btoa(this.clientId+':'+this.clientSecret);
  authTokenBody = new URLSearchParams({'grant_type':'client_credentials'});
  authTokenHeaders = new HttpHeaders({'Authorization':'Basic ' +this.base64Credentials, 'Content-Type': 'application/x-www-form-urlencoded' });
  

  constructor(private http:HttpClient) { }

  getTokenServ():Observable<Object> {
    
  return this.http.post(this.tokenUrl, this.authTokenBody, { headers: this.authTokenHeaders }) as Observable<Object>;

  }


  getnewReleasesServ(token:string):Observable<Object> {


    return this.http.get(this.requestUrl + 'browse/new-releases', {headers: new HttpHeaders({'Authorization': 'Bearer '+token })})
  }

  searchSongServ(token:string, song:string, artist:string):Observable<Object> {
    return this.http.get(this.requestUrl + 'search?q=' +song+ ' ' + artist+'&type=track&market=us&offset=0&limit=5', {headers: new HttpHeaders({'Authorization': 'Bearer '+token })})
  }

  getSongServ(token:string, songId:string):Observable<Object> {
    return this.http.get(this.requestUrl +'tracks/'+songId +'?market=us', {headers: new HttpHeaders({'Authorization': 'Bearer '+token })})
  }
    
}
