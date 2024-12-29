package com.example.obriotestproject.views

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.obriotestproject.ui.theme.BackgroundDrop

@Composable
fun ArchiveMenu(
    formatList: List<String>,
    selectedFormat: (Int) -> Unit,
) {
    Column(
        Modifier
            .wrapContentHeight()
            .width(160.dp)
            .border(2.dp, Color.White, RoundedCornerShape(12.dp))
            .background(BackgroundDrop, RoundedCornerShape(12.dp))
    ) {
        formatList.forEachIndexed { index, s ->
            Box(
                Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
                    .height(40.dp)
                    .clickable {
                        selectedFormat(index)
                    },
                Alignment.CenterStart
            ) {
                Text(text = s, color = Color.White)
            }
        }
    }
}