import com.google.cloud.tools.jib.gradle.JibExtension

plugins {
	java
	id("org.springframework.boot") version "4.1.0"
	id("io.spring.dependency-management") version "1.1.7"
	id("com.google.cloud.tools.jib") version "3.5.3"
}

group = "com.mftech"
version = "0.0.1"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-webmvc")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.6.0")
	compileOnly("org.projectlombok:lombok")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-webmvc-test")
	testCompileOnly("org.projectlombok:lombok")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	testAnnotationProcessor("org.projectlombok:lombok")

	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	runtimeOnly("org.postgresql:postgresql")
	testImplementation("org.springframework.boot:spring-boot-starter-data-jpa-test")
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	testImplementation("org.springframework.boot:spring-boot-starter-actuator-test")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

configure<JibExtension> {
	to {
		image = "ecommerce/monolith-ecommerce"
		tags = setOf(project.version.toString(), "latest")
	}
//	extraDirectories {
//		paths {
//			path {
//				setFrom("/config")
//				into = "/app/config"
//			}
//		}
//	}
	container {
		ports = listOf("8080")
		jvmFlags = mutableListOf("-Xms256m", "-Xmx512m")
		creationTime.set("USE_CURRENT_TIMESTAMP")
	}
}
