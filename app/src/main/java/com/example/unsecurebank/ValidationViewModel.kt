package com.example.unsecurebank

import androidx.lifecycle.ViewModel

class ValidationViewModel : ViewModel() {

    fun isValidUsernamePassword(input: String): Boolean {
        val usernamePasswordRegex = "[_\\-.0-9a-z]{1,127}".toRegex()
        return input.matches(usernamePasswordRegex)
    }

    fun isValidBalance(input: String): Boolean {
        val balanceRegex = "(0|[1-9][0-9]*).[0-9]{2}".toRegex()
        return input.matches(balanceRegex) && input.toDouble() in 0.00..4294967295.99
    }
}
