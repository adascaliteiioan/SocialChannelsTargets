package com.example.rinfassignement.data.model

data class SocialChannel(
    val name: String,
    val targetSpecifics: List<String>,
    val monthlySubscription: List<MonthlySubscription>
)

data class MonthlySubscription(
    val price: String,
    val services: List<String>
)