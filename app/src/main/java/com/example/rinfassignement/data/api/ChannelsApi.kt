package com.example.rinfassignement.data.api

import retrofit2.http.GET

interface ChannelsApi {

    companion object {
        const val BASE_URL = "https://mocki.io/v1/"
    }

    @GET("fc583aaa-b255-4e5b-adf1-f599d2ceb821")
    suspend fun getSocialChannels(): SocialChannelResponse
}