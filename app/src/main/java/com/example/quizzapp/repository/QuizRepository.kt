package com.example.quizzapp.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.quizzapp.model.QuestionsList
import com.example.quizzapp.retrofit.QuestionsAPI
import com.example.quizzapp.retrofit.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class QuizRepository {

    var questionsAPI: QuestionsAPI

    init {
        questionsAPI = RetrofitInstance().getRetrofitInstance()
            .create(QuestionsAPI::class.java)
    }

    fun getQuestionsFromAPI(): LiveData<QuestionsList>{

        // Live Data
        var data = MutableLiveData<QuestionsList>()
        var questionsList: QuestionsList

        GlobalScope.launch(Dispatchers.IO) {

            // Returning the Response <QuestionsList>
            val response = questionsAPI.getQuestions()

            if (response != null){

                // saving data to the list
                questionsList = response.body()!!

                data.postValue(questionsList)
                Log.i("TAGY", "" +data.value)

            }
        }
        return data
    }

    fun getMoreQuestionsFromAPI(): LiveData<QuestionsList>{
        // Live Data
        var data = MutableLiveData<QuestionsList>()
        var questionsList: QuestionsList

        GlobalScope.launch(Dispatchers.IO) {
            // Returning the Response <QuestionsList>
            val response = questionsAPI.getMoreQuestions()
            if (response != null){
                // saving data to the list
                questionsList = response.body()!!
                data.postValue(questionsList)
                Log.i("TAGY", "" +data.value)
            }
        }
        return data
    }

}