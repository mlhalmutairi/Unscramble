package com.example.android.unscramble.ui.game

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


// state holder
class GameViewModel : ViewModel() {

    // to save data state if ui state is changed

    // declare variables
    // local for ViewModel to modify
    private var _score  = MutableLiveData<Int>(0)
    private var _counter = 0
    private var _currentScrambledWord = MutableLiveData<String>()
    private var _currentWordCount = MutableLiveData<Int>(0)

    // global for other classes to use
    // no other class can change its value (it only gets the value of "_counter")
    val score : LiveData<Int>
        get() = _score

    val counter: Int
        get() = _counter

    val currentScrambledWord: LiveData<String>
        get() = _currentScrambledWord

    val currentWordCount: LiveData<Int>
        get() = _currentWordCount

    // hold a list of words you use in the game, to avoid repetitions
    private var wordsList: MutableList<String> = mutableListOf()
    private lateinit var currentWord: String // counter


    init {
        getNextWord()
    }

    // functions needed to update data based on user clicks
    // [1] Get next word
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
        }

        // check if a word has been used already
        if (wordsList.contains(currentWord)) {
            getNextWord()
        } else {
            _currentScrambledWord.value = String(tempWord)

            _currentWordCount.value = (_currentWordCount.value)?.inc()
            wordsList.add(currentWord)
        }

    } // End getNextWord()


    /*
    * Returns true if the current word count is less than MAX_NO_OF_WORDS.
    * Updates the next word.
    */
    fun nextWord(): Boolean {
        return if (currentWordCount.value!! < MAX_NO_OF_WORDS) {
            getNextWord()
            true
        } else false
    }

    // [2] Update score
    private fun increaseScore(){
        // live data is an object not variable
        _score.value = (_score.value)?.plus(SCORE_INCREASE)
    }

    fun isUserWordCorrect(playerWord: String) : Boolean{
        if (playerWord.equals(currentWord, true)){
            increaseScore()
            return true
        } else
            return false
    }

    /*
    * Re-initializes the game data to restart the game.
    */
    fun reinitializeData() {
        _score.value = 0
        _currentWordCount.value = 0
        wordsList.clear()
        getNextWord()
    }

}