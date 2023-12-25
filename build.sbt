val scala3Version = "3.3.1"

lazy val root = project
  .in(file("."))
  .settings(
    name := "My Logistics",
    version := "0.0.1",

    scalaVersion := scala3Version,

    libraryDependencies += "org.scalameta" %% "munit" % "0.7.29" % Test
  )
