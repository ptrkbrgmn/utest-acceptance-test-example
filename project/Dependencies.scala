import sbt._

object Dependencies {
  lazy val utest = "com.lihaoyi" %% "utest" % "0.6.3" % "test"
  lazy val sttp = "com.softwaremill.sttp" %% "core" % "1.1.14"



  // Projects
 val backendDeps =
   Seq(utest, sttp)
}
