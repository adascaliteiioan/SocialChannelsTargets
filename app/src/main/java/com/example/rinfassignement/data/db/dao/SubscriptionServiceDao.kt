package com.example.rinfassignement.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.rinfassignement.data.db.model.SubscriptionService

@Dao
interface SubscriptionServiceDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(subscriptionService: SubscriptionService)

    @Query("DELETE FROM table_subscription_service")
    suspend fun deleteAll()
}