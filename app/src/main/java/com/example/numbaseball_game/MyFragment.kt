package com.example.numbaseball_game

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class MyFragment : Fragment() {
    private var position: Int = 0
    private var userNumbers: String? = null
    private var result: String? = null
    companion object {
        private const val ARG_POSITION = "position"
        private const val ARG_USER_NUMBERS = "user_numbers"
        private const val ARG_RESULT = "result"

        fun newInstance(position: Int, userNumbers: String, result: String): MyFragment {
            val fragment = MyFragment()
            val args = Bundle().apply {
                putInt(ARG_POSITION, position)
                putString(ARG_USER_NUMBERS, userNumbers)
                putString(ARG_RESULT, result)
            }
            fragment.arguments = args
            return fragment
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            position = it.getInt(ARG_POSITION)
            userNumbers= it.getString(ARG_USER_NUMBERS)
            result= it.getString(ARG_RESULT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_push, container, false)

        val numbersTextView = view.findViewById<TextView>(R.id.numbersTextView)
        val resultTextView = view.findViewById<TextView>(R.id.resultTextView)
        numbersTextView.text = userNumbers
        resultTextView.text = result

        return view

    }
}