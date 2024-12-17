package com.example2

import aws.sdk.kotlin.services.s3.*
import aws.sdk.kotlin.services.s3.model.BucketLocationConstraint
import aws.sdk.kotlin.services.s3.model.CreateBucketRequest
import aws.sdk.kotlin.services.s3.model.GetObjectRequest
import aws.smithy.kotlin.runtime.content.*
import com.example.KEY
import kotlinx.coroutines.runBlocking
import java.io.File
import java.nio.file.Path
import java.util.UUID

//val REGION = "us-west-2"
//val BUCKET = "bucket-${UUID.randomUUID()}"
//val KEY = "key"

lateinit var defaultS3Client: S3

fun main() {

    runBlocking {

        defaultS3Client = S3.fromEnvironment { region = Region.usｰwestｰ2 }

        val bucket = StringBucket("bucket-${UUID.randomUUID()}") {
            if (defaultS3Client.region != Region.usｰeastｰ1) // Do not set location constraint for us-east-1.
                createBucketConfiguration {
                    locationConstraint = BucketLocationConstraint.fromValue(defaultS3Client.region.region)
                }
        }

        println("Bucket ${bucket.id}` created successfully!")

        println("Creating object ${bucket.id}/$KEY...")

        bucket[KEY] = "Testing with the Kotlin SDK"

        println("Object ${bucket.id}/$KEY created successfully!")

        println(bucket[KEY])

        println("Deleting object $${bucket.id}/$KEY...")

        bucket -= KEY

        println("Object $${bucket.id}/$KEY deleted successfully!")

        println("Deleting bucket $${bucket.id}...")

        bucket.delete()
        println("Bucket ${bucket.id} deleted successfully!")

        defaultS3Client.close()

        println("s3 client closed")
    }
}