import User from './User';

export default  interface SecurityTokenAndUserInfoInterface {
    token?:string;
    user?:User;
  }
  
  export  default class SecurityTokenAndUserInfo implements SecurityTokenAndUserInfoInterface {
    public token?:string;
    public user?:User;

 
  
    constructor (s:SecurityTokenAndUserInfoInterface) {
    this.token = s.token;
      this.user = s.user;
    }
  }
