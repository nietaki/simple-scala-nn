name := "simple-scala-nn"

version := "0.2"

libraryDependencies  ++= Seq(
            //"org.scalanlp" %% "breeze-math" % "0.4-SNAPSHOT",
            "org.scalanlp" % "breeze_2.10" % "0.5.2",
            //"org.scalanlp" %% "breeze-learn" % "0.4-SNAPSHOT",
            //"org.scalanlp" %% "breeze-process" % "0.4-SNAPSHOT",
            //"org.scalanlp" %% "breeze-viz" % "0.4-SNAPSHOT",
            "org.specs2" %% "specs2" % "1.14" % "test",
            "org.scalatest" % "scalatest_2.10" % "1.9.1" % "test"
)


resolvers ++= Seq(
            //"Sonatype Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/"
            "Sonatype Releases" at "https://oss.sonatype.org/content/repositories/releases/"
)

scalacOptions += "-deprecation"

scalacOptions ++= Seq("-Xmax-classfile-name", "254")

scalaVersion := "2.10.3"

