package com.example.rinfassignement.data.db.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.rinfassignement.data.db.dao.*
import com.example.rinfassignement.data.db.model.*
import com.example.rinfassignement.data.db.model.Target

@Database(
    entities = [Channel::class, Target::class, ChannelTarget::class,
        Subscription::class, Service::class, SubscriptionService::class],
    version = 1,
    exportSchema = false
)
abstract class ChannelsDatabase : RoomDatabase() {

    abstract fun channelDao(): ChannelDao
    abstract fun targetDao(): TargetDao
    abstract fun channelTargetDao(): ChannelTargetDao
    abstract fun subscriptionDao(): SubscriptionDao
    abstract fun serviceDao(): ServiceDao
    abstract fun subscriptionServiceDao(): SubscriptionServiceDao
}