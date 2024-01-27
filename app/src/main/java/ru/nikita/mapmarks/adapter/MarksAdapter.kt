package ru.nikita.mapmarks.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.nikita.mapmarks.databinding.ItemCardBinding
import ru.nikita.mapmarks.dto.Marks

interface OnInteractionListener {
    fun onEdit(marks: Marks) {}
    fun onRemove(marks: Marks) {}

    fun onItemClicked(marks: Marks) {}
}


class MarksAdapter(
    private val onInteractionListener: OnInteractionListener,
) : ListAdapter<Marks, MarksViewHolder>(MarksDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarksViewHolder {
        val binding = ItemCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MarksViewHolder(binding, onInteractionListener)
    }

    override fun onBindViewHolder(holder: MarksViewHolder, position: Int) {
        val marks = getItem(position)
        holder.bind(marks)
    }
}

class MarksViewHolder(
    private val binding: ItemCardBinding,
    private val onInteractionListener: OnInteractionListener
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(marks: Marks) {
        binding.itemTitle.text = marks.title
        binding.latitudeValue.text = marks.latitude.toString()
        binding.longitudeValue.text = marks.longitude.toString()
        itemListener(marks)
    }

    private fun itemListener(marks: Marks) {
        binding.editButton.setOnClickListener {
            onInteractionListener.onEdit(marks)
        }

        binding.deleteButton.setOnClickListener {
            onInteractionListener.onRemove(marks)

        }

        itemView.setOnClickListener {
            onInteractionListener.onItemClicked(marks)
        }
    }

}


class MarksDiffCallback : DiffUtil.ItemCallback<Marks>() {
    override fun areItemsTheSame(oldItem: Marks, newItem: Marks): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Marks, newItem: Marks): Boolean {
        return oldItem == newItem
    }
}