package com.example.debtmanager;

data class Friend(val image: Int, val name: String, val givenMoney: Int, val takenMoney: Int) {
    val debt: Int
        get() = givenMoney - takenMoney
}