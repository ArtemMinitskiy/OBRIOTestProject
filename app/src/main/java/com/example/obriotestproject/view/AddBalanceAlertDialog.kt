package com.example.obriotestproject.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.obriotestproject.R
import com.example.obriotestproject.ui.theme.Background

@Composable
fun AddBalanceAlertDialog(
    add: (Double) -> Unit,
    closeDialog: () -> Unit
) {
    val amountBtc = remember { mutableStateOf("") }

    Dialog(onDismissRequest = { closeDialog() }) {
        Box(
            Modifier
                .fillMaxWidth()
                .height(161.dp)
                .border(4.dp, Color.White, RoundedCornerShape(16.dp))
                .background(Background, RoundedCornerShape(16.dp))
        ) {
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
                    .padding(horizontal = 16.dp),
                value = amountBtc.value,
                label = { Text(stringResource(R.string.enter_the_amount_btc)) },
                onValueChange = { amountBtc.value = it.trim() },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            Spacer(Modifier.height(16.dp))

            Row(
                Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 16.dp, bottom = 16.dp)) {
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    modifier = Modifier,
                    onClick = {
                        try {
                            add(amountBtc.value.toDouble())
                        } catch (e: Exception) {

                        }
                    },
                    border = BorderStroke(2.dp, Color.White),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Background)
                ) {
                    Text(text = stringResource(R.string.add), color = Color.White)
                }
                Spacer(modifier = Modifier.width(16.dp))
                Button(
                    modifier = Modifier,
                    onClick = {
                        closeDialog()
                    },
                    border = BorderStroke(2.dp, Color.White),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Background)
                ) {
                    Text(text = stringResource(R.string.cancel), color = Color.White)
                }
                Spacer(modifier = Modifier.width(16.dp))
            }
        }
    }
}