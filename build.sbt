ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.2.1"

lazy val root = (project in file("."))
  .settings(
    name := "TowerOfHanoi",
    idePackagePrefix := Some("com.github.hemirime.toh")
  )

libraryDependencies ++= Seq(
  "org.typelevel" %% "cats-effect" % "3.3.14",
  "org.typelevel" %% "cats-core" % "2.8.0",
  "org.typelevel" %% "munit-cats-effect-3" % "1.0.7" % Test
)
