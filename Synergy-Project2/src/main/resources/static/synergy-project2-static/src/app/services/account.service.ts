import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';


@Injectable({
  providedIn: 'root'
})
export class AccountService {

  // groups spotify app account
  // clientId:string = 'b652928c89cc4c72b3c12c1c255bb631';
  // clientSecret:string = '3ba7579a1a7c4f09bc20a5cee2ff1849';

  // phil's spotify app account
  clientId:string = '73b711d1fcc346988ad44a364fe52b5a';
  clientSecret:string = '8047fa6e7f19446c8b719e6a2aa24170';


  requestUrl:string = 'https://api.spotify.com/v1/';
  authUrl:string = 'https://accounts.spotify.com/authorize';
  tokenUrl:string = 'https://accounts.spotify.com/api/token';
  token2Url:string = '';

  // additional params needed to create OAUTH 2.0 token
  redirectUri:string = 'http://localhost:4200'; //figure out what goes here
  callbackUrl:string = 'http://localhost:4200'; //figure out what goes here
  responseType:string = 'code';
  scopes:string = 'user-library-read';

  
  base64Credentials = btoa(this.clientId+':'+this.clientSecret);
  authTokenBody = new URLSearchParams({'grant_type':'client_credentials'});
  authTokenHeaders = new HttpHeaders({'Authorization':'Basic ' +this.base64Credentials, 'Content-Type': 'application/x-www-form-urlencoded' });

  // change this to match implicit token header
  auth2TokenHeaders = new HttpHeaders({'Authorization':'Basic ' +this.base64Credentials, 'Content-Type': 'application/x-www-form-urlencoded' });

  constructor(private http:HttpClient) { }

  getTokenServ():Observable<Object> {
    
    return this.http.post(this.tokenUrl, this.authTokenBody, { headers: this.authTokenHeaders }) as Observable<Object>;

  }

  getAccessToken(){
    this.token2Url = this.buildRequest(this.clientId, this.redirectUri, this.scopes);
    console.log(this.token2Url);

  }

  // // get OAUTH 2.0 token
  // getAuthTokenServ():Observable<Object> {
    
  //   return this.http.post(this.token2Url, this.authTokenBody, { headers: this.auth2TokenHeaders }) as Observable<Object>;
  
  // }

  // build OAUTH request
  buildRequest(client_id:string, redirect_uri:string, scopes:string){
    // var client_id = 'CLIENT_ID';
    // var redirect_uri = 'http://localhost:8888/callback';

    var state = 'r9XzPTQ8fjBqfwC5';
        
    var url = 'https://accounts.spotify.com/authorize';
    url += '?response_type=token';
    url += '&client_id=' + encodeURIComponent(client_id);
    url += '&scope=' + encodeURIComponent(scopes);
    url += '&redirect_uri=' + encodeURIComponent(redirect_uri);
    url += '&state=' + encodeURIComponent(state);
    url += '&callback_url=' + encodeURIComponent(this.callbackUrl);

    return url;
    
  }


  getnewReleasesServ(token:string):Observable<Object> {
    return this.http.get(this.requestUrl + 'browse/new-releases', {headers: new HttpHeaders({'Authorization': 'Bearer '+token })})
  }

  searchSongServ(token:string, song:string):Observable<Object> {
    return this.http.get(this.requestUrl + 'search?q=' +song+'&type=track&market=us&offset=0&limit=5', {headers: new HttpHeaders({'Authorization': 'Bearer '+token })})
  }

  getSongServ(token:string, songId:string):Observable<Object> {
    return this.http.get(this.requestUrl +'tracks/'+songId +'?market=us', {headers: new HttpHeaders({'Authorization': 'Bearer '+token })})
  }

  getGenres(token:string):Observable<Object> {
    return this.http.get(this.requestUrl + 'recommendations/available-genre-seeds', {headers: new HttpHeaders({'Authorization': 'Bearer '+token })})
  }

  getTopArtists(token:string):Observable<Object>{
    return this.http.get(this.requestUrl + 'recommendations/me/top/artists?limit=5', {headers: new HttpHeaders({'Authorization': 'Bearer '+token })})
  }
    
}
