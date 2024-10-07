import xyz.jpenilla.resourcefactory.bukkit.BukkitPluginYaml

plugins {
  `java-library`
  id("io.papermc.paperweight.userdev") version "1.7.1"
  id("xyz.jpenilla.run-paper") version "2.3.0" // Adds runServer and runMojangMappedServer tasks for testing
  id("xyz.jpenilla.resource-factory-bukkit-convention") version "1.1.1" // Generates plugin.yml based on the Gradle config
}

group = "org.esoteric_organisation"
version = "0.1"
description = "The quality of life features of the survival gamemode on The Slimy Swamp."

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}

dependencies {
    compileOnly(files("../TSS-Core/build/libs/tss_core-0.1-dev-all.jar"))

    paperweight.paperDevBundle("1.21-R0.1-SNAPSHOT")
}

tasks {
    compileJava {
        options.encoding = Charsets.UTF_8.name()
        options.release.set(21)
    }
}

bukkitPluginYaml {
  main = "org.esoteric_organisation.tss_survival_qol_plugin.TSSSurvivalQOLPlugin"
  load = BukkitPluginYaml.PluginLoadOrder.STARTUP
  authors.addAll("Esoteric Organisation", "rolyPolyVole", "Esoteric Enderman")
  apiVersion = "1.21"
}
