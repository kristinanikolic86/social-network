package security

import io.jsonwebtoken.{Claims, Jwts, SignatureAlgorithm}
import javax.inject.Inject
import models.{Repository, User}

import scala.concurrent.{ExecutionContext, Future}

class TokenUtils@Inject() (val repo: Repository) (implicit executionContext: ExecutionContext)  {

  val secretKey = "secretKey"

  // generisemo token
  def generateToken(user:User): String = {
    val claimsSet = Jwts.claims().setSubject(user.username)
    Jwts.builder().setClaims(claimsSet).signWith(SignatureAlgorithm.HS256, secretKey).compact()
  }

  // dekodiramo token, uzimamo claims
  def decodeToken(jwt: String): Claims ={
    val claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwt).getBody
    claims
  }

  // uzimamo username iz claims-a
  def getUsernameFromToken(jwt:String): String ={
    val username = decodeToken(jwt).getSubject
    username
  }

  // validiramo token
  def validateToken(jwt:String):Future[Boolean] = {
    val username  = getUsernameFromToken(jwt)
    repo.findByUsername(username).map(result=>
      if(result.isEmpty) false
      else true
    )
  }

  // vracamo token i user-a
  def returnTokenAndUser(user:User): SecurityToken= {
    val securityToken =  SecurityToken (generateToken(user), user)
    securityToken
  }
}