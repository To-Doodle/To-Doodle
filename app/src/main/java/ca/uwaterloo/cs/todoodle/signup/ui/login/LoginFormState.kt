package ca.uwaterloo.cs.todoodle.signup.ui.login

/**
 * Data validation state of the login form.
 */
data class LoginFormState(
    val usernameError: Int? = null,
    val passwordError: Int? = null,
    val confirmpasswordError: Int? = null,
    val isDataValid: Boolean = false
)