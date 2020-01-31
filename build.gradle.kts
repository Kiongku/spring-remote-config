plugins {
    val kotlinVersion = "1.3.61"
    val springBootVersion = "2.2.4.RELEASE"
    val springDmVersion = "1.0.9.RELEASE"
    val dokkaVersion = "0.10.0"

    kotlin("jvm") version kotlinVersion
    kotlin("kapt") version kotlinVersion
    kotlin("plugin.spring") version kotlinVersion
    kotlin("plugin.jpa") version kotlinVersion
    id("org.springframework.boot") version springBootVersion
    id("io.spring.dependency-management") version springDmVersion
    id("org.jetbrains.dokka") version dokkaVersion
}

group = "org.example"
version = "1.0-SNAPSHOT"

extra["gradleVersion"] = "6.1.1"
extra["springCloudVersion"] = "Hoxton.SR1"
extra["kotlinTestVersion"] = "3.4.2"

repositories {
    mavenCentral()
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
    }
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.liquibase:liquibase-core")
    runtimeOnly("com.h2database:h2")
    implementation("org.springframework.cloud:spring-cloud-starter-config")
    implementation("org.springframework.cloud:spring-cloud-config-server")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.kotlintest:kotlintest-runner-junit5:${property("kotlinTestVersion")}")
    testImplementation("io.kotlintest:kotlintest-extensions-spring:${property("kotlinTestVersion")}")

    kapt("org.springframework.boot:spring-boot-configuration-processor")
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }

    wrapper {
        distributionType = Wrapper.DistributionType.ALL
        gradleVersion = "${property("gradleVersion")}"
    }

    test {
        useJUnitPlatform()
    }
}