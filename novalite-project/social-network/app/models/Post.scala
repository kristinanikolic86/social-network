package models

import play.api.libs.json._

case class Post(id: Option[Long], text: String, likes:Int, userId:Long)


object Post{

  /*implicit object timestampFormat extends Format[Timestamp] {
    val format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SS'Z'")

    import java.util.TimeZone

    format.setTimeZone(TimeZone.getTimeZone("UTC"))
    def reads(json: JsValue) = {
      val str = json.as[String]
      JsSuccess(new Timestamp(format.parse(str).getTime))
    }
    def writes(ts: Timestamp) = JsString(format.format(ts))
  }*/

  implicit val postFormat = Json.format[Post]


}
