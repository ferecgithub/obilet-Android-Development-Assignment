package com.ferechamitebeyli.journey.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ferechamitebeyli.data.model.location.LocationDataUiModel
import com.ferechamitebeyli.data.util.OnItemClickListener
import com.ferechamitebeyli.journey.R
import com.ferechamitebeyli.journey.databinding.LayoutItemQueryBinding

class QueryDialogListAdapter(private val onItemClickListener: OnItemClickListener<LocationDataUiModel>) :
    RecyclerView.Adapter<QueryDialogListAdapter.QueryViewHolder>() {
    inner class QueryViewHolder(val binding: LayoutItemQueryBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<LocationDataUiModel>() {
        override fun areItemsTheSame(
            oldItem: LocationDataUiModel,
            newItem: LocationDataUiModel
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: LocationDataUiModel,
            newItem: LocationDataUiModel
        ): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    fun submitList(list: List<LocationDataUiModel>) = differ.submitList(list)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QueryViewHolder =
        QueryViewHolder(
            LayoutItemQueryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: QueryViewHolder, position: Int) {

        val query = differ.currentList[position]

        holder.binding.apply {
            textViewItemQueryCity.text = query.name

            root.setOnClickListener {
                query.isSelected = !query.isSelected

                onItemClickListener.onItemClick(position, query) // Passing the data to the query fragment

                if (query.isSelected) root.setBackgroundResource(R.drawable.shape_tab_indicator) else root.setBackgroundResource(
                    com.ferechamitebeyli.ui.R.drawable.background_list_item
                )
            }
        }
    }

    override fun getItemCount(): Int = differ.currentList.size

}