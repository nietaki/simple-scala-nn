name := "simple-scala-nn"

version := "0.2"

libraryDependencies  ++= Seq(
            "org.scalanlp" %% "breeze-math" % "0.4-SNAPSHOT",
            //"org.scalanlp" %% "breeze-learn" % "0.4-SNAPSHOT",
            //"org.scalanlp" %% "breeze-process" % "0.4-SNAPSHOT",
            //"org.scalanlp" %% "breeze-viz" % "0.4-SNAPSHOT",
            "org.specs2" %% "specs2" % "1.14" % "test",
            "org.scalatest" % "scalatest_2.10" % "1.9.1" % "test"
)


resolvers ++= Seq(
            "Sonatype Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/"
)

scalaVersion := "2.10.1"

