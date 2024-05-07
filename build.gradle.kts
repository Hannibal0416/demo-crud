import com.github.spotbugs.snom.Confidence
import com.github.spotbugs.snom.Effort
import info.solidsoft.gradle.pitest.PitestTask
import net.ltgt.gradle.errorprone.CheckSeverity
import net.ltgt.gradle.errorprone.errorprone

plugins {
	java
	groovy
	checkstyle
	pmd
	id("org.springframework.boot") version "3.2.3"
	id("io.spring.dependency-management") version "1.1.4"
	id("org.graalvm.buildtools.native") version "0.9.28"
	id("com.github.sherter.google-java-format") version "0.9"
	id("com.diffplug.spotless") version "6.25.0"
	id("com.github.spotbugs") version "6.0.12"
	id("net.ltgt.errorprone") version "3.1.0"
	id("name.remal.sonarlint") version "4.1.1"

	id("info.solidsoft.pitest") version "1.15.0"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_21
}

googleJavaFormat {

}

spotless {
//	java {
//		target("src/*/java/**/*.java", "test/*/java/**/*.java")
//		importOrder()
//		removeUnusedImports()
//		eclipse().configFile("${project.rootDir}/assets/eclipse-java-google-style.xml")
//		indentWithSpaces()
//		formatAnnotations()
//	}
	java {
		formatAnnotations()
		googleJavaFormat()
	}

	groovy {

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

sonarLint {
	languages {
		include("java")
	}

//	rules {
//		rule("java:S100") {
//			ignoredPaths.add("**/dto/**") // Ignore all files which relative path matches `**/dto/**` glob for rule `java:S100`
//		}
//	}
	ignoredPaths.add("**/entity/**")

	logging {
		withDescription = false // Hide rule descriptions from console output
	}
}

pitest {
	junit5PluginVersion = "1.2.1"
	pitestVersion = "1.15.2"
	outputFormats = listOf("XML", "HTML")
	timestampedReports = false
//	failWhenNoMutations = false
//	verbose = true
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

var spockReportsVersion = "2.5.1-groovy-4.0"
dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa:3.2.3")
	implementation("org.springframework.cloud:spring-cloud-starter-circuitbreaker-resilience4j:3.1.1")
	implementation("io.github.resilience4j:resilience4j-bulkhead:2.1.0")
	implementation("org.postgresql:postgresql:42.7.3")
	implementation("org.springframework.boot:spring-boot-starter-validation:3.2.3")
	implementation("org.springframework.boot:spring-boot-starter-actuator:3.2.3")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.3.0")
	implementation("org.liquibase:liquibase-core:4.27.0")
	implementation("com.google.guava:guava:33.2.0-jre")
	implementation("com.h2database:h2:2.2.224")


	compileOnly("org.projectlombok:lombok")

	annotationProcessor("org.projectlombok:lombok")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.mockito:mockito-core:5.11.0")
	testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.2")
//	testImplementation("com.tngtech.jgiven:jgiven-spring-junit5:1.3.1")
//	testImplementation("io.cucumber:cucumber-junit:7.17.0")
	testImplementation("org.spockframework:spock-spring:2.4-M4-groovy-4.0")
	testImplementation("org.apache.groovy:groovy-all:4.0.18")
//	testImplementation("org.codehaus.groovy:groovy:3.0.21")
	testImplementation( "com.athaydes:spock-reports:$spockReportsVersion" ) {
		isTransitive = false // this avoids affecting your version of Groovy/Spock
	}

	spotbugsPlugins("com.h3xstream.findsecbugs:findsecbugs-plugin:1.13.0")

	errorprone("com.google.errorprone:error_prone_core:2.27.0")
	errorprone("com.uber.nullaway:nullaway:0.10.25")

//	pitest("com.arcmutate:arcmutate-spring:1.0.0") // no licence
}

tasks.withType<PitestTask> {
	environment("LIQUIBASE_DUPLICATE_FILE_MODE", "WARN")
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
		excludedPaths = ".*test.*"

	}
}