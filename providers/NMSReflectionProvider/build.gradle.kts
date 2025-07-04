plugins { id("essentials.base-conventions") }

dependencies {
    implementation(project(":providers:BaseProviders"))
    api("org.bukkit:bukkit:1.12.2-R0.1-SNAPSHOT")
}

essentials {
    injectBukkitApi.set(false)
    injectBstats.set(false)
}

