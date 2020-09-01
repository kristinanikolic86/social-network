package models

import play.api.libs.json.Json

case class Love(userId:Long, postId:Long)

object Love{
  implicit val postFormat = Json.format[Love]
}
