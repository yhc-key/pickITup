package controllers;

import models.UserSimilarityRequest
import serializers.UserSimilarityRequestSerializer._
import play.api.mvc._

import javax.inject._
import play.api.libs.json._
import services.mongo.MongoService
import services.recommend.SimilarityService

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future;

class SimilarityController @Inject()(cc: ControllerComponents, mongoService: MongoService) extends AbstractController(cc) {

  def asyncCalculateUserSimilarity(): Action[AnyContent] = Action.async(parse.json) { implicit request =>

    request.body.validate[UserSimilarityRequest].fold(
      errors => {
        Future.successful(BadRequest("Invalid request."))
      },
      userSimilarityRequest => {
        val futureWork = Future {
          if (userSimilarityRequest.status == "update") {
            mongoService.deleteUserSimilarities(userSimilarityRequest.userId)
          }
          SimilarityService.calculateUserSimilarity(userSimilarityRequest.userId)
        }

        futureWork.map { result =>
          println("User similarity " + userSimilarityRequest.status + " for user " + userSimilarityRequest.userId + " completed.")
        }

        Future.successful(Accepted("User similarity calculation started."));
      }
    )
  }
}
