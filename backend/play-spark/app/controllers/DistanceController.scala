package controllers

import akka.http.scaladsl.model.HttpHeader.ParsingResult.Ok
import models.UserRequest
import serializers.UserSimilarityRequestSerializer._
import play.api.libs.json._
import play.api.mvc.Results.Ok
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}
import services.DistanceCalculator
import services.mongo.MongoService
import services.recommend.UserCompanyDistanceService

import javax.inject.Inject
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class DistanceController @Inject()(cc: ControllerComponents, mongoService: MongoService) extends AbstractController(cc) {

  def asyncCalculateAllUserDistance(): Action[AnyContent] = Action.async { implicit request =>

    val futureWork = Future {
      UserCompanyDistanceService.calculateAllUserAllCompanyDistances()
    }

    futureWork.map(_ => println("User distance calculation completed."));

    Future.successful(Accepted("User distance calculation started."));
  }
}
