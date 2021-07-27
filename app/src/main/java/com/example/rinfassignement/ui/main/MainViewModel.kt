package com.example.rinfassignement.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rinfassignement.data.repository.ChannelRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    repository: ChannelRepository
) : ViewModel() {

    val channels = repository.getAllChannels()
        .stateIn(viewModelScope, SharingStarted.Lazily, null)

}