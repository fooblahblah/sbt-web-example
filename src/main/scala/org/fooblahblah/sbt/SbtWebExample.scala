package org.fooblahblah.sbt

import sbt._
import sbt.Keys._
import com.typesafe.sbt.web._
import com.typesafe.sbt.jse.SbtJsTask
import spray.json._

object Import {

  object SbtWebExampleKeys {
    val example = TaskKey[Seq[File]]("example", "Invoke the fake external compiler.")
  }

}

object SbtWebExample extends AutoPlugin {

  override def requires = SbtJsTask

  override def trigger = AllRequirements

  val autoImport = Import

  import SbtWeb.autoImport._
  import WebKeys._
  import SbtJsTask.autoImport.JsTaskKeys._
  import autoImport.SbtWebExampleKeys._

  val exampleUnscopedSettings = Seq(
    includeFilter := GlobFilter("*.js")
  )

  override def projectSettings = inTask(example)(
    SbtJsTask.jsTaskSpecificUnscopedSettings ++
      inConfig(Assets)(exampleUnscopedSettings) ++
      inConfig(TestAssets)(exampleUnscopedSettings) ++
      Seq(
        moduleName := "sbt-web-example",
        shellFile := getClass.getClassLoader.getResource("stub.js"),

        taskMessage in Assets := "Fake tool compiling",
        taskMessage in TestAssets := "Fake tool test compiling"
      )
  ) ++ SbtJsTask.addJsSourceFileTasks(example) ++ Seq(
    example in Assets := (example in Assets).dependsOn(webModules in Assets).value,
    example in TestAssets := (example in TestAssets).dependsOn(webModules in TestAssets).value
  )

}
