package ca.uwaterloo.cs.todoodle.data

import android.content.ContentValues.TAG
import android.util.Log
import ca.uwaterloo.cs.todoodle.data.model.LoggedInUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.io.IOException
import kotlin.reflect.typeOf

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {
    private val db: FirebaseFirestore = Firebase.firestore

    fun login(useremail: String, password: String): Result<LoggedInUser> {
        try {
            // TODO: handle loggedInUser authentication
            // split the useremail to the username
            val userName = useremail.split("@")[0]
            /*val data1 = hashMapOf(
                "name" to "San Francisco",
                "state" to "CA",
                "country" to "USA",
                "capital" to false,
                "population" to 860000,
                "regions" to listOf("west_coast", "norcal")
            )

            db.collection("users").document("toremove").set(data1)*/
                db.collection("users").whereEqualTo("useremail", useremail).get().addOnSuccessListener { document ->
                    //val user = result.toString()
                    //if(document != null){
                        //Log.d("the fuck00000", document.docs[0])
                    //} else {
                        //Log.d("the fuck00000", document.data())
                    //}
                    if(document is QuerySnapshot){
                        Log.d("lets see", document.isEmpty().toString())
                    }
                    Log.d("hmm", document.toString())
                    for (doc in document) {
                        Log.d("no way", doc.get("password").toString())

                    }
                    //print(user)

                }
                .addOnFailureListener { exception ->
                    Log.d(TAG, "Error getting documents: ", exception)
                    // if failed to find user in DB create the user
                    val user = hashMapOf(
                        "useremail" to userName,
                        "password" to password
                    )
                    db.collection("users").document(userName).set(user).addOnSuccessListener { result ->
                        Log.d("the fuck1", result.toString())

                    }.addOnFailureListener { result ->
                        Log.d("fuck", result.toString())

                    }
                }

            Log.d("the fuck2", "helllo")

            val fakeUser = LoggedInUser(useremail, userName)
            return Result.Success(fakeUser)
        } catch (e: Throwable) {
            return Result.Error(IOException("Error logging in", e))
        }
    }

    fun logout() {
        // TODO: revoke authentication
    }
}