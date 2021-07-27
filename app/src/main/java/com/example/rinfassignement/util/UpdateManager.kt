package com.example.rinfassignement.util

import java.util.concurrent.TimeUnit
import javax.inject.Inject

class UpdateManager @Inject constructor(private val prefsHelper: PrefsHelper) {

    fun needsUpdate(): Boolean {
        val currentTime = System.currentTimeMillis()
        val lastUpdateTime = prefsHelper.read(TIME_UPDATE_KEY, 0)
        val weekAgoTime = currentTime - TimeUnit.DAYS.toMillis(7)
        return weekAgoTime >lastUpdateTime
    }

    fun setUpdateTime() {
        prefsHelper.write(TIME_UPDATE_KEY, System.currentTimeMillis())
    }

    companion object {
        private const val TIME_UPDATE_KEY = "days_to_update"
    }
}