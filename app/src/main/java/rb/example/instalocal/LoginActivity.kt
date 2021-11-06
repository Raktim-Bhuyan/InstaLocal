package rb.example.instalocal

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import rb.example.instalocal.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val auth = FirebaseAuth.getInstance()
        if(auth.currentUser!=null){
            goPostsActivity()
        }

        binding.btnLogin.setOnClickListener{
            binding.btnLogin.isEnabled=false
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            if(email.isBlank()||password.isBlank()){
                Toast.makeText(this,"Email/Password cannot be empty",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            //Firebase authentication check

            auth.signInWithEmailAndPassword(email,password).addOnCompleteListener{task->
                binding.btnLogin.isEnabled =true
                if(task.isSuccessful){
                    Toast.makeText(this,"Success!",Toast.LENGTH_SHORT).show()
                    goPostsActivity()
                }else{
                    Log.i(TAG,"signInWithEmail failed",task.exception)
                    Toast.makeText(this,"Authentication failed",Toast.LENGTH_SHORT).show()

                }
            }
        }
    }

    private fun goPostsActivity() {
        Log.i(TAG,"goPostAcivity")
        val intent = Intent(this,PostsActivity::class.java)
        startActivity(intent)
        finish()
    }
}