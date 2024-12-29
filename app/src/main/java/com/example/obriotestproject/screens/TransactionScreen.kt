package com.example.obriotestproject.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.obriotestproject.MainViewModel
import com.example.obriotestproject.R
import com.example.obriotestproject.ui.theme.Background
import com.example.obriotestproject.utils.Constants.ELECTRONICS
import com.example.obriotestproject.utils.Constants.GROCERIES
import com.example.obriotestproject.utils.Constants.OTHER
import com.example.obriotestproject.utils.Constants.RESTAURANT
import com.example.obriotestproject.utils.Constants.TAXI
import com.example.obriotestproject.views.Dropdown

@Composable
fun TransactionScreen(mainViewModel: MainViewModel, onBack: () -> Unit) {
    val user by mainViewModel.user.collectAsState()
    val amountBtc = remember { mutableStateOf("") }
    val category = remember { mutableStateOf(GROCERIES) }
    val list = listOf(GROCERIES, TAXI, ELECTRONICS, RESTAURANT, OTHER)

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 16.dp)) {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            value = amountBtc.value,
            label = { Text(stringResource(R.string.enter_the_amount_btc)) },
            onValueChange = { amountBtc.value = it.trim() },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Spacer(Modifier.height(16.dp))
        Dropdown(list, selected = {
            category.value = list[it]
        })
        Spacer(Modifier.height(16.dp))
        Button(
            modifier = Modifier,
            onClick = {
                if (amountBtc.value.isNotEmpty()) {
                    try {
                        mainViewModel.addTransaction(amount = amountBtc.value.toDouble(), category = category.value)
                        mainViewModel.updateUserAmount(amount = user.balance - amountBtc.value.toDouble())
                    } catch (e: Exception) {

                    }
                    onBack()
                } else {

                }
            },
            border = BorderStroke(2.dp, Color.White),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = Background)
        ) {
            Text(text = stringResource(R.string.add), color = Color.White)
        }
    }
}