package com.example.rinfassignement.ui.channels.details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rinfassignement.R
import com.example.rinfassignement.databinding.ChannelDetailsFragmentBinding
import com.example.rinfassignement.ui.channels.ChannelsViewModel
import kotlinx.coroutines.flow.collect

class ChannelDetailsFragment : Fragment(R.layout.channel_details_fragment) {

    private var _binding: ChannelDetailsFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ChannelsViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = ChannelDetailsFragmentBinding.bind(view)
        requireActivity().title = getString(R.string.channels_details_title)

        viewModel.selectedSubscription = null

        val channelDetailsAdapter = ChannelDetailsAdapter {
            viewModel.selectedSubscription = it
            findNavController().navigate(ChannelDetailsFragmentDirections.actionChannelDetailsToChannelSubscriptionReview())
        }

        with(binding.subscriptionsRv) {
            adapter = channelDetailsAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.getChannelSubscriptions().collect {
                channelDetailsAdapter.submitList(it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}