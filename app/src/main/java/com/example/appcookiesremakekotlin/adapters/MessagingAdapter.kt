package com.example.appcookiesremakekotlin.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appcookiesremakekotlin.R
import com.example.appcookiesremakekotlin.db.Message
import com.example.appcookiesremakekotlin.utils.Constants
import kotlinx.android.synthetic.main.message_item.view.*

class MessagingAdapter: RecyclerView.Adapter<MessagingAdapter.MyHolder>() {

    var messageList = mutableListOf<Message>()

    inner class MyHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                messageList.removeAt(adapterPosition)
                notifyItemRemoved(adapterPosition)
            }
        }
    }

    fun insertMessage(message: Message) {
        this.messageList.add(message)
        notifyItemInserted(messageList.size)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        return MyHolder(LayoutInflater.from(parent.context).inflate(R.layout.message_item, parent, false))
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val currentMessage = messageList[position]

        when(currentMessage.id) {
            Constants.SEND_ID -> {
                holder.itemView.tv_message.apply {
                    text = currentMessage.message
                    visibility = View.VISIBLE
                }
                holder.itemView.tv_bot_message.visibility = View.GONE
            }
            Constants.RECEIVE_ID -> {
                holder.itemView.tv_bot_message.apply {
                    text = currentMessage.message
                    visibility = View.VISIBLE
                }
                holder.itemView.tv_message.visibility = View.GONE
            }
        }

    }

    override fun getItemCount(): Int {
        return messageList.size
    }

}