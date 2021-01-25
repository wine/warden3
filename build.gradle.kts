plugins {
    kotlin("jvm") version "1.4.10"
}

group = "io.github.wine"
version = "0.1.0-dev"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))

    implementation("org.ow2.asm:asm-all:5.2")
    implementation("com.xenomachina:kotlin-argparser:2.0.7")

    compileOnly(files("$projectDir/libs/Minecraft-deobf.jar"))
}

// Registers our own Gradle task for shading runtime dependencies
// to the "uber" jar, this will run independently without requiring
// the dependencies to manually be loaded into the classpath.
// This "uber" artifact is the only artifact that should be used,
// therefore we only include the manifest attributes in this jar,
// rendering the other artifacts "useless" unless the manifest
// is manually added after compilation.
val uberJar = task("uberJar", Jar::class) {
    archiveBaseName.set("${project.name}-uber")

    manifest {
        attributes["Agent-Class"] = "io.github.wine.warden.load.AgentKt"
        attributes["Premain-Class"] = "io.github.wine.warden.load.AgentKt"
        attributes["Can-Redefine-Classes"] = true
        attributes["Can-Retransform-Classes"] = true
    }

    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
    with(tasks.jar.get() as CopySpec)
}

tasks {
    "build" {
        dependsOn(uberJar)
    }
}