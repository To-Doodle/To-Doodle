package ca.uwaterloo.cs.todoodle

import android.view.View
import androidx.recyclerview.widget.RecyclerView

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController
import ca.uwaterloo.cs.todoodle.data.AppDatabase
import ca.uwaterloo.cs.todoodle.data.Task
import ca.uwaterloo.cs.todoodle.data.TaskDao

class RecycleViewAdapter(private val titles: List<String>, private val deadlines: List<String>, private val tasks: List<Task>) : RecyclerView.Adapter<RecycleViewAdapter.ViewHolder>() {

    private lateinit var dao: TaskDao
    private lateinit var dialog: TaskDialogFragment

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val itemTitle: TextView = itemView.findViewById(R.id.title_view)
        val itemDue: TextView = itemView.findViewById(R.id.date_view)
        val taskDialogTitle: TextView = itemView.findViewById(R.id.taskdialog_title)
        var itemTask: Task = Task(0, "", "")
        init {
            itemView.setOnClickListener { v: View ->

                dialog = TaskDialogFragment()
                val fragmentManager: FragmentManager = (itemView.context as FragmentActivity).supportFragmentManager
                dialog.show(fragmentManager, "tag")

                dao = AppDatabase.getInstance(itemView.context).taskDao()
                dao.delete(itemTask)
                // I want to show the dialog fragment when I click here
//                dialogFragment = TaskDialogFragment()
//                dialogFragment.show(childFragmentManager, "taskDialog")
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
        holder.taskDialogTitle.text = titles[position]
        holder.itemDue.text = deadlines[position]
        holder.itemTask = tasks[position]
    }
}
