rootProject.name = "DestriumPlugin"

plugins {
    // See documentation on https://scaffoldit.dev
    id("dev.scaffoldit") version "0.2.+"
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

hytale {
    usePatchline("release")
    useVersion("latest")

    repositories {
        gradlePluginPortal()
        mavenCentral()
        maven("https://maven.hytale-modding.info/releases") {
            name = "HytaleModdingReleases"
        }
    }

    dependencies {
        implementation("com.hypixel.hytale:Server:+")
    }
}