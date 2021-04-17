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

    implementation("com.github.trystan:AsciiPanel:master-SNAPSHOT")
}

application {
    mainClass.set("roguelike.Client")
}
