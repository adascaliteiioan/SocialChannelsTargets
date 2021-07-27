package com.example.rinfassignement.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.rinfassignement.data.db.model.Channel
import kotlinx.coroutines.flow.Flow

@Dao
interface ChannelDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(channel: Channel): Long

    @Query("SELECT * FROM table_channel WHERE id IN (SELECT channelId FROM table_channel_target WHERE target IN (:targetIds))")
    fun getChannelsByTargetId(targetIds: List<String>): Flow<List<Channel>>

    @Query("SELECT * FROM table_channel")
    suspend fun getAllChannels(): List<Channel>

    @Query("DELETE FROM table_channel")
    suspend fun deleteAll()
}