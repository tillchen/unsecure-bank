package com.example.unsecurebank

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.unsecurebank.databinding.ActivityBankingBinding

class BankingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBankingBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var username: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBankingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("BankApp", Context.MODE_PRIVATE)
        username = intent.getStringExtra("username")!!

        val depositButton = findViewById<Button>(R.id.depositButton)
        depositButton.setOnClickListener {
            val amount = binding.amountEditText.text.toString()

            if (!isValidAmount(amount)) {
                Toast.makeText(this, "Invalid input", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            var balance = sharedPreferences.getFloat("$username balance", 0f)
            with(sharedPreferences.edit()) {
                putFloat("$username balance", balance + amount.toFloat())
                apply()
            }

            balance = sharedPreferences.getFloat("$username balance", 0f)
            binding.balanceTextView.text = getString(R.string.balance_with_value, balance)
            Toast.makeText(this, "Deposit successful", Toast.LENGTH_SHORT).show()
        }

        val withdrawButton = findViewById<Button>(R.id.withdrawButton)
        withdrawButton.setOnClickListener {
            val amount = binding.amountEditText.text.toString()

            if (!isValidAmount(amount)) {
                Toast.makeText(this, "Invalid input", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            var balance = sharedPreferences.getFloat("$username balance", 0f)

            if (balance >= amount.toFloat()) {
                with(sharedPreferences.edit()) {
                    putFloat("$username balance", balance - amount.toFloat())
                    apply()
                }

                balance = sharedPreferences.getFloat("$username balance", 0f)
                binding.balanceTextView.text = getString(R.string.balance_with_value, balance)
                Toast.makeText(this, "Withdrawal successful", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Insufficient balance", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val balance = sharedPreferences.getFloat("$username balance", 0f)
        binding.balanceTextView.text = getString(R.string.balance_with_value, balance)
    }

    private fun isValidAmount(amount: String): Boolean {
        val amountRegex = "^\\d+\\.\\d{2}$".toRegex()
        return amount.matches(amountRegex) && amount.toDouble() in 0.00..4294967295.99
    }
}

