package com.example.rinfassignement.ui.channels.socialchannels

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.rinfassignement.data.model.ChannelChecked
import com.example.rinfassignement.databinding.ItemTargetBinding

class ChannelAdapter(
    private val onChannelClick: (ChannelChecked) -> Unit
) : ListAdapter<ChannelChecked, ChannelAdapter.ChannelViewHolder>(ChannelComparator()) {

    inner class ChannelViewHolder(private val binding: ItemTargetBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private var channel: ChannelChecked? = null

        init {
            binding.root.setOnClickListener {
                channel?.let {
                    onChannelClick(it)
                    val checked = !it.isChecked
                    currentList.forEach { item -> item.isChecked = false }
                    it.isChecked = checked
                    notifyDataSetChanged()
                }
            }
        }


        fun bind(channel: ChannelChecked) {
            this.channel = channel
            with(binding) {
                targetTv.text = channel.channel.name
                checkedIv.isVisible = channel.isChecked
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChannelViewHolder {
        val binding = ItemTargetBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ChannelViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChannelViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class ChannelComparator : DiffUtil.ItemCallback<ChannelChecked>() {
    override fun areItemsTheSame(oldItem: ChannelChecked, newItem: ChannelChecked) =
        oldItem.channel.id == newItem.channel.id

    override fun areContentsTheSame(oldItem: ChannelChecked, newItem: ChannelChecked) =
        oldItem.channel.name == oldItem.channel.name &&
                oldItem.isChecked == newItem.isChecked

}