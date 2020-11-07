package com.example.quiz

import java.io.Serializable

class Question (val question : String,
                val answers: List<Pair<String,Boolean>>
) : Serializable {

    init {
        var selectedAns = mutableListOf<Boolean>()
        for (i in 0..answers.size-1){
            selectedAns.add(false)
        }
    }
}