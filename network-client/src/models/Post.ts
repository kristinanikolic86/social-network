export default interface PostInterface {
    id?:number;
    text?:string;
    likes?:number;
    userId?:number;
  }
  
  export default class Post implements PostInterface {

    public id?:number;
    public text?:string;
    public likes?: number;
    public userId?:number;
 
  
    constructor (p:PostInterface) {
        this.id=p.id;
        this.text = p.text;
        this.likes = p.likes;
        this.userId = p.userId;
    }
  }