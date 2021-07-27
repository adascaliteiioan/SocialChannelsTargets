package com.example.rinfassignement.data.model

import com.example.rinfassignement.data.db.model.Channel

data class ChannelChecked(
    val channel: Channel,
    var isChecked: Boolean = false
)