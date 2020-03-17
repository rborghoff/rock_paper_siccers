package com.example.rockpapersiccers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_game_history.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GameHistoryActivity : AppCompatActivity() {
    private val gameHistory = arrayListOf<GameHistory>()
    private lateinit var historyRepository: HistoryRepository
    private val mainscope = CoroutineScope(Dispatchers.Main)
    private val historyAdapter = HistoryAdapter(gameHistory)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        historyRepository= HistoryRepository(this)
        setContentView(R.layout.activity_game_history)

        initViews()
    }
    private fun initViews(){
        rvHistory.layoutManager = LinearLayoutManager(this@GameHistoryActivity, RecyclerView.VERTICAL,false)
        rvHistory.adapter= historyAdapter
         button.setOnClickListener{deleteHistory()}
        rvHistory.addItemDecoration(DividerItemDecoration(this@GameHistoryActivity, DividerItemDecoration.VERTICAL))
       getGameHistoryFromDatabase()

    }

    private fun getGameHistoryFromDatabase() {
        mainscope.launch {
            val gameHistory = withContext(Dispatchers.IO) {
                historyRepository.getAllGames()
            }
            this@GameHistoryActivity.gameHistory.clear()
            this@GameHistoryActivity.gameHistory.addAll(gameHistory)
            this@GameHistoryActivity.historyAdapter.notifyDataSetChanged()

        }
    }
    private fun deleteHistory() {
        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.IO) {
                historyRepository.deleteAllGames()
            }
            getGameHistoryFromDatabase()
        }
    }


}
