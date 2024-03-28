package controllers

import models.Recommendation
import serializers.RecommendationSerializer._
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}
import services.recommend.{CollaborativeFilteringService, ContentBasedFilteringService, SimilarityService}

import javax.inject.Inject

class RecommendController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def test():Action[AnyContent] = Action { implicit request =>
    Ok("Test API is working!!!")
  }

  def contentBasedRecommend(userId: Int): Action[AnyContent] = Action { implicit request =>
    val list: List[Recommendation] = ContentBasedFilteringService.recommend(userId)
    Ok(Json.toJson(list))
  }

  def collaborativeRecommend(userId: Int): Action[AnyContent] = Action { implicit request =>
    val list: List[Recommendation] = CollaborativeFilteringService.recommend(userId)
    Ok(Json.toJson(list))
  }

  def userSimilarity(): Action[AnyContent] = Action { implicit request =>
    val str = SimilarityService.calculateAllUserSimilarities()
    Ok("Similarity API is working! " + str)
  }
}
