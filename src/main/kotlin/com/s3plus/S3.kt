package com.s3plus

import aws.sdk.kotlin.services.s3.S3Client
import aws.sdk.kotlin.services.s3.model.CreateBucketRequest
import aws.sdk.kotlin.services.s3.model.CreateBucketResponse

class S3(val s3Client: S3Client) : S3Client by s3Client {

    lateinit var region: Region

    override suspend fun createBucket(input: CreateBucketRequest): CreateBucketResponse = s3Client.createBucket(input)

    class Builder {
        var region: Region? = null
    }

    companion object {
        suspend fun fromEnvironment(block: Builder.() -> Unit): S3 {
            val builder = Builder().apply(block)
            return S3(S3Client.fromEnvironment {
                region = builder.region!!.region
            }).apply {
                region = builder.region!!
            }
        }
    }
}