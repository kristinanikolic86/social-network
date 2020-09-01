package controllers

import javax.inject.Inject
import models.Love
import play.api.libs.json.Json
import play.api.mvc.{BaseController, ControllerComponents}
import security.Authentication
import services.{LoveService, PostService, UserService}

import scala.concurrent.{ExecutionContext, Future}

class LoveController @Inject()(val controllerComponents: ControllerComponents, service:UserService, postService: PostService, loveService:LoveService, authentication:Authentication)(implicit executionContext: ExecutionContext) extends BaseController  {

  def getAllLoves = Action.async {request =>
    loveService.listAllLoves.map(result=> Ok(Json.toJson(result)))
  }


  def addLove() = authentication.async {request=>
    loveService.addLove(request.body.asJson.get.as[Love]).flatMap{result=>
      if (result!=0) {
        postService.incrementLikes(request.body.asJson.get.as[Love].postId).map(res=> Ok(res))
      } else{
        Future(BadRequest("You cannot like the same post twice"))
      }
    }

  }


}
