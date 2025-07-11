plugins { id("essentials.base-conventions") }

dependencies {
    implementation(project(":providers:BaseProviders")) {
        exclude(group = "org.spigotmc", module = "spigot-api")
    }
    implementation("org.spigotmc:spigot-api:1.8.8-R0.1-SNAPSHOT")
}

essentials {
    injectBukkitApi.set(false)
    injectBstats.set(false)
}

