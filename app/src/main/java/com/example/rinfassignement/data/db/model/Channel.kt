package com.example.rinfassignement.data.db.model

import androidx.room.*

@Entity(tableName = "table_channel")
data class Channel(
    val name: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null
}

@Entity(tableName = "table_target")
data class Target(
    @PrimaryKey val name: String
)

@Entity(
    tableName = "table_channel_target",
    primaryKeys = ["channelId", "target"]
)
data class ChannelTarget(
    val channelId: Long,
    val target: String
)