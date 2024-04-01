package controllers;

import play.api.mvc._
import services.recommend.UserSimilarityService

import javax.inject._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future;

class SimilarityController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def asyncCalculateAllUserSimilarity(): Action[AnyContent] = Action.async { implicit request =>

    val futureWork = Future {
      UserSimilarityService.calculateAllUserSimilarities()
    }

    futureWork.map { _ =>
      println("User similarity calculation completed.")
    }

    Future.successful(Accepted("User similarity calculation started."))
  }
}
