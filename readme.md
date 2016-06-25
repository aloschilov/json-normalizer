
# JSON normalizer

It is possible to write down JSON fields in dot interpolated notation:

      {
        "section1.name" : "Watership Down",
        "section2.location" : {
          "lat" : 51.235685,
          "long" : -1.309197
        },
        "section1.residents" : [ {
          "section_some.name" : "Fiver",
          "age" : 4,
          "section_some.role" : null
        }, {
          "name" : "Bigwig",
          "age" : 6,
          "role" : "Owsla"
        } ]
      }
      
We usually expect the JSON snipped above in the following format:

    {
      "section2" : {
        "location" : {
          "lat" : 51.235685,
          "long" : -1.309197
        }
      },
      "section1" : {
        "name" : "Watership Down",
        "residents" : [ {
          "age" : 4,
          "section_some" : {
            "name" : "Fiver",
            "role" : null
          }
        }, {
          "role" : "Owsla",
          "age" : 6,
          "name" : "Bigwig"
        } ]
      }
    }
    
This project provides JSON normalizer which convert dot interpolated 
JSON into normalized representation.

# Example 

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
      
# Dependencies

This project relies on JSON from Play Framework:
    
     libraryDependencies += "com.typesafe.play" %% "play-json" % "2.5.4"