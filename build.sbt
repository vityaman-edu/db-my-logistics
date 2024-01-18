import com.typesafe.sbt.packager.docker.DockerApiVersion

inThisBuild(
  Seq(
    // Project
    organization := "ru.vityaman",
    version := "0.0.1",

    // Scala
    scalaVersion := "2.13.12",
    scalacOptions += "-Ywarn-unused:imports",

    // ScalaFix
    semanticdbEnabled := true,
    semanticdbVersion := scalafixSemanticdb.revision,
    scalafixScalaBinaryVersion := "2.13"
  )
)

lazy val root = (project in file("."))
  .settings(
    name := "MyLogistics"
  )
  .aggregate(backend)

lazy val backend = (project in file("backend"))
  .enablePlugins(JavaAppPackaging, DockerPlugin)
  .settings(
    // Docker image settings
    dockerBaseImage := "openjdk:21",
    dockerExposedPorts ++= Seq(8080),
    dockerApiVersion := Some(DockerApiVersion(3, 8)),

    // Dependencies
    libraryDependencies ++= Seq(
      // Testing
      "org.scalameta" %% "munit" % "0.7.29" % Test,

      // ZIO
      "dev.zio" %% "zio" % "2.0.20",
      "dev.zio" %% "zio-http" % "3.0.0-RC3",
      "dev.zio" %% "zio-json" % "0.6.2",
      "dev.zio" %% "zio-json-interop-refined" % "0.5.0",
      "dev.zio" %% "zio-interop-cats" % "23.0.03",
      "dev.zio" %% "zio-logging-slf4j2" % "2.1.15",

      // Database
      "org.typelevel" %% "cats-effect" % "3.5.2",
      "org.tpolecat" %% "doobie-core" % "1.0.0-RC4",
      "org.tpolecat" %% "doobie-hikari" % "1.0.0-RC4",
      "org.tpolecat" %% "doobie-postgres" % "1.0.0-RC4",
      "org.tpolecat" %% "doobie-refined" % "1.0.0-RC4",

      // Refined
      "eu.timepit" %% "refined" % "0.11.0",
      "eu.timepit" %% "refined-cats" % "0.11.0",
      "eu.timepit" %% "refined-scalacheck" % "0.11.0",

      // Time
      "com.github.nscala-time" %% "nscala-time" % "2.32.0",

      // Logging
      "ch.qos.logback" % "logback-classic" % "1.4.12" % Runtime
    )
  )

lazy val frontend = (project in file("frontend"))
  .enablePlugins(ScalaJSPlugin)
  .settings(
    scalaJSUseMainModuleInitializer := true,
    jsEnv := new org.scalajs.jsenv.jsdomnodejs.JSDOMNodeJSEnv(),
    libraryDependencies ++= Seq(
      "com.raquo" %%% "laminar" % "16.0.0",
      "org.scala-js" %%% "scalajs-dom" % "2.4.0"
    )
  )

addCommandAlias(
  "ensureQuality",
  Seq(
    "backend/scalafmtCheck",
    "frontend/scalafmtCheck",
    "backend/scalafix --check",
    "frontend/scalafix --check",
    "backend/test",
    "backend/Docker/stage",
    "frontend/fastLinkJS"
  ).mkString(";")
)
