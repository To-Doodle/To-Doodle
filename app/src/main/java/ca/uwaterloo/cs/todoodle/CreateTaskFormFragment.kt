package ca.uwaterloo.cs.todoodle

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import ca.uwaterloo.cs.todoodle.databinding.FragmentCreateTaskFormBinding
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import java.text.SimpleDateFormat

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class CreateTaskFormFragment : Fragment(), AdapterView.OnItemSelectedListener {

    private var _binding: FragmentCreateTaskFormBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    // Navigation controller
    private lateinit var navCtr: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentCreateTaskFormBinding.inflate(inflater, container, false)
        navCtr = findNavController()

        initSpinner()

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Calendar is hidden at first render
        initDateTimePicker()

        // Pass form data to the previous screen for display purpose. Will store into DB in the future.
        binding.buttonCreateTaskFormDone.setOnClickListener {
            val formData = validatedForm()

            if (formData != null)
                navCtr.navigate(
                    R.id.action_CreateTaskFormFragment_to_SecondFragment,
                    formData,
                )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * Required for dropdown adapter
     */
    override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
    }

    /**
     * Required for dropdown adapter
     */
    override fun onNothingSelected(parent: AdapterView<*>) {
        // Another interface callback
    }

    /**
     * Initialize task category spinner
     */
    private fun initSpinner() {
        val config = hashMapOf<Spinner, Int>(
            binding.createTaskFormCat to R.array.task_cat_array,
            binding.createTaskFormGoal to R.array.task_goal_array,
        )

        for ((spinner, resId) in config) {
            spinner.onItemSelectedListener = this

            // Create an ArrayAdapter using the string array and a default spinner layout
            ArrayAdapter.createFromResource(
                context!!,
                resId,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                // Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                // Apply the adapter to the spinner
                spinner.adapter = adapter
            }
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
        // Notice that all date and time are in UTC
        datePicker.addOnPositiveButtonClickListener {
            // Pass date to time picker for atomicity
            // Timezone depends on device settings
            val args = bundleOf(
                "date" to it,
            )
            timePicker.arguments = args

            timePicker.show(parentFragmentManager, "TIME_PICKER")
        }

        // Set date and time input value
        timePicker.addOnPositiveButtonClickListener {
            // Date passed from date picker
            val args = timePicker.arguments!!
            val date = args.getLong("date")

            // Set deadline input
            val hour = timePicker.hour
            val minute = timePicker.minute
            var input = SimpleDateFormat("yyyy-MM-dd").format(date)
            input += " "
            if (hour < 10) input += 0
            input += hour
            input += ":"
            if (minute < 10) input += 0
            input += timePicker.minute

            binding.createTaskFormDdl.setText(input)
        }
    }

    /**
     * Validate form input.
     * @return validated form data
     */
    private fun validatedForm(): Bundle? {

        // Very basic validation. May use third-party lib for this.
        val requiredText = "Required"
        if (binding.createTaskFormName.text.isEmpty()) {
            binding.createTaskFormName.error = requiredText
            return null
        }
        if (binding.createTaskFormDdl.text.isEmpty()) {
            binding.createTaskFormDdl.error = requiredText
            return null
        }

        return bundleOf(
            "name" to binding.createTaskFormName.text,
            "cat" to binding.createTaskFormCat.selectedItem,
            "ddl" to binding.createTaskFormDdl.text,
            "duration" to binding.createTaskFormDuration.text,
            "goal" to binding.createTaskFormGoal.selectedItem,
            "note" to binding.createTaskFormNote.text,
        )
    }
}