plugins {
    eclipse
    id("java")
    id("com.diffplug.spotless") version "7.0.4"
    id("essentials.parent-build-logic")
}

group = "net.essentialsx"

version = "1.19.4"

extra.apply { this["FULL_VERSION"] = version.toString() }

subprojects {
    apply(plugin = "com.diffplug.spotless")

    plugins.withId("java") {
        the<JavaPluginExtension>().toolchain {
            languageVersion.set(JavaLanguageVersion.of(17))
            vendor.set(JvmVendorSpec.GRAAL_VM)
        }
    }
}

spotless {
    java {
        removeUnusedImports()
        palantirJavaFormat()
    }
    kotlinGradle {
        ktfmt().kotlinlangStyle().configure { it.setMaxWidth(120) }
        target("build.gradle.kts", "settings.gradle.kts")
    }
}
