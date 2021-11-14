plugins {
    kotlin("jvm") version Versions.kotlin
    kotlin("plugin.serialization") version Versions.kotlin

    id("net.mamoe.mirai-console") version Versions.mirai
}

group = "io.github.gnuf0rce"
version = "1.0.0"

mirai {
    configureShadow {
        listOf(
            "kotlin",
            "org/intellij",
            "org/jetbrains",
            "org/slf4j"
        ).forEach { prefix ->
            exclude { element ->
                element.path.startsWith(prefix)
            }
        }
    }
}

repositories {
    clear()
    mavenLocal()
    maven(url = "https://maven.aliyun.com/repository/public")
    mavenCentral()
    maven(url = "https://maven.aliyun.com/repository/gradle-plugin")
    gradlePluginPortal()
}

kotlin {
    sourceSets {
        all {
            languageSettings.optIn("kotlin.RequiresOptIn")
        }
    }
}

dependencies {
    implementation(ktor("client-encoding", Versions.ktor)) {
        exclude(group = "io.ktor", module = "ktor-client-core")
    }
    implementation(ktor("client-serialization", Versions.ktor)) {
        exclude(group = "io.ktor", module = "ktor-client-core")
    }
    // test
    testImplementation(kotlin("test", Versions.kotlin))
}

tasks {
    test {
        useJUnitPlatform()
    }
}
