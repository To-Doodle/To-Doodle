package ca.uwaterloo.cs.todoodle

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.SoundEffectConstants
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import ca.uwaterloo.cs.todoodle.data.AppDatabase
import ca.uwaterloo.cs.todoodle.data.Task
import ca.uwaterloo.cs.todoodle.data.TaskDao
import ca.uwaterloo.cs.todoodle.databinding.FragmentTodoBinding


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

    private lateinit var dao: TaskDao

    private lateinit var taskList: List<Task>

    private var titlesList = mutableListOf<String>()
    private var deadlinesList = mutableListOf<String>()
    private var categoryList = mutableListOf<String>()
    private var notesList = mutableListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        dao = AppDatabase.getInstance(requireContext()).taskDao()
        taskList = dao.getAll()
        titlesList.clear()
        deadlinesList.clear()
        categoryList.clear()
        notesList.clear()
        for (i in taskList) {
            addToList(i.taskName.toString(), i.dueDate.toString(), i.category.toString(), i.additionalNotes.toString())
        }

        _binding = FragmentTodoBinding.inflate(inflater, container, false)
        navCtr = findNavController()

        binding.fab.setOnClickListener { view ->
            findNavController().navigate(R.id.action_SecondFragment_to_CreateTaskFormFragment)
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(activity!!.applicationContext)
        binding.recyclerView.adapter = RecycleViewAdapter(titlesList, deadlinesList, taskList)
        initCreateTaskFormData()

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Restore FirstFragment by uncommenting lines below
//        binding.buttonSecond.setOnClickListener {
//            navCtr.navigate(R.id.action_SecondFragment_to_FirstFragment)
//        }
//        binding.buttonCreateTaskForm.setOnClickListener {
//            navCtr.navigate(R.id.action_SecondFragment_to_CreateTaskFormFragment)
//        }
        /*binding.coinIndicator.setOnClickListener {
            val intent = Intent(activity, AchievementsActivity::class.java)
            startActivity(intent)
        }*/
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

    private fun addToList(title:String, deadLine:String, category:String, note:String) {
        titlesList.add(title)
        deadlinesList.add(deadLine)
        categoryList.add(category)
        notesList.add(note)
    }
}