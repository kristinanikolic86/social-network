package controllers

import javax.inject.Inject
import models.Friendship
import play.api.libs.json.Json
import play.api.mvc.{BaseController, ControllerComponents}
import security.Authentication
import services.{FriendshipService, PostService}

import scala.concurrent.{ExecutionContext, Future}

class FriendshipController @Inject()(val controllerComponents: ControllerComponents, fService: FriendshipService, postService:PostService, authentication:Authentication)(implicit executionContext: ExecutionContext) extends BaseController{

  def addFriend() = authentication.async { request =>
    fService.checkFriendship(request.body.asJson.get.as[Friendship].senderId, request.body.asJson.get.as[Friendship].accepterId).flatMap{ res =>
      if (!res.isEmpty) {
        Future(BadRequest("Request has been already sent!"))
      } else {
        fService.addFriend(request.body.asJson.get.as[Friendship]).map { result => Ok("Friend request sent!") }
      }
    }
  }


  def getFriendshipRequests(id:Long) = authentication.async {request=>
    fService.getFriendshipRequests(id).map(res=> Ok(Json.toJson(res)))
  }

  def getFriends(id:Long) = authentication.async {request=>
    fService.getFriends(id).map(res=> Ok(Json.toJson(res)))
  }

  def checkFriendship(id1:Long, id2:Long) = authentication.async {request=>
    fService.checkFriendship(id1,id2).map(res=> Ok(Json.toJson(res)))
  }

  def accept(senderId:Long, accepterId:Long) = authentication.async {request=>
    fService.accept(senderId:Long, accepterId:Long).map(res=>Ok(res.toString))
  }

  def decline(senderId:Long, accepterId:Long) = authentication.async {request=>
    fService.decline(senderId:Long, accepterId:Long).map(res=>Ok(res.toString))
  }

  /*def getFriendsPosts(id:Long)= authentication.async{ request=>
    /*val ids = fService.getFriends(id).map(users=>for (u<-users) yield u.id.get).map(ids=> for(i<-ids) yield i)

    var finalSeq = Seq[Post]()
    val seqSeq = ids.map(ids=> for(i<-ids) yield postService.getPostsByUser(i)*/


  }*/
}
