package com.example.obriotestproject.navigation

sealed class NavigationItem(val route: String, val name: String = "") {
    data object Balance : NavigationItem(Screen.BALANCE.name)
    data object Transaction : NavigationItem(Screen.TRANSACTION.name)
}