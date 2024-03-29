package services

object DistanceCalculator {

  private val EARTH_RADIUS = 6371 // 지구의 반지름(km)

  // Haversine formula로 두 지점(위도/경도) 간의 거리 계산
  def calculateDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double = {

    if ((lat1 == lat2) && (lon1 == lon2)) return 0.0

    val latDistance = Math.toRadians(lat2 - lat1)
    val lonDistance = Math.toRadians(lon2 - lon1)
    val originLat = Math.toRadians(lat1)
    val destinationLat = Math.toRadians(lat2)
    val a = Math.pow(Math.sin(latDistance / 2), 2) +
      Math.pow(Math.sin(lonDistance / 2), 2) * Math.cos(originLat) * Math.cos(destinationLat)
    val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))
    EARTH_RADIUS * c
  }
}
