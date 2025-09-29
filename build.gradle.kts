plugins {
    kotlin("jvm") version "2.2.10"
    id("com.typewritermc.module-plugin") version "2.0.0"
}

group = "ar.mrfakon"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.typewritermc:QuestExtension:0.9.0")
    implementation("net.kyori:adventure-text-minimessage:4.24.0")
}

typewriter {
    namespace = "mrfakon"

    extension {
        name = "ObjectiveMultilines"
        shortDescription = "Allows multi-line objectives to be displayed"
        description = """Allows multi-line objectives to be displayed in tracked objectives into the SideBar.
            It adds a new 'MultiLine Objective Lines' entry, and a new placeholder %typewriter_tracked_objectives_multiline%.
            Now every type of 'Objective' entry supports multi-line. 
            Using <newline> or '\n' or '|NL|' will split the objective into multiple lines."""
        engineVersion = "0.9.0-beta-166"
        channel = com.typewritermc.moduleplugin.ReleaseChannel.BETA

        dependencies {
            dependency("typewritermc", "Quest")
            paper()
        }
    }
}

kotlin {
    jvmToolchain(21)
}