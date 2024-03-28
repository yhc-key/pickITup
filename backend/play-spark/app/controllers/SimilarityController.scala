package controllers;

import models.UserRequest
import serializers.UserSimilarityRequestSerializer._
import play.api.mvc._

import javax.inject._
import play.api.libs.json._
import services.mongo.MongoService
import services.recommend.SimilarityService

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future;

class SimilarityController @Inject()(cc: ControllerComponents, mongoService: MongoService) extends AbstractController(cc) {

  def asyncCalculateAllUserSimilarity(): Action[AnyContent] = Action.async { implicit request =>

    val futureWork = Future {
      SimilarityService.calculateAllUserSimilarities()
    }

    futureWork.map { _ =>
      println("User similarity calculation completed.")
    }

    Future.successful(Accepted("User similarity calculation started."))
  }
}
