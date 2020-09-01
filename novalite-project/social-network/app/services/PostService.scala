package services

import com.google.inject.Inject
import models.{Post, Repository, User}

import scala.concurrent.Future

class PostService @Inject() (repo: Repository) {

  def getPostsByUser(id:Long): Future[Seq[Post]] = {
    repo.getPostsByUser(id)
  }

  def addPost(post:Post): Future[String] = {
    repo.addPost(post)
  }

  def deletePost(id: Long): Future[Int] = {
    repo.deletePost(id)
  }

  def getPost(id: Long): Future[Option[Post]] = {
    repo.getPost(id)
  }

  def listAllPosts: Future[Seq[Post]] = {
    repo.listAllPosts
  }

  def editPost(id:Long, post:Post) : Future[String] ={
    repo.editPost(id, post)
  }

  def incrementLikes(id:Long): Future[String] = {
    repo.incrementLikes(id)
  }

  def showPeopleByPost(id:Long) : Future[Seq[User]] = {
    repo.showPeopleByPost(id)
  }

  def getUserByPostId(id:Long) : Future[Seq[User]] = {
    repo.getUserByPostId(id)
  }


}