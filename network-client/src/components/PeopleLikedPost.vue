<template>
<html>
    <body>
        <router-link to="/user/me"><h3>Back To Profile</h3></router-link>
            <div class = "card-wrapper">
            <div class = "card"  v-for = "u in users" :key = "u.id">
                <div id = "center-me">
                <img @click=" refresh()" src = "https://www.qbakbak.com/assets/img/avatar-default.png"/>
              </div>
                <div class = "center">
                <h1>{{u.firstName + " "}} {{u.lastName}}</h1>
          <!-- <div id = "date">{{article.publicationDate | date}}</div> -->
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
import LoginComponent from './LoginComponent.vue';
import SecurityTokenAndUserInfo from '../models/SecurityTokenAndUserInfo';

@Component
export default class PeopleLikedPost extends Vue {

    loginComponent: LoginComponent = new LoginComponent()

  currentTokenAndUserInfo: SecurityTokenAndUserInfo = this.loginComponent.getCurrentTokenAndUserInfo()

 myConfig = {
  headers : {"X-Auth-Token" : this.currentTokenAndUserInfo.token}
 }
    
      users: User[] = []

    
    mounted(){
        this.getPeople()
    }
    
    
    getPeople(){
      axios.get('http://localhost:9000/api/post/'+this.$route.params.id+'/people', this.myConfig).then( res =>
      this.users = res.data
      )
    }

        refresh(){
      window.location.reload()
    }

}
</script>

<style scoped>


.card-wrapper {
  display: flex;
  flex-wrap: wrap;
  padding-top: 20px;
}

.card {
  flex: 0 0 300px;
  border:none;
  margin: 1rem .75em;
  padding-bottom: 15px;

}

.card {
  opacity: 0;
  animation: fadeIn 0.9s 1;
  animation-fill-mode: forwards;
}

.card:nth-child(5n+1) {
  animation-delay: 0.5s;
}


@keyframes fadeIn {
  0% {
    opacity: 0.0;
  }
  100% {
    opacity: 1.0;
  }
}

.card img {
  width: 70%;
  height:70%;
  object-fit: cover;

}

.card .center {
   text-align: center;
}

.card h1 {
  font-size:30px;
  color: #E88D18;

 
}


.card .btn {
  padding: 8px 25px;
  background-color: #E88D18;
  color:#FFFFFF;
  border-radius: 20px;
  transition: all .5s;
  cursor:pointer;
  margin-right: 10px;
}

.card .btn:hover {
  transform: translateY(-2px);
  box-shadow: .5rem .5rem 2rem rgba(0,0,0,.2);
}
.card .btn:active {
  transform: translateY(0);
  box-shadow: none;
}

#center-me {
  display:flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

h3 {
  cursor: pointer;
   color:#E88D18;
}

h3:hover{
  color:rgb(85, 85, 85);
}


</style>