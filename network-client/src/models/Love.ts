export default interface LoveInterface {
    userId:number;
    postId:number;
  }
  
  export default class Love implements LoveInterface {
    
    public userId:number;
    public postId:number;
 
  
    constructor (l:LoveInterface) {
        this.userId = l.userId;
        this.postId = l.postId;
    }
  }