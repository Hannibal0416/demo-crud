plugins {
	java
	id("org.springframework.boot") version "3.2.3"
	id("io.spring.dependency-management") version "1.1.4"
	id("org.graalvm.buildtools.native") version "0.9.28"
	id("com.diffplug.spotless") version "6.25.0"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_21
}

spotless {
	java {
		importOrder()
		removeUnusedImports()
		eclipse().configFile("${project.rootDir}/assets/intellij-java-google-style.xml")
		indentWithSpaces()
		formatAnnotations()
	}
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa:3.2.3")
	implementation("org.postgresql:postgresql:42.7.3")
	implementation("org.springframework.boot:spring-boot-starter-validation:3.2.3")
	implementation("org.springframework.boot:spring-boot-starter-actuator:3.2.3")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.3.0")
	implementation("org.liquibase:liquibase-core:4.27.0")
	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.mockito:mockito-core:5.11.0")
	testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.2")
	implementation("com.h2database:h2:2.2.224")

}

tasks.withType<Test> {
	useJUnitPlatform()
	environment("LIQUIBASE_DUPLICATE_FILE_MODE", "WARN")
}
