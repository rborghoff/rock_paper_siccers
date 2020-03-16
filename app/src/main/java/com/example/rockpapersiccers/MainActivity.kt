package com.example.rockpapersiccers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private var cpuMove = 0
    private var win = 0
    private var draw = 0
    private var lose = 0
    private var playersChoise = 0

    private val gameHistory = arrayListOf<GameHistory>()
    private lateinit var historyRepository: HistoryRepository
    private val mainscope = CoroutineScope(Dispatchers.Main)
//    private val historyAdapter = HistoryAdapter(gameHistory)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
historyRepository= HistoryRepository(this)
        ivPaper.setOnClickListener { onPaperClick() }
        ivRock.setOnClickListener { onRockClick() }
        ivScissor.setOnClickListener { onScissorClick() }
        initViews()




    }

    private fun randomImg() {
        cpuMove = (1..3).random()
        createCpuMove()
    }

    private fun createCpuMove() {
        imgCpu.setImageResource(
            when (cpuMove) {
                1 -> (R.drawable.rock)
                2 -> (R.drawable.paper)
                3 -> (R.drawable.scissors)
                else -> (R.drawable.scissors)
            }
        )
    }

    private fun onRockClick() {
        playersChoise = 1
        imgPlayer.setImageResource(R.drawable.rock)
        gameResult()
        addGame()

    }

    private fun onPaperClick() {
        playersChoise = 2
        imgPlayer.setImageResource(R.drawable.paper)
        gameResult()
        addGame()
    }

    private fun onScissorClick() {
        playersChoise = 3
        imgPlayer.setImageResource(R.drawable.scissors)
        gameResult()
        addGame()
    }

    private fun onResultDraw() {
        tvWinLose.text = getString(R.string.draw)
    }

    private fun onResultWin() {
        tvWinLose.text = getString(R.string.win)
    }

    private fun onResultLose() {
        tvWinLose.text = getString(R.string.lose)
    }

    private fun gameResult() {
        randomImg()
        if (cpuMove == 1 && playersChoise == 1) {
            onResultDraw()
            draw++
        } else if (cpuMove == 2 && playersChoise == 2) {
            onResultDraw()
            draw++
        } else if (cpuMove == 3 && playersChoise == 3) {
            onResultDraw()
            draw++
        } else if (cpuMove == 1 && playersChoise == 2) {
            onResultWin()
            win++
        } else if (cpuMove == 2 && playersChoise == 3) {
            onResultWin()
            win++
        } else if (cpuMove == 3 && playersChoise == 1) {
            onResultWin()
            win++
        } else if (cpuMove == 1 && playersChoise == 3) {
            onResultLose()
            lose++
        } else if (cpuMove == 2 && playersChoise == 1) {
            onResultLose()
            lose++
        } else {
            onResultLose()
            lose++
        }
    }

    private fun initViews() {
getGameHistoryFromDatabase()

    }

    private fun getGameHistoryFromDatabase() {
        mainscope.launch {
            val gameHistory = withContext(Dispatchers.IO) {
                historyRepository.getAllGames()
            }

            this@MainActivity.gameHistory.addAll(gameHistory)

        }
    }

    private fun addGame() {

        mainscope.launch {
            val game = GameHistory(
                win = win,
                draw = draw,
                loss = lose,
                cpuMove = cpuMove,
                playerMove = playersChoise
            )

            withContext(Dispatchers.IO) {
                historyRepository.insertGame(game)
            }

            getGameHistoryFromDatabase()
        }

    }
}
