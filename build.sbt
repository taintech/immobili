name := "immobili"

version := "1.0"

scalaVersion := "2.11.2"

mainClass := Some("com.taintech.immobili.example.akka.Main2")

resolvers += "Typesafe Releases" at "https://repo.typesafe.com/typesafe/releases/"

resolvers += "Akka Snapshot Repository" at "http://repo.akka.io/snapshots/"

libraryDependencies += "com.typesafe.akka" % "akka-actor_2.11" % "2.3.6"

libraryDependencies += "org.seleniumhq.selenium" % "selenium-server" % "2.43.1"

libraryDependencies += "org.jsoup" % "jsoup" % "1.7.3"

ideaExcludeFolders += ".idea"

ideaExcludeFolders += ".idea_modules"