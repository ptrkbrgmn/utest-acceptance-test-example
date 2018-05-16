package example


import com.softwaremill.sttp._
import scala.collection.immutable.Seq

import utest._

import scala.io.Source

object NavKlaraVideoSpec extends TestSuite {

  implicit val backend = HttpURLConnectionBackend()

  var getVideoKlaraJson: String = _
  val tests = Tests {

    "Klara videos" - {

      "Fetch a single video" - {

        "Given there is a video added to elasticsearch with id dn.screen9.1uwHxJLDuuBKBHGHQcissw" - {
          val videoElasticJson = Source.fromURL(getClass.getResource("/es-dn.screen9.1uwHxJLDuuBKBHGHQcissw.json")).mkString
          val uri = Uri.apply("es-content.dev.bonnier.news", 9200, Seq("klara1", "klara", "dn.screen9.1uwHxJLDuuBKBHGHQcissw"))
          val addVideoElasticRequest = sttp.header("content-type", "application/json; charset=UTF-8")
                                           .body(videoElasticJson)
                                           .post(uri)
          val addVideoResponse = addVideoElasticRequest.send()
          assert(addVideoResponse.isSuccess)
        }

        "When fetching a video with id dn.screen9.1uwHxJLDuuBKBHGHQcissw" - {
          val getVideoKlaraRequest = sttp.header("content-type", "application/json; charset=UTF-8")
                                         .get(uri"http://nav-klara-dn.dev.internal.bonnier.news/videos/dn.screen9.1uwHxJLDuuBKBHGHQcissw")
          val getVideoKlaraResponse = getVideoKlaraRequest.send()
          assert(getVideoKlaraResponse.isSuccess)
          getVideoKlaraJson = getVideoKlaraResponse.unsafeBody

        }

        "Then the response should contain the right json data" - {
          val videoKlaraExpectedJson = Source.fromURL(getClass.getResource("/nav-dn.screen9.1uwHxJLDuuBKBHGHQcissw.json")).mkString
          assert(getVideoKlaraJson == videoKlaraExpectedJson)
        }

        "Clean up afterwards" - {
          val getVideoElasticRequest = sttp.header("content-type", "application/json; charset=UTF-8").get(uri"http://es-content.dev.bonnier.news:9200/klara1/klara/dn.screen9.1uwHxJLDuuBKBHGHQcissw/")
          val getVideoElasticResponse = getVideoElasticRequest.send()

          getVideoElasticResponse.code match {
            case 200 => sttp.header("content-type", "application/json; charset=UTF-8")
              .delete(uri"http://es-content.dev.bonnier.news:9200/klara1/klara/dn.screen9.1uwHxJLDuuBKBHGHQcissw/")
              .send().isSuccess
            case _ => println("No video entry with id 'dn.screen9.1uwHxJLDuuBKBHGHQcissw'")
          }
        }
      }
    }
  }
}


