import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName         = "play_forms_asyncbinding_test"
  val appVersion      = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    // Add your project dependencies here,
    jdbc,
    anorm,
    "org.reactivemongo" %% "reactivemongo" % "0.8"
  )


  val main = play.Project(appName, appVersion, appDependencies).settings(
    // Add your own project settings here      
  )

}
