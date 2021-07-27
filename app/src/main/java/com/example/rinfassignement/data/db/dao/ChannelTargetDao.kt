package com.example.rinfassignement.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.rinfassignement.data.db.model.ChannelTarget

@Dao
interface ChannelTargetDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(channelTarget: ChannelTarget)

    @Query("DELETE FROM table_channel_target")
    suspend fun deleteAll()
}