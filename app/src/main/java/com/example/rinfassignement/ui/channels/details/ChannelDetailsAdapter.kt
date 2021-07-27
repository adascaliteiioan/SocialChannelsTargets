package com.example.rinfassignement.ui.channels.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.rinfassignement.data.model.MonthlySubscription
import com.example.rinfassignement.databinding.ItemSubscriptionBinding

class ChannelDetailsAdapter(
    private val onSubscriptionClick: (MonthlySubscription) -> Unit
) :
    ListAdapter<MonthlySubscription, ChannelDetailsAdapter.ChannelDetailsViewHolder>(
        ChannelDetailsComparator()
    ) {

    inner class ChannelDetailsViewHolder(private val binding: ItemSubscriptionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private var subscription: MonthlySubscription? = null

        init {
            binding.root.setOnClickListener {
                subscription?.let { onSubscriptionClick(it) }
            }
        }

        fun bind(subscription: MonthlySubscription) {
            this.subscription = subscription
            with(binding) {
                priceTv.text = subscription.price
                servicesTv.text = subscription.services.joinToString("\n")
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChannelDetailsViewHolder {
        val binding =
            ItemSubscriptionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ChannelDetailsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChannelDetailsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class ChannelDetailsComparator : DiffUtil.ItemCallback<MonthlySubscription>() {
    override fun areItemsTheSame(
        oldItem: MonthlySubscription,
        newItem: MonthlySubscription
    ) = oldItem == newItem

    override fun areContentsTheSame(
        oldItem: MonthlySubscription,
        newItem: MonthlySubscription
    ) = oldItem.price == newItem.price
            && oldItem.services == newItem.services

}