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
import ca.uwaterloo.cs.todoodle.data.AchievementRepository
import ca.uwaterloo.cs.todoodle.data.AppDatabase
import ca.uwaterloo.cs.todoodle.data.TaskDao
import ca.uwaterloo.cs.todoodle.data.model.AchievementType
import ca.uwaterloo.cs.todoodle.data.model.Task
import ca.uwaterloo.cs.todoodle.data.model.TaskType
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class RecycleViewAdapter(
    private val titles: List<String>,
    private val deadlines: List<String>,
    private val categories: List<String>,
    private val notes: List<String>,
    private val keys: List<String>,
    private val achievementRepository: AchievementRepository? = null,
) : RecyclerView.Adapter<RecycleViewAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemTitle: TextView = itemView.findViewById(R.id.title_view)
        val itemDue: TextView = itemView.findViewById(R.id.date_view)
        var itemCategory = ""
        var itemNote = ""
        var itemKey = ""

        init {
            itemView.setOnClickListener { v: View ->

                val builder = AlertDialog.Builder(v.context)
                val dialogView =
                    LayoutInflater.from(v.rootView.context).inflate(R.layout.task_dialog, null)
                builder.setView(dialogView)
                val dialog = builder.create()
                dialog.show()
                val dialogTitle: TextView = dialogView.findViewById(R.id.taskdialog_title)
                val dialogDate: TextView = dialogView.findViewById(R.id.taskdialog_date)
                val dialogCategory: TextView = dialogView.findViewById(R.id.taskdialog_category)
                val dialogNotes: TextView = dialogView.findViewById(R.id.taskdialog_notes)
                val clearButton: ImageButton = dialogView.findViewById(R.id.clearButton)
                val editButton: ImageButton = dialogView.findViewById(R.id.editButton)
                dialogTitle.text = itemTitle.text.toString()
                dialogDate.text = itemDue.text.toString()
                dialogCategory.text = itemCategory
                dialogNotes.text = itemNote
                editButton.setOnClickListener {
//                    val activity = itemView.context as AppCompatActivity
//
//                    activity.supportFragmentManager.beginTransaction().replace(R.id.res,createTaskFormFragment).addToBackStack(null).commit()


                }

                clearButton.setOnClickListener {
                    dialog.dismiss()

                }

                // Siyuan: doneButton only shows in approval page with supervisor logged in
                val doneButton: ImageButton = dialogView.findViewById(R.id.doneButton)
                if (achievementRepository != null) {
                    doneButton.setOnClickListener {
                        // Set task to approved
                        updateTaskStatus(itemKey, TaskType.COMPLETED)
                        dialog.dismiss()
                        itemTitle.apply {
                            paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                        }
                        itemDue.apply {
                            paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                        }
                        Toast.makeText(
                            itemView.context,
                            "Task Completed!",
                            Toast.LENGTH_SHORT
                        ).show()

                        // Update achievement
                        achievementRepository.checkAndUpdateAchievements(AchievementType.DUE)
                    }
                } else {
                    doneButton.visibility = View.GONE
                }

                // Siyuan: deleteButton only shows in task page
                val deleteButton: ImageButton = dialogView.findViewById(R.id.deleteButton)
                if (achievementRepository == null) {
                    deleteButton.setOnClickListener {
                        updateTaskStatus(itemKey, TaskType.PENDING_APPROVAL)
                        dialog.dismiss()
                        itemTitle.apply {
                            paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                        }
                        itemDue.apply {
                            paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                        }
                        Toast.makeText(
                            itemView.context,
                            "Waiting for Approval!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    deleteButton.visibility = View.GONE
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
        holder.itemCategory = categories[position]
        holder.itemNote = notes[position]
        holder.itemKey = keys[position]
    }

    /**
     * Update task status
     * @param itemKey DB item key
     * @param status Task status enum
     */
    private fun updateTaskStatus(itemKey: String, status: TaskType) {
        val database = Firebase.database.reference
        database.child("tasks").child(itemKey).child("status")
            .setValue(status)
    }
}
