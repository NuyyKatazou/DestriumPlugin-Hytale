plugins {
    `java-library`
    `maven-publish`
}

group = "fr.amazonia"
version = "0.0.1-SNAPSHOT"
description = "DestriumPlugin"
java.sourceCompatibility = JavaVersion.VERSION_1_8

repositories {
    mavenLocal()
    maven {
        url = uri("https://repo.maven.apache.org/maven2/")
    }
}

dependencies {
    compileOnly(files("Server/HytaleServer.jar"))
}

publishing {
    publications.create<MavenPublication>("maven") {
        from(components["java"])
    }
}

tasks.withType<JavaCompile>() {
    options.encoding = "UTF-8"
}

tasks.withType<Javadoc>() {
    options.encoding = "UTF-8"
}
