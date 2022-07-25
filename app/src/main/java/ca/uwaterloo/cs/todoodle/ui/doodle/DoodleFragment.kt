package ca.uwaterloo.cs.todoodle.ui.doodle

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Paint
import android.graphics.Path
import android.media.MediaScannerConnection
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ca.uwaterloo.cs.todoodle.MainActivity
import ca.uwaterloo.cs.todoodle.databinding.FragmentDoodleBinding
import ca.uwaterloo.cs.todoodle.ui.doodle.CanvasView.Companion.colourList
import ca.uwaterloo.cs.todoodle.ui.doodle.CanvasView.Companion.pathList

class DoodleFragment : Fragment() {

    private lateinit var doodleViewModel: DoodleViewModel

    private var _binding: FragmentDoodleBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var canvasExporter: CanvasExporter? = null

    companion object{
        var path = Path()
        var paintBrush = Paint()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        doodleViewModel = DoodleViewModel(requireActivity().application)
//        val doodleViewModel =
//            ViewModelProvider(this).get(DoodleViewModel::class.java)

        _binding = FragmentDoodleBinding.inflate(inflater, container, false)
        val root: View = binding.root

        canvasExporter = CanvasExporter()
        val displayMetrics = resources.displayMetrics
        binding.paintView.init(displayMetrics.widthPixels, displayMetrics.heightPixels)

        binding.clear.setOnClickListener {
            pathList.clear()
            colourList.clear()
            path.reset()
        }
        binding.undo.setOnClickListener{
            binding.paintView.undo()
        }
        binding.redo.setOnClickListener{
            binding.paintView.redo()
        }
        binding.save.setOnClickListener{
            canvasExporter!!.exportType = CanvasExporter.FLAG_SAVE
            checkForPermissions()
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun checkForPermissions() {
        val permission = ContextCompat.checkSelfPermission(
            requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        if (permission == PackageManager.PERMISSION_DENIED) {
            requestStoragePermission()
        } else {
            exportImage()
        }
    }

    private fun requestStoragePermission() {
        val permission = Manifest.permission.WRITE_EXTERNAL_STORAGE
        ActivityCompat.requestPermissions(
            requireActivity(), arrayOf(permission),
            CanvasExporter.PERMISSION_WRITE_EXTERNAL_STORAGE
        )
    }

    private fun exportImage() {
        val fileName = canvasExporter!!.saveImage(binding.paintView.bitmap)
        if (fileName != null) {
            // Clear the doodle board
            binding.clear.performClick()

            // Update achievements
            val amount = canvasExporter!!.getExistingFileCount(canvasExporter!!.subDirectory)
            doodleViewModel.updateAchievements(activity!!, amount)

            // Update points display
            val points = doodleViewModel.getPoints()
            val mainActivity = activity as MainActivity
            mainActivity.initPoints(points)

            MediaScannerConnection.scanFile(
                context, arrayOf(fileName), null, null
            )
            Toast.makeText(
                context,  "Doodle saved!",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            Toast.makeText(
                context, "An error has occurred.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}