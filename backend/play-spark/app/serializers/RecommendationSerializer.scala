package serializers

import models.Recommendation
import play.api.libs.json.{JsString, JsValue, Json, Writes}

import java.sql.Timestamp
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

object RecommendationSerializer {

  implicit val timestampWrites: Writes[Timestamp] = new Writes[Timestamp] {
    def writes(t: Timestamp): JsValue = {
      val instant = t.toInstant
      val str = DateTimeFormatter.ISO_INSTANT.withZone(ZoneOffset.UTC).format(instant)
      JsString(str) // 또는 ISO 8601 형식으로 변환할 수 있습니다.
    }
  }

  implicit val recommendationWrites: Writes[Recommendation] = Json.writes[Recommendation]
}
