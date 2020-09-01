<template>
<html>
<router-link to="/user/me"><h3>Back To Profile</h3></router-link>
     <div class = "container">
                 <h1>Edit your profile</h1>
      <form>
         <input type="text" id="first-name" class="form-control" name="first-name" placeholder="First Name" v-model="newUser.firstName" required autofocus>
          <div class = "alert" v-if="!newUser.firstName">
              *Mandatory field.
            </div>
               <input type="text" id="last-name" class="form-control" name="last-name" placeholder="Last Name" v-model="newUser.lastName" required autofocus>
          <div class = "alert" v-if="!newUser.lastName">
              *Mandatory field.
            </div>
        <textarea name="text" id = "big" rows="20" cols="50" v-model="newUser.details"></textarea>
        <button type="submit" @click="editProfile(newUser.id, $event)" v-if="newUser.firstName && newUser.lastName">Edit</button>
      </form>
     </div>
</html>
</template>

<script lang="ts">
import { Component, Prop, Vue } from 'vue-property-decorator';
import axios from 'axios';
import User from '../models/User';
import LoginComponent from './LoginComponent.vue';
import SecurityTokenAndUserInfo from '../models/SecurityTokenAndUserInfo';

@Component
export default class EditProfile extends Vue {

    loginComponent: LoginComponent = new LoginComponent()

  currentTokenAndUserInfo: SecurityTokenAndUserInfo = this.loginComponent.getCurrentTokenAndUserInfo()

 myConfig = {
  headers : {"X-Auth-Token" : this.currentTokenAndUserInfo.token}
 }

    newUser:User = new User({
    })

    mounted(){
        this.getUser()
    }

    getUser(){
           axios.get('http://localhost:9000/api/user/'+this.currentTokenAndUserInfo.user!.id, this.myConfig).then( res =>
      this.newUser = res.data
           )
    }

    editProfile(id:number, e:Event){
      e.preventDefault();
            axios.put('http://localhost:9000/api/user/'+id, this.newUser, this.myConfig).then( res =>
       this.$router.push('/user/me')
           ).catch(error=> alert("You have to add some text!"))
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
  width: 700px;
  flex-direction: column;
  display: flex;
  justify-content: center;
  align-items: center;
}

.container textarea {
  width: 100%;
  padding: 12px 20px;
  outline: none;
  border: 1px solid rgb(148, 146, 146);
  margin-bottom: 10px;
}

.container textarea:focus {
  box-shadow: 0 0 5px #E88D18;
  border: 1px solid #E88D18;
}

.container input {
  width: 100%;
  padding: 12px 20px;
  outline: none;
  border: 1px solid rgb(148, 146, 146);
  margin-bottom: 20px;
}

.container input:focus {
  box-shadow: 0 0 5px #E88D18;
  border: 1px solid #E88D18;
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

h3 {
  cursor: pointer;
   color:#E88D18;
}

h3:hover{
  color:rgb(85, 85, 85);
}

</style>