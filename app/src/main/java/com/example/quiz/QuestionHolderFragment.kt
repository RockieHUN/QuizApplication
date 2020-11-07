package com.example.quiz

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.quiz.databinding.FragmentQuestionHolderBinding



class QuestionHolderFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding : FragmentQuestionHolderBinding


    private val viewModel : MyViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_question_holder,container,false)
        return binding.root



    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val questions = questions()
        var counter = 0

        var answers = generateAnswersForView(questions)

        viewModel.addAnswers(answers)
        viewModel.setCount(0)



        //load the first question
        var fragmentTransaction = parentFragmentManager.beginTransaction()
        var fragment = QuestionFragment.newInstance(questions[0])
        fragmentTransaction.add(R.id.fragment_container,fragment)
        fragmentTransaction.commit()


        //listeners for buttons
        binding.nextButton.setOnClickListener {
            if (counter + 1 < questions.size){
                fragmentTransaction = parentFragmentManager.beginTransaction()
                counter++
                fragment = QuestionFragment.newInstance(questions[counter])
                fragmentTransaction.replace(R.id.fragment_container,fragment)
                viewModel.setCount(counter)
                fragmentTransaction.commit()
            }
            else{
                findNavController().navigate(R.id.action_questionHolderFragment_to_statisticFragment)
            }
        }

        binding.prevButton.setOnClickListener {
            if (counter - 1 > -1){
                fragmentTransaction = parentFragmentManager.beginTransaction()
                counter --
                fragment = QuestionFragment.newInstance(questions[counter])
                fragmentTransaction.replace(R.id.fragment_container,fragment)
                viewModel.setCount(counter)
                fragmentTransaction.commit()
            }
        }

    }


    fun questions(): MutableList<Question> {

        var questions = mutableListOf<Question>()

        var text = "Mit jelent kotlinban a sealed osztály módosító?"
        var answers = mutableListOf<Pair<String,Boolean>>()
        answers.add(Pair("Nincs ilyen módosító",false))
        answers.add(Pair("Nem lehet származtatni",false))
        answers.add(Pair("Csak file-on belül lehet származtatni és nem lehet példányosítani",true))
        answers.add(Pair("Nem lehet példányosítani",false))

        questions.add(Question(text,answers))

        text = "Miért nem jó a Constraint Layout?"
        answers = mutableListOf<Pair<String,Boolean>>()
        answers.add(Pair("Nehéz elrendezni rajta az elemeket",true))
        answers.add(Pair("Túl kicsi lesz rajta egy elem",false))
        answers.add(Pair("Teljesen fekete a háttere",false))
        answers.add(Pair("Az őrületbe kergeti a programozót -___-",true))

        questions.add(Question(text,answers))

        return questions
    }

    fun generateAnswersForView(questions : MutableList<Question>): MutableList<MutableList<Boolean>>{

        var answers = mutableListOf<MutableList<Boolean>>()

        for (question in questions){
            var answerList = mutableListOf<Boolean>()
            for (answer in question.answers)
            {
                answerList.add(false)
            }
            answers.add(answerList)
        }

        return answers
    }
}