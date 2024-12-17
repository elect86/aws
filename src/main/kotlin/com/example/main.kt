package com.example

import aws.sdk.kotlin.runtime.auth.credentials.ProfileCredentialsProvider
import aws.sdk.kotlin.services.dynamodb.DynamoDbClient
import aws.sdk.kotlin.services.dynamodb.model.CreateTableRequest

import aws.sdk.kotlin.services.s3.*
import aws.sdk.kotlin.services.s3.model.BucketLocationConstraint
import aws.sdk.kotlin.services.s3.model.GetObjectRequest
import aws.smithy.kotlin.runtime.content.ByteStream
import aws.smithy.kotlin.runtime.content.decodeToString
import kotlinx.coroutines.runBlocking
import java.util.UUID

val REGION = "us-west-2"
val BUCKET = "bucket-${UUID.randomUUID()}"
val KEY = "key"

fun main() = runBlocking {

    suspend fun setupTutorial(s3: S3Client) {
        println("Creating bucket $BUCKET...")
        s3.createBucket {
            bucket = BUCKET
            if (REGION != "us-east-1") { // Do not set location constraint for us-east-1.
                createBucketConfiguration {
                    locationConstraint = BucketLocationConstraint.fromValue(REGION)
                }
            }
        }
        println("Bucket $BUCKET created successfully!")
    }

    suspend fun cleanUp(s3: S3Client) {
        println("Deleting object $BUCKET/$KEY...")
        s3.deleteObject {
            bucket = BUCKET
            key = KEY
        }
        println("Object $BUCKET/$KEY deleted successfully!")

        println("Deleting bucket $BUCKET...")
        s3.deleteBucket {
            bucket = BUCKET
        }
        println("Bucket $BUCKET deleted successfully!")
    }

    S3Client.fromEnvironment { region = REGION }
            .use { s3 ->
                setupTutorial(s3)

                println("Creating object $BUCKET/$KEY...")

                s3.putObject {
                    bucket = BUCKET
                    key = KEY
                    body = ByteStream.fromString("Testing with the Kotlin SDK")
                }

                s3.getObject(GetObjectRequest {
                    bucket = BUCKET
                    key = KEY
                }) {
                    println(it.body?.decodeToString())
                }

                println("Object $BUCKET/$KEY created successfully!")

                cleanUp(s3)
            }
}