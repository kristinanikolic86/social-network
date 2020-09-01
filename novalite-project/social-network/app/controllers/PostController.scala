package controllers

import javax.inject._
import models.{Post, Repository}
import play.api.libs.json.Json
import play.api.mvc._
import security.Authentication
import services.{PostService, UserService}
import slick.jdbc.SQLActionBuilder

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class PostController @Inject()(val controllerComponents: ControllerComponents, service:UserService, postService: PostService, repo:Repository, authentication:Authentication)(implicit executionContext: ExecutionContext) extends BaseController {

  def index() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.index())
  }

  def getPostsByUser(id:Long) = authentication.async{request=>
    postService.getPostsByUser(id).map(res=> Ok(Json.toJson(res)))

  }

  def getPost(id:Long) = authentication.async { request=>
    postService.getPost(id).map{result=>
      if(result.isEmpty) NotFound("There is no such post!")
      else Ok(Json.toJson(result))}

  }

  def addPost() = authentication.async(parse.json) { request=>
    if(request.body.as[Post].text.trim()!="" && request.body.as[Post].likes == 0) {
      postService.addPost(request.body.as[Post])
      Future(Created("Successful!"))
    } else {
      Future(BadRequest("Invalid input!"))
    }

  }

  def deletePost(id:Long) = authentication.async{ request =>
    postService.getPost(id).map { result =>
      if (result.isEmpty) NotFound("There is no such post!")
      else {
        postService.deletePost(id)
        Ok("Deleted!")
      }
    }
  }

  def editPost(id:Long) = authentication.async(parse.json) { request =>
    postService.getPost(id).map { result =>
      if (result.isEmpty) BadRequest("There is no such post!")
      else {
        if (request.body.as[Post].text.trim() != "" && result.get.likes == request.body.as[Post].likes) {
          postService.editPost(id, request.body.as[Post])
          Created("Successful!")
        } else {
          BadRequest("Invalid input!")
        }
      }
    }
  }

  def incrementLikes(id:Long) = Action.async { request=>
    repo.incrementLikes(id).map(res=> Ok(res.toString))
  }

  def showPeopleByPost(id:Long) = authentication.async {request =>
    postService.showPeopleByPost(id).map(res=> Ok(Json.toJson(res)))
  }

  def getUserByPostId(id:Long) = authentication.async { request=>
    postService.getUserByPostId(id).map{result=> Ok(Json.toJson(result))}

  }



}
