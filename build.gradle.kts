import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.kotlin.konan.properties.Properties
import java.io.FileInputStream

plugins {
    id("org.springframework.boot") version "2.7.3"
    id("io.spring.dependency-management") version "1.0.13.RELEASE"
    kotlin("jvm") version "1.6.21"
    kotlin("plugin.spring") version "1.6.21"
    kotlin("plugin.jpa") version "1.6.21"
    id("org.sonarqube") version "3.4.0.2513"
    id("java")
}

group = "br.com.tcc"
version = "0.0.3-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

val localProperties: Properties = Properties().apply {
    val localProperties = File("local.properties")
    if (localProperties.isFile) {
        FileInputStream(localProperties).use { reader ->
            load(reader)
        }
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:2.7.3")
    implementation("org.springframework.boot:spring-boot-starter-security:2.7.3")
    implementation("org.springframework.boot:spring-boot-starter-web:2.7.3")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.3")
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.6.21")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.6.21")
    developmentOnly("org.springframework.boot:spring-boot-devtools:2.7.3")
    runtimeOnly("mysql:mysql-connector-java:8.0.30")

    testImplementation("org.springframework.boot:spring-boot-starter-test:2.7.3")
    testImplementation("org.springframework.security:spring-security-test:5.7.3")
    testImplementation("junit:junit:4.13.2")

    implementation("io.jsonwebtoken:jjwt:0.9.1")

    implementation("org.springframework.boot:spring-boot-starter-validation:2.7.3")
    implementation("org.springframework.boot:spring-boot-starter-cache:2.7.3")
    implementation("org.springframework.boot:spring-boot-starter-mail:2.7.3")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server:2.7.3")

//	implementation("org.springframework.boot:spring-boot-starter-actuator")
//	implementation("de.codecentric:spring-boot-admin-starter-client:2.7.4")

    implementation("io.springfox:springfox-swagger2:3.0.0")
    implementation("org.springdoc:springdoc-openapi-ui:1.6.14")

    implementation("org.springframework:spring-web:5.3.22")

    implementation("com.google.firebase:firebase-admin:9.1.1")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<Jar> {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

sonarqube {
    properties {
        property("sonar.projectName", "Petsus Backend")
        property("sonar.projectKey", "petsus-backend")
        property("sonar.sources", "src")
        property("sonar.sourceEncoding", "UTF-8")
        property("sonar.tests", "src/test/kotlin")
        property("sonar.test.inclusions", "**/*Test*/**")
        property("sonar.login", "admin")
        property("sonar.password", localProperties["sonar.password"].toString())
    }
}