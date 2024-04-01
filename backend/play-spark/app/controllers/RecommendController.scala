package controllers

import models.Recommendation
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}
import serializers.RecommendationSerializer._
import services.recommend.{CollaborativeFilteringService, ContentBasedFilteringService}

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
}
