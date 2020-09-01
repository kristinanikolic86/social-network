export default interface FriendshipInterface {
    senderId?:number;
    accepterId?:number;
    requestStatus?:string
  }
  
  export default class Friendship implements FriendshipInterface {
    
    public senderId?:number;
    public accepterId?:number;
    public requestStatus?:string;
 
  
    constructor (f:FriendshipInterface) {
        this.senderId = f.senderId;
        this.accepterId = f.accepterId;
        this.requestStatus=f.requestStatus
    }
  }