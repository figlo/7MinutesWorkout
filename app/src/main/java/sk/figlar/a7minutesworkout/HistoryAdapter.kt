package sk.figlar.a7minutesworkout

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import sk.figlar.a7minutesworkout.databinding.ItemHistoryRowBinding

class HistoryAdapter(private val items: List<String>) : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    class ViewHolder(binding: ItemHistoryRowBinding) : RecyclerView.ViewHolder(binding.root) {
        val llHistoryItem = binding.llHistoryItem
        val tvPosition = binding.tvPosition
        val tvItem = binding.tvItem

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemHistoryRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val date = items[position]
        holder.tvPosition.text = (position + 1).toString()
        holder.tvItem.text = date

        if(position % 2 == 0) {
            holder.llHistoryItem.setBackgroundColor(Color.parseColor("#ebebeb"))
        } else {
            holder.llHistoryItem.setBackgroundColor(Color.parseColor("#ffffff"))
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}