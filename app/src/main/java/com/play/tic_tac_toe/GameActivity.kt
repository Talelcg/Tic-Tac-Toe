package com.play.tic_tac_toe

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.play.tic_tac_toe.databinding.ActivityGameBinding

class GameActivity : ComponentActivity() {
    enum class Turn { NOUGHT, CROSS }

    private lateinit var binding: ActivityGameBinding
    private var currentTurn = Turn.CROSS
    private var gameBoard = mutableListOf<Button>()
    private var gameOver = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initBoard()

        binding.playAgainButton.setOnClickListener {
            resetBoard()
        }
    }

    private fun initBoard() {
        gameBoard.addAll(
            listOf(
                binding.Bord11, binding.Bord12, binding.Bord13,
                binding.Bord21, binding.Bord22, binding.Bord23,
                binding.Bord31, binding.Bord32, binding.Bord33
            )
        )
        resetBoard()
    }

    fun boardTapped(view: View) {
        if (gameOver) return
        if (view !is Button || view.text.isNotEmpty()) return

        view.text = if (currentTurn == Turn.CROSS) "X" else "O"
        currentTurn = if (currentTurn == Turn.CROSS) Turn.NOUGHT else Turn.CROSS
        binding.turn.text = "Turn: ${if (currentTurn == Turn.CROSS) "X" else "O"}"

        when {
            isVictory("X") -> result("X Wins!")
            isVictory("O") -> result("O Wins!")
            fullBoard() -> result("It's a Draw!")
        }
    }

    private fun isVictory(symbol: String): Boolean {
        val lines = listOf(
            listOf(binding.Bord11, binding.Bord12, binding.Bord13),
            listOf(binding.Bord21, binding.Bord22, binding.Bord23),
            listOf(binding.Bord31, binding.Bord32, binding.Bord33),
            listOf(binding.Bord11, binding.Bord21, binding.Bord31),
            listOf(binding.Bord12, binding.Bord22, binding.Bord32),
            listOf(binding.Bord13, binding.Bord23, binding.Bord33),
            listOf(binding.Bord11, binding.Bord22, binding.Bord33),
            listOf(binding.Bord13, binding.Bord22, binding.Bord31)
        )
        return lines.any { line -> line.all { it.text == symbol } }
    }

    private fun fullBoard(): Boolean = gameBoard.all { it.text.isNotEmpty() }

    private fun result(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        binding.playAgainButton.visibility = View.VISIBLE
        gameOver = true
    }

    private fun resetBoard() {
        gameBoard.forEach {
            it.text = ""
        }
        currentTurn = Turn.CROSS
        binding.turn.text = "Turn: X"
        binding.playAgainButton.visibility = View.GONE
        gameOver = false
    }
}
