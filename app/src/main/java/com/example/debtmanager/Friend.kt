package com.example.debtmanager;

data class Friend(val name: String, val givenMoney: Double, val takenMoney: Double) {
    val debt: Double
        get() = givenMoney - takenMoney
}