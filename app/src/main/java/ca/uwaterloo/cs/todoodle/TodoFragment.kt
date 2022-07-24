package ca.uwaterloo.cs.todoodle

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import ca.uwaterloo.cs.todoodle.data.model.Task
import ca.uwaterloo.cs.todoodle.data.model.TaskType
import ca.uwaterloo.cs.todoodle.databinding.FragmentTodoBinding
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentTodoBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    // Navigation controller
    private lateinit var navCtr: NavController

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

        titlesList.clear()
        deadlinesList.clear()
        categoryList.clear()
        notesList.clear()
        keysList.clear()

        val database = Firebase.database.reference

        database.child("tasks").get().addOnSuccessListener { dataSnapshot ->
            for (postSnapshot in dataSnapshot.children) {
                // TODO: handle the post
                val task = postSnapshot.getValue(Task::class.java)
                if (task!!.status == TaskType.IN_PROGRESS) {
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
                titlesList, deadlinesList, categoryList, notesList, keysList)
        }

        _binding = FragmentTodoBinding.inflate(inflater, container, false)
        navCtr = findNavController()

        binding.fab.setOnClickListener { view ->
            findNavController().navigate(R.id.action_SecondFragment_to_CreateTaskFormFragment)
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(requireActivity().applicationContext)

        initCreateTaskFormData()

        return binding.root

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * Process data from create task form if applicable. Should be replaced by data from DB in the future.
     */
    private fun initCreateTaskFormData() {
        val from = navCtr.previousBackStackEntry?.destination?.id
        if (from == R.id.CreateTaskFormFragment && arguments != null) {
            println(arguments)
        }
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