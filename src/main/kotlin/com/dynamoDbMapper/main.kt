@file:OptIn(ExperimentalApi::class)

package com.dynamoDbMapper

import aws.sdk.kotlin.hll.dynamodbmapper.DynamoDbMapper
import aws.sdk.kotlin.hll.dynamodbmapper.expressions.KeyFilter
import aws.sdk.kotlin.hll.dynamodbmapper.model.Table
import aws.sdk.kotlin.hll.dynamodbmapper.operations.*
import aws.sdk.kotlin.services.dynamodb.DynamoDbClient
import aws.smithy.kotlin.runtime.ExperimentalApi
import kotlinx.coroutines.runBlocking
import com.dynamoDbMapper.dynamodbmapper.generatedschemas.CarSchema
import com.dynamoDbMapper.dynamodbmapper.generatedschemas.getCarTable

fun main() {

    runBlocking {

        val client = DynamoDbClient.fromEnvironment()
        val mapper = DynamoDbMapper(client)

        val carsTable = mapper.getCarTable("cars")

        carsTable.putItem {
            item = Car(make = "Ford", model = "Model T", 2009)
        }

        carsTable += Car(make = "Ford", model = "Model T", 2009)

        carsTable
                .queryPaginated {
                    keyCondition = KeyFilter(partitionKey = "Peugeot")
                }
                .items()
                .collect { car -> println(car) }
    }
}

suspend operator fun <T> TableOperations<T>.plusAssign(it: T) {
    putItem(PutItemRequestBuilder<T>().apply { item = it }.build())
}
