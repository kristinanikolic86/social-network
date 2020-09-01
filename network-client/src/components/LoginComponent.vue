<template>
      <body>
    <div class = "container">
          <h1>If you already have an account...</h1>
      <form class="form-signin" >
        <input type="text" id="username" class="form-control" name="username" placeholder="Username" v-model="newLogin.username" required autofocus>
        <input type="password" id="inputPassword" class="form-control" name="username" placeholder="Password" v-model="newLogin.password" required> 
        <button @click="login($event)">Sign in</button>
      </form>
    </div>
  </body>
</template>

<script lang="ts">
import { Component, Prop, Vue } from 'vue-property-decorator';
import axios from 'axios';
import Login from '../models/Login';
import SecurityTokenAndUserInfo from '../models/SecurityTokenAndUserInfo';
import User from '../models/User';

@Component
export default class LoginComponent extends Vue {

  

    newLogin: Login = new Login({
    })

   
    mounted(){

    }

   login(e: Event) {
     e.preventDefault();
     let securityTokenAndUserInfo:SecurityTokenAndUserInfo = new SecurityTokenAndUserInfo({})
        axios.post('http://localhost:9000/api/login', this.newLogin).then( res => {
          securityTokenAndUserInfo = res.data 
             console.log(JSON.stringify(securityTokenAndUserInfo))  
             localStorage.setItem('currentTokenAndUserInfo', JSON.stringify(securityTokenAndUserInfo) )
             this.$router.push('/user/me')
        }     
      ).catch(error=> alert('Invalid input'))
  }

  getCurrentTokenAndUserInfo(){
    return JSON.parse(localStorage.currentTokenAndUserInfo)
  }


  //   login2(){
  //   this.login()
  //     if(this.isLoggedIn()==true){
  //        this.$router.push('/user/me/')
  //     }
  //   }

  // getToken(): String {
  //   var currentUser = JSON.parse(localStorage.getItem('currentUser') || '{}');
  //   var token = currentUser && currentUser.token;
  //   return token ? token : "";
  // }

  // logout(): void {
  //   localStorage.removeItem('currentUser');
  // }

  // isLoggedIn(): boolean{
  //   if(this.getToken()!='') return true;
  //   else return false;
  // }

  // getCurrentUser(){
  //   if(localStorage.currentUser){
  //     return JSON.parse(localStorage.currentUser);
  //   }
  //   else{
  //     return undefined;
  //   }
  // }
}
</script>

<style scoped>
.container {
  flex-direction: column;
  display: flex;
  justify-content: center;
  align-items: center;
  margin-top:30px;
  min-height: 500px;
}

.container form {
  padding-top: 30px;
  width: 300px;
  flex-direction: column;
  display: flex;
  justify-content: center;
  align-items: center;
}

.container input {
  width: 100%;
  padding: 12px 20px;
  outline: none;
  border: 1px solid rgb(148, 146, 146);
  margin-bottom: 10px;
}

.container input:focus {
  box-shadow: 0 0 5px #E88D18;
  border: 1px solid #E88D18;
}

.container h1 {
  color: #E88D18;
  text-align: center;
  margin-bottom: 30px;
}

.container button {
font-family: 'Verdana';
  background-color: #E88D18;
  border: none;
  color: white;
  padding: 16px;
  text-decoration: none;
  margin-top: 20px;
  cursor: pointer;
  margin-bottom: 30px;
  font-size: 1.2em;
}

.container button:hover {
  background-color: #333;
}
</style>
