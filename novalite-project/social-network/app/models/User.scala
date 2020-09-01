package models

import play.api.libs.json._

case class User(id: Option[Long], firstName: String, lastName: String, username: String, password:String, picture:Option[String],
                details: Option[String])

object User{
  implicit val userFormat = Json.format[User]
}

