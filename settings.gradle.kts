dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
    repositories {
        mavenCentral()
        gradlePluginPortal()
        maven("https://repo.purpurmc.org/snapshots/")
        maven("https://hub.spigotmc.org/nexus/content/groups/public/")
        maven("https://repo.papermc.io/repository/maven-public/")
        maven("https://jitpack.io") {
            content { includeGroup("com.github.milkbowl") }
            content { includeGroup("com.github.MinnDevelopment") }
        }
        maven("https://repo.extendedclip.com/content/repositories/placeholderapi/") {
            content { includeGroup("me.clip") }
        }
        maven("https://libraries.minecraft.net/") { content { includeGroup("com.mojang") } }
        mavenCentral {
            content { includeGroup("net.dv8tion") }
            content { includeGroup("net.kyori") }
            content { includeGroup("org.apache.logging.log4j") }
            content { includeGroup("com.google.googlejavaformat") }
        }
    }
}

pluginManagement {
    repositories { gradlePluginPortal() }
    plugins {
        id("com.diffplug.spotless") version "7.0.4"
        id("com.gradleup.shadow") version "8.3.6"
    }
    includeBuild("build-logic")
}

rootProject.name = "EssentialsXParent"

include(":EssentialsX")

project(":EssentialsX").projectDir = file("Essentials")

include(":providers:BaseProviders")

include(":providers:NMSReflectionProvider")

include(":providers:PaperProvider")

include(":providers:1_8Provider")

include(":providers:1_12Provider")
