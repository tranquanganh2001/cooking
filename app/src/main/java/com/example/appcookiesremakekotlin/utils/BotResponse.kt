package com.example.appcookiesremakekotlin.utils

import java.sql.Date
import java.sql.Timestamp
import java.text.SimpleDateFormat

object BotResponse {

    fun basicResponses(_message: String): String {

        val random = (0..2).random()
        val message = _message.toLowerCase()

        return when {
            //Hello
            message.contains("hello") -> {
                when (random) {
                    0 -> "Hello there!"
                    1 -> "Sup"
                    2 -> "Buongiorno!"
                    else -> "error" }
            }

            //Math calculations
            message.contains("solve") -> {
                val equation: String? = message.substringAfterLast("solve")
                return try {
                    val answer = SolveMath.solveMath(equation ?: "0")
                    "$answer"

                } catch (e: Exception) {
                    "Sorry, I can't solve that."
                }
            }

            //breakfast
            message.contains("breakfast") -> {
                when (random) {
                    0 -> "You can go to the breakfast dishes section in the categories section"
                    1 -> "Breakfast Potatoes, Salmon Eggs Eggs Benedict,.."
                    2 -> "Smoked Haddock Kedgeree, Home-made Mandazi,.."
                    else -> "error" }
            }

            //vegan
            message.contains("vegan") -> {
                when (random) {
                    0 -> "You can go to the vegan section in the categories section"
                    1 -> "Roast fennel and aubergine paella,Vegan Chocolate Cake,.."
                    2 -> "Vegan Lasagna,.."
                    else -> "error" }
            }

            //dinner
            message.contains("dinner") -> {
                when (random) {
                    0 -> "You can go to the beef section in the categories section"
                    1 -> "Beef Bourguignon, Beef Randang, Beef Lo Mein,.."
                    2 -> "Beef Wellington, Braised Beef Chilli, Egyption Fatteh,.."
                    else -> "error" }
            }

            //lunch
            message.contains("lunch") -> {
                when (random) {
                    0 -> "You can go to the vegetarian section in the categories section"
                    1 -> "Dal Fry, Egg Drop Soup, Flamiche,.."
                    2 -> "Kafteji, Koshari, Matar Paneer, Ratatouille,.."
                    else -> "error" }
            }

            //How are you?
            message.contains("how are you") -> {
                when (random) {
                    0 -> "I'm doing fine, thanks!"
                    1 -> "I'm hungry..."
                    2 -> "Pretty good! How about you?"
                    else -> "error"
                }
            }

            //help
            message.contains("help") -> {
                when (random) {
                    0 -> "What would you like to contribute to us can be reached via email nguyenluongnghia412@gmail.com"
                    1 -> "What would you like to contribute to us can be reached via email nguyenluongnghia412@gmail.com"
                    2 -> "What would you like to contribute to us can be reached via email nguyenluongnghia412@gmail.com"
                    else -> "error" }
            }

            //What time is it?
            message.contains("time") && message.contains("?")-> {
                val timeStamp = Timestamp(System.currentTimeMillis())
                val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm")
                val date = sdf.format(Date(timeStamp.time))

                date.toString()
            }

            //Open Google
            message.contains("open") && message.contains("google")-> {
                Constants.OPEN_GOOGLE
            }

            //Search on the internet
            message.contains("search")-> {
                Constants.OPEN_SEARCH
            }

            //When the programme doesn't understand...
            else -> {
                when (random) {
                    0 -> "I don't understand..."
                    1 -> "Try asking me something different"
                    2 -> "Idk"
                    else -> "error"
                }
            }


        }

    }

}