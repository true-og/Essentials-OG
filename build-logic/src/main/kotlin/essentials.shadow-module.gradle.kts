import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("essentials.module-conventions")
    id("com.github.johnrengelman.shadow")
}

tasks {
    jar {
        archiveClassifier.set("unshaded")
    }
    shadowJar {
        archiveClassifier.set("") // Empty String, NOT null.
    }
}

extensions.configure<EssentialsModuleExtension> {
    archiveFile.set(tasks.named<ShadowJar>("shadowJar").flatMap { it.archiveFile })
}

