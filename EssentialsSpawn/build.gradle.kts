plugins {
    id("essentials.module-conventions")
}

dependencies {
    compileOnly(project(":EssentialsX"))
}

tasks.jar {
    archiveFileName.set("EssentialsXSpawn-OG-${rootProject.extra["FULL_VERSION"]}.jar")
    destinationDirectory.set(rootProject.layout.buildDirectory.dir("libs"))
}

