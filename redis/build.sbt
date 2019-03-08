name := "play-modules-redis"
organization := "yieldstreet"

scalaVersion := "2.11.6"
crossScalaVersions := Seq("2.11.6", "2.10.5")
javacOptions ++= Seq("-source", "1.8", "-target", "1.8", "-Xlint:unchecked", "-encoding", "UTF-8")
scalacOptions += "-deprecation"

libraryDependencies ++= Seq(
  "com.typesafe.play"         %% "play"               % "2.4.0"     % "provided",
  "com.typesafe.play"         %% "play-cache"         % "2.4.0",
  "biz.source_code"           %  "base64coder"        % "2010-12-19",
  "redis.clients"             %  "jedis"              % "3.0.1",
  "com.typesafe.play"         %% "play-test"          % "2.4.0"     % "test",
  "com.typesafe.play"         %% "play-specs2"        % "2.4.0"     % "test",
  "org.specs2"                %% "specs2-core"        % "3.3.1"     % "test"
)

resolvers ++= Seq(
  "pk11 repo" at "http://pk11-scratch.googlecode.com/svn/trunk",
  "Scalaz Bintray Repo" at "http://dl.bintray.com/scalaz/releases"
)

publishTo := {
  val repository = if (isSnapshot.value) "snapshots" else "releases"
  Some("YieldStreet Nexus" at s"https://repo.yieldstreet.com/repository/maven-$repository/")
}

releasePublishArtifactsAction := PgpKeys.publishSigned.value
releaseTagName := s"redis-${(version in ThisBuild).value}"
releaseCrossBuild := true

import ReleaseTransformations._
releaseProcess := Seq[ReleaseStep](
  checkSnapshotDependencies,
  inquireVersions,
  runTest,
  setReleaseVersion,
  commitReleaseVersion,
  tagRelease,
  publishArtifacts,
  setNextVersion,
  commitNextVersion,
  pushChanges
)
