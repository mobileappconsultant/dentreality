plugins {
    id("io.gitlab.arturbosch.detekt") version "1.16.0"
}

buildscript {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    dependencies {
        classpath(Build.buildTools)
        classpath(Build.kotlinGradlePlugin)
        classpath(Build.hiltGradlePlugin)
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

val genericBaselineFile = file("$projectDir/detekt/baseline/detekt-baseline.xml")
val moduleSpecificBaselineFile =
    file("$projectDir/detekt/baseline/detekt-$subprojects}-baseline.xml")
val baselineFile =
    if (moduleSpecificBaselineFile.exists()) moduleSpecificBaselineFile else genericBaselineFile

detekt {
    baseline = baselineFile // a way of suppressing issues before introducing detekt
    toolVersion = "1.16.0"
    parallel = true
    config = files("$projectDir/detekt/detekt-config.yml")
    buildUponDefaultConfig = true
    disableDefaultRuleSets = false
    debug = false
    ignoreFailures = false
    reports {
        xml {
            enabled = false
        }
        html {
            enabled = false
        }
        txt {
            enabled = false
        }
    }
}


tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}