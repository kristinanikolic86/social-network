package services

import com.google.inject.Inject
import models.{Love, Repository}

import scala.concurrent.Future

class LoveService @Inject() (repo: Repository) {

  def listAllLoves: Future[Seq[Love]] = {
    repo.listAllLoves
  }

  def addLove(love:Love): Future[Int] ={
    repo.addLove(love)
  }

}
