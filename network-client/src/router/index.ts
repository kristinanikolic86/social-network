import Vue from 'vue'
import VueRouter from 'vue-router'
import LoginComponent from '../components/LoginComponent.vue'
import Register from '../components/Register.vue'
import Me from '../components/Me.vue'
import MyRequests from '../components/MyRequests.vue'
import EditProfile from '../components/EditProfile.vue'
import EditPost from '../components/EditPost.vue'
import MyFriends from '../components/MyFriends.vue'
import User from '../components/User.vue'
import PeopleLikedPost from '../components/PeopleLikedPost.vue'
import MyNewsFeed from '../components/MyNewsFeed.vue'
import PageNotFound from '../components/PageNotFound.vue'
import Search from '../components/Search.vue'

Vue.use(VueRouter)

const routes = [

  {
    path: '', redirect: '/register'
    

  },
  {
    path: '/login',
    name: 'login',
    component: LoginComponent
  },
  {
    path: '/register',
    name: 'register',
    component: Register
  },
  {
    path: '/user/me',
    name: 'me',
    component: Me
  },

  {
    path: '/user/me/requests',
    name: 'requests',
    component: MyRequests
  },
  {
    path: '/user/me/friends',
    name: 'friends',
    component: MyFriends
  },
  {
    path: '/user/me/edit-profile',
    name: 'edit-profile',
    component: EditProfile
  },
  {
    path: '/user/me/edit-post/:id',
    name: 'edit-post',
    component: EditPost
  },

  {
    path: '/user/:id',
    name: 'user',
    component: User
  },

  {
    path: '/user/me/news-feed',
    name: 'news-feed',
    component: MyNewsFeed
  },
  {
    path: '/post/:id/people',
    name: 'people',
    component: PeopleLikedPost
  },

  {
    path: '/search/:keyWord',
    name: 'search',
    component: Search
  },
  
  {
    path: '**',
    name: 'page-not-found',
    component: PageNotFound
  }
  

]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
