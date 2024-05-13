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

rootProject.name = "ZimranTestTask"
include(":app")
include(":imageLibrary:core")
include(":imageLibrary:image-compose")
include(":common:core")
include(":common:core-compose")
include(":common:main:api")
include(":common:main:data")
include(":common:main:presentation")
include(":common:main:compose")
include(":common:core-utils")
include(":common:umbrella-compose")
include(":common:umbrella-core")

