package ca.uwaterloo.cs.todoodle.ui.pending

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import ca.uwaterloo.cs.todoodle.R
import ca.uwaterloo.cs.todoodle.RecycleViewAdapter
import ca.uwaterloo.cs.todoodle.data.AchievementRepository
import ca.uwaterloo.cs.todoodle.data.SHAREDPREF_FILENAME
import ca.uwaterloo.cs.todoodle.data.model.Task
import ca.uwaterloo.cs.todoodle.data.model.TaskType
import ca.uwaterloo.cs.todoodle.databinding.FragmentPendingBinding
import ca.uwaterloo.cs.todoodle.databinding.FragmentTodoBinding
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class PendingFragment : Fragment() {

    private var _binding: FragmentPendingBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    // Navigation controller
    private lateinit var navCtr: NavController

    private lateinit var sharedPreferences: SharedPreferences

    private var taskList = mutableListOf<Task>()

    private var titlesList = mutableListOf<String>()
    private var deadlinesList = mutableListOf<String>()
    private var categoryList = mutableListOf<String>()
    private var notesList = mutableListOf<String>()
    private var keysList = mutableListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Initialize pending ViewModel for the adapter
        val pendingViewModel = PendingViewModel(activity!!.application)
        val achievementRepository = AchievementRepository(activity!!.application, "achievements.json")

        titlesList.clear()
        deadlinesList.clear()
        categoryList.clear()
        notesList.clear()
        keysList.clear()

        sharedPreferences = requireActivity().getSharedPreferences(SHAREDPREF_FILENAME, Context.MODE_PRIVATE)
        val userKey = sharedPreferences.getString("key", "defaultKey")

        val database = Firebase.database.reference

        database.child("tasks").get().addOnSuccessListener { dataSnapshot ->
            for (postSnapshot in dataSnapshot.children) {
                // TODO: handle the post
                val task = postSnapshot.getValue(Task::class.java)
                if (task!!.userKey == userKey && task!!.status == TaskType.PENDING_APPROVAL) {
                    taskList.add(task!!)
                    addToList(
                        task!!.taskName!!,
                        task!!.deadline!!,
                        task!!.category!!,
                        task!!.notes!!,
                        postSnapshot.key!!
                    )
                }
            }
            binding.recyclerView.adapter = RecycleViewAdapter(
                titlesList,
                deadlinesList,
                categoryList,
                notesList,
                keysList,
                activity!!,
                achievementRepository,
            )
        }

        _binding = FragmentPendingBinding.inflate(inflater, container, false)
        navCtr = findNavController()

        binding.recyclerView.layoutManager =
            LinearLayoutManager(requireActivity().applicationContext)

        return binding.root

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun addToList(
        title:String,
        deadLine:String,
        category:String,
        note:String,
        key:String
    ) {
        titlesList.add(title)
        deadlinesList.add(deadLine)
        categoryList.add(category)
        notesList.add(note)
        keysList.add(key)
    }
}