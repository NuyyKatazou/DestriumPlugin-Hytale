rootProject.name = "DestriumPlugin"

plugins {
    // See documentation on https://scaffoldit.dev
    id("dev.scaffoldit") version "0.2.+"
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

// Would you like to do a split project?
// Create a folder named "common", then configure details with `common { }`

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

    manifest {
        Group = "fr.amazonia"
        Name = "DestriumMod"
        Version = "0.0.4-SNAPSHOT" //4 Version
        Main = "fr.amazonia.DestriumPlugin"
    }
}