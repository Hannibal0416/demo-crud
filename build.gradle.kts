import com.github.spotbugs.snom.Confidence
import com.github.spotbugs.snom.Effort
import net.ltgt.gradle.errorprone.CheckSeverity
import net.ltgt.gradle.errorprone.errorprone

plugins {
	java
	checkstyle
	pmd
	id("org.springframework.boot") version "3.2.3"
	id("io.spring.dependency-management") version "1.1.4"
	id("org.graalvm.buildtools.native") version "0.9.28"
	id("com.diffplug.spotless") version "6.25.0"
	id("com.github.spotbugs") version "6.0.12"
	id("net.ltgt.errorprone") version "3.1.0"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_21
}

spotless {
	java {
		target("src/*/java/**/*.java", "test/*/java/**/*.java")
		importOrder()
		removeUnusedImports()
		eclipse().configFile("${project.rootDir}/assets/eclipse-java-google-style.xml")
		indentWithSpaces()
		formatAnnotations()
	}
}

checkstyle {
	toolVersion = "10.15.0"
	config = rootProject.resources.text.fromFile("assets/google_checks.xml")
//	ignoreFailures = false
	maxWarnings = 0
}

pmd {
//	isConsoleOutput = true
	rulesMinimumPriority = 5
	ruleSets = listOf("category/java/codestyle.xml", "category/java/bestpractices.xml", "category/java/errorprone.xml",)
	ruleSetFiles = files(rootProject.resources.text.fromFile("assets/pmd-custom.xml"))
}

spotbugs {
	ignoreFailures = false
	showStackTraces = true
	showProgress = true
	effort = Effort.DEFAULT
	reportLevel = Confidence.DEFAULT
	visitors = listOf("FindSqlInjection", "SwitchFallthrough")
	omitVisitors = listOf("FindNonShortCircuit")
	reportsDir = file(layout.buildDirectory.dir("spotbugs"))
//	includeFilter = file("include.xml")
//	excludeFilter = file("exclude.xml")
//	baselineFile = file("baseline.xml")
//	onlyAnalyze = listOf("com.foobar.MyClass", "com.foobar.mypkg.*")
	maxHeapSize = "1g"
	extraArgs = listOf("-nested:false")
//	jvmArgs = listOf("-Duser.language=ja")
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


	spotbugsPlugins("com.h3xstream.findsecbugs:findsecbugs-plugin:1.13.0")

	errorprone("com.google.errorprone:error_prone_core:2.27.0")
	errorprone("com.uber.nullaway:nullaway:0.10.25")
}

tasks.withType<Test> {
	useJUnitPlatform()
	environment("LIQUIBASE_DUPLICATE_FILE_MODE", "WARN")
}

tasks.withType<JavaCompile>().configureEach {
	options.errorprone.disableWarningsInGeneratedCode = true
	options.errorprone.isEnabled = true
	options.errorprone {
		check("NullAway", CheckSeverity.ERROR)
		option("NullAway:AnnotatedPackages", "com.example.demo")
		option("NullAway:ExcludedClassAnnotations", "lombok.Data")

	}
}