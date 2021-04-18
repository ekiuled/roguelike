plugins {
    application
}

repositories {
    mavenCentral()
    maven(url = "https://jitpack.io")
}

dependencies {
    testImplementation("junit:junit:4.13")
    implementation("org.slf4j:slf4j-api:1.7.+")
    implementation("org.slf4j:slf4j-simple:1.7.+")
    implementation("com.rabbitmq:amqp-client:5.+")
    implementation("com.google.code.gson:gson:2.8.+")
    implementation("com.github.trystan:AsciiPanel:master-SNAPSHOT")
    implementation(project(":lib"))
}

application {
    mainClass.set("roguelike.Client")
}

tasks.compileJava {
    options.release.set(15)
}