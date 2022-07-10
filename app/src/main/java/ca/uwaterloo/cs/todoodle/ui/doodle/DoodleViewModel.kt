package ca.uwaterloo.cs.todoodle.ui.doodle

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DoodleViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Draw a doodle!"
    }
    val text: LiveData<String> = _text
}