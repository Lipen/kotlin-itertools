import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

group = "com.github.lipen"

plugins {
    kotlin("jvm") version Versions.kotlin
    `build-scan`
    `maven-publish`
    id("org.jlleitschuh.gradle.ktlint") version Versions.ktlint
    id("com.github.ben-manes.versions") version Versions.gradle_versions
    id("fr.brouillard.oss.gradle.jgitver") version Versions.jgitver
}

repositories {
    jcenter()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    testImplementation(Libs.junit_jupiter_api)
    testImplementation(Libs.junit_jupiter_params)
    testImplementation(Libs.kluent)
    testRuntimeOnly(Libs.junit_jupiter_engine)
}

val sourcesJar by tasks.registering(Jar::class) {
    archiveClassifier.set("sources")
    from(sourceSets.main.get().allSource)
}

artifacts {
    add("archives", sourcesJar)
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
            artifact(sourcesJar.get())
        }
    }
    repositories {
        maven(url = "$buildDir/repository")
    }
}

buildScan {
    termsOfServiceUrl = "https://gradle.com/terms-of-service"
    termsOfServiceAgree = "yes"
}

ktlint {
    ignoreFailures.set(true)
}

jgitver {
    strategy("MAVEN")
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
    }

    withType<Test> {
        @Suppress("UnstableApiUsage")
        useJUnitPlatform {
            systemProperty("junit.jupiter.testinstance.lifecycle.default", "per_class")
        }
        testLogging.events(
            TestLogEvent.PASSED,
            TestLogEvent.FAILED,
            TestLogEvent.SKIPPED,
            TestLogEvent.STANDARD_ERROR
        )
    }

    wrapper {
        gradleVersion = "5.6.1"
        distributionType = Wrapper.DistributionType.ALL
    }
}

defaultTasks("clean", "build")
