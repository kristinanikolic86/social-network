export default  interface UserInterface {
    id?:number;
    firstName?:string;
    lastName?:string;
    username?:string;
    password?:string;
    picture?:string;
    details?:string
  }
  
  export  default class User implements UserInterface {
    public id?:number;
    public firstName?:string;
    public lastName?:string;
    public username?:string;
    public password?:string;
    public picture?:string;
    public details?:string
 
  
    constructor (u:UserInterface) {
      this.id = u.id;
      this.firstName = u.firstName;
      this.lastName = u.lastName;
      this.username = u.username;
      this.password = u.password;
      this.picture = u.picture;
      this.details = u.details
    }
  }
