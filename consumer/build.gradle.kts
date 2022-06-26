plugins {
    java
    application
}

repositories {
    mavenCentral()
    maven {
        url = uri("https://packages.confluent.io/maven/")
    }
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter:5.7.1")

    implementation(project(":avro"))
    implementation("org.apache.kafka:kafka-clients:3.1.0")
    implementation("io.confluent:kafka-avro-serializer:7.1.1")
    implementation("org.slf4j:slf4j-simple:1.7.30")

    compileOnly("org.projectlombok:lombok:1.18.20")
    annotationProcessor("org.projectlombok:lombok:1.18.20")
}

application {
    mainClass.set("kafka.sandbox.App")
    applicationDefaultJvmArgs = listOf(
            "-javaagent:${project.rootDir}/opentelemetry-javaagent.jar",
            "-Dotel.service.name=kafka-client-consumer",
            "-Dotel.javaagent.enabled=true",
            "-Dotel.traces.exporter=jaeger",
            "-Dotel.exporter.jaeger.endpoint=http://localhost:14250",
            "-Dotel.metrics.exporter=none"
    )
}

tasks.test {
    useJUnitPlatform()
}
