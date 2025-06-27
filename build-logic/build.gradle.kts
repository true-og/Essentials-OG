plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
}

dependencies {
    implementation("net.kyori:indra-common:3.1.1")
    implementation("xyz.jpenilla:run-task:2.1.0")
    implementation("com.gradleup.shadow:com.gradleup.shadow.gradle.plugin:8.3.6")
}

