package ca.uwaterloo.cs.todoodle.signup.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Patterns
import ca.uwaterloo.cs.todoodle.R
import ca.uwaterloo.cs.todoodle.data.LoginRepository
import ca.uwaterloo.cs.todoodle.data.Result

class LoginViewModelParent(private val loginRepository: LoginRepository): ViewModel() {
    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    fun login(username: String, password: String, childrenEmail: String) {
        // can be launched in a separate asynchronous job
        val result = loginRepository.signupParent(username, password, childrenEmail)

        if (result is Result.Success) {
            _loginResult.value =
                LoginResult(success = LoggedInUserView(displayName = result.data.displayName))
        } else {
            _loginResult.value = LoginResult(error = R.string.signup_failed)
        }
    }

    fun loginDataChanged(username: String, confirmpassword: String, password: String, childrenEmail: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else if(!isConfirmPasswordValid(confirmpassword, password)){
            _loginForm.value = LoginFormState(confirmpasswordError = R.string.invalid_confirmpassword)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }

    private fun isConfirmPasswordValid(confirmpassword: String, password: String): Boolean{
        return (password == confirmpassword)
    }
}
