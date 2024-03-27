package serializers

import models.UserSimilarityRequest
import play.api.libs.json.{Json, OFormat}

object UserSimilarityRequestSerializer {
  implicit val userSimilarityRequestFormat: OFormat[UserSimilarityRequest] = Json.format[UserSimilarityRequest]
}
