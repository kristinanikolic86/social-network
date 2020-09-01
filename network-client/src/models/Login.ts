export default  interface LoginInterface {
    username?:string;
    password?:string;
  }
  
  export  default class Login implements LoginInterface {
    public username?:string;
    public password?:string;

 
  
    constructor (l:LoginInterface) {
      this.username = l.username;
      this.password = l.password;
    }
  }