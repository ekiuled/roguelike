plugins {
    application
}

repositories {
    mavenCentral()
    maven(url = "https://jitpack.io")
}

dependencies {
    implementation(project(":client"))
    implementation(project(":server"))
}

application {
    mainClass.set("roguelike.SinglePlayer")
}

tasks.compileJava {
    options.release.set(15)
}