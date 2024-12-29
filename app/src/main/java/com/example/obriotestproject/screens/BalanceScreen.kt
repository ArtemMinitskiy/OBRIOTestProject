package com.example.obriotestproject.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.obriotestproject.MainViewModel
import com.example.obriotestproject.R
import com.example.obriotestproject.retrofit.NetworkResult
import com.example.obriotestproject.ui.theme.Background
import com.example.obriotestproject.ui.theme.Green
import com.example.obriotestproject.ui.theme.Red
import com.example.obriotestproject.utils.Constants.REPLENISHMENT
import com.example.obriotestproject.utils.isLastSeenHourAgo
import com.example.obriotestproject.view.AddBalanceAlertDialog
import com.example.obriotestproject.view.TransactionItem
import java.text.SimpleDateFormat
import java.util.Locale

@SuppressLint("DefaultLocale")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BalanceScreen(mainViewModel: MainViewModel, onTransaction: () -> Unit) {
    val isAddBalanceDialogOpen = remember { mutableStateOf(false) }
    val items by mainViewModel.items.collectAsState()
    val user by mainViewModel.user.collectAsState()
    val lazyListState = rememberLazyListState()
    val groupedItems = items.sortedByDescending { it.date }.groupBy { item ->
        SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(item?.date)
    }

    LaunchedEffect(user) {
        Log.i("mLogUser", "User: $user")
        if (user.lastSeen != 0L) {
            if (isLastSeenHourAgo(user.lastSeen)) mainViewModel.getBtcToUsd()
        }
    }

    LaunchedEffect(mainViewModel) {
        mainViewModel.response.observeForever { response ->
            when (response) {
                is NetworkResult.Success -> {
                    Log.i("mLogNetworkResult", "Success ${response.data}")
                    response.data?.ask?.let { mainViewModel.updateUserLastExchangeRate(it) }
                }

                is NetworkResult.Error -> {
                    Log.e("mLogNetworkResult", "Error ${response.message}")
                }

                is NetworkResult.Loading -> {
                    Log.i("mLogNetworkResult", "Loading")
                }
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        ) {
            Text(
                text = stringResource(R.string.balance) + ": ",
                color = Color.White
            )
            Spacer(Modifier.weight(1f))
            Text(
                text = stringResource(R.string.exchange_rate) + ": ",
                color = Color.White
            )
        }
        Spacer(Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        ) {
            Text(
                text = " ${String.format("%.2f", user.balance)}",
                color = when {
                    user.balance == 0.0 -> Color.White
                    user.balance > 0.0 -> Green
                    else -> Red
                },
                style = TextStyle(
                    fontWeight = FontWeight(800),
                    fontSize = 24.sp,
                    lineHeight = 19.2.sp
                )
            )
            Spacer(Modifier.weight(1f))
            Text(
                text = "${user.lastExchangeRate}$",
                color = Color.White,
                style = TextStyle(
                    fontWeight = FontWeight(800),
                    fontSize = 24.sp,
                    lineHeight = 19.2.sp
                )
            )
        }
        Spacer(Modifier.height(8.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                modifier = Modifier,
                onClick = {
                    isAddBalanceDialogOpen.value = true
                },
                border = BorderStroke(2.dp, Color.White),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Background)
            ) {
                Text(text = stringResource(R.string.top_up_balance), color = Color.White)
            }
            Spacer(Modifier.weight(1f))
            Button(
                modifier = Modifier,
                onClick = {
                    onTransaction()
                },
                border = BorderStroke(2.dp, Color.White),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Background)
            ) {
                Text(text = stringResource(R.string.add_transaction), color = Color.White)
            }
        }

        Spacer(Modifier.height(16.dp))

        LaunchedEffect(lazyListState) {
            snapshotFlow { lazyListState.layoutInfo.visibleItemsInfo }
                .collect { visibleItems ->
                    if (visibleItems.isNotEmpty()) {
                        val lastVisibleItemIndex = visibleItems.lastOrNull()?.index ?: 0
                        if (lastVisibleItemIndex >= items.size - 10) {
                            mainViewModel.loadMoreItems()
                        }
                    }
                }
        }

        LazyColumn(modifier = Modifier.padding(horizontal = 8.dp), state = lazyListState) {
            groupedItems.forEach { (date, itemsForDate) ->
                stickyHeader {
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .background(Background)
                    ) {
                        date?.let {
                            Text(
                                text = it,
                                style = MaterialTheme.typography.titleMedium,
                                color = Color.White,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .align(Alignment.CenterStart),
                            )
                        }
                    }
                }
                items(itemsForDate) { item ->
                    TransactionItem(item)
                }
            }
        }

        if (isAddBalanceDialogOpen.value) {
            AddBalanceAlertDialog(
                add = { amount ->
                    isAddBalanceDialogOpen.value = false
                    mainViewModel.addTransaction(amount = amount, category = REPLENISHMENT)
                    mainViewModel.updateUserAmount(amount = user.balance + amount)
                },
                closeDialog = {
                    isAddBalanceDialogOpen.value = false
                }
            )
        }

        BackHandler { }
    }
}