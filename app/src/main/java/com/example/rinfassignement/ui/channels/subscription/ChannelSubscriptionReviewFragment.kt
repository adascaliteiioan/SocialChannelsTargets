package com.example.rinfassignement.ui.channels.subscription

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.rinfassignement.R
import com.example.rinfassignement.databinding.SubscriptionReviewFragmentBinding
import com.example.rinfassignement.ui.channels.ChannelsViewModel

class ChannelSubscriptionReviewFragment : Fragment(R.layout.subscription_review_fragment) {

    private var _binding: SubscriptionReviewFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ChannelsViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = SubscriptionReviewFragmentBinding.bind(view)
        requireActivity().title = getString(R.string.subscription_review_title)

        with(binding) {
            viewModel.selectedSubscription?.let { monthlySubscription ->
                priceTv.text = monthlySubscription.price
                servicesTv.text = monthlySubscription.services.joinToString("\n")

                btnContinue.setOnClickListener {
                    if (ratingEt.text.isNullOrBlank()) {
                        Toast.makeText(
                            requireContext(),
                            getText(R.string.subscription_no_review),
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        sendEmail()
                        viewModel.selectedSubscription = null
                    }
                }
            }
        }
    }

    private fun sendEmail() {
        val to = arrayOf("bogus@bogus.com")
        val emailIntent = Intent(Intent.ACTION_SEND)
        emailIntent.data = Uri.parse("mailto:")
        emailIntent.type = "text/plain"
        emailIntent.putExtra(Intent.EXTRA_EMAIL, to)
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Review")
        emailIntent.putExtra(Intent.EXTRA_TEXT, createMailContent())
        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."))
            findNavController().popBackStack(R.id.targetSpecificsFragment, false)
        } catch (ex: ActivityNotFoundException) {
            Toast.makeText(
                requireContext(),
                "There is no email client installed.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun createMailContent(): String {
        val message = StringBuilder()
        message.append(viewModel.selectedChannel?.name.orEmpty())
            .append("\n")
            .append(viewModel.selectedSubscription?.services?.joinToString("\n").orEmpty())
            .append("\n")
            .append("Review stars : ${binding.ratingBar.rating}")
            .append("\n")
            .append(binding.ratingEt.text)
        return message.toString()
    }
}