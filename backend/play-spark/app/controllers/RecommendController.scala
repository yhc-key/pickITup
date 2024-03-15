package controllers

import com.typesafe.config.ConfigFactory
import play.api.Configuration
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}
import services.recommend.RecommendService

import javax.inject.Inject

class RecommendController @Inject()(cc: ControllerComponents, config: Configuration) extends AbstractController(cc) {

  def test():Action[AnyContent] = Action { implicit request =>
    println(s"환경변수 좀 출력해봐: ${config.get[String]("mongo.hostname")}")
    Ok("Test API is working!!!")
  }

  def recommend(): Action[AnyContent] = Action { implicit request =>
//    val str = cbfService.recommend()
    val str = RecommendService.recommend()
    Ok("Recommendation API is working! " + str)
  }

}
