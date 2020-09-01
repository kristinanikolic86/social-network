<template>
<html>
      <head>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
</head>
    <body>
        <router-link to="/user/me"><h3>Back To Profile</h3></router-link>
             <div class="search-container">
          <form>
            <input type="text" placeholder="Search.." name="search" v-model= "keyWord">
            <button type="submit" @click = "search($event)"><i class="fa fa-search"></i></button>
          </form>
        </div>
      <div class= "container">
        <h1 @click=" refresh()"> News Feed </h1> 
            <div>
               <div class = "card"  v-for = "p in finalPosts" :key = "p.id">
                <h2 @click="goToProfile(p.userId)">{{getUser(p.userId)}}</h2> 
               <p> {{p.text}} </p>
               <div> &#129505; {{p.likes}} </div>
                <nav class = "nav-second">
         <ul>
           <li><a @click="likePost(p.id)" >Like</a></li>
           <li><a @click="peopleLikedPost(p.id)" >People</a></li>
         </ul>
       </nav>
               </div>
          </div>
       </div>
       </body>
</html>
       </template>

<script lang="ts">
import { Component, Prop, Vue } from 'vue-property-decorator';
import axios from 'axios';
import User from '../models/User';
import Post from '../models/Post';
import Love from '../models/Love';
import Friendship from '../models/Friendship';
import LoginComponent from './LoginComponent.vue';
import SecurityTokenAndUserInfo from '../models/SecurityTokenAndUserInfo';


@Component
export default class MyNewsFeed extends Vue {

    loginComponent: LoginComponent = new LoginComponent()

  currentTokenAndUserInfo: SecurityTokenAndUserInfo = this.loginComponent.getCurrentTokenAndUserInfo()

 myConfig = {
  headers : {"X-Auth-Token" : this.currentTokenAndUserInfo.token}
 }

  keyWord:string = ''

    users:User[] = []
    finalPosts:Post[]=[]

    mounted(){

        this.getFriends()
       
    }

      getFriends(){
          var users:User[]=[]
          var posts: Post[]=[]
      axios.get('http://localhost:9000/api/friends/'+this.currentTokenAndUserInfo.user!.id, this.myConfig).then( res =>{
      users = res.data
      this.users = res.data
      console.log(users)
      for(var u of users){
        axios.get('http://localhost:9000/api/user/'+u.id+'/posts', this.myConfig).then( res => {
            posts = res.data
            this.finalPosts= this.finalPosts.concat(posts)
        }
        )
    
      }
       this.finalPosts = this.finalPosts.flat()
      })
    }

    getUser(id:number){
        for(var u of this.users){
            if(u.id == id){
                return u.firstName+" "+u.lastName
            }
        }
    }

    search(e:Event){
      e.preventDefault()
      this.$router.push('/search/'+this.keyWord)
    }

     likePost(id:number){
      var newLove = new Love({
        userId:this.currentTokenAndUserInfo.user!.id || 1,
        postId: id
      });
          axios.post('http://localhost:9000/api/loves', newLove, this.myConfig).then( res =>
          this.refresh()
      ).catch(error=> {
        alert("You cannot like the same post twice!")
      })
      
     
    }

     refresh(){
      window.location.reload()
    }

    peopleLikedPost(id:number){
      this.$router.push('/post/'+id+'/people')
    }

    goToProfile(id:number){
       this.$router.push('/user/'+id)
    }
}

</script>

<style scoped>
html, body {
  font-family: 'Lora';
  margin: 0;
  padding: 0;
  min-height: 500px;
}


.container {
  flex-direction: column;
  display: flex;
  justify-content: center;
  align-items: center;
  margin-top: 30px;
}


h1, h2{
    cursor: pointer;
    color:rgb(85, 85, 85);

}

 h1,h2:hover {
  color:#E88D18;
}

h3 {
  cursor: pointer;
   color:#E88D18;
}

h3:hover{
  color:rgb(85, 85, 85);
}

.card {
  flex-direction: column;
  display: flex;
  justify-content: center;
  align-items: center;
  width:800px;
  overflow: hidden;
  border:solid 1px lightsalmon;
  margin-bottom: 10px;
}

.card p {
  color: rgb(87, 86, 86);
  line-height: 1.8;
  font-size: 15px;
  text-align: justify;
  padding:20px;
}

.nav-second {
  display: flex;
  justify-content: center;
  align-items: center;
}

.nav-second ul {
  padding: 0px;
  margin:0px;
  list-style: none;
}

.nav-second ul li {
  float: left;
  cursor: pointer;
}

 .nav-second ul li a {
  display: inline-block;
  padding: 15px;
  font-size: 1.2em;
  font-weight: bold;
  text-decoration:none;
  color:#E88D18;
}
.nav-second ul li a:hover {
  color:#333;
  transition: 0.5s;
}

 .search-container {
  float:right;
  margin-top:-60px;
}

.search-container input[type=text] {
  padding: 6px;
  margin-top: 18px;
  font-size: 17px;
  border: solid 1px #E88D18;
  color:#E88D18;
}


  .search-container button {
  font-family: 'Verdana';
  float:right;
  text-decoration: none;
  padding: 6px 10px;
  margin-top: 15px;
  margin-right: 16px;
  background-color: white;
  color:#E88D18;
  font-size: 20px;
  border: 1px solid white;
  cursor: pointer;

}

 .search-container button:hover {
  background-color: whitesmoke;
  color: #E88D18;
  text-decoration: none;
}


</style>