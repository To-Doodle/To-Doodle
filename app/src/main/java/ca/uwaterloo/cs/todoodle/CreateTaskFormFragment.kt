package ca.uwaterloo.cs.todoodle

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.navigation.fragment.findNavController
import ca.uwaterloo.cs.todoodle.databinding.FragmentCreateTaskFormBinding
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class CreateTaskFormFragment : Fragment(), AdapterView.OnItemSelectedListener {

    private var _binding: FragmentCreateTaskFormBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentCreateTaskFormBinding.inflate(inflater, container, false)

        initCatSpinner()

        initDateTimePicker()

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonCreateTaskFormDone.setOnClickListener {
            findNavController().navigate(R.id.action_CreateTaskFormFragment_to_SecondFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
        println(id)
    }

    override fun onNothingSelected(parent: AdapterView<*>) {
        // Another interface callback
    }

    /**
     * Initialize task category spinner
     */
    private fun initCatSpinner() {
        val spinner: Spinner = binding.createTaskFormCat
        spinner.onItemSelectedListener = this

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            context!!,
            R.array.task_cat_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }
    }

    /**
     * Initialize date and time picker
     */
    private fun initDateTimePicker() {
        // Initialize picker dialog. May lift it up to global component.
        // Allow only further date
        val today = MaterialDatePicker.todayInUtcMilliseconds()
        val calendarConstraints = CalendarConstraints.Builder()
            .setValidator(DateValidatorPointForward.now())
            .build()
        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Select date")
            .setSelection(today)
            .setCalendarConstraints(calendarConstraints)
            .build()

        val timePicker = MaterialTimePicker.Builder()
            .setTitleText("Select Time")
            .build()

        // Open dialog on click
        binding.createTaskFormDdl.setOnClickListener {
            datePicker.show(parentFragmentManager, "DATE_PICKER")
        }

        // Set value and open time dialog if input valid
        datePicker.addOnPositiveButtonClickListener {
            timePicker.show(parentFragmentManager, "TIME_PICKER")
        }

        timePicker.addOnPositiveButtonClickListener {
            println(it)
        }
    }

}