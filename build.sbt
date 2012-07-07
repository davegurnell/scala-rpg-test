resolvers ++= Seq(
  "Sonasnaps" at "http://oss.sonatype.org/content/repositories/snapshots",
  "Codahale"  at "http://repo.codahale.com"
)

libraryDependencies ++= Seq(
  "com.codahale" %% "jerkson" % "0.5.0",
  "org.scalaz" %% "scalaz-core" % "7.0-SNAPSHOT"
)
