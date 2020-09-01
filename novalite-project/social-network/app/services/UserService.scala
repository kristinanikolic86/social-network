package services

import com.google.inject.Inject
import models.{User, Repository}

import scala.concurrent.Future

class UserService @Inject() (repo: Repository) {

  def addUser(user: User): Future[Int] = {
    repo.addUser(user)
  }

  def updateUser(id:Long, user:User):Future[Int] ={
    repo.updateUser(id, user)
  }

  def deleteUser(id: Long): Future[Int] = {
    repo.deleteUser(id)
  }

  def getUser(id: Long): Future[Option[User]] = {
    repo.getUser(id)
  }

  def listAllUsers: Future[Seq[User]] = {
    repo.listAllUsers
  }

  def findByUsername(username:String) :Future[Option[User]] = {
    repo.findByUsername(username)
  }

  def filterByName(keyWord:String):Future[Seq[User]] = {
    repo.filterByName(keyWord)
  }
}