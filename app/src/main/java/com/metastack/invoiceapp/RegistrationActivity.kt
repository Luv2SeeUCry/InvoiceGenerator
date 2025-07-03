package com.metastack.invoiceapp

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

class RegistrationActivity : AppCompatActivity() {

    private lateinit var fullName: EditText
    private lateinit var mobile: EditText
    private lateinit var businessName: EditText
    private lateinit var gst: EditText
    private lateinit var pan: EditText
    private lateinit var address: EditText
    private lateinit var registerButton: Button

    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        fullName = findViewById(R.id.fullNameEditText)
        mobile = findViewById(R.id.mobileEditText)
        businessName = findViewById(R.id.businessEditText)
        gst = findViewById(R.id.gstEditText)
        pan = findViewById(R.id.panEditText)
        address = findViewById(R.id.addressEditText)
        registerButton = findViewById(R.id.registerButton)

        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        registerButton.setOnClickListener {
            val name = fullName.text.toString().trim()
            val phone = mobile.text.toString().trim()
            val business = businessName.text.toString().trim()
            val gstNo = gst.text.toString().trim()
            val panNo = pan.text.toString().trim()
            val addr = address.text.toString().trim()
            val email = auth.currentUser?.email ?: ""

            if (name.isEmpty() || phone.isEmpty() || business.isEmpty()) {
                Toast.makeText(this, "Please fill in all required fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val userId = auth.currentUser?.uid ?: return@setOnClickListener
            val profile = hashMapOf(
                "uid" to userId,
                "username" to name,
                "email" to email,
                "mobileNumber" to phone,
                "businessName" to business,
                "gstNumber" to gstNo,
                "panNumber" to panNo,
                "address" to addr,
                "createdAt" to Date()
            )

            firestore.collection("users").document(userId)
                .set(profile)
                .addOnSuccessListener {
                    Toast.makeText(this, "Profile saved", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Failed: ${it.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }
}
