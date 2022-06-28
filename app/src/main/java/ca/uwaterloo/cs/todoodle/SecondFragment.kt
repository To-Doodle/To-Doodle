package ca.uwaterloo.cs.todoodle

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import ca.uwaterloo.cs.todoodle.data.AppDatabase
import ca.uwaterloo.cs.todoodle.data.TaskDao
import ca.uwaterloo.cs.todoodle.data.UserDao
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        dao = AppDatabase.getInstance(requireContext()).taskDao()
        println(dao.getAll())

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        navCtr = findNavController()

        initCreateTaskFormData()

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Restore FirstFragment by uncommenting lines below
//        binding.buttonSecond.setOnClickListener {
//            navCtr.navigate(R.id.action_SecondFragment_to_FirstFragment)
//        }
        binding.buttonCreateTaskForm.setOnClickListener {
            navCtr.navigate(R.id.action_SecondFragment_to_CreateTaskFormFragment)
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
        }
    }
}