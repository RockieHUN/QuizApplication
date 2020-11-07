package com.example.quiz

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.LinearLayout.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.example.quiz.databinding.FragmentQuestionBinding


class QuestionFragment : Fragment() {
    private var question :Question? =null
    private lateinit var binding : FragmentQuestionBinding

    private val viewModel : MyViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            question = it.getSerializable("question") as Question?
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_question,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        var answers = viewModel.getAnswers()
        var count = viewModel.getCount()


        Log.d("****************",answers.toString())
        Log.d("****************",count.toString())


        //set question text
        binding.questionView.text = question?.question

        val layout = binding.answerHolder
        var buttonList = mutableListOf<Button>()

        //generate buttons
        for (i in 0 until question?.answers?.size!!){

            //create button programatically
            val btn = Button(context)
            btn.layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            )

            if (!answers[count][i])
            {
                btn.backgroundTintList = ColorStateList.valueOf(Color.BLACK)
                btn.setTextColor(Color.WHITE)
            }
            else
            {
                btn.backgroundTintList = ColorStateList.valueOf(Color.WHITE)
                btn.setTextColor(Color.BLACK)
            }


            btn.text = question?.answers!![i].first

            //add button to layout
            layout.addView(btn)

            //add button to list
            buttonList.add(btn)
            //add listeners
            addButtonListeners(buttonList,answers,count)
        }

    }

    fun addButtonListeners(buttons : MutableList<Button>,answers : MutableList<MutableList<Boolean>>, count : Int){

        for (i in 0 until buttons.size){
            buttons[i].setOnClickListener {
                if (answers[count][i]== false){
                    answers[count][i]=true
                    buttons[i].backgroundTintList = ColorStateList.valueOf(Color.WHITE)
                    buttons[i].setTextColor(Color.BLACK)
                    viewModel.addAnswers(answers)
                }
                else
                {
                    answers[count][i]=false
                    buttons[i].backgroundTintList = ColorStateList.valueOf(Color.BLACK)
                    buttons[i].setTextColor(Color.WHITE)
                    viewModel.addAnswers(answers)
                }
            }
        }
    }

    companion object {
        @JvmStatic


        fun newInstance(question : Question) =
                QuestionFragment().apply {
                    arguments = Bundle().apply {
                       putSerializable("question",question);
                    }
                }
    }
}