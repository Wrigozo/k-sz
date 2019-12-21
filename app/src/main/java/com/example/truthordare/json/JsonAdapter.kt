package com.example.truthordare.json

import android.annotation.SuppressLint

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.truthordare.R

import com.example.truthordare.api.JsonModel
import com.example.truthordare.databinding.JsonelementsBinding


private const val ITEM_VIEW_TYPE_HEADER = 0
private const val ITEM_VIEW_TYPE_ITEM = 1
class JsonAdapter(val clickListener: JsonListener) : ListAdapter<DataItem, RecyclerView.ViewHolder>(JsonDiffCallBack()) {


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ViewHolder -> {
                val jsonItem = getItem(position) as DataItem.JsonItem
                holder.bind(jsonItem.jsonModel, clickListener)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) :RecyclerView.ViewHolder{
        return when (viewType) {
            ITEM_VIEW_TYPE_HEADER -> TextViewHolder.from(parent)
            ITEM_VIEW_TYPE_ITEM -> ViewHolder.from(parent)
            else -> throw ClassCastException("Unknown viewType ${viewType}")
        }
    }

    fun addHeaderAndSubmitList(list: List<JsonModel>?) {
        val items = when (list) {
            null -> listOf(DataItem.Header)
            else -> listOf(DataItem.Header) + list.map { DataItem.JsonItem(it) }
        }
        submitList(items)
    }

    class TextViewHolder(view: View): RecyclerView.ViewHolder(view) {
        companion object {
            fun from(parent: ViewGroup): TextViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.header, parent, false)
                return TextViewHolder(view)
            }
        }
    }

    class ViewHolder private constructor(val binding: JsonelementsBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: JsonModel, clickListener:JsonListener) {
            binding.jsonModel = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = JsonelementsBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(
                    binding
                )
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is DataItem.Header -> ITEM_VIEW_TYPE_HEADER
            is DataItem.JsonItem -> ITEM_VIEW_TYPE_ITEM
        }
    }
}

class JsonDiffCallBack : DiffUtil.ItemCallback<DataItem>() {
    override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem.id == newItem.id
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem == newItem
    }
}

class JsonListener(val clickListener: (jsonId: String) -> Unit) {
    fun onClick(jsonModel: JsonModel) = clickListener(jsonModel.id)
}

sealed class DataItem {
    data class JsonItem(val jsonModel: JsonModel): DataItem() {
        override val id = jsonModel.id
    }

    object Header: DataItem() {
        override val id = Long.MIN_VALUE.toString()
    }

    abstract val id: String
}