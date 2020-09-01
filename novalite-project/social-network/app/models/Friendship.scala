package models

import play.api.libs.json.Json

case class Friendship (senderId: Long, accepterId: Long, requestStatus:String)


  object Friendship{
    implicit val friendshipFormat = Json.format[Friendship]
  }

