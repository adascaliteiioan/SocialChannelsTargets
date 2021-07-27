package com.example.rinfassignement.data.db.model

import androidx.room.*

@Entity(
    tableName = "table_subscription",
    foreignKeys = [ForeignKey(
        entity = Channel::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("channelId"),
        onDelete = ForeignKey.CASCADE
    )]
)
data class Subscription(
    val price: String,
    val channelId: Long
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null
}

@Entity(tableName = "table_service")
data class Service(
    @PrimaryKey val name: String
)

@Entity(
    tableName = "table_subscription_service",
    primaryKeys = ["subscriptionId", "service"]
)
data class SubscriptionService(
    val subscriptionId: Long,
    val service: String
)