package security

import javax.inject.Inject
import play.api.mvc.{ActionBuilder, AnyContent, BodyParser, BodyParsers, Request, Result, Results, WrappedRequest}
import scala.concurrent.{ExecutionContext, Future}

case class UserRequest[A](token:String, request: Request[A]) extends WrappedRequest(request)


class Authentication @Inject() (utils:TokenUtils)(bodyParser: BodyParsers.Default)
                               (implicit ec: ExecutionContext)
  extends ActionBuilder[UserRequest, AnyContent] {

  override def parser: BodyParser[AnyContent] = bodyParser
  override protected def executionContext: ExecutionContext = ec

  override def invokeBlock[A](request: Request[A],
                              block: UserRequest[A] => Future[Result]): Future[Result] = {

    val token = request.headers.get("X-Auth-Token").get

    utils.validateToken(token).flatMap{
      case true => block(UserRequest(token, request))
      case false => Future(Results.Unauthorized)
    }

  }
}