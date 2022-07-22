package ca.uwaterloo.cs.todoodle

import android.app.AlertDialog
import android.content.Intent
import android.content.Intent.getIntent
import android.graphics.Paint
import android.view.View
import androidx.recyclerview.widget.RecyclerView

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat.recreate
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import ca.uwaterloo.cs.todoodle.data.AppDatabase
import ca.uwaterloo.cs.todoodle.data.Task
import ca.uwaterloo.cs.todoodle.data.TaskDao

class RecycleViewAdapter(private val titles: List<String>, private val deadlines: List<String>, private val tasks: List<Task>) : RecyclerView.Adapter<RecycleViewAdapter.ViewHolder>() {

    private lateinit var dao: TaskDao
    private lateinit var navCtr: NavController

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemTitle: TextView = itemView.findViewById(R.id.title_view)
        val itemDue: TextView = itemView.findViewById(R.id.date_view)
        var itemTask: Task = Task(0, "", "", "", "")


        init {
            itemView.setOnClickListener { v: View ->
                dao = AppDatabase.getInstance(itemView.context).taskDao()

                val builder = AlertDialog.Builder(v.context)
                val dialogView = LayoutInflater.from(v.rootView.context).inflate(R.layout.task_dialog, null)
                builder.setView(dialogView)
                val dialog = builder.create()
                dialog.show()
                val dialogTitle: TextView = dialogView.findViewById(R.id.taskdialog_title)
                val dialogDate: TextView = dialogView.findViewById(R.id.taskdialog_date)
                val dialogCategory: TextView = dialogView.findViewById(R.id.taskdialog_category)
                val dialogNotes: TextView = dialogView.findViewById(R.id.taskdialog_notes)
                val clearButton: ImageButton = dialogView.findViewById(R.id.clearButton)
                val deleteButton: ImageButton = dialogView.findViewById(R.id.deleteButton)
                val editButton: ImageButton = dialogView.findViewById(R.id.editButton)
                dialogTitle.text = itemTask.taskName.toString()
                dialogDate.text = "Date: " + itemTask.dueDate.toString()
                dialogCategory.text = "Category: " + itemTask.category.toString()
                dialogNotes.text = "Additonal notes: " + itemTask.additionalNotes.toString()
                editButton.setOnClickListener{
//                    val activity = itemView.context as AppCompatActivity
//
//                    activity.supportFragmentManager.beginTransaction().replace(R.id.res,createTaskFormFragment).addToBackStack(null).commit()


                }
                deleteButton.setOnClickListener{
                    dao.delete(itemTask)
                    dialog.dismiss()
                    itemTitle.apply {
                        paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    }
                    itemDue.apply {
                        paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    }
                    Toast.makeText(itemView.context, "Task complete!", Toast.LENGTH_SHORT).show()
                }

                clearButton.setOnClickListener {
                    dialog.dismiss()

                }
            }
        }
    }

    fun findIndex(arr: List<Task>, item: Task): Int {
        return arr.indexOf(item)
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
        holder.itemTask = tasks[position]

    }
}
