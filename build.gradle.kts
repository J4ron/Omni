plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "sh.omni"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("info.picocli:picocli:4.7.5")
    annotationProcessor("info.picocli:picocli-codegen:4.7.5")

    implementation("org.apache.pdfbox:pdfbox:3.0.1")
    implementation("org.apache.commons:commons-compress:1.26.1")

    implementation("org.apache.poi:poi-ooxml:5.4.1")
    implementation("commons-io:commons-io:2.15.1")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

configurations.all {
    resolutionStrategy {
        force("org.apache.commons:commons-compress:1.26.1")
    }
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "sh.omni.OmniApp"
    }
}

tasks.test {
    useJUnitPlatform()
}

// ShadowJar Task konfigurieren
tasks.shadowJar {
    archiveBaseName.set("omni")
    archiveVersion.set("1.0-SNAPSHOT")
    archiveClassifier.set("") // kein classifier, damit jar heißt: omni-1.0-SNAPSHOT.jar
    mergeServiceFiles()       // wichtig für META-INF/services für SPI (z.B. picocli)
    manifest {
        attributes["Main-Class"] = "sh.omni.OmniApp"
    }
}
