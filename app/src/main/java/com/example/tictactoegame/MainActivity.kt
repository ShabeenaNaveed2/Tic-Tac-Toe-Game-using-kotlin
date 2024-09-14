package com.example.tictactoegame

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var buttons: Array<Array<Button>>
    private lateinit var statusTextView: TextView
    private lateinit var resetButton: Button

    private var playerXTurn = true
    private var roundCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        statusTextView = findViewById(R.id.statusTextView)
        resetButton = findViewById(R.id.resetButton)
        buttons = Array(3) { r ->
            Array(3) { c ->
                initButton(r, c)
            }
        }

        resetButton.setOnClickListener {
            resetGame()
        }
    }

    private fun initButton(r: Int, c: Int): Button {
        val button: Button = findViewById(
            resources.getIdentifier("button$r$c", "id", packageName)
        )
        button.setOnClickListener {
            onButtonClick(button)
        }
        return button
    }

    private fun onButtonClick(button: Button) {
        if (button.text != "") {
            return
        }

        if (playerXTurn) {
            button.text = "X"
            statusTextView.text = "Player O's turn"
        } else {
            button.text = "O"
            statusTextView.text = "Player X's turn"
        }

        roundCount++

        if (checkForWin()) {
            if (playerXTurn) {
                playerWins("X")
            } else {
                playerWins("O")
            }
        } else if (roundCount == 9) {
            draw()
        } else {
            playerXTurn = !playerXTurn
        }
    }

    private fun checkForWin(): Boolean {
        val field = Array(3) { r ->
            Array(3) { c ->
                buttons[r][c].text.toString()
            }
        }

        for (i in 0..2) {
            if (field[i][0] == field[i][1] && field[i][1] == field[i][2] && field[i][0] != "") {
                return true
            }
        }

        for (i in 0..2) {
            if (field[0][i] == field[1][i] && field[1][i] == field[2][i] && field[0][i] != "") {
                return true
            }
        }

        if (field[0][0] == field[1][1] && field[1][1] == field[2][2] && field[0][0] != "") {
            return true
        }

        if (field[0][2] == field[1][1] && field[1][1] == field[2][0] && field[0][2] != "") {
            return true
        }

        return false
    }

    private fun playerWins(player: String) {
        statusTextView.text = "Player $player wins!"
        disableButtons()
    }

    private fun draw() {
        statusTextView.text = "Draw!"
    }

    private fun disableButtons() {
        for (i in 0..2) {
            for (j in 0..2) {
                buttons[i][j].isEnabled = false
            }
        }
    }

    private fun resetGame() {
        for (i in 0..2) {
            for (j in 0..2) {
                buttons[i][j].text = ""
                buttons[i][j].isEnabled = true
            }
        }

        roundCount = 0
        playerXTurn = true
        statusTextView.text = "Player X's turn"
    }
}