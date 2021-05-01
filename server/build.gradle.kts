plugins {
    application
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.7.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("org.slf4j:slf4j-api:1.7.+")
    implementation("org.slf4j:slf4j-simple:1.7.+")
    implementation("com.rabbitmq:amqp-client:5.+")
    implementation("com.google.code.gson:gson:2.8.+")
    implementation(project(":lib"))
}

application {
    mainClass.set("roguelike.Server")
}

tasks.compileJava {
    options.release.set(15)
}

tasks.test {
    useJUnitPlatform()
}
