package com.example.quizzapp.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.quizzapp.R
import com.example.quizzapp.databinding.ActivityMain2Binding
import com.example.quizzapp.databinding.ActivityMainBinding
import com.example.quizzapp.model.Question
import com.example.quizzapp.viewmodel.QuizViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity2 : AppCompatActivity() {

    lateinit var binding1 : ActivityMain2Binding
    lateinit var quizViewModel: QuizViewModel
    lateinit var questionsList: List<Question>

    companion object{
        var result = 0
        var totalQuestions = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding1 = DataBindingUtil.setContentView(this, R.layout.activity_main2)

        //Reseting the scores
        MainActivity2.result = 0
        MainActivity2.totalQuestions = 0

        // Getting the response
        quizViewModel = ViewModelProvider(this).get(QuizViewModel::class.java)

        GlobalScope.launch(Dispatchers.Main) {
            quizViewModel.getMoreQuestionsFromLiveData().observe(this@MainActivity2, Observer {
                if(it.size>0){
                    questionsList = it
                    Log.i("TAGY", "This is the 1st question: ${questionsList[0]}")

                    binding1.apply {
                        txtQuestion1.text = questionsList!![0].question
                        radio1.text = questionsList!![0].option1
                        radio2.text = questionsList!![0].option2
                        radio3.text = questionsList!![0].option3
                        radio4.text = questionsList!![0].option4
                    }

                }
            })
        }

        var i = 1
        binding1.apply {
            btnNext.setOnClickListener(View.OnClickListener {
                val selectedOption = radioGroup?.checkedRadioButtonId

                if(selectedOption != -1){
                    val radButton = findViewById<View>(selectedOption!!) as RadioButton

                    questionsList.let{
                        if (i< it.size!!){

                            // Getting Number of Questions
                            MainActivity.totalQuestions = it.size
                            // check if correct or not
                            if(radButton.text.toString().equals(it[i-1].correction_option)){
                                MainActivity.result++
                                txtResult?.text = "Correct Answer : ${MainActivity.result}"
                            }

                            // Displaying next question
                            txtQuestion1.text = "${i+1}: " + it[i].question
                            radio1.text = it[i].option1
                            radio2.text = it[i].option2
                            radio3.text = it[i].option3
                            radio4.text = it[i].option4

                            // Checking if it the last question
                            if(i == it.size!!.minus(1)){
                                btnNext.text = "FINISH"
                            }

                            radioGroup?.clearCheck()
                            i++
                        }else{
                            if (radButton.text.toString().equals(it[i-1].correction_option)){
                                MainActivity.result++
                                txtResult?.text = "Correct Answer: ${MainActivity.result}"
                            }else{

                            }
                            val intent = Intent(this@MainActivity2, ResultActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    }
                }else{
                    Toast.makeText(this@MainActivity2, "Please select One Option", Toast.LENGTH_LONG).show()
                }

            })
        }
    }
}