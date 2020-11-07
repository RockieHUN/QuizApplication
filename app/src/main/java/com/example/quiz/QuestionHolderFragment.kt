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
                if (counter+1 == questions.size)
                {
                    binding.nextButton.text = "FINISH"
                }
                viewModel.setCount(counter)
                fragmentTransaction.commit()
            }
            else{
                answers = viewModel.getAnswers()
                generateResults(questions,answers)
                findNavController().navigate(R.id.action_questionHolderFragment_to_statisticFragment)
            }
        }

        binding.prevButton.setOnClickListener {
            if (counter - 1 > -1){
                fragmentTransaction = parentFragmentManager.beginTransaction()
                counter --
                fragment = QuestionFragment.newInstance(questions[counter])
                fragmentTransaction.replace(R.id.fragment_container,fragment)
                if (counter == questions.size -2){
                    binding.nextButton.text="NEXT"
                }
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


        text = "Válaszd ki az igaz kijelentéseket!"
        answers = mutableListOf<Pair<String,Boolean>>()
        answers.add(Pair("Egy Fragment több Activity-t tartalmazhat",false))
        answers.add(Pair("Egy abstract osztály példányosítható",false))
        answers.add(Pair("Egy Activityn belül több fragment alkalmazható",true))
        answers.add(Pair("Egy Fragmentből több Fragmentbe is navigálhatunk",true))

        questions.add(Question(text,answers))

        text = "Válaszd ki a HAMIS kijelentéseket a SharedPreferences-re vonatkozóan:"
        answers = mutableListOf<Pair<String,Boolean>>()
        answers.add(Pair("adatmegosztásra alkalmas",false))
        answers.add(Pair("globális változóban tárolja az adatokat",true))
        answers.add(Pair("fájlban tárolja az adatokat",false))
        answers.add(Pair("egyszerre csak egy változót tud tárolni",true))


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

    private fun generateResults(questions : MutableList<Question>, answers : MutableList<MutableList<Boolean>>){

        var total : Int = answers.size
        var correct : Int = 0
        var wrong : Int = 0


        for (i in 0 until questions.size){
            var isCorrect = true
            for (j in questions[i].answers.indices)
            {
                if (questions[i].answers[j].second != answers[i][j]){
                    isCorrect = false
                    break
                }
            }
            if (isCorrect){
                correct += 1
            }
            else{
                wrong += 1
            }
        }
        viewModel.setResults(mutableListOf(total,correct,wrong))

    }
}