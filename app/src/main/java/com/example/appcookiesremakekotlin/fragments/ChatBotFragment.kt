package com.example.appcookiesremakekotlin.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appcookiesremakekotlin.R
import com.example.appcookiesremakekotlin.adapters.MessagingAdapter
import com.example.appcookiesremakekotlin.databinding.FragmentChatBotBinding
import com.example.appcookiesremakekotlin.db.Message
import com.example.appcookiesremakekotlin.utils.BotResponse
import com.example.appcookiesremakekotlin.utils.Constants
import com.example.appcookiesremakekotlin.utils.Time
import kotlinx.android.synthetic.main.fragment_chat_bot.*
import kotlinx.coroutines.*

class ChatBotFragment : Fragment() {

    private lateinit var binding: FragmentChatBotBinding
    private lateinit var adapterMessage: MessagingAdapter
    private val botList = listOf("Peter", "Leonardo Dicaprio", "Timothee Chalamet", "Luna")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChatBotBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView()

        clickEvents()

        val random = (0..3).random()
        customMessage("Hello! Today you're speaking with ${botList[random]}, how may i help?")
    }

    private fun clickEvents() {
        btn_send.setOnClickListener {
            sendMessage()
        }

        et_message.setOnClickListener {
            GlobalScope.launch {
                delay(100)
                withContext(Dispatchers.Main) {
                    rv_messages.scrollToPosition(adapterMessage.itemCount-1)
                }
            }
        }
    }

    private fun sendMessage() {
        val message = et_message.text.toString()
        val timeStamp = Time.timeStamp()

        if(message.isNotEmpty()) {
            et_message.setText("")

            adapterMessage.insertMessage(Message(message, Constants.SEND_ID, timeStamp))
            rv_messages.scrollToPosition(adapterMessage.itemCount-1)

            botResponse(message)
        }
    }

    private fun botResponse(message: String) {
        val timeStamp = Time.timeStamp()

        GlobalScope.launch {
            delay(1000)
            withContext(Dispatchers.Main) {
                val response = BotResponse.basicResponses(message)
                adapterMessage.insertMessage(Message(response, Constants.RECEIVE_ID, timeStamp))

                rv_messages.scrollToPosition(adapterMessage.itemCount-1)
                when(response) {
                    Constants.OPEN_GOOGLE -> {
                        val site = Intent(Intent.ACTION_VIEW)
                        site.data = Uri.parse("https://www.google.com/")
                        startActivity(site)
                    }
                    Constants.OPEN_SEARCH -> {
                        val site = Intent(Intent.ACTION_VIEW)
                        val searchItem: String? = message.substringAfter("search")
                        site.data = Uri.parse("https://www.google.com/search?&q=$searchItem")
                        startActivity(site)
                    }
                }
            }
        }
    }

    private fun recyclerView() {
        adapterMessage = MessagingAdapter()
        rv_messages.adapter = adapterMessage
        rv_messages.layoutManager = LinearLayoutManager(context)
    }

    private fun customMessage(message: String) {
        GlobalScope.launch {
            delay(500)
            withContext(Dispatchers.Main) {
                val timeStamp = Time.timeStamp()
                adapterMessage.insertMessage(Message(message, Constants.RECEIVE_ID, timeStamp))

                rv_messages.scrollToPosition(adapterMessage.itemCount-1)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        //khởi chạy các quy trình cấp cao nhất đang hoạt động trên
        // toàn bộ vòng đời của ứng dụng và không bị hủy bỏ sớm.
        GlobalScope.launch {
            delay(500)
            withContext(Dispatchers.Main) {
                rv_messages.scrollToPosition(adapterMessage.itemCount-1)

            }
        }
    }
}