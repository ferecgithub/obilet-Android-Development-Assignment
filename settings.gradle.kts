pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "ObiletAndroidAssignment"
include(":app")
include(":core")
include(":core:caching")
include(":core:network")
include(":core:navigation")
include(":core:ui")
include(":core:data")
include(":feature:journey")
include(":feature:splash")
