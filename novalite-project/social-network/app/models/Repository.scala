package models


import javax.inject.{Inject, Singleton}
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Repository @Inject()()(dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {

  val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._

  class UserTable(tag:Tag) extends Table[User] (tag, "user") {
    def id = column[Long]("id", O.PrimaryKey,O.AutoInc)
    def firstName = column[String]("first_name")
    def lastName = column[String]("last_name")
    def username = column[String]("username")
    def password = column[String]("password")
    def picture = column[String]("picture")
    def details = column[String]("details")

    def * = (id.?, firstName, lastName, username, password, picture.?, details.?) <> ((User.apply _).tupled, User.unapply)
  }

  val userTable = TableQuery[UserTable]


  // metode za user-e
  def addUser(user: User): Future[Int] =  {
    db.run(userTable += user)
  }

  def updateUser(id:Long, user:User): Future[Int] = {
   val action = userTable.filter(_.id === id)
     .map(user=> (user.id, user.firstName, user.lastName, user.username, user.password, user.details))
     .update(id, user.firstName, user.lastName, user.username, user.password, user.details.get)
    db.run(action)
  }

  def deleteUser(id: Long): Future[Int] = db.run {
    userTable.filter(_.id === id).delete
  }

  def getUser(id: Long): Future[Option[User]] = db.run {
    userTable.filter(_.id === id).result.headOption
  }

  def findByUsername(username:String) : Future[Option[User]] = db.run {
    userTable.filter(_.username === username).result.headOption
  }

  def listAllUsers: Future[Seq[User]] = db.run {
   userTable.result
  }

  def filterByName(keyWord:String): Future[Seq[User]] = {
    val query = (for {
      user <- userTable if user.firstName ++ user.lastName like s"%${keyWord}%"
    } yield user).result

    db.run(query)
  }

  /*def filterByName(keyWord:String): Future[Seq[User]] = {
    implicit val getResult = GetResult(r => User(r.nextLongOption, r.nextString, r.nextString,
      r.nextString, r.nextString, r.nextStringOption, r.nextStringOption))
    val action = sql"""select *  from user where concat(first_name, ' ', last_name)  like s"%${keyWord}%"""".as[User](getResult)
    dbConfig.db.run(action)
  }*/

  class PostTable(tag:Tag) extends Table[Post] (tag, "post") {
    def id = column[Long]("id", O.PrimaryKey,O.AutoInc)
    def text = column[String]("text")
    def likes = column[Int]("likes")
    def userId = column[Long]("user_id")
    def * = (id.?, text, likes, userId) <> ((Post.apply _).tupled, Post.unapply)
    def user = foreignKey("user_id", userId, userTable)(_.id)
  }

  val postTable = TableQuery[PostTable]

  //metode za postove

  def getPostsByUser(id:Long) : Future[Seq[Post]] = db.run {
    postTable.filter(_.userId===id).result
  }

  def addPost(post: Post): Future[String] =  {
    val action =(postTable += post).map(res => "Post successfully added")
    db.run(action)
  }

  def deletePost(id: Long): Future[Int] = db.run {
    postTable.filter(_.id === id).delete
  }

  def getPost(id: Long): Future[Option[Post]] = db.run {
    postTable.filter(_.id === id).result.headOption
  }

  def listAllPosts: Future[Seq[Post]] = db.run{
    postTable.result
  }

  def editPost(id:Long, post:Post): Future[String] = db.run {
    postTable.filter(_.id === id)
      .map(post=> (post.id, post.text, post.likes, post.userId))
      .update((id, post.text, post.likes, post.userId))
      .map(res => "Post successfully updated!")

  }

  def incrementLikes(id:Long) :Future[String] = {
    val action = sqlu"""update post set likes = likes+1 where post.id = $id"""
    db.run(action).map(res=> "You have successfully liked post!").recover({
      case ex: Exception => ex.getCause.getMessage
    })
  }

  def showPeopleByPost(id:Long) : Future[Seq[User]] = {
    val action = (for(l <- loveTable;
                      u <- userTable if (l.userId === u.id
                      && l.postId===id)
                      ) yield u
      ).result

    dbConfig.db.run(action)
  }

  def getUserByPostId(id:Long) : Future[Seq[User]] = {
    val action = (for(p <- postTable;
                      u <- userTable if (p.userId === u.id
      && p.id===id)
                      ) yield u
      ).result

    dbConfig.db.run(action)
  }





  class LoveTable(tag:Tag) extends Table[Love] (tag, "love") {
    def userId = column[Long]("user_id", O.PrimaryKey)
    def postId = column[Long]("post_id", O.PrimaryKey)
    def * = (userId, postId) <> ((Love.apply _).tupled, Love.unapply)
    def user = foreignKey("user_id", userId, userTable)(_.id)
    def post = foreignKey("post_id", postId, postTable)(_.id)
  }

  val loveTable = TableQuery[LoveTable]

  //metode za love

  def listAllLoves: Future[Seq[Love]] = {
    dbConfig.db.run(loveTable.result)
  }


  def addLove(love:Love): Future[Int] = {
    dbConfig.db.run(loveTable += love).recover({
      case ex: Exception => 0
    })
  }


  class FriendshipTable(tag:Tag) extends Table[Friendship] (tag, "friendship") {
    def senderId = column[Long]("sender_id", O.PrimaryKey)
    def accepterId = column[Long]("accepter_id", O.PrimaryKey)
    def requestStatus = column[String]("request_status")
    def * = (senderId, accepterId, requestStatus) <> ((Friendship.apply _).tupled, Friendship.unapply)
    def sender = foreignKey("sender_id", senderId, userTable)(_.id)
    def accepter = foreignKey("accepter_id", accepterId, userTable)(_.id)
  }

  val friendshipTable = TableQuery[FriendshipTable]

  // metode za friendship

  def addFriend(friendship:Friendship): Future[Int] = {
    dbConfig.db.run(friendshipTable += friendship).recover {
      case ex: Exception => 0
    }
  }

  def getFriendshipRequests(id:Long) : Future[Seq[User]] = {
    val action = (for(f <- friendshipTable;
                      u <- userTable if (f.senderId === u.id
      && f.accepterId ===id && f.requestStatus === "Pending")
                      ) yield u
      ).result

    dbConfig.db.run(action)
  }

  def checkFriendship(id1:Long, id2:Long) : Future[Option[Friendship]] ={
    val action = (for(f <- friendshipTable;
                  if(f.senderId === id1
      && f.accepterId === id2) || (f.senderId === id2
                    && f.accepterId === id1)
                      ) yield f
      ).result.headOption

    dbConfig.db.run(action)
  }

  def getFriends(id:Long) : Future[Seq[User]] = {
    val q1 = for(f <- friendshipTable;
                      u <- userTable if (f.senderId === u.id
      && f.accepterId ===id && f.requestStatus === "Accepted")
                      ) yield u


    val q2 = for(f <- friendshipTable;
                      u <- userTable if (f.accepterId === u.id
      && f.senderId ===id && f.requestStatus === "Accepted")
                      ) yield u


    val action = (q1++q2).result
    dbConfig.db.run(action)
  }

  def accept(senderId:Long, accepterId:Long) : Future[String] = db.run {
    friendshipTable.filter(_.senderId === senderId)
      .filter(_.accepterId === accepterId)
      .map(f=> f.requestStatus)
      .update("Accepted")
      .map(res => "Friend request accepted!")
  }

  def decline(senderId:Long, accepterId:Long): Future[String] = db.run{
    friendshipTable.filter(_.senderId === senderId).filter(_.accepterId=== accepterId).delete.map(res=> "Friend request deleted!")
  }




}