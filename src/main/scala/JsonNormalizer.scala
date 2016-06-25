import play.api.libs.json._

object JsonNormalizer {

  def main(args: Array[String]) {

    val json: JsValue = Json.obj(
      "section1.name" -> "Watership Down",
      "section2.location" -> Json.obj("lat" -> 51.235685, "long" -> -1.309197),
      "section1.residents" -> Json.arr(
        Json.obj(
          "section_some.name" -> "Fiver",
          "age" -> 4,
          "section_some.role" -> JsNull
        ),
        Json.obj(
          "name" -> "Bigwig",
          "age" -> 6,
          "role" -> "Owsla"
        )
      )
    )

    println(Json.prettyPrint(json))
    println(Json.prettyPrint(normalize(json)))
  }

  def normalize(json: JsValue): JsValue = json match {
    case JsObject(underlying) =>
      JsObject(
        underlying.keys.groupBy(key => key.split('.').head).map {
          case (headKey, matchingKeys)
            if matchingKeys.count( key => headKey == key) == 1 =>
            (headKey, normalize(underlying.getOrElse(headKey, JsString(""))))
          case (headKey, matchingKeys) =>
            headKey -> JsObject(
              matchingKeys.map {
                case key =>
                  val tailKey = key.split('.').tail.mkString(".")
                  (tailKey, normalize(underlying.getOrElse(key, JsString(""))))
              }.toSeq
            )
        }.toSeq
      )
    case JsArray(values) =>
      JsArray(
        values.map {
          case value => normalize(value)
        }
      )
    case value => value
  }
}
