import sbt.Keys.libraryDependencies

name := "ScalaSandBox"

version := "1.0"

lazy val commonSettings = Seq(
  organization := "com.devitconsulting",
  version := "0.1.0-SNAPSHOT",
  scalaVersion := "2.12.7",
  libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.5" % "test"
)

lazy val root = (project in file("."))
  .aggregate(higher_kinded_types, abstract_types)
  .settings(
    commonSettings
  )

lazy val higher_kinded_types = (project in file("higher_kinded_types"))
  .settings(
    commonSettings
  )


lazy val abstract_types = (project in file("abstract_types"))
  .settings(
    commonSettings
  )