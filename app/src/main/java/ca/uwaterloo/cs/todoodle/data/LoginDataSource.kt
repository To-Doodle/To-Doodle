package ca.uwaterloo.cs.todoodle.data

import android.content.ContentValues.TAG
import android.os.Handler
import android.util.Log
import ca.uwaterloo.cs.todoodle.data.model.LoggedInUser
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.Query
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.delay
import java.io.IOException
import java.lang.Exception
import java.util.*
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
            var loginFail: Boolean = false
            var User = LoggedInUser("", "")
            /*val data1 = hashMapOf(
                "name" to "San Francisco",
                "state" to "CA",
                "country" to "USA",
                "capital" to false,
                "population" to 860000,
                "regions" to listOf("west_coast", "norcal")
            )
Log.d("Lets see", loginFail.toString())
            db.collection("users").document("toremove").set(data1)*/
            Log.d("Lets see", loginFail.toString())
            var Qsnap : Task<QuerySnapshot> = db.collection("users").whereEqualTo("useremail", useremail).get()
            //Tasks.await(Qsnap)
            while(Qsnap.isComplete() != true ){
                //busy wait my way through this
                //Timer().schedule(TimerTask()::class.java, 2000)
                //Handler().postDelayed({
                //    TODO("Do something")
                //}, 100)
            }

            if(Qsnap.isSuccessful != true){
                return Result.Error(Exception("Failed"))
            }
            var document : QuerySnapshot = Qsnap.getResult()
            if (document.isEmpty) {
                loginFail = true

            } else {
                Log.d("Lets see", password)
                Log.d(
                    "LEts sss",
                    (password == document.first().get("password").toString()).toString()
                )
                if (password != document.first().get("password").toString()) {
                    // password is not correct
                    loginFail = true
                } else {
                    User = LoggedInUser(userName, useremail)
                }
                Log.d("Lets see", loginFail.toString())
            }
            /*db.collection("users").whereEqualTo("useremail", useremail).get().addOnSuccessListener { document ->
                    //val user = result.toString()
                    //if(document != null){
                    //Log.d("the fuck00000", document.docs[0])
                    //} else {
                    //Log.d("the fuck00000", document.data())
                    //}
                    /*if (document is QuerySnapshot) {
                        Log.d("lets see", document.isEmpty().toString())
                    }
                    Log.d("hmm", document.toString())
                    for (doc in document) {
                        Log.d("no way", doc.get("password").toString())

                    }*/

                    if (document.isEmpty) {
                        loginFail = true

                    } else {
                        Log.d("Lets see", password)
                        Log.d(
                            "LEts sss",
                            (password == document.first().get("password").toString()).toString()
                        )
                        if (password != document.first().get("password").toString()) {
                            // password is not correct
                            loginFail = true
                        } else {
                            User = LoggedInUser(userName, useremail)
                        }
                        Log.d("Lets see", loginFail.toString())
                    }
                    //print(user)

                }*/

            Log.d("Lets see", loginFail.toString())
            Log.d("hmmm", "hello")
            //val fakeUser = LoggedInUser(useremail, userName)

            if(loginFail){

                return Result.Error(Exception("Failed"))
            }
            return Result.Success(User)
        } catch (e: Throwable) {
            return Result.Error(IOException("Error logging in", e))
        }
    }

    fun signup(useremail: String, password: String): Result<LoggedInUser>{
        try{
            val userName = useremail.split("@")[0]
            var User = LoggedInUser("", "")

            var Qsnap : Task<QuerySnapshot> = db.collection("users").whereEqualTo("useremail", useremail).get()
            //Tasks.await(Qsnap)
            while(Qsnap.isComplete() != true ){
                //busy wait my way through this
                //Timer().schedule(TimerTask()::class.java, 2000)
                //Handler().postDelayed({
                //    TODO("Do something")
                //}, 100)
            }

            if(Qsnap.isSuccessful != true){
                return Result.Error(Exception("Failed"))
            }
            var document : QuerySnapshot = Qsnap.getResult()

            if(document.isEmpty()){
                val user = hashMapOf(
                    "useremail" to useremail,
                    "password" to password
                )
                db.collection("users").document(userName).set(user).addOnSuccessListener{ documentinside ->
                    User = LoggedInUser(useremail, userName)
                }
            } else {
                return Result.Error(Exception("The account already exists"))
            }
            /*db.collection("users").whereEqualTo("useremail", useremail).get().addOnSuccessListener { document ->
                //val user = result.toString()
                //if(document != null){
                //Log.d("the fuck00000", document.docs[0])
                //} else {
                //Log.d("the fuck00000", document.data())
                //}



            }*/


            return Result.Success(User)

        } catch (e: Throwable) {
            return Result.Error(IOException("An Error occurred", e))
        }
    }

    fun loginParent(useremail: String, password: String): Result<LoggedInUser> {
        try {
            // TODO: handle loggedInUser authentication
            // split the useremail to the username
            val userName = useremail.split("@")[0]
            var loginFail: Boolean = false
            var User = LoggedInUser("", "")
            /*val data1 = hashMapOf(
                "name" to "San Francisco",
                "state" to "CA",
                "country" to "USA",
                "capital" to false,
                "population" to 860000,
                "regions" to listOf("west_coast", "norcal")
            )
Log.d("Lets see", loginFail.toString())
            db.collection("users").document("toremove").set(data1)*/
            Log.d("Lets see", loginFail.toString())
            var Qsnap : Task<QuerySnapshot> = db.collection("parents").whereEqualTo("useremail", useremail).get()
            //Tasks.await(Qsnap)
            while(Qsnap.isComplete() != true  ){
                //busy wait my way through this

            }
            if(Qsnap.isSuccessful() != true){
                return Result.Error(Exception("Failed"))
            }
            var document : QuerySnapshot = Qsnap.getResult()

            if (document.isEmpty) {
                loginFail = true

            } else {
                Log.d("Lets see", password)
                Log.d(
                    "LEts sss",
                    (password == document.first().get("password").toString()).toString()
                )
                if (password != document.first().get("password").toString()) {
                    // password is not correct
                    loginFail = true
                } else {
                    User = LoggedInUser(userName, useremail)
                }
                Log.d("Lets see", loginFail.toString())
            }

            /*Tasks.await(db.collection("parents").whereEqualTo("useremail", useremail).get().addOnSuccessListener { document ->
                //val user = result.toString()
                //if(document != null){
                //Log.d("the fuck00000", document.docs[0])
                //} else {
                //Log.d("the fuck00000", document.data())
                //}
                /*if (document is QuerySnapshot) {
                    Log.d("lets see", document.isEmpty().toString())
                }
                Log.d("hmm", document.toString())
                for (doc in document) {
                    Log.d("no way", doc.get("password").toString())

                }*/

                if (document.isEmpty) {
                    loginFail = true

                } else {
                    Log.d("Lets see", password)
                    Log.d(
                        "LEts sss",
                        (password == document.first().get("password").toString()).toString()
                    )
                    if (password != document.first().get("password").toString()) {
                        // password is not correct
                        loginFail = true
                    } else {
                        User = LoggedInUser(userName, useremail)
                    }
                    Log.d("Lets see", loginFail.toString())
                }
                //print(user)

            })*/

            Log.d("Lets see", loginFail.toString())
            Log.d("hmmm", "hello")
            //val fakeUser = LoggedInUser(useremail, userName)

            if(loginFail){

                return Result.Error(Exception("Failed"))
            }
            return Result.Success(User)
        } catch (e: Throwable) {
            return Result.Error(IOException("Error logging in", e))
        }
    }

    fun signupParent(useremail: String, password: String, children:String): Result<LoggedInUser>{
        try{
            val userName = useremail.split("@")[0]
            var User = LoggedInUser("", "")

            val kids : List<String> = children.split(";")

            var Qsnap : Task<QuerySnapshot> = db.collection("parents").whereEqualTo("useremail", useremail).get()
            //Tasks.await(Qsnap)
            while(Qsnap.isComplete() != true  ){
                //busy wait my way through this

            }
            if(Qsnap.isSuccessful() != true){
                return Result.Error(Exception("Failed"))
            }
            var document : QuerySnapshot = Qsnap.getResult()

            if(document.isEmpty()){
                val user = hashMapOf(
                    "useremail" to useremail,
                    "password" to password,
                    "children" to kids
                )
                db.collection("parents").document(userName).set(user).addOnSuccessListener{ documentinside ->
                    User = LoggedInUser(useremail, userName)
                }
            } else {
                return Result.Error(Exception("Failed"))
            }

            /*db.collection("parents").whereEqualTo("useremail", useremail).get().addOnSuccessListener { document ->
                //val user = result.toString()
                //if(document != null){
                //Log.d("the fuck00000", document.docs[0])
                //} else {
                //Log.d("the fuck00000", document.data())
                //}

                if(document.isEmpty()){
                    val user = hashMapOf(
                        "useremail" to useremail,
                        "password" to password,
                        "children" to kids
                    )
                    db.collection("parents").document(userName).set(user).addOnSuccessListener{ documentinside ->
                        User = LoggedInUser(useremail, userName)
                    }
                }

            }*/


            return Result.Success(User)

        } catch (e: Throwable) {
            return Result.Error(IOException("An Error occurred", e))
        }
    }

    fun logout() {
        // TODO: revoke authentication
    }
}