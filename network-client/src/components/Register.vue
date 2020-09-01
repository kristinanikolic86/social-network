<template>
    <div class = "container">
        <h1>Create a New Account</h1>
      <form class="form-signin"> 
        <input type="text" id="first-name" class="form-control" name="first-name" placeholder="First Name" v-model="newUser.firstName" required autofocus>
          <div class = "alert" v-if="!newUser.firstName">
              *Mandatory field.
            </div>
        <input type="text" id="last-name" class="form-control" name="last-name" placeholder="Last Name" v-model="newUser.lastName" required autofocus>
          <div class = "alert" v-if="!newUser.lastName">
              *Mandatory field.
            </div>
        <input type="text" id="username" class="form-control" name="username" placeholder="Username" v-model="newUser.username" required autofocus>
          <div class = "alert" v-if="!newUser.username">
              *Mandatory field.
            </div>
        <input type="password" id="inputPassword" class="form-control" name="username" placeholder="Password" v-model="newUser.password" required>
          <div class = "alert" v-if="!newUser.password">
              *Mandatory field.
            </div>
        <button type="submit" @click="addUser($event)" v-if="newUser.firstName && newUser.lastName && newUser.username && newUser.password">Register</button>
      </form>
         <h2> ... or sign in if you are already registered!</h2><router-link to="/login"><button>Sign in</button></router-link>
    </div>
</template>

<script lang="ts">

import { Component, Prop, Vue } from 'vue-property-decorator';
import axios from 'axios';
import User from '../models/User';

@Component
export default class Register extends Vue {

  newUser:User = new User({
    details:"Say something about yourself!"
  })

  mounted(){}

  
    addUser(e:Event){
      e.preventDefault();
           axios.post('http://localhost:9000/api/users', this.newUser).then( res =>
        this.$router.push('/login')
      ).catch(error=> {
        alert("Please fill in the form!")
        this.refresh()
      })
    }

       refresh(){
      window.location.reload()
    }
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

.container h1, h2 {
  color: #E88D18;
  text-align: center;
  margin-top:20px;
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

.container .alert {
  font-size: 0.8em;
  padding-bottom: 20px;
  color: red;
}
</style>