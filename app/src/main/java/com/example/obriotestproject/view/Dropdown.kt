package com.example.obriotestproject.views

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.example.obriotestproject.R
import com.example.obriotestproject.ui.theme.BackgroundDrop

@Composable
fun Dropdown(
    list: List<String>,
    selected: (Int) -> Unit
) {
    val localDensity = LocalDensity.current
    var menuWidthHeightDp by remember { mutableStateOf(0.dp) }
    var showMenu by remember { mutableStateOf(false) }
    var category by remember { mutableStateOf(list[0]) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { showMenu = !showMenu },
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .height(40.dp)
                .border(2.dp, Color.White, RoundedCornerShape(12.dp))
                .background(BackgroundDrop, RoundedCornerShape(12.dp))
                .clickable {
                    showMenu = !showMenu
                }
                .onGloballyPositioned { coordinates ->
                    menuWidthHeightDp = with(localDensity) { coordinates.size.width.toDp() }
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.padding(start = 16.dp),
                text = category,
                color = Color.White
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                modifier = Modifier
                    .padding(end = 16.dp)
                    .size(16.dp),
                painter = if (showMenu) painterResource(id = R.drawable.dropdown_icon_up) else painterResource(id = R.drawable.dropdown_icon_down),
                contentDescription = "",
            )
        }

        if (showMenu) {
            Popup(
                alignment = Alignment.TopCenter,
                onDismissRequest = { showMenu = false },
                properties = PopupProperties(focusable = true)
            ) {
                Surface(
                    modifier = Modifier
                        .width(menuWidthHeightDp)
                        .padding(top = 48.dp),
                    shape = MaterialTheme.shapes.medium,
                    color = MaterialTheme.colorScheme.surface,
                    shadowElevation = 4.dp
                ) {
                    ArchiveMenu(
                        list,
                        selectedFormat = {
                            showMenu = false
                            category = list[it]
                            selected(it)
                        }
                    )
                }
            }
        }
    }
}