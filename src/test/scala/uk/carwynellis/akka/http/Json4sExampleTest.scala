package uk.carwynellis.akka.http

import akka.http.scaladsl.model.{ContentTypes, StatusCodes}
import akka.http.scaladsl.testkit.ScalatestRouteTest
import de.heikoseeberger.akkahttpjson4s.Json4sSupport
import org.json4s.{DefaultFormats, native}
import org.scalatest.{FunSuite, Matchers}

/**
  * Tests for the Json4s example
  */
class Json4sExampleTest extends FunSuite with ScalatestRouteTest with Matchers with Json4sSupport {

  implicit val formats = DefaultFormats
  implicit val serialization = native.Serialization

  test("should return multiple users on GET of /users") {
    Get("/users") ~> Json4sExample.route ~> check {
      status should equal (StatusCodes.OK)
      responseAs[List[User]] should be (List(User("Mr", "Foo Bar"), User("Dr", "Baz")))
      contentType should be (ContentTypes.`application/json`)
    }
  }

  test("should return HTTP 404 for any other resources") {
    Get("/foo") ~> Json4sExample.route ~> check {
      // This request should be rejected
      handled should be (false)
    }
  }
}