package com.vila.demorequestwebapi.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vila.demorequestwebapi.R
import com.vila.demorequestwebapi.databinding.ItemNotebookBinding
import com.vila.demorequestwebapi.domain.models.Notebook

class NotebookAdapter(val click: (Notebook, View) -> Unit) :
    ListAdapter<Notebook, NotebookAdapter.Holder>(MyDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding =
            ItemNotebookBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getItem(position))
    }

    object MyDiffUtil : DiffUtil.ItemCallback<Notebook>() {

        override fun areItemsTheSame(oldItem: Notebook, newItem: Notebook): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: Notebook, newItem: Notebook): Boolean {
            return oldItem.title == newItem.title && oldItem.description == newItem.description
        }
    }

    inner class Holder(private val binding: ItemNotebookBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(notebook: Notebook) {
            binding.icon.transitionName = notebook.image
            binding.root.setOnClickListener { click(notebook, binding.icon) }
            binding.tittle.text = notebook.title.trim()
            binding.description.text = notebook.description
            Glide.with(binding.root.context)
                .load(notebook.image)
                .circleCrop()
                .placeholder(R.drawable.ic_baseline_downloading_24)
                .into(binding.icon)
        }
    }

}