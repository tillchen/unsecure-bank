package com.example.unsecurebank

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.unsecurebank.databinding.ActivityRegisterBinding
import java.util.regex.Pattern

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var sharedPreferences: SharedPreferences

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

            if (!isValidInput(username, password, balance)) {
                Toast.makeText(this, "invalid_input", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
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

    private fun isValidInput(username: String, password: String, balance: String): Boolean {
        val usernamePattern = "^[\\w-.]{1,127}$"
        val passwordPattern = "^[\\w-.]{1,127}$"
        val balancePattern = "^(0|[1-9][0-9]*)\\.[0-9]{2}$"
        val balanceValue = balance.toDoubleOrNull()

        if (!Pattern.matches(usernamePattern, username)) {
            return false
        }

        if (!Pattern.matches(passwordPattern, password)) {
            return false
        }

        if (!Pattern.matches(balancePattern, balance) ||
            balanceValue == null ||
            balanceValue < 0.00 ||
            balanceValue > 4294967295.99
        ) {
            return false
        }

        return true
    }
}
