package com.example.rinfassignement.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.rinfassignement.data.db.model.Target
import kotlinx.coroutines.flow.Flow

@Dao
interface TargetDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(target: Target)

    @Query("SELECT * FROM table_target")
    fun getAllTargets(): Flow<List<Target>>

    @Query("SELECT * FROM table_target where name IN (SELECT name FROM table_channel_target WHERE channelId = :channelId)")
    suspend fun getChannelTargets(channelId: Long): List<Target>

    @Query("DELETE FROM table_target")
    suspend fun deleteAll()
}