import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("com.gradleup.shadow")
    id("essentials.shadow-module")
}

dependencies {
    compileOnly("com.github.milkbowl:VaultAPI:1.7") {
        exclude(group = "org.bukkit", module = "bukkit")
    }
    compileOnly("net.luckperms:api:5.0")
    api("io.papermc:paperlib:1.0.6")
    implementation("org.spongepowered:configurate-yaml:4.1.2")
    implementation("org.checkerframework:checker-qual:3.14.0")
    api(project(":providers:BaseProviders"))
    api(project(":providers:PaperProvider"))
    api(project(":providers:NMSReflectionProvider")) {
        exclude(group = "org.bukkit", module = "bukkit")
    }
    api(project(":providers:1_8Provider")) {
        exclude(group = "org.spigotmc", module = "spigot")
    }
    api(project(":providers:1_12Provider")) {
        exclude(group = "org.bukkit", module = "bukkit")
    }
}

tasks.test {
    testLogging.showStandardStreams = true
}

tasks.named<ShadowJar>("shadowJar") {
    dependsOn(tasks.spotlessApply)
    archiveFileName.set("Essentials-OG-${rootProject.extra["FULL_VERSION"]}.jar")
    destinationDirectory.set(rootProject.layout.buildDirectory.dir("libs"))
    dependencies {
        include(dependency("io.papermc:paperlib"))
        include(dependency("org.spongepowered:configurate-yaml"))
        include(dependency("org.spongepowered:configurate-core"))
        include(dependency("org.yaml:snakeyaml"))
        include(dependency("io.leangen.geantyref:geantyref"))
        include(dependency("org.checkerframework:checker-qual"))
        include(project(":providers:BaseProviders"))
        include(project(":providers:PaperProvider"))
        include(project(":providers:NMSReflectionProvider"))
        include(project(":providers:1_8Provider"))
        include(project(":providers:1_12Provider"))
    }
    relocate("io.papermc.lib",               "com.earth2me.essentials.paperlib")
    relocate("org.spongepowered.configurate","com.earth2me.essentials.libs.configurate")
    relocate("org.yaml.snakeyaml",           "com.earth2me.essentials.libs.snakeyaml")
    relocate("io.leangen.geantyref",         "com.earth2me.essentials.libs.geantyref")
    relocate("org.checkerframework",         "com.earth2me.essentials.libs.checkerframework")
    minimize { include(dependency("org.checkerframework:checker-qual")) }
}

