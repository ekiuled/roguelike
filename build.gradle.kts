plugins {
    java
}

dependencies {
    implementation(project(":client"))
    implementation(project(":server"))
}

tasks.register("run") {
    dependsOn("server:run")
    dependsOn("client:run")
}