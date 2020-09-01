package services

import com.google.inject.Inject
import models.{Friendship, Repository, User}

import scala.concurrent.Future

class FriendshipService @Inject() (repo: Repository) {

  def addFriend(friendship:Friendship): Future[Int] ={
    repo.addFriend(friendship)
  }

  def getFriendshipRequests(id:Long): Future[Seq[User]] = {
    repo.getFriendshipRequests(id)
  }

  def getFriends(id:Long) : Future[Seq[User]] = {
    repo.getFriends(id)
  }

  def checkFriendship(id1:Long, id2:Long) : Future[Option[Friendship]] ={
    repo.checkFriendship(id1, id2)
  }

  def accept(senderId:Long, accepterId:Long): Future[String] = {
    repo.accept(senderId, accepterId)
  }

  def decline(senderId:Long, accepterId:Long): Future[String] ={
    repo.decline(senderId,accepterId)
  }

}
