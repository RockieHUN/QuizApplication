package com.example.quiz

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyViewModel(

): ViewModel()
{
     //var answers: MutableList<MutableList<Boolean>> = mutableListOf<MutableList<Boolean>>()
     private var answers : MutableLiveData <MutableList<MutableList<Boolean>>> = MutableLiveData<MutableList<MutableList<Boolean>>>()
     private var count : MutableLiveData <Int> = MutableLiveData<Int>()
     private var results : MutableLiveData <MutableList<Int>> = MutableLiveData<MutableList<Int>>()

    fun setResults(list : MutableList<Int>){
        results.value=list
    }

    fun getResults () : MutableList<Int>{
        return results.value!!
    }

    fun setCount(number :Int){
        this.count.value = number
    }

    fun getCount (): Int{
        return this.count.value!!
    }

    fun addAnswers(list : MutableList<MutableList<Boolean>>){
        this.answers.value=list
    }

    fun getAnswers() : MutableList<MutableList<Boolean>>{
        return this.answers.value!!
    }

}