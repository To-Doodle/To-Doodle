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
import ca.uwaterloo.cs.todoodle.databinding.FragmentSecondBinding


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    // Navigation controller
    private lateinit var navCtr: NavController

    private lateinit var dao: TaskDao

    private lateinit var taskList: List<Task>

    private var titlesList = mutableListOf<String>()
    private var deadlinesList = mutableListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        dao = AppDatabase.getInstance(requireContext()).taskDao()
//        println(dao.getAll().get(0).taskName.toString())
        taskList = dao.getAll()
        for (i in taskList) {
            addToList(i.taskName.toString(), i.dueDate.toString())
        }


//        postToList()

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        navCtr = findNavController()


        binding.recyclerView.layoutManager = LinearLayoutManager(activity!!.applicationContext)
        binding.recyclerView.adapter = RecycleViewAdapter(titlesList, deadlinesList)
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

        binding.coinIndicator.setOnClickListener {
            val intent = Intent(activity, RewardsActivity::class.java)
            startActivity(intent)
        }
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
            addToList(arguments!!["name"].toString(), arguments!!["ddl"].toString())
        }
    }

    private fun addToList(title:String, deadLine:String) {
        titlesList.add(title)
        deadlinesList.add(deadLine)
    }

    private fun postToList() {
        for (i in 1..25){
            addToList("Title $i", "Deadlines $i")
        }
    }
}