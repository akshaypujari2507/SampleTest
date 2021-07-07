package com.sampletest.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.sampletest.R
import com.sampletest.data.local.entities.Message
import com.sampletest.ui.interfaces.OnMessageClicked
import com.sampletest.ui.view.viewholder.MessageViewHolder
import java.util.*


class MessageAdapter(private val listener: OnMessageClicked) :
    RecyclerView.Adapter<MessageViewHolder>()
    , Filterable {

    public var filteredList: MutableList<Message> = arrayListOf()

    var messages: List<Message>? = null
        set(value) {
            field = value
            filteredList.addAll(value!!)
            notifyDataSetChanged()
        }

    override fun getItemCount() = filteredList?.size ?: 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_message, parent, false)
        return MessageViewHolder(view, listener)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message: Message? = filteredList?.get(position)!!
        if (filteredList != null) {
            val viewHolder = holder as MessageViewHolder
            viewHolder.bindNowShowingData(message)
        } else {
            notifyItemRemoved(position)
        }
    }

    override fun getFilter(): Filter {
        // TODO("Not yet implemented")
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                filteredList.clear()
                if (charSearch.isEmpty()) {
                    filteredList.addAll(messages!!)
                } else {
                    for (row in messages!!) {
                        if (row.createdBy!!.toLowerCase(Locale.ROOT)
                                .contains(charSearch.toLowerCase(Locale.ROOT))) {
                            filteredList.add(row)
                        }
                    }
                }
                val filterResults = FilterResults()
                filterResults.values = filteredList
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                notifyDataSetChanged()
            }

        }
    }

/*
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                filteredList.clear()
                if (charSearch.isEmpty()) {
                    filteredList.addAll(message!!)
                } else {
                    for (row in message!!) {
                        if (row.name!!.toLowerCase(Locale.ROOT)
                                .contains(charSearch.toLowerCase(Locale.ROOT)) ||
                            row.dept_name!!.toLowerCase(Locale.ROOT)
                                .contains(charSearch.toLowerCase(Locale.ROOT))
                        ) {
                            filteredList.add(row)
                        }
                    }
                }
                val filterResults = FilterResults()
                filterResults.values = filteredList
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                notifyDataSetChanged()
            }

        }
    }
*/

}
