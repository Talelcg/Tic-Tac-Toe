package com.play.tic_tac_toe

import android.os.Bundle
import androidx.activity.ComponentActivity
import android.view.View
import android.widget.Button
import com.play.tic_tac_toe.databinding.ActivityMainBinding
import android.widget.Toast

class MainActivity : ComponentActivity() {
    enum class Turn
    {
        NOUGHT, CROSS
    }
    enum class Player(val symbol: String) {
        NOUGHT("O"),
        CROSS("X")
    }

    private var firstTurn = Turn.CROSS
    private var currentTurn = Turn.CROSS

    private var playerXScore = 0
    private var playerOScore = 0

    private var gameBoard = mutableListOf<Button>()

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initBoard()

        binding.playAgainButton.setOnClickListener {
            resetBoard()
            binding.playAgainButton.visibility = View.GONE
            binding.turn.visibility = View.VISIBLE
        }
    }

    private fun initBoard()
    {
        gameBoard.addAll(
            listOf(
                binding.Bord11, binding.Bord12, binding.Bord13,
                binding.Bord21, binding.Bord22, binding.Bord23,
                binding.Bord31, binding.Bord32, binding.Bord33
            )
        )
    }
    private fun setTurn() {
        val turnText = when (currentTurn) {
            Turn.CROSS -> "Turn - ${Player.CROSS.symbol}"
            Turn.NOUGHT -> "Turn - ${Player.NOUGHT.symbol}"
        }
        binding.turn.text = turnText
    }
    fun boardTapped(view: View) {
        if (view !is Button) return

        markCell(view)

        when {
            isVictory(Player.NOUGHT.symbol) -> {
                playerOScore++
                result("Noughts Win The Game!")
            }
            isVictory(Player.CROSS.symbol) -> {
                playerXScore++
                result("Crosses Win The Game!")
            }
            fullBoard() -> {
                result("New Game, no one wins")
            }
        }
    }

    private fun isVictory(symbol: String): Boolean {
        val winningCombinations = listOf(
            // Horizontal combinations
            listOf(binding.Bord11, binding.Bord12, binding.Bord13),
            listOf(binding.Bord21, binding.Bord22, binding.Bord23),
            listOf(binding.Bord31, binding.Bord32, binding.Bord33),

            // Vertical combinations
            listOf(binding.Bord11, binding.Bord21, binding.Bord31),
            listOf(binding.Bord12, binding.Bord22, binding.Bord32),
            listOf(binding.Bord13, binding.Bord23, binding.Bord33),

            // Diagonal combinations
            listOf(binding.Bord11, binding.Bord22, binding.Bord33),
            listOf(binding.Bord13, binding.Bord22, binding.Bord31)
        )

        // Check if any winning combination matches the given symbol
        return winningCombinations.any { combination ->
            combination.all { button -> button.text == symbol }
        }
    }

    private fun result(title: String)
    {
        val message = "\nNoughts $playerOScore\n\nCrosses $playerXScore"

        Toast.makeText(this, "$title\n$message", Toast.LENGTH_LONG).show()
        resetBoard()
        binding.turn.visibility = View.GONE
        binding.playAgainButton.visibility = View.VISIBLE
    }

    private fun resetBoard()
    {
        for(button in gameBoard)
        {
            button.text = ""
        }

        if(firstTurn == Turn.NOUGHT)
            firstTurn = Turn.CROSS
        else if(firstTurn == Turn.CROSS)
            firstTurn = Turn.NOUGHT

        currentTurn = firstTurn
        setTurn()
    }

    private fun markCell(button: Button)
    {
        if (button.text.isNotEmpty()) return

        button.text = if (currentTurn == Turn.NOUGHT) Player.NOUGHT.symbol else Player.CROSS.symbol
        currentTurn = if (currentTurn == Turn.NOUGHT) Turn.CROSS else Turn.NOUGHT

        setTurn()
    }

    private fun fullBoard(): Boolean
    {
        for(button in gameBoard)
        {
            if(button.text == "")
                return false
        }
        return true
    }


}

