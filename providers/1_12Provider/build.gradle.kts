plugins { id("essentials.base-conventions") }

dependencies { api(project(":providers:NMSReflectionProvider")) }

essentials {
    injectBukkitApi.set(false)
    injectBstats.set(false)
}

