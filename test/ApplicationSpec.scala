package test

import org.specs2.mutable._

import play.api.test._
import play.api.test.Helpers._

/**
 * Add your spec here.
 * You can mock out a whole application including requests, plugins etc.
 * For more information, consult the wiki.
 */
class ApplicationSpec extends Specification {
  
  "Application" should {
    "check username and password" in {
      running(FakeApplication()) {
        val formpost = route(FakeRequest(POST, "/", new FakeHeaders(), "email=test@example.com&password=qwerty")).get
        status(formpost) must equalTo(OK)
        contentAsString(formpost) must contain ("Login ok")
      }
    }
  }
}