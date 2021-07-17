name := "spark-prueba"

version := "0.1"

scalaVersion := "2.12.14"

idePackagePrefix := Some("org.et.spark.example")


libraryDependencies += "org.apache.spark" %% "spark-sql" % "3.1.2"
libraryDependencies +=  "org.scalaj" %% "scalaj-http" % "2.4.2"