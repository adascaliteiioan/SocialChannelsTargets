package com.example.rinfassignement.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.rinfassignement.data.db.model.Service

@Dao
interface ServiceDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(service: Service)

    @Query("SELECT * FROM table_service WHERE name IN (SELECT service FROM table_subscription_service WHERE subscriptionId = :subscriptionId)")
    suspend fun getAllSubscriptionServices(subscriptionId: Long): List<Service>

    @Query("DELETE FROM table_service")
    suspend fun deleteAll()
}