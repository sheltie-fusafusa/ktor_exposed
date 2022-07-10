val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project

val exposedVersion: String by project

plugins {
    application
    kotlin("jvm") version "1.7.0"
    kotlin("plugin.serialization").version("1.7.0")
}

group = "xyz.sheltiegarage"
version = "0.0.1"
application {
    mainClass.set("xyz.sheltiegarage.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
    maven { url = uri("https://maven.pkg.jetbrains.space/public/p/ktor/eap") }
}

dependencies {
    implementation("io.ktor:ktor-server-core-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-tomcat-jvm:$ktor_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    testImplementation("io.ktor:ktor-server-tests-jvm:$ktor_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")

    implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-dao:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-java-time:$exposedVersion")

    implementation("io.ktor:ktor-server-content-negotiation:$ktor_version")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktor_version")

    // https://mvnrepository.com/artifact/mysql/mysql-connector-java
    implementation("mysql:mysql-connector-java:8.0.29")

    // https://mvnrepository.com/artifact/com.zaxxer/HikariCP
    implementation("com.zaxxer:HikariCP:4.0.3")

}