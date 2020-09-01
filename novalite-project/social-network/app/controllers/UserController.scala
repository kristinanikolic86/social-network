package controllers

import javax.inject._
import models.{Repository, User}
import play.api.libs.json.Json
import play.api.mvc._
import security.{Authentication, Login, TokenUtils}
import services.UserService

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class UserController @Inject()(val controllerComponents: ControllerComponents, service:UserService, repo:Repository, utils:TokenUtils, authentication:Authentication)(implicit executionContext: ExecutionContext) extends BaseController {


  def index() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.index())
  }

  def getUsers = authentication.async{request=>
    service.listAllUsers.map(user=> Ok(Json.toJson(user)))
  }

  def filterByName(keyWord:String) = authentication.async{request =>
    service.filterByName(keyWord).map(user=> Ok(Json.toJson(user)))
  }


  def login()= Action.async{ request=>
    service.findByUsername(request.body.asJson.get.as[Login].username).map(result=>
      if(request.body.asJson.get.as[Login].username.trim()=="" || result.isEmpty) BadRequest ("Wrong username!")
      else Ok(Json.toJson(utils.returnTokenAndUser(result.get)))
    )
  }

  def getUser(id:Long) = authentication.async { request=>
    service.getUser(id).map{result=>
      if(result.isEmpty) NotFound("The user doesn't exist!")
      else Ok(Json.toJson(result))}

  }

  def addUser() = Action.async(parse.json) { request=>
    if(request.body.as[User].firstName.trim()!="" &&request.body.as[User].lastName.trim()!=""&& request.body.as[User].username.trim()!=""&& request.body.as[User].password.trim()!="") {
      service.addUser(request.body.as[User])
      Future(Created("Successful!"))
    }
    else {
      Future(BadRequest("Body is empty!"))
    }
    }


  def updateUser(id:Long) = authentication.async(parse.json) {request =>
    if(request.body.as[User].firstName.trim()!="" &&request.body.as[User].lastName.trim()!=""&& request.body.as[User].username.trim()!=""&& request.body.as[User].password.trim()!="") {
      service.updateUser(id, request.body.as[User]).map{result=>
        if(result == 1) {
          Created("Successful!")
        } else {
          NotFound("There is no such user!")
        }
      }
    } else {
      Future(BadRequest("Body is empty!"))
    }
  }

  def deleteUser(id:Long) = authentication.async { request =>
    service.getUser(id).map { result =>
      if (result.isEmpty) NotFound("The user doesn't exist!")
      else {
        service.deleteUser(id)
        Ok("Deleted!")
      }
    }
  }
}
