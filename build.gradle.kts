plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven {
        url = uri("https://jitpack.io")
    }

}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("org.glassfish:javax.json:1.1.4")
    implementation("com.badlogicgames.gdx:gdx:1.9.14")
    implementation("com.github.umjammer:jlayer:1.0.3")
}

tasks.test {
    useJUnitPlatform()
}