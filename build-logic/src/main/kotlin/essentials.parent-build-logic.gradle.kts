import xyz.jpenilla.runpaper.task.RunServer

plugins {
    id("base")
    id("net.kyori.indra.git")
    id("xyz.jpenilla.run-paper")
}

runPaper {
    disablePluginJarDetection()
}

val runModules = (findProperty("runModules") as String?)
    ?.trim()?.split(",") ?: emptySet()

tasks {
    named<Delete>("clean") {
        delete(file("jars"))
    }
}
