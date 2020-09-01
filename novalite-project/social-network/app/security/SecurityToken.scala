package security

import models.User
import play.api.libs.json.Json

case class SecurityToken(token:String, user:User)

object SecurityToken{
  implicit val securityTokenFormat = Json.format[SecurityToken]
}
