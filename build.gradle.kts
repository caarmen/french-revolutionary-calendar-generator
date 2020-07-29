plugins {
    application
    kotlin("jvm") version "1.3.72"
}

group = "ca.rmen"
version = "1.0-SNAPSHOT"

application {
    mainClassName = "ca.rmen.frcg.MainKt"
}
repositories {
    jcenter()
    maven(url = "https://kotlin.bintray.com/kotlinx")
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("org.jetbrains.kotlinx:kotlinx-cli:0.2.1")
    implementation("ca.rmen:lib-french-revolutionary-calendar:1.8.2")
}

tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = application.mainClassName
    }
    from({
        configurations.runtimeClasspath.get().filter { it.name.endsWith("jar") }.map { zipTree(it) }
    })
}


