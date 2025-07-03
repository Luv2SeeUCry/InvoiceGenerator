package com.metastack.invoiceapp

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class ResetPasswordActivity : AppCompatActivity() {

    private lateinit var resetEmailEditText: EditText
    private lateinit var resetButton: Button
    private lateinit var loginRedirectText: TextView

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset)

        resetEmailEditText = findViewById(R.id.resetEmailEditText)
        resetButton = findViewById(R.id.resetButton)
        loginRedirectText = findViewById(R.id.loginRedirectText)

        auth = FirebaseAuth.getInstance()

        resetButton.setOnClickListener {
            val email = resetEmailEditText.text.toString().trim()

            if (email.isEmpty()) {
                Toast.makeText(this, "Enter your email", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            auth.sendPasswordResetEmail(email)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Reset link sent to email", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, LoginActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(this, "Failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
        }

        loginRedirectText.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}
