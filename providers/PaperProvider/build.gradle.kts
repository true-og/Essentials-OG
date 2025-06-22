plugins { id("essentials.base-conventions") }

java { disableAutoTargetJvm() }

dependencies {
    implementation(project(":providers:BaseProviders")) {
        exclude(module = "spigot-api")
    }
    compileOnly("org.purpurmc.purpur:purpur-api:1.19.4-R0.1-SNAPSHOT")
    compileOnly("io.papermc.paper:paper-mojangapi:1.19.4-R0.1-SNAPSHOT")
}

essentials {
    injectBukkitApi.set(false)
    injectBstats.set(false)
}

