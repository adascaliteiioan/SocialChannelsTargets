package com.example.rinfassignement.ui.channels

import androidx.lifecycle.ViewModel
import com.example.rinfassignement.data.db.model.Channel
import com.example.rinfassignement.data.db.model.Target
import com.example.rinfassignement.data.model.ChannelChecked
import com.example.rinfassignement.data.model.MonthlySubscription
import com.example.rinfassignement.data.model.TargetChecked
import com.example.rinfassignement.data.repository.ChannelRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class ChannelsViewModel @Inject constructor(
    private val repository: ChannelRepository
) : ViewModel() {

    private val targetsSelected = mutableListOf<Target>()
    var selectedChannel: Channel? = null
    var selectedSubscription: MonthlySubscription? = null

    fun targetSelected(target: Target) {
        if (targetsSelected.contains(target)) {
            targetsSelected.remove(target)
        } else {
            targetsSelected.add(target)
        }
    }

    fun clearSelectedTargets() {
        targetsSelected.clear()
    }

    fun continueToChannels() = targetsSelected.isNotEmpty()

    fun targets() =
        repository.getAllTargets()
            .map { it.map { target -> TargetChecked(target, targetsSelected.contains(target)) } }

    fun targetChannels() = repository.getChannelByTargetIds(targetsSelected.map { it.name })
        .map { list -> list.map { ChannelChecked(it, it.name == selectedChannel?.name) } }


    fun getChannelSubscriptions(): Flow<List<MonthlySubscription>> {
        val channelId = selectedChannel?.id ?: return emptyFlow()
        return repository.getChannelSubscriptions(channelId)
    }
}