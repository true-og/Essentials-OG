plugins {
    eclipse
    id("essentials.parent-build-logic")
}

group = "net.essentialsx"
version = "2.21.0-SNAPSHOT"

val gitCommit: String = indraGit.commit()?.abbreviate(7)?.name() ?: "unknown"
val gitDepth: Int = GitUtil.commitsSinceLastTag(project) ?: 0
val gitBranch: String = GitUtil.headBranchName(project) ?: "detached"

extra.apply {
    this["GIT_COMMIT"] = gitCommit
    this["GIT_DEPTH"] = gitDepth
    this["GIT_BRANCH"] = gitBranch
    this["FULL_VERSION"] =
        version.toString().replace("-SNAPSHOT", "-dev+${gitDepth}-${gitCommit}")
}

