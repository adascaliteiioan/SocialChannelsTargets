package com.example.rinfassignement.data.repository

import androidx.room.withTransaction
import com.example.rinfassignement.data.api.ChannelsApi
import com.example.rinfassignement.data.db.database.ChannelsDatabase
import com.example.rinfassignement.data.db.model.*
import com.example.rinfassignement.data.db.model.Target
import com.example.rinfassignement.data.model.MonthlySubscription
import com.example.rinfassignement.data.model.SocialChannel
import com.example.rinfassignement.util.Resource
import com.example.rinfassignement.util.UpdateManager
import com.example.rinfassignement.util.networkBoundResource
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class ChannelRepository @Inject constructor(
    private val channelsApi: ChannelsApi,
    private val db: ChannelsDatabase,
    private val updateManager: UpdateManager
) {

    private val targetDao = db.targetDao()
    private val channelDao = db.channelDao()
    private val channelTargetDao = db.channelTargetDao()
    private val subscriptionDao = db.subscriptionDao()
    private val serviceDao = db.serviceDao()
    private val subscriptionServiceDao = db.subscriptionServiceDao()

    fun getAllChannels(): Flow<Resource<List<SocialChannel>>> =
        networkBoundResource(
            query = {
                flow {
                    val channels = channelDao.getAllChannels()
                    channels.map { channel ->
                        channel.id?.let { channelId ->
                            val targets = targetDao.getChannelTargets(channelId)
                            val subscriptions = subscriptionDao.getAllChannelSubscription(channelId)
                            val monthlySubscriptionList =
                                subscriptionToMonthlySubscription(subscriptions)

                            Resource.Success(
                                SocialChannel(
                                    name = channel.name,
                                    targetSpecifics = targets.map { it.name },
                                    monthlySubscription = monthlySubscriptionList
                                )
                            )
                        }
                    }
                }
            },
            fetch = {
                val channelsResponse = channelsApi.getSocialChannels()
                channelsResponse.channels
            },
            saveFetchResult = { socialChannels ->
                db.withTransaction {
                    serviceDao.deleteAll()
                    subscriptionServiceDao.deleteAll()
                    subscriptionDao.deleteALl()
                    targetDao.deleteAll()
                    channelDao.deleteAll()
                    channelTargetDao.deleteAll()

                    socialChannels.forEach { channel ->
                        val channelId = channelDao.insert(Channel(channel.name))
                        channel.targetSpecifics.forEach {
                            targetDao.insert(Target(it))
                            channelTargetDao.insert(ChannelTarget(channelId, it))
                        }
                        channel.monthlySubscription.forEach { subscription ->
                            val subscriptionId =
                                subscriptionDao.insert(Subscription(subscription.price, channelId))
                            subscription.services.forEach { service ->
                                serviceDao.insert(Service(service))
                                subscriptionServiceDao.insert(
                                    SubscriptionService(
                                        subscriptionId,
                                        service
                                    )
                                )
                            }
                        }
                    }

                    updateManager.setUpdateTime()
                }
            },
            shouldFetch = {
                updateManager.needsUpdate()
            },
            onFetchSuccess = {},
            onFetchFailed = {}
        )

    fun getAllTargets() = targetDao.getAllTargets()

    fun getChannelByTargetIds(targetIds: List<String>) = channelDao.getChannelsByTargetId(targetIds)

    fun getChannelSubscriptions(channelId: Long) = flow {
        val subscriptions = subscriptionDao.getAllChannelSubscription(channelId)
        val monthlySubscriptionList = subscriptionToMonthlySubscription(subscriptions)
        emit(monthlySubscriptionList)
    }

    private suspend fun subscriptionToMonthlySubscription(subscriptions: List<Subscription>): List<MonthlySubscription> {
        val monthlySubscriptionList = mutableListOf<MonthlySubscription>()
        subscriptions.forEach { subscription ->
            subscription.id?.let { subscriptionId ->
                val services =
                    serviceDao.getAllSubscriptionServices(subscriptionId)
                val monthlySubscription = MonthlySubscription(
                    price = subscription.price,
                    services.map { service -> service.name })
                monthlySubscriptionList.add(monthlySubscription)
            }
        }
        return monthlySubscriptionList
    }
}