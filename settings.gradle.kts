plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}
rootProject.name = "server"
include("store-api")
include("payment-api")
include("delivery-api")
