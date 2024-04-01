package controllers

import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}
import services.recommend.UserCompanyDistanceService

import javax.inject.Inject
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class DistanceController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def asyncCalculateAllUserDistance(): Action[AnyContent] = Action.async { implicit request =>

    val futureWork = Future {
      UserCompanyDistanceService.calculateAllUserAllCompanyDistances()
    }

    futureWork.map(_ => println("User distance calculation completed."));

    Future.successful(Accepted("User distance calculation started."));
  }
}
