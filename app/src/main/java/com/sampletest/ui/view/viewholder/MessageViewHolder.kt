package com.sampletest.ui.view.viewholder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sampletest.R
import com.sampletest.data.local.entities.Message
import com.sampletest.ui.interfaces.OnMessageClicked


class MessageViewHolder(
    itemView: View?,
    val listener: OnMessageClicked
) : RecyclerView.ViewHolder(itemView!!), View.OnClickListener {

    var tvCreatedBy: TextView
    var tvMessage: TextView
    var tvCreatedOn: TextView
    private var mMessage: Message? = null

    init {
        tvCreatedBy = itemView!!.findViewById(R.id.tvCreatedBy)
        tvMessage = itemView!!.findViewById(R.id.tvMessage)
        tvCreatedOn = itemView!!.findViewById(R.id.tvCreatedOn)

        itemView?.setOnClickListener(this)

    }

    fun bindNowShowingData(mMessage: Message?) {
        if (mMessage == null) {
            return
        } else {

            this.mMessage = mMessage

            tvCreatedBy.setText("${mMessage.createdBy}")
            tvMessage.setText("${mMessage.msg}")
            tvCreatedOn.setText("${mMessage.createdOn}")
//            tv_Item.setPaintFlags(tv_Item.getPaintFlags() or Paint.UNDERLINE_TEXT_FLAG)

        }
    }

    override fun onClick(p0: View?) {
        val position: Int = adapterPosition
        if (position != RecyclerView.NO_POSITION) {
//            listener.onItemClicked(mMessage!!)
        }
    }

}