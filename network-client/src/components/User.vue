<template>
<html>
    <body>

        <button class = "friend" id="add-friend" v-if="show==0" @click="addFriend()">Add friend</button>
        <button class = "friend" v-if="show==1" disabled="true">Pending</button>
        
      <div class= "container">
        <img @click=" refresh()" src = "https://www.qbakbak.com/assets/img/avatar-default.png"/>
        <h1 @click=" refresh()"> {{user.firstName + " "}} {{user.lastName}} </h1> 
                <div class = "container2">
       <p>{{user.details}}</p>
       </div>
            <div v-if="posts&&show==2">
               <div class = "card"  v-for = "p in posts" :key = "p.id">
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
export default class Me extends Vue {

    loginComponent: LoginComponent = new LoginComponent()

  currentTokenAndUserInfo: SecurityTokenAndUserInfo = this.loginComponent.getCurrentTokenAndUserInfo()

 myConfig = {
  headers : {"X-Auth-Token" : this.currentTokenAndUserInfo.token}
 }

    user:User = new User({})
    id = this.currentTokenAndUserInfo.user!.id
    posts:Post[] = []
    show:number =1

    mounted(){
        this.getFriendship()
    }

       getUser(){
           axios.get('http://localhost:9000/api/user/'+Number.parseInt(this.$route.params.id), this.myConfig).then( res =>
      this.user = res.data
           ).catch(error=> this.$router.push('/**'))
    }

      refresh(){
      window.location.reload()
    }

    getFriendship() {
        if(this.id==Number.parseInt(this.$route.params.id)) {
            this.$router.push('/user/me')
        } else {
            this.getUser()
               axios.get('http://localhost:9000/api/checkfriendship/'+this.id +'/'+Number.parseInt(this.$route.params.id), this.myConfig).then( res =>{
            var friendship = new Friendship({})
            friendship = res.data
        if(friendship!=null && friendship.requestStatus == "Pending"){
                 this.show=1;

           } else if(friendship!=null &&friendship.requestStatus == "Accepted") {
               axios.get('http://localhost:9000/api/user/'+Number.parseInt(this.$route.params.id)+'/posts', this.myConfig).then( res =>{
                this.posts = res.data
                this.show = 2;}
               )
           } else if (friendship==null){
               this.show=0
           }
      }
      
           )
          
         
        }
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

    addFriend(){
        var friendship = new Friendship({
            senderId :this.id,
            accepterId :Number.parseInt(this.$route.params.id),
            requestStatus:"Pending"
        })
              axios.post('http://localhost:9000/api/friendship', friendship, this.myConfig).then( res =>
            this.refresh()
           )
    }

     peopleLikedPost(id:number){
      this.$router.push('/post/'+id+'/people')
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

.container img {
   padding-top: 30px;
  cursor: pointer;
  width: 20%;

}

h1{
    cursor: pointer;
    color:rgb(85, 85, 85);

}

 h1:hover {
  color:#E88D18;
}

.container2 {
  flex-direction: column;
  display: flex;
  justify-content: center;
  align-items: center;
  margin-top:20px;
  margin-right: 10%;
  margin-left: 10%;

}

.container2 p {
  color: rgb(87, 86, 86);
  line-height: 1.8;
  font-size: 18px;
  text-align: justify;
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



.friend {
    background-color: #E88D18;
    font-size: 25px;
    color:white;
    margin-top: 20px;
    border: none;
    padding: 10px;
    border:solid 1px rgb(126, 124, 124);
}

 #add-friend {
   cursor: pointer;
}

#add-friend:hover {
   background-color:#333;
  transition: 0.5s;
}

h3 {
  cursor: pointer;
   color:#E88D18;
}

h3:hover{
  color:rgb(85, 85, 85);
}

</style>