import org.apache.tools.ant.filters.ReplaceTokens

plugins {
    id("java")
    id("net.kyori.indra")
}

val baseExtension = extensions.create<EssentialsBaseExtension>("essentials", project)

val spigotVersion = "1.19.4-R0.1-SNAPSHOT"
val junit5Version = "5.7.0"
val mockitoVersion = "3.2.0"

dependencies {
    testImplementation("org.junit.jupiter", "junit-jupiter", junit5Version)
    testImplementation("org.junit.vintage", "junit-vintage-engine", junit5Version)
    testImplementation("org.mockito", "mockito-core", mockitoVersion)

    constraints {
        implementation("org.yaml:snakeyaml:1.28") {
            because("Bukkit API ships old versions, Configurate requires modern versions")
        }
    }
}

afterEvaluate {
    if (baseExtension.injectBukkitApi.get()) {
        dependencies {
            api("org.spigotmc", "spigot-api", spigotVersion)
        }
    }
}

tasks {
    compileJava {
        options.compilerArgs.add("-Xlint:-deprecation")
    }
    javadoc {
        title = "${project.name} API (v${rootProject.ext["FULL_VERSION"]})"
        val options = options as? StandardJavadocDocletOptions ?: return@javadoc
        options.links(
            "https://hub.spigotmc.org/javadocs/spigot/"
        )
        options.addBooleanOption("Xdoclint:none", true)
    }
    withType<Jar> {
        archiveVersion.set(rootProject.ext["FULL_VERSION"] as String)
    }
    withType<Sign> {
        onlyIf { project.hasProperty("forceSign") }
    }
}

// Dependency caching
configurations.all {
    resolutionStrategy.cacheChangingModulesFor(5, "minutes")
}

indra {
    github("EssentialsX", "Essentials")
    gpl3OnlyLicense()

    javaVersions {
        target(17)
        minimumToolchain(17)
    }
}

// undo https://github.com/KyoriPowered/indra/blob/master/indra-common/src/main/kotlin/net/kyori/indra/IndraPlugin.kt#L57
extensions.getByType<BasePluginExtension>().archivesName.set(project.name)
