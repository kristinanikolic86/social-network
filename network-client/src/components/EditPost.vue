<template>
     <div class = "container">
                 <h1>Edit your post</h1>
      <form>
        <textarea name="text" id = "big" rows="20" cols="50" v-model="newPost.text"></textarea>
        <button type="submit" @click="editPost(newPost.id, $event)">Edit</button>
      </form>
     </div>
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
export default class EditPost extends Vue {

    loginComponent: LoginComponent = new LoginComponent()

  currentTokenAndUserInfo: SecurityTokenAndUserInfo = this.loginComponent.getCurrentTokenAndUserInfo()

 myConfig = {
  headers : {"X-Auth-Token" : this.currentTokenAndUserInfo.token}
 }

    newPost:Post = new Post({
    })

    mounted(){
        this.getPost()
    }

    getPost(){
           axios.get('http://localhost:9000/api/post/'+Number.parseInt(this.$route.params.id), this.myConfig).then( res =>
      this.newPost = res.data
           ).catch(error=>this.$router.push('/**'))
    }

    editPost(id:number, e:Event){
      e.preventDefault();
            axios.put('http://localhost:9000/api/post/'+id, this.newPost, this.myConfig).then( res =>
       this.$router.push('/user/me')
           )
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