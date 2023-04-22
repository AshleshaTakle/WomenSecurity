package com.example.women_security_app

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth

class login : AppCompatActivity() {

    lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {



        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val usernamelogin = findViewById<EditText>(R.id.userloginname)
        val userpasswordlogin = findViewById<EditText>(R.id.userloginpassword)
        val btnlogin = findViewById<Button>(R.id.btn_login)
        auth = FirebaseAuth.getInstance()

        //goto register
        val btnre = findViewById<Button>(R.id.btn_register)
        btnre.setOnClickListener {
            val intent = Intent(applicationContext,register::class.java)
            startActivity(intent)


            //skip code





        }

        val txtforgot = findViewById<TextView>(R.id.txtforgot)

        txtforgot.setOnClickListener(){
            
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Forgot Password")
                val view = layoutInflater.inflate(R.layout.dailog_forgot,null)
                val username = view.findViewById<EditText>(R.id.ed_forgot)
                builder.setView(view)
                builder.setPositiveButton("Reset", DialogInterface.OnClickListener { _, _ ->
                    forgotpassword(username)
                })
                builder.setNegativeButton("Close", DialogInterface.OnClickListener { _, _ ->  })
                builder.show()
            
        }

        //login
    btnlogin.setOnClickListener {

        if(usernamelogin.text.isEmpty() ) {
            usernamelogin.setError("Enter Email Id")
            return@setOnClickListener
        }
        else if (userpasswordlogin.text.isEmpty()){
            userpasswordlogin.setError("Enter Password")
            return@setOnClickListener
        }
        auth.signInWithEmailAndPassword(usernamelogin.text.toString(),userpasswordlogin.text.toString())
            .addOnCompleteListener {
                if(it.isSuccessful)
                {
                    Toast.makeText(applicationContext,"Login successfull", Toast.LENGTH_LONG).show()
                    val intent = Intent(applicationContext, MainActivity::class.java)
                    startActivity(intent)
                }
                else
                {
                    Toast.makeText(applicationContext,"Login Failed", Toast.LENGTH_LONG).show()
                }
            }


    }






    }

    private fun forgotpassword(username: EditText) {

        auth.sendPasswordResetEmail(username!!.text.toString())
            .addOnCompleteListener {
                if(it.isSuccessful)
                {
                    Toast.makeText(applicationContext,"Email Sent", Toast.LENGTH_LONG).show()
                }
            }

    }
}