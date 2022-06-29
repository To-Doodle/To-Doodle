package ca.uwaterloo.cs.todoodle

import android.view.View
import androidx.recyclerview.widget.RecyclerView

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

class RecycleViewAdapter(private val titles: List<String>, private val deadlines: List<String>) : RecyclerView.Adapter<RecycleViewAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemTitle: TextView = itemView.findViewById(R.id.title_view)
        val itemDue: TextView = itemView.findViewById(R.id.date_view)

        init {
            itemView.setOnClickListener { v: View ->
                Toast.makeText(itemView.context, "You clicked on task", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_card, parent, false)
        return ViewHolder(v)
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return titles.size
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemTitle.text = titles[position]
        holder.itemDue.text = deadlines[position]
    }
}
