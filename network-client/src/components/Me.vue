<template>
<html>
  <body>
      <div class= "container">
        <button id = "logout" v-if = "isLoggedIn()" @click = "logout()">Logout</button>
        <img @click=" refresh()" src = "https://www.qbakbak.com/assets/img/avatar-default.png"/>
        <h1 @click=" refresh()"> {{user.firstName + " "}} {{user.lastName}} </h1> 
        <nav class = "nav-second">
         <ul>
           <router-link to="/user/me/edit-profile"><li><a >Edit profile</a></li></router-link>
           <li><a @click="deleteProfile()">Delete profile</a></li>
           <router-link to="/user/me/friends"><li><a >Friends</a></li></router-link>
           <router-link to="/user/me/requests"><li><a >Requests</a></li></router-link>
           <li><a @click="toggle()">Add new post</a></li>
           <router-link to="/user/me/news-feed"><li><a >News Feed</a></li></router-link>
         </ul>
       </nav>
       <div class = "container2">
         <h3>About</h3>
       <p>{{user.details}}</p>
       </div>
        <form v-if="show" @submit="addPost($event)">
        <textarea name="text" id = "big" rows="20" cols="50" v-model="newPost.text"></textarea>
        <button type="submit">Post</button>
      </form>
            <div class = "card"  v-for = "p in posts" :key = "p.id">
              <nav class = "nav-second">
                <ul>
                 <li><a @click="changePost(p.id)" >Edit post</a></li>
                <li><a @click="deletePost(p.id)">Delete post</a></li>
              </ul>
             </nav>
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
        </body>
</html>
</template>


<script lang="ts">
import { Component, Prop, Vue } from 'vue-property-decorator';
import axios from 'axios';
import User from '../models/User';
import Post from '../models/Post';
import Love from '../models/Love';
import LoginComponent from './LoginComponent.vue';
import SecurityTokenAndUserInfo from '../models/SecurityTokenAndUserInfo';


@Component
export default class Me extends Vue {

  loginComponent: LoginComponent = new LoginComponent()

  currentTokenAndUserInfo: SecurityTokenAndUserInfo = this.loginComponent.getCurrentTokenAndUserInfo()

 myConfig = {
  headers : {"X-Auth-Token" : this.currentTokenAndUserInfo.token}
 }
  user:User = new User ({});
  posts: Post[] =[];
  show:boolean = false;
  newPost:Post=new Post({
    likes:0,
    userId:this.currentTokenAndUserInfo.user!.id 
  });

    mounted(){
      this.getUser()
      this.getPosts()
    }

    
   isLoggedIn(): boolean{
     if(this.currentTokenAndUserInfo!='') return true;
     else return false;
   }

   logout(): void {
      localStorage.removeItem('currentTokenAndUserInfo');
      this.$router.push('/login')
   }

   
   getUser(){
      axios.get('http://localhost:9000/api/user/'+ this.currentTokenAndUserInfo.user!.id, this.myConfig).then( res =>
      this.user = res.data
      ).catch(error=>this.$router.push('/**'))
    }

    refresh(){
      window.location.reload()
    }

    deleteProfile(){
      axios.delete('http://localhost:9000/api/user/'+this.currentTokenAndUserInfo.user!.id, this.myConfig).then(res=>
          this.$router.push('/register')
      )
    }

    getPosts(){
         axios.get('http://localhost:9000/api/user/'+this.currentTokenAndUserInfo.user!.id + '/posts', this.myConfig).then( res =>
      this.posts = res.data
      )
    }

    likePost(id:number){
      var newLove:Love = new Love({
        userId:this.currentTokenAndUserInfo.user!.id || 1,
        postId: id
      });
          axios.post('http://localhost:9000/api/loves', newLove, this.myConfig).then( res =>
          this.refresh()
      ).catch(error=> {
        alert("You cannot like the same post twice!")
      })
      
     
    }

    toggle(){
      this.show = !this.show
    }

    addPost(e:Event){
      e.preventDefault();
           axios.post('http://localhost:9000/api/posts', this.newPost, this.myConfig).then( res =>
        this.refresh()
      ).catch(error=> {
        alert("You have to add some text!")
      })
    }

    deletePost(id:number){
        axios.delete('http://localhost:9000/api/post/'+id, this.myConfig).then(res=>
          this.refresh()
      ).catch(error=> alert("The post is not found!"))
    }

    changePost(id:number){
       this.$router.push('/user/me/edit-post/'+id)
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
  width: 15%;

}

h1{
    cursor: pointer;
    color:rgb(85, 85, 85);

}

 h1:hover {
  color:#E88D18;
}

h3 {
  color:#E88D18;
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

.container2 {
  flex-direction: column;
  display: flex;
  justify-content: center;
  align-items: center;
  margin-top:20px;
  margin-right: 10%;
  margin-left: 10%;
  margin-bottom: 20px;

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

form {
  padding-top: 30px;
  width: 800px;
  flex-direction: column;
  display: flex;
  justify-content: center;
  align-items: center;
}

  textarea {
   width: 100%;
   height: 100px;
   padding: 12px 20px;
   border-radius: 4px;
   resize: none;
   outline: none;
   border: 1px solid rgb(148, 146, 146);
 }

 textarea:focus {
   box-shadow: 0 0 5px #E88D18;
   border: 1px solid #E88D18;
 }

 form button {
  color: #E88D18;
  background-color: white;
  padding: 15px;
  font-size: 1.2em;
  font-weight: bold;
  text-decoration:none;
  cursor: pointer;
  border: none;
  margin-bottom: 15px;
 }

form button:hover {
  color:#333;
  transition: 0.5s;
}

 .nav-first {
  display: flex;
  justify-content: center;
  align-items: center;
}

#logout {
  float:right;
    color: #E88D18;
  background-color: white;
  padding: 15px;
  font-size: 1.2em;
  font-weight: bold;
  text-decoration:none;
  cursor: pointer;
  border: none;
  margin-bottom: 15px;
}

#logout:hover {
    color:#333;
  transition: 0.5s;
}









</style>
