package com.example.rinfassignement.ui.channels

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.rinfassignement.data.model.TargetChecked
import com.example.rinfassignement.databinding.ItemTargetBinding

class TargetsAdapter(
    private val onItemClick: (TargetChecked) -> Unit
) : ListAdapter<TargetChecked, TargetsAdapter.TargetViewHolder>(TargetComparator()) {

    inner class TargetViewHolder(private val binding: ItemTargetBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private var target: TargetChecked? = null

        init {
            binding.root.setOnClickListener {
                target?.let {
                    onItemClick(it)
                    it.isChecked = !it.isChecked
                    notifyDataSetChanged()
                }
            }
        }

        fun bind(target: TargetChecked) {
            this.target = target
            with(binding) {
                targetTv.text = target.target.name
                checkedIv.isVisible = target.isChecked
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TargetViewHolder {
        val binding = ItemTargetBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TargetViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TargetViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class TargetComparator : DiffUtil.ItemCallback<TargetChecked>() {
    override fun areItemsTheSame(oldItem: TargetChecked, newItem: TargetChecked) =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: TargetChecked, newItem: TargetChecked) =
        oldItem.target.name == newItem.target.name

}