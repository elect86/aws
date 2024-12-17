//import aws.sdk.kotlin.hll.dynamodbmapper.DynamoDbItem
//import aws.sdk.kotlin.hll.dynamodbmapper.DynamoDbPartitionKey
//import aws.sdk.kotlin.hll.dynamodbmapper.DynamoDbSortKey
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    embeddedKotlin("jvm")
    application
    id("aws.sdk.kotlin.hll.dynamodbmapper.schema.generator") version "1.3.95-beta"
}

group = "com"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    //    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    //    testImplementation("org.junit.jupiter:junit-jupiter")

    implementation(awssdk.services.s3)
    implementation(awssdk.services.dynamodb)
    implementation(awssdk.services.iam)
    implementation(awssdk.services.cloudwatch)
    implementation(awssdk.services.cognitoidentityprovider)
    implementation(awssdk.services.sns)
    implementation(awssdk.services.pinpoint)

    implementation("aws.sdk.kotlin:dynamodb-mapper:1.3.95-beta")
    implementation("aws.sdk.kotlin:dynamodb-mapper-annotations:1.3.95-beta")
    implementation("com.fasterxml.jackson.core:jackson-core:2.14.2")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.14.2")

    // test dependency
    testImplementation(embeddedKotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_17
    }
}

//// The annotations used in the Car class are used by the plugin to generate a schema.
//@DynamoDbItem
//data class Car(
//    @DynamoDbPartitionKey
//    val make: String,
//
//    @DynamoDbSortKey
//    val model: String,
//
//    val initialYear: Int
//)