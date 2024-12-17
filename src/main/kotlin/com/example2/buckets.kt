package com.example2

import aws.sdk.kotlin.services.s3.createBucket
import aws.sdk.kotlin.services.s3.deleteBucket
import aws.sdk.kotlin.services.s3.deleteObject
import aws.sdk.kotlin.services.s3.model.CreateBucketRequest
import aws.sdk.kotlin.services.s3.model.GetObjectRequest
import aws.sdk.kotlin.services.s3.putObject
import aws.smithy.kotlin.runtime.content.*
import java.io.File
import java.nio.file.Path

class StringBucket private constructor(val id: String) {

    suspend operator fun set(key: String, value: String) = defaultS3Client.putObject {
        bucket = id
        this.key = key
        body = ByteStream.fromString(value)
    }

    suspend operator fun get(key: String): String? = defaultS3Client.getObject(GetObjectRequest {
        bucket = id
        this.key = key
    }) {
        it.body?.decodeToString()
    }

    suspend operator fun minusAssign(key: String) {
        defaultS3Client.deleteObject {
            bucket = id
            this.key = key
        }
    }

    suspend fun delete() = defaultS3Client.deleteBucket { bucket = id }

    companion object {
        suspend operator fun invoke(id: String, init: CreateBucketRequest.Builder.() -> Unit): StringBucket {
            defaultS3Client.createBucket {
                bucket = id
                init()
            }
            return StringBucket(id)
        }
    }
}

class BytesBucket private constructor(val id: String) {

    suspend operator fun set(key: String, value: ByteArray) = defaultS3Client.putObject {
        bucket = id
        this.key = key
        body = ByteStream.fromBytes(value)
    }

    suspend operator fun get(key: String): ByteArray? = defaultS3Client.getObject(GetObjectRequest {
        bucket = id
        this.key = key
    }) {
        it.body?.toByteArray()
    }

    suspend operator fun minusAssign(key: String) {
        defaultS3Client.deleteObject {
            bucket = id
            this.key = key
        }
    }

    suspend fun delete() = defaultS3Client.deleteBucket { bucket = id }

    companion object {
        suspend operator fun invoke(id: String, init: CreateBucketRequest.Builder.() -> Unit): BytesBucket {
            defaultS3Client.createBucket {
                bucket = id
                init()
            }
            return BytesBucket(id)
        }
    }
}

class FileBucket private constructor(val id: String) {

    suspend operator fun set(key: String, value: File) = defaultS3Client.putObject {
        bucket = id
        this.key = key
        body = ByteStream.fromFile(value)
    }

    suspend operator fun get(key: String, file: File): Long? = defaultS3Client.getObject(GetObjectRequest {
        bucket = id
        this.key = key
    }) {
        it.body?.writeToFile(file)
    }

    suspend operator fun get(key: String, path: Path): Long? = defaultS3Client.getObject(GetObjectRequest {
        bucket = id
        this.key = key
    }) {
        it.body?.writeToFile(path)
    }

    suspend operator fun minusAssign(key: String) {
        defaultS3Client.deleteObject {
            bucket = id
            this.key = key
        }
    }

    suspend fun delete() = defaultS3Client.deleteBucket { bucket = id }

    companion object {
        suspend operator fun invoke(id: String, init: CreateBucketRequest.Builder.() -> Unit): FileBucket {
            defaultS3Client.createBucket {
                bucket = id
                init()
            }
            return FileBucket(id)
        }
    }
}