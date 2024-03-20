package serializers

import models.Recommendation
import play.api.libs.json.{Json, Writes}

object RecommendationSerializer {
  implicit val recommendationWrites: Writes[Recommendation] = Json.writes[Recommendation]
}
