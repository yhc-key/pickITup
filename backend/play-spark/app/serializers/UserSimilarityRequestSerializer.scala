package serializers

import models.UserRequest
import play.api.libs.json.{Json, OFormat}

object UserSimilarityRequestSerializer {
  implicit val userSimilarityRequestFormat: OFormat[UserRequest] = Json.format[UserRequest]
}
