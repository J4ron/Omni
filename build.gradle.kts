plugins {
    id("java")
}

group = "sh.omni"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.apache.pdfbox:pdfbox:3.0.1")
    implementation("commons-io:commons-io:2.15.1")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}



tasks {
    jar {
        manifest {
            attributes["Main-Class"] = "sh.omni.OmniApp"
        }
    }
}

tasks.test {
    useJUnitPlatform()
}
