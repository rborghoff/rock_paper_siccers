package com.example.rockpapersiccers


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class MainActivity : AppCompatActivity() {
    private var cpuMove = 0
    private var win = 0
    private var draw = 0
    private var lose = 0
    private var playersChoise = 0
    private var gameResult=" "
    private var date = Date()

    private val gameHistory = arrayListOf<GameHistory>()
    private lateinit var historyRepository: HistoryRepository
    private val mainscope = CoroutineScope(Dispatchers.Main)
    private val historyAdapter = HistoryAdapter(gameHistory)//

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        historyRepository= HistoryRepository(this)
        ivPaper.setOnClickListener { onPaperClick() }
        ivRock.setOnClickListener { onRockClick() }
        ivScissor.setOnClickListener { onScissorClick() }
        btnHistory.setOnClickListener{startAddActivity()}
        initViews()




    }
    private fun startAddActivity() {
        val intent = Intent(this, GameHistoryActivity::class.java)
        startActivity(intent)
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
   getStats()
    }

    private fun onPaperClick() {
        playersChoise = 2
        imgPlayer.setImageResource(R.drawable.paper)
        gameResult()
        addGame()
        getStats()
    }

    private fun onScissorClick() {
        playersChoise = 3
        imgPlayer.setImageResource(R.drawable.scissors)
        gameResult()
        addGame()
        getStats()

    }

    private fun onResultDraw() {
        tvWinLose.text = getString(R.string.draw)
      draw++
        gameResult = "Draw"

    }

    private fun onResultWin() {
        tvWinLose.text = getString(R.string.win)
        win++
        gameResult = "You WIN!"
    }

    private fun onResultLose() {
        tvWinLose.text = getString(R.string.lose)
        lose++
        gameResult = "Computer WINS!"
    }

    private fun gameResult() {
        randomImg()
        if (cpuMove == 1 && playersChoise == 1) {
            onResultDraw()


        } else if (cpuMove == 2 && playersChoise == 2) {
            onResultDraw()


        } else if (cpuMove == 3 && playersChoise == 3) {
            onResultDraw()


        } else if (cpuMove == 1 && playersChoise == 2) {
            onResultWin()


        } else if (cpuMove == 2 && playersChoise == 3) {
            onResultWin()

        } else if (cpuMove == 3 && playersChoise == 1) {
            onResultWin()


        } else if (cpuMove == 1 && playersChoise == 3) {
            onResultLose()


        } else if (cpuMove == 2 && playersChoise == 1) {
            onResultLose()


        } else {
            onResultLose()

        }
    }

    private fun initViews() {
getGameHistoryFromDatabase()
        getStats()


    }

    private fun getStats(){
        CoroutineScope(Dispatchers.Main).launch {

            var won =0
            var draws = 0
            var losses = 0
            withContext(Dispatchers.IO){
                won = historyRepository.getWins()
                draws = historyRepository.getDraws()
                losses = historyRepository.getLosses()


            }
            textView5.text = getString(R.string.results, won, draws ,losses)
        }
    }
    private fun getGameHistoryFromDatabase() {
        mainscope.launch {
            val gameHistory = withContext(Dispatchers.IO) {
                historyRepository.getAllGames()
            }

            this@MainActivity.gameHistory.addAll(gameHistory)
            this@MainActivity.gameHistory.clear()

            this@MainActivity.historyAdapter.notifyDataSetChanged()

        }
    }

    private fun addGame() {

        mainscope.launch {
            val game = GameHistory(
                win = win,
                draw = draw,
                loss = lose,
                cpuMove = cpuMove,
                playerMove = playersChoise,
                gameResult = gameResult,
                date = date
            )

            withContext(Dispatchers.IO) {
                historyRepository.insertGame(game)
            }

            getGameHistoryFromDatabase()
        }

    }


}
