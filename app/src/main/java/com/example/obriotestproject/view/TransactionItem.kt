package com.example.obriotestproject.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.obriotestproject.R
import com.example.obriotestproject.model.UserTransaction
import com.example.obriotestproject.ui.theme.Green
import com.example.obriotestproject.ui.theme.Red
import com.example.obriotestproject.utils.Constants.REPLENISHMENT
import com.example.obriotestproject.utils.convertLongToDateTimeString

@Composable
fun TransactionItem(item: UserTransaction) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(Modifier.align(Alignment.CenterStart)) {
            Spacer(Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    modifier = Modifier
                        .size(20.dp),
                    painter = if (item.category == REPLENISHMENT) painterResource(id = R.drawable.dropdown_icon_up) else painterResource(id = R.drawable.dropdown_icon_down),
                    contentDescription = "",
                    tint = if (item.category == REPLENISHMENT) Green else Red
                )
                Text(
                    text = item.amount.toString(),
                    color = if (item.category == REPLENISHMENT) Green else Red
                )
            }
            Spacer(Modifier.height(4.dp))
            Text(text = item.category, color = Color.White)
        }
        Text(
            modifier = Modifier.align(Alignment.BottomEnd),
            text = convertLongToDateTimeString(item.date),
            color = Color.Gray
        )
        HorizontalDivider(
            Modifier
                .fillMaxWidth()
                .background(Color.Gray)
                .width(2.dp)
                .align(Alignment.BottomStart)
        )
    }
}