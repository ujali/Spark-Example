name := """spark-examples"""

version := "1.0"

scalaVersion := "2.11.5"

// Change this to another test framework if you prefer
libraryDependencies ++=Seq( 
"org.scalatest" %% "scalatest" % "2.2.4" % "test",
//libraryDependencies += "com.typesafe.akka" % "akka-actor_2.11" % "2.3.9",
 "org.apache.spark"   %% "spark-core"              % "1.2.1",
  "org.apache.spark"   %% "spark-streaming-twitter" % "1.2.1",
  "org.apache.spark"   %% "spark-sql"               % "1.2.1",
  "org.apache.spark"   %% "spark-mllib"             % "1.2.1",
  "com.nflabs.zeppelin" % "zeppelin-zengine" % "0.3.3")
