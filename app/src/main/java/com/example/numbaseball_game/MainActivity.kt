package com.example.numbaseball_game

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction

fun getRandomThreeNumbers(): List<Int> {
    val list = (1..9).shuffled()

    return list.take(3)
}

class MainActivity : AppCompatActivity() {

    private lateinit var btnEnd: Button
    private lateinit var fragmentContainer: LinearLayout
    private var fragmentCount = 0
    private lateinit var num_View1: TextView
    private lateinit var num_View2: TextView
    private lateinit var num_View3: TextView
    private var randomNumbers: List<Int> = listOf()
    var number = 0;

    private fun onNumberButtonClicked(number: String) {
        if (num_View1.text.isEmpty()) {
            num_View1.text = number
        } else if (num_View2.text.isEmpty() && num_View1.text.toString() != number) {  // Check if the number is not same as the first one.
            num_View2.text = number
        } else if (num_View3.text.isEmpty() && num_View1.text.toString() != number && num_View2.text.toString() != number) {  // Check if the number is not same as the first and second ones.
            num_View3.text = number
        } else {
            if(number == num_View1.text.toString() || number == num_View2.text.toString()) {
                Toast.makeText(this, "같은 숫자는 사용할 수 없습니다.", Toast.LENGTH_SHORT).show()
            }

            else {
                Toast.makeText(this, "숫자가 가득 찼습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun onBackButtonClicked() {
        if (!num_View3.text.isEmpty()) {
            num_View3.text = ""
        } else if (!num_View2.text.isEmpty()) {
            num_View2.text = ""
        } else if (!num_View1.text.isEmpty()) {
            num_View1.text = ""
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game_mode_play)
        btnEnd = findViewById(R.id.btn_choice_end)
        fragmentContainer = findViewById(R.id.second_row_layout)

        num_View1 = findViewById(R.id.my_choice_num1)
        num_View2 = findViewById(R.id.my_choice_num2)
        num_View3 = findViewById(R.id.my_choice_num3)
        val buttons = listOf(
            findViewById<Button>(R.id.btn_choice0),
            findViewById<Button>(R.id.btn_choice1),
            findViewById<Button>(R.id.btn_choice2),
            findViewById<Button>(R.id.btn_choice3),
            findViewById<Button>(R.id.btn_choice4),
            findViewById<Button>(R.id.btn_choice5),
            findViewById<Button>(R.id.btn_choice6),
            findViewById<Button>(R.id.btn_choice7),
            findViewById<Button>(R.id.btn_choice8),
            findViewById<Button>(R.id.btn_choice9)
        )

        for (button in buttons) {
            button.setOnClickListener { onNumberButtonClicked(button.text.toString()) }
        }
        val backButton = findViewById<Button>(R.id.btn_choice_back)

        backButton.setOnClickListener { onBackButtonClicked() }

        val numbers = getRandomThreeNumbers()

        randomNumbers = getRandomThreeNumbers()
        Toast.makeText(this, "${randomNumbers}", Toast.LENGTH_SHORT).show()

        btnEnd.setOnClickListener {
            if (num_View1.text.isEmpty() || num_View2.text.isEmpty() || num_View3.text.isEmpty()) {
                Toast.makeText(this, "세 개의 숫자를 모두 선택해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val userNumbers = listOf(num_View1.text.toString().toInt(), num_View2.text.toString().toInt(), num_View3.text.toString().toInt())
            val result = checkNumbers(userNumbers, randomNumbers)

            num_View1.text = ""
            num_View2.text = ""
            num_View3.text = ""

            if (result.first == 3) {
                Toast.makeText(this, "축하합니다! 세개를 다 맞추셨습니다.", Toast.LENGTH_SHORT).show()
            } else {
                val newFragment = MyFragment.newInstance(
                    fragmentCount + 1,
                    userNumbers.joinToString(""),
                    " ${result.first}스트라이크 ${result.second}볼"
                )
                newFragment.arguments = Bundle().apply {
                    putString("user_numbers", userNumbers.joinToString(""))
                    putString("result", "    ${result.first}스트라이크 ${result.second}볼")
                }
                val newFrameLayout = FrameLayout(this).apply {
                    id = View.generateViewId()
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    )
                }
                val output = findViewById<TextView>(R.id.Count)
                number++
                output.setText(number.toString())

                fragmentContainer.addView(newFrameLayout)

                supportFragmentManager.beginTransaction()
                    .add(newFrameLayout.id, newFragment)
                    .addToBackStack(null)
                    .commit()

                fragmentCount++
            }
        }
    }
    private fun checkNumbers(user: List<Int>, random: List<Int>): Pair<Int, Int> {
        var strikes = 0
        var balls = 0

        for ((index, number) in user.withIndex()) {
            if (number == random[index]) {
                strikes++
            } else if (random.contains(number)) {
                balls++
            }
        }

        return Pair(strikes, balls)
    }
}
