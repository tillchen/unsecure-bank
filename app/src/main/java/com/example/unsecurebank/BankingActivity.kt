package com.example.unsecurebank

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.unsecurebank.databinding.ActivityBankingBinding
import com.google.android.material.snackbar.Snackbar

class BankingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBankingBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var username: String
    private val validationViewModel: ValidationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBankingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPreferences = getSharedPreferences("BankApp", Context.MODE_PRIVATE)
        username = intent.getStringExtra("username")!!
        setListeners()
    }

    override fun onResume() {
        super.onResume()
        val balance = sharedPreferences.getFloat("$username balance", 0f)
        binding.balanceTextView.text = getString(R.string.balance_with_value, balance)
    }

    private fun setListeners() {
        binding.depositButton.setOnClickListener {
            val amount = binding.amountEditText.text.toString()
            if (!validationViewModel.isValidBalance(amount)) {
                Snackbar.make(binding.root, R.string.invalid_input, Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            var balance = sharedPreferences.getFloat("$username balance", 0f)
            with(sharedPreferences.edit()) {
                putFloat("$username balance", balance + amount.toFloat())
                apply()
            }
            balance = sharedPreferences.getFloat("$username balance", 0f)
            binding.balanceTextView.text = getString(R.string.balance_with_value, balance)
            Snackbar.make(binding.root, R.string.deposit_success, Snackbar.LENGTH_SHORT).show()
        }

        binding.withdrawButton.setOnClickListener {
            val amount = binding.amountEditText.text.toString()
            if (!validationViewModel.isValidBalance(amount)) {
                Snackbar.make(binding.root, R.string.invalid_input, Snackbar.LENGTH_SHORT).show()
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
                Snackbar.make(binding.root, R.string.withdraw_success, Snackbar.LENGTH_SHORT).show()
            } else {
                Snackbar.make(binding.root, R.string.insufficient_balance, Snackbar.LENGTH_SHORT).show()
            }
        }
    }
}
