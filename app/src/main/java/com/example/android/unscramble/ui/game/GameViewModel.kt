

package com.example.android.unscramble.ui.game

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class GameViewModel : ViewModel() {

    private val _score = MutableLiveData(0)
     private var _counter = 0
    private val _currentScrambledWord = MutableLiveData<String>()
    private val _currentWordCount = MutableLiveData(0)

    val score: LiveData<Int>
        get() = _score
       val counter: Int
       get() = _counter

    val currentWordCount: LiveData<Int>
        get() = _currentWordCount

    val currentScrambledWord: LiveData<String>
        get() = _currentScrambledWord

    // hold a list of words you use in the game, to avoid repetitions
    private var wordsList: MutableList<String> = mutableListOf()
    private lateinit var currentWord: String // counter

    init {
        getNextWord()
    }

    private fun getNextWord() {

        // random word
        currentWord = allWordsList.random()

        // temp word
        val tempWord = currentWord.toCharArray()
        tempWord.shuffle()

        //  shuffled order of characters is the same as the original word
        while (String(tempWord).equals(currentWord, false)) {
            // if true
            // keep shuffle()
            tempWord.shuffle()
        }// end while

        // check if a word has been used already
        if (wordsList.contains(currentWord)) {
            getNextWord()
        } else {
            _currentScrambledWord.value = String(tempWord)
            _currentWordCount.value = (_currentWordCount.value)?.inc()
            wordsList.add(currentWord)
        }// end else

    } // end getNextWord

    fun nextWord(): Boolean {
        return if (_currentWordCount.value!! < MAX_NO_OF_WORDS) {
            getNextWord()
            true
        } else false
    }//end nextWord

    private fun increaseScore() {
        _score.value = (_score.value)?.plus(SCORE_INCREASE)
    }// end increaseScore

    fun isUserWordCorrect(playerWord: String): Boolean {
        if (playerWord.equals(currentWord, true)) {
            increaseScore()
            return true
        } else
            return false
    }// end isUserWordCorrect

    fun reinitializeData() {
        _score.value = 0
        _currentWordCount.value = 0
        wordsList.clear()
        getNextWord()
    }// end reinitializeData


}// end view model