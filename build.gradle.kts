plugins {
  java
  kotlin("jvm") version "1.4.20"
}

group = "ee.hannilo"
version = "1.0-SNAPSHOT"

repositories {
  mavenCentral()
}

dependencies {
  implementation(kotlin("stdlib-jdk8"))
  implementation("org.jetbrains.kotlin:kotlin-reflect:1.4.20")
  implementation("io.github.microutils:kotlin-logging-jvm:2.0.2")
  //implementation("org.slf4j:slf4j-simple:1.7.29")
  implementation("ch.qos.logback:logback-core:1.2.3")
  implementation("ch.qos.logback:logback-classic:1.2.3")
  testImplementation(platform("org.junit:junit-bom:5.7.0"))
  testImplementation("org.junit.jupiter:junit-jupiter")
}

configure<JavaPluginConvention> {
  sourceCompatibility = JavaVersion.VERSION_11
}

tasks {
  compileKotlin {
    kotlinOptions.jvmTarget = "11"
  }
  compileTestKotlin {
    kotlinOptions.jvmTarget = "11"
  }
  test {
    useJUnitPlatform()
    testLogging {
      events("passed", "skipped", "failed")
    }
  }
}
