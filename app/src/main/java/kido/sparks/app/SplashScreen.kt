package kido.sparks.app

import android.os.Bundle
import android.util.Log
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class SplashScreen : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    val db = Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.splash_screen)
        auth = Firebase.auth
//        auth.createUserWithEmailAndPassword("admin@gmail.com", "admin123")
//                .addOnCompleteListener(this) { task ->
//                    if (task.isSuccessful) {
//                        // Sign in success, update UI with the signed-in user's information
//                        Log.e("TAG", "createUserWithEmail:success")
//                        val user = auth.currentUser
//                       // updateUI(user)
//                    } else {
//                        // If sign in fails, display a message to the user.
//                        Log.e("TAG", "createUserWithEmail:failure", task.exception)
//                        Toast.makeText(baseContext, "Authentication failed.",
//                                Toast.LENGTH_SHORT).show()
//                      //  updateUI(null)
//                    }
//
//                    // ...
//                }

        auth.signInWithEmailAndPassword("admin@gmail.com", "admin123").addOnCompleteListener(this){ task->
            if (task.isSuccessful){
                Log.e("TAG", "createUserWithEmail:success")
                val user = hashMapOf(
                    "first" to "Ada",
                    "last" to "Lovelace",
                    "born" to 1815
                )

// Add a new document with a generated ID
                db.collection("users")
                        .add(user)
                        .addOnSuccessListener { documentReference ->
                            Log.d("TAG", "DocumentSnapshot added with ID: ${documentReference.id}")
                        }
                        .addOnFailureListener { e ->
                            Log.w("TAG", "Error adding document", e)
                        }
            }
            else
            {
                Log.e("TAG", "createUserWithEmail:not")
            }
        }
    }
}