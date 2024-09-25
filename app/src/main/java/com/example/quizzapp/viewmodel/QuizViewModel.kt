package com.example.quizzapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.quizzapp.model.QuestionsList
import com.example.quizzapp.repository.QuizRepository

class QuizViewModel : ViewModel() {

    var repository : QuizRepository = QuizRepository()
    lateinit var questionsLiveData: LiveData<QuestionsList>
    lateinit var moreQuestionsLiveData: LiveData<QuestionsList>

    init {
        questionsLiveData = repository.getQuestionsFromAPI()
        moreQuestionsLiveData = repository.getMoreQuestionsFromAPI()
    }

    fun getQuestionsFromLiveData():LiveData<QuestionsList>{
        return questionsLiveData
    }

    fun getMoreQuestionsFromLiveData():LiveData<QuestionsList>{
        return moreQuestionsLiveData
    }

}