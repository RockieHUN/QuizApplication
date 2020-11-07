package com.example.quiz

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.quiz.databinding.FragmentStatisticBinding
import java.util.*
import kotlin.concurrent.timerTask


class StatisticFragment : Fragment() {

    private lateinit var binding : FragmentStatisticBinding
    private val viewModel : MyViewModel by activityViewModels()
    private lateinit var sharedPref : SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_statistic,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var results = viewModel.getResults()
        binding.totalCounter.text = results[0].toString()
        binding.correctCounter.text = results[1].toString()
        binding.wrongCounter.text = results[2].toString()


        sharedPref = requireContext().getSharedPreferences("results", Context.MODE_PRIVATE)
        var editor = sharedPref.edit()
        editor.clear()
        editor.putInt("total",results[0])
        editor.putInt("correct",results[1])
        editor.putInt("wrong",results[2])
        editor.apply()

        binding.restartButton.setOnClickListener {
            findNavController().navigate(R.id.action_statisticFragment_to_startFragment)
        }

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            findNavController().navigate(R.id.action_statisticFragment_to_startFragment)
        }
    }




}