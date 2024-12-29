package com.example.obriotestproject

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.obriotestproject.model.User
import com.example.obriotestproject.navigation.NavigationItem
import com.example.obriotestproject.screens.BalanceScreen
import com.example.obriotestproject.screens.TransactionScreen
import com.example.obriotestproject.ui.theme.Background
import com.example.obriotestproject.ui.theme.OBRIOTestProjectTheme
import com.example.obriotestproject.utils.Constants.USER_ID
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val mainViewModel: MainViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as App).appComponent.inject(this)

        setContent {
            LaunchedEffect(mainViewModel) {
                Log.i("mLogUser", "doesUserExist ${mainViewModel.doesUserExist()}")
                if (!mainViewModel.doesUserExist()) {
                    mainViewModel.addUser(
                        User(
                            userId = USER_ID,
                            balance = 0.0,
                            lastSeen = System.currentTimeMillis(),
                            lastExchangeRate = 0.0
                        )
                    )
                    mainViewModel.getBtcToUsd()
                } else {
                    mainViewModel.getUser()
                    mainViewModel.updateLastSeen()
                }
            }
            val navController = rememberNavController()
            OBRIOTestProjectTheme {
                Box(
                    Modifier
                        .fillMaxSize()
                        .background(Background), contentAlignment = Alignment.Center
                ) {
                    NavHost(navController = navController,
                        startDestination = NavigationItem.Balance.route,
                    ) {
                        composable(NavigationItem.Balance.route) {
                            BalanceScreen(mainViewModel,
                                onTransaction = {
                                    navController.navigate(NavigationItem.Transaction.route)
                                })
                        }
                        composable(NavigationItem.Transaction.route) {
                            TransactionScreen(mainViewModel) {
                                navController.popBackStack()
                            }
                        }
                    }
                }
            }
        }
    }
}