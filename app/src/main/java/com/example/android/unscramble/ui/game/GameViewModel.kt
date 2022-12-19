package com.example.android.unscramble.ui.game

import androidx.lifecycle.ViewModel


// state holder
class GameViewModel : ViewModel() {




    // to save data state if ui state is changed
    // declare variables
    // local for viewmodel to modify
    private var _score = 0
    private var _counter = 0
    private var _currentScrambledWord = "test"
    private var _currentWordCount = 0


    // global for other classes to use
    // no other class can change its value (it only gets the value of "_counter")
    val score : Int
        get() = _score

    val counter: Int
        get() = _counter

    val currentScrambledWord: String
        get() = _currentScrambledWord

    val currentWordCount: Int
        get() = _currentWordCount

    // hold a list of words you use in the game, to avoid repetitions
    private var wordsList: MutableList<String> = mutableListOf()
    private lateinit var currentWord: String // counter








    // To Make The Project Start From This Point (This Function)
    init {
        getNextWord()
    }







    // New Function -----------------------------------------------------------------------
    // functions needed to update data based on user clicks
    // Get next word
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
            _currentScrambledWord = String(tempWord)
            ++_currentWordCount
            wordsList.add(currentWord)
        }

    }







    // New Function -----------------------------------------------------------------------
    // Returns true if the current word count is less than MAX_NO_OF_WORDS.
    // Updates the next word.
    fun nextWord(): Boolean {
        return if (currentWordCount < MAX_NO_OF_WORDS) {
            getNextWord()
            true
        } else false
    }







    // New Function -----------------------------------------------------------------------
    // Update score
    private fun increaseScore(){
        _score += SCORE_INCREASE
    }







    // New Function -----------------------------------------------------------------------
    fun isUserWordCorrect(playerWord: String) : Boolean{
        if (playerWord.equals(currentWord, true)){
            increaseScore()
            return true
        } else
            return false
    }








    // New Function -----------------------------------------------------------------------
    //Re-initializes the game data to restart the game.
    fun reinitializeData() {
        _score = 0
        _currentWordCount = 0
        wordsList.clear()
        getNextWord()
    }








} //End ViewModell