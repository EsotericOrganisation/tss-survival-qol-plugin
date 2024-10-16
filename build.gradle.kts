import xyz.jpenilla.resourcefactory.bukkit.BukkitPluginYaml

plugins {
  `java-library`
  id("io.papermc.paperweight.userdev") version "1.7.1"
  id("xyz.jpenilla.run-paper") version "2.3.0" // Adds runServer and runMojangMappedServer tasks for testing
  id("xyz.jpenilla.resource-factory-bukkit-convention") version "1.1.1" // Generates plugin.yml based on the Gradle config
}

group = "org.esoteric"
version = "0.1.0"
description = "The quality of life features of the survival gamemode on The Slimy Swamp."

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}

repositories {
    mavenCentral()
    maven("https://jitpack.io")
}

dependencies {
    paperweight.paperDevBundle("1.21-R0.1-SNAPSHOT")
}

tasks {
    compileJava {
        options.encoding = Charsets.UTF_8.name()
        options.release.set(21)
    }
}

bukkitPluginYaml {
    name = "TSSSurvivalQOL"
    description = project.description
    authors.addAll("Esoteric Organisation", "rolyPolyVole", "Esoteric Enderman")

    version = project.version.toString()
    apiVersion = "1.21"
    main = "org.esoteric.tss.minecraft.plugins.survival.qol.TSSSurvivalQOLPlugin"
    load = BukkitPluginYaml.PluginLoadOrder.STARTUP
}
