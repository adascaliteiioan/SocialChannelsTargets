package com.example.rinfassignement.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.rinfassignement.data.db.model.Subscription

@Dao
interface SubscriptionDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(subscription: Subscription): Long

    @Query("SELECT * FROM table_subscription WHERE channelId = :channelId")
    suspend fun getAllChannelSubscription(channelId: Long): List<Subscription>

    @Query("DELETE FROM table_subscription")
    suspend fun deleteALl()
}