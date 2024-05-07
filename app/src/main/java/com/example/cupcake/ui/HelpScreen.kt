package com.example.cupcake.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp

@Composable
fun SelectHelpScreen(
    options: List<String>,
    onSelectionChanged: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(" Instructions", fontSize = 35.sp)
        Text(" ", fontSize = 30.sp)
        Text("  1. Select the Source Measurment drop down to indicate which measurement you would like to convert.", fontSize = 28.sp)
        Text("  2. Enter the Convert Amount in the entry filled to indicate how many of the Source Measurement you would like to convert.", fontSize = 28.sp)
        Text("  3. Select the Target Measurment drop down to indicate the measurement you would like to use.", fontSize = 28.sp)
    }
}
