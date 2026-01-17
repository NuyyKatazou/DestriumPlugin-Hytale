plugins {
    `java-library`
    `maven-publish`
    id("hytale-mod") version("0.+")
}

group = "fr.amazonia"
version = "0.0.2-SNAPSHOT" //2 Version
description = "DestriumMod"
java.sourceCompatibility = JavaVersion.VERSION_1_8

val appData = System.getenv("APPDATA") ?: (System.getenv("HOME") + "/.var/app/com.hypixel.HytaleLauncher/data")
val hytaleAssets = file("$appData/Hytale/install/release/package/game/latest/Assets.zip")

repositories {
    mavenCentral()
    maven("https://maven.hytale-modding.info/releases") {
        name = "HytaleModdingReleases"
    }
}

dependencies {
    compileOnly(files("Server/HytaleServer.jar"))

    if (hytaleAssets.exists()) {
        compileOnly(files(hytaleAssets))
    } else {
        // Optional: Print a warning so you know why it's missing
        logger.warn("Hytale Assets.zip not found at: ${hytaleAssets.absolutePath}")
    }
}

tasks.named<ProcessResources>("processResources") {
    var replaceProperties = mapOf(
        "plugin_group" to findProperty("plugin_group"),
        "plugin_maven_group" to project.group,
        "plugin_name" to project.name,
        "plugin_version" to project.version,
        "server_version" to findProperty("server_version"),

        "plugin_description" to findProperty("plugin_description"),
        "plugin_website" to findProperty("plugin_website"),

        "plugin_main_entrypoint" to findProperty("plugin_main_entrypoint"),
        "plugin_author" to findProperty("plugin_author")
    )

    filesMatching("manifest.json") {
        expand(replaceProperties)
    }

    inputs.properties(replaceProperties)
}

tasks.withType<Jar> {
    manifest {
        attributes["Specification-Title"] = rootProject.name
        attributes["Specification-Version"] = version
        attributes["Implementation-Title"] = project.name
        attributes["Implementation-Version"] =
            providers.environmentVariable("COMMIT_SHA_SHORT")
                .map { "${version}-${it}" }
                .getOrElse(version.toString())
    }
}

publishing {
    repositories {
        // This is where you put repositories that you want to publish to.
        // Do NOT put repositories for your dependencies here.
    }
    publications.create<MavenPublication>("maven") {
        from(components["java"])
    }
}

val syncAssets = tasks.register<Copy>("syncAssets") {
    group = "hytale"
    description = "Automatically syncs assets from Build back to Source after server stops."

    // Take from the temporary build folder (Where the game saved changes)
    from(layout.buildDirectory.dir("resources/main"))

    // Copy into your actual project source (Where your code lives)
    into("src/main/resources")

    // IMPORTANT: Protect the manifest template from being overwritten
    exclude("manifest.json")

    // If a file exists, overwrite it with the new version from the game
    duplicatesStrategy = DuplicatesStrategy.INCLUDE

    doLast {
        println("✅ Assets successfully synced from Game to Source Code!")
    }
}

tasks.withType<JavaCompile>() {
    options.encoding = "UTF-8"
}

tasks.withType<Javadoc>() {
    options.encoding = "UTF-8"
}
