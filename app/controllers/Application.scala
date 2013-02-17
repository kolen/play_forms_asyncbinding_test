package controllers

import play.api._
import play.api.data._
import play.api.data.Forms._
import play.api.mvc._
import reactivemongo.api._
import reactivemongo.core.actors.Authenticate
import reactivemongo.bson._
import play.api.libs.concurrent.Execution.Implicits._
import reactivemongo.bson.handlers.DefaultBSONHandlers._
import concurrent.Future
import scala.concurrent.duration._
import scala.concurrent.Await

object Application extends Controller {
  def checkUser(e:String, p:String):Boolean = {
    val connection = MongoConnection(
      List( "ds043467.mongolab.com:43467" ),
      List(Authenticate("kolen-test", "kolen-test", "qwerty"))
    )
    val db = connection("kolen-test")
    val collection = db("test")

    val query = BSONDocument("email" -> BSONString(e), "password" -> BSONString(p))
    val cursor = collection.find(query)
    val result = cursor.toList().map( _.length != 0)

    Await.result(result, 5 seconds)
  }

  val loginForm = Form(
    tuple(
      "email" -> email,
      "password" -> text
    ) verifying("Invalid user name or password", fields => fields match { 
      case (e, p) => checkUser(e, p)
    })
  )

  def index = Action { implicit request =>
    val data: Future[Form[(String, String)]] = scala.concurrent.Future { loginForm.bindFromRequest }
    Async {
      data.map(form => {
        if (form.hasErrors) {
          Ok("Invalid user name")
        } else {
          Ok("Login ok")
        }
      })
    }
  }
  
}