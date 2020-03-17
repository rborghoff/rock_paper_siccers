package com.example.rockpapersiccers

import android.content.Context
import android.provider.Settings.Global.getString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.game_history_view.view.*

class HistoryAdapter (private val games:List<GameHistory>) :
RecyclerView.Adapter<HistoryAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryAdapter.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.game_history_view,parent,false)
        )
    }

    override fun getItemCount(): Int =games.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int)  =holder.bind(games[position])

inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    private val context: Context = itemView.context.applicationContext
    private val tvDate: TextView = itemView.findViewById(R.id.tvDate)
    private val ivPlayerImg: ImageView = itemView.findViewById(R.id.ivPlayerImg)
    private val ivCpuImg: ImageView = itemView.findViewById(R.id.ivCpuImg)
    private val tvResult: TextView = itemView.findViewById(R.id.tvResult)
    private val tvPlayerMove: TextView = itemView.findViewById(R.id.tvPlayerMove)
    private val tvCpuMove: TextView = itemView.findViewById(R.id.tvCpuMove)


    fun bind(history: GameHistory){
       tvDate.text = history.date.toString()
        tvCpuMove.text = context.getString(R.string.computer)
        tvPlayerMove.text = context.getString(R.string.you)
        tvResult.text= history.gameResult
        when{
            history.playerMove == 1 -> ivPlayerImg.setImageDrawable(itemView.context.applicationContext.getDrawable(R.drawable.rock))
            history.playerMove == 2 -> ivPlayerImg.setImageDrawable(itemView.context.applicationContext.getDrawable(R.drawable.paper))
            history.playerMove == 3 -> ivPlayerImg.setImageDrawable(itemView.context.applicationContext.getDrawable(R.drawable.scissors))
        }
        when{history.cpuMove == 1 -> ivCpuImg.setImageDrawable(itemView.context.applicationContext.getDrawable(R.drawable.rock))
            history.cpuMove == 2 -> ivCpuImg.setImageDrawable(itemView.context.applicationContext.getDrawable(R.drawable.paper))
            history.cpuMove == 3 -> ivCpuImg.setImageDrawable(itemView.context.applicationContext.getDrawable(R.drawable.scissors))

        }
    }
}

}