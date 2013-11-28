import play.Project._

name := """semantiQ"""

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
	"org.webjars" %% "webjars-play" % "2.2.0", 
	"org.webjars" % "bootstrap" % "2.3.1",
	"org.apache.opennlp" % "opennlp-tools" % "1.5.3"
	)

playJavaSettings
