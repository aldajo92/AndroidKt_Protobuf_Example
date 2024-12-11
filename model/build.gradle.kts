plugins {
    id("java-library")
    id("com.google.protobuf")
    alias(libs.plugins.jetbrains.kotlin.jvm)
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

kotlin {
    compilerOptions {
        jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11
    }
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:3.21.7"
    }
    plugins {
        generateProtoTasks {
            all().forEach { task ->
                task.builtins {
                    getByName("java") { // Get the existing 'java' plugin
                        option("lite") // Set the 'lite' option
                    }
                }
            }
        }
    }
}

dependencies {
    implementation("com.google.protobuf:protobuf-javalite:4.26.1")
}

sourceSets {
    named("main") {
        java.srcDir("build/generated/source/proto/main/java")
        proto.srcDir("src/main/proto")
    }
}

tasks.withType<ProcessResources> {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}
