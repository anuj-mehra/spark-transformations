import sbtassembly.MergeStrategy

name := "spark-transformations"

version := "0.1"

scalaVersion := "2.11.8"

updateOptions := updateOptions.value.withCachedResolution(true)
updateOptions := updateOptions.value.withGigahorse(false)
updateOptions := updateOptions.value.withInterProjectFirst(false)
updateOptions := updateOptions.value.withLatestSnapshots(true)

lazy val compileScalastyle = taskKey[Unit]("compileScalastyle")

lazy val buildSettings = Seq(
  organization := "com.anuj.spark_transformations"
  /*,coverageEnabled in (Test,Compile) := true,
  coverageEnabled in (Compile,Compile) := false*/
)

lazy val commonSettings = Seq(
  target := { baseDirectory.value/"target" }
)

lazy val `spark-transformations` =  project.in(file("."))
  .aggregate(`transformations`)
  .settings(compileScalastyle := scalastyle.in(Compile).toTask("").value,
    (compile in Compile) := ((compile in Compile) dependsOn compileScalastyle).value)
  .settings(Seq(scalafmtOnCompile := true))

lazy val aggregatedProjects: Seq[ProjectReference] = Seq(transformations)

lazy val `transformations` = project
  .settings(libraryDependencies ++= Dependencies.SparkModule)
  .settings(libraryDependencies ++= Dependencies.logging)
  .settings(excludeDependencies ++= Dependencies.excludedJackson)
  .settings(excludeDependencies ++= Dependencies.excluded)
  .settings(assemblySettings)


lazy val assemblySettings = Seq(
  test in assembly := {},
  assemblyJarName in assembly := "my-spark-transformations" + name.value + "-" + version.value + ".jar",
  assemblyMergeStrategy in assembly := {

    case PathList("META-INF", xs@_*) => xs.map(_.toLowerCase) match {
      case "manifest.mf" :: Nil |
           "index.list" :: Nil |
           "dependencies" :: Nil |
           "license" :: Nil |
           "dummy.dsa" :: Nil |
           "notice" :: Nil => MergeStrategy.discard
      case _ => MergeStrategy.first
    }

    case _ => MergeStrategy.first
  }

)
unmanagedClasspath in Test += baseDirectory.value / "src/main/resources"