package com.example.rinfassignement.ui.channels.targets

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.rinfassignement.R
import com.example.rinfassignement.databinding.TargetsFragmentBinding
import com.example.rinfassignement.ui.channels.ChannelsViewModel
import com.example.rinfassignement.ui.channels.TargetsAdapter
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class TargetSpecificsFragment : Fragment(R.layout.targets_fragment) {

    private var _binding: TargetsFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ChannelsViewModel by activityViewModels()
    private lateinit var targetAdapter: TargetsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = TargetsFragmentBinding.bind(view)

        requireActivity().title = getString(R.string.target_specifics_title)

        viewModel.clearSelectedTargets()

        targetAdapter = TargetsAdapter {
            viewModel.targetSelected(it.target)
        }

        with(binding) {
            btnContinue.setOnClickListener {
                if (viewModel.continueToChannels()) {
                    findNavController().navigate(TargetSpecificsFragmentDirections.actionTargetSpecificsToChannels())
                } else {
                    Toast.makeText(
                        requireContext(),
                        getText(R.string.targets_no_selection),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            targetsRv.adapter = targetAdapter
            targetsRv.layoutManager =
                FlexboxLayoutManager(requireContext(), FlexDirection.ROW, FlexWrap.WRAP)
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.targets().collect { targets ->
                targetAdapter.submitList(targets)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}