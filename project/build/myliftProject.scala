import sbt._

class myliftProject(info: ProjectInfo) extends DefaultWebProject(info) {
  val liftVersion = "2.0"

  override def libraryDependencies = Set(
    "net.liftweb" % "lift-webkit" % liftVersion % "compile->default",
    "net.liftweb" % "lift-mapper" % liftVersion % "compile->default",
	"net.liftweb" % "lift-wizard" % liftVersion % "compile->default",
    "org.mortbay.jetty" % "jetty" % "6.1.22" % "test->default",
    "junit" % "junit" % "4.5" % "test->default",
    "org.scala-tools.testing" % "specs" % "1.6.2.1" % "test->default",
    "com.h2database" % "h2" % "1.2.138",
	"mysql" % "mysql-connector-java" % "5.1.12"
  ) ++ super.libraryDependencies

  override val jettyPort = 8080
}
