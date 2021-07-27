package com.example.rinfassignement.ui.channels.socialchannels

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.rinfassignement.R
import com.example.rinfassignement.databinding.ChannelsFragmentBinding
import com.example.rinfassignement.ui.channels.ChannelsViewModel
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import kotlinx.coroutines.flow.collect

class ChannelsFragment : Fragment(R.layout.channels_fragment) {

    private var _binding: ChannelsFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ChannelsViewModel by activityViewModels()
    private lateinit var channelAdapter: ChannelAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = ChannelsFragmentBinding.bind(view)
        requireActivity().title = getString(R.string.channels_title)
        setHasOptionsMenu(true)

        viewModel.selectedChannel = null

        channelAdapter = ChannelAdapter {
            viewModel.selectedChannel = it.channel
        }

        with(binding) {
            btnContinue.setOnClickListener {
                if (viewModel.selectedChannel == null) {
                    Toast.makeText(
                        requireContext(),
                        getText(R.string.channels_no_selection),
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    findNavController().navigate(ChannelsFragmentDirections.actionChannelsToChannelDetails())
                }
            }

            channelsRv.layoutManager =
                FlexboxLayoutManager(requireContext(), FlexDirection.ROW, FlexWrap.WRAP)
            channelsRv.adapter = channelAdapter
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.targetChannels().collect {
                channelAdapter.submitList(it)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.channel_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_reset -> {
                viewModel.selectedChannel?.let {
                    val position = channelAdapter.currentList.map { it.channel }.indexOf(it)
                    channelAdapter.currentList[position].isChecked = false
                    viewModel.selectedChannel = null
                    channelAdapter.notifyItemChanged(position)
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}