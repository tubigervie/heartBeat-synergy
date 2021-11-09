import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class TransferService {

  private username:string = '';
  private password:string = '';

  constructor() { }

  setUsername(username:string){
    this.username=username;
  }
  getUsername():string{
    return this.username;
  }

  setPassword(password:string) {
    this.password=password;
  }

  getPassword():string{
    return this.password
  }
}
