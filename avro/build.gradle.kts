import com.github.davidmc24.gradle.plugin.avro.GenerateAvroJavaTask

plugins {
    java
    `java-library`
    id("com.github.davidmc24.gradle.plugin.avro-base") version "1.2.0"
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter:5.7.1")

    implementation("org.apache.avro:avro:1.11.0")
}

tasks.test {
    useJUnitPlatform()
}

tasks.register<GenerateAvroJavaTask>("generateAvro") {
    description = "Generates Avro Java Classes"
    group = "Kafka Sandbox"
    setSource("src/main/avro")
    setOutputDir(file("src/main/java"))
}