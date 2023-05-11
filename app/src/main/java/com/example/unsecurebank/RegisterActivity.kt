package com.example.unsecurebank

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.unsecurebank.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var sharedPreferences: SharedPreferences
    private val validationViewModel: ValidationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("BankApp", Context.MODE_PRIVATE)

        val registerButton = findViewById<Button>(R.id.registerButton)
        registerButton.setOnClickListener {
            val username = binding.usernameEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            val balance = binding.balanceEditText.text.toString()

            validationViewModel.apply {
                if (!isValidUsernamePassword(username) || !isValidUsernamePassword(password) || !isValidBalance(balance)) {
                    Toast.makeText(this@RegisterActivity, "invalid_input", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
            }

            with(sharedPreferences.edit()) {
                putString("$username password", password)
                putFloat("$username balance", balance.toFloat())
                apply()
            }

            Toast.makeText(this, "Account created successfully", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
