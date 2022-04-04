package ir.ebcom.githubsimplesample.data.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ir.ebcom.githubsimplesample.data.response.SearchResponse
import ir.ebcom.githubsimplesample.databinding.ItemResultBinding

class ResultAdapter(val list: SearchResponse): RecyclerView.Adapter<ResultAdapter.ResultViewHolder>() {
    lateinit var listener: OnSelectedListener

    class ResultViewHolder(var binging: ItemResultBinding): RecyclerView.ViewHolder(binging.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
        val binding = ItemResultBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ResultViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
        holder.binging.nameTv.text = "name: " + list.items[position].login
        holder.binging.scoreTv.text = """score: ${list.items[position].score}"""
        holder.binging.resultCv.setOnClickListener(View.OnClickListener {
            if (listener != null && position != RecyclerView.NO_POSITION)
                listener.setOnSelectedListener(list.items[position].login)
        })
    }

    override fun getItemCount() = list.items.size

    public fun setOnSelectedListener(listener: OnSelectedListener){
        this.listener = listener
    }

    interface OnSelectedListener{
        fun setOnSelectedListener(name: String)
    }
}