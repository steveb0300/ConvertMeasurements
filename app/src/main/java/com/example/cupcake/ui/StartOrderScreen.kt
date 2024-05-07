/*
 * Copyright (C) 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.cupcake.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.example.cupcake.R
import com.example.cupcake.data.DataSource
import com.example.cupcake.ui.theme.CupcakeTheme




/**
 * Composable that allows the user to select the desired cupcake quantity and expects
 * [onNextButtonClicked] lambda that expects the selected quantity and triggers the navigation to
 * next screen
 */

@Composable
fun StartOrderScreen(
    quantityOptions: List<Pair<Int, Int>>,
    onNextButtonClicked: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var convertText by remember { mutableStateOf("1") }
    var sourceText by remember { mutableStateOf("") }
    var targetText by remember { mutableStateOf("") }
    var convertedDouble by remember { mutableStateOf("") }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            //Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))
            val options = listOf("US Gallon", "US Quart", "US Pint", "US Cup", "US Fluid Ounce", "US Tablespoon", "US Teaspoon")
            var expanded by remember { mutableStateOf(false) }
            var selectedOptionText by remember { mutableStateOf(options[0])
            }

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded },
            ) {
                TextField(
                    modifier = Modifier.menuAnchor(),
                    readOnly = true,
                    value = selectedOptionText,
                    onValueChange = {},
                    label = { Text("Source Measurement") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    colors = ExposedDropdownMenuDefaults.textFieldColors(),
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                ) {
                    options.forEach { selectionOption ->
                        DropdownMenuItem(
                            text = { Text(selectionOption) },
                            onClick = {
                                selectedOptionText = selectionOption
                                sourceText = selectionOption
                                //val convertToDouble = convertedDouble.toDouble()
                                convertedDouble = UnitConverter.convert(sourceText, targetText, convertText.toDouble()).toString()
                                expanded = false
                            },
                        )
                    }
                }
            }
        }
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            TextField(
                value = convertText,
                singleLine = true,
                onValueChange = {
                    convertText = it
                    if (convertText != "") {
                        if (convertText.toDouble() > 0) {
                            convertedDouble = UnitConverter.convert(
                                sourceText,
                                targetText,
                                convertText.toDouble()
                            ).toString()
                        }
                    }
                                },
                label = { Text("Convert Amount") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
        }
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            //Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))
            val options = listOf("US Gallon", "US Quart", "US Pint", "US Cup", "US Fluid Ounce", "US Tablespoon", "US Teaspoon")
            var expanded by remember { mutableStateOf(false) }
            var selectedOptionText by remember { mutableStateOf(options[0]) }

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded },
            ) {
                TextField(
                    modifier = Modifier.menuAnchor(),
                    readOnly = true,
                    value = selectedOptionText,
                    onValueChange = {},
                    label = { Text("Target Measurement") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    colors = ExposedDropdownMenuDefaults.textFieldColors(),
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                ) {
                    options.forEach { selectionOption ->
                        DropdownMenuItem(
                            text = { Text(selectionOption) },
                            onClick = {
                                selectedOptionText = selectionOption
                                targetText = selectionOption
                                convertedDouble = UnitConverter.convert(sourceText, targetText, convertText.toDouble()).toString()
                                expanded = false
                            },
                        )
                    }
                }
            }
        }
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            //Text("Result = $convertText $sourceText $targetText", fontSize = 20.sp)
            Text("Result = $convertedDouble")
        }
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(
                dimensionResource(id = R.dimen.padding_medium)
            )
        ) {
            quantityOptions.forEach { item ->
                SelectQuantityButton(
                    labelResourceId = item.first,
                    onClick = { onNextButtonClicked(item.second) },
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }
    }
}

/**
 * Customizable button composable that displays the [labelResourceId]
 * and triggers [onClick] lambda when this composable is clicked
 */
@Composable
fun SelectQuantityButton(
    @StringRes labelResourceId: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier.widthIn(min = 250.dp)
    ) {
        Text(stringResource(labelResourceId))
    }
}

@Preview
@Composable
fun StartOrderPreview() {
    CupcakeTheme {
        StartOrderScreen(
            quantityOptions = DataSource.quantityOptions,
            onNextButtonClicked = {},
            modifier = Modifier
                .fillMaxSize()
                .padding(dimensionResource(R.dimen.padding_medium))
        )
    }
}

object UnitConverter {
    fun convert(sourceUnit: String, targetUnit: String, value: Double): Double {
        var convertValue = value
        var source = ""
        var target = ""
        
        if (sourceUnit == "")
            source = "US Gallon"
        else
            source = sourceUnit

        if (targetUnit == "")
            target = "US Gallon"
        else
            target = targetUnit

        return when {
            source == "US Gallon" && target == "US Quart" -> convertValue * 4
            source == "US Gallon" && target == "US Pint" -> convertValue * 8
            source == "US Gallon" && target == "US Cup" -> convertValue * 16
            source == "US Gallon" && target == "US Fluid Ounce" -> convertValue * 128
            source == "US Gallon" && target == "US Tablespoon" -> convertValue * 256
            source == "US Gallon" && target == "US Teaspoon" -> convertValue * 768

            source == "US Quart" && target == "US Gallon" -> convertValue / 4
            source == "US Quart" && target == "US Pint" -> convertValue * 2
            source == "US Quart" && target == "US Cup" -> convertValue * 4
            source == "US Quart" && target == "US Fluid Ounce" -> convertValue * 32
            source == "US Quart" && target == "US Tablespoon" -> convertValue * 64
            source == "US Quart" && target == "US Teaspoon" -> convertValue * 192

            source == "US Pint" && target == "US Gallon" -> convertValue / 8
            source == "US Pint" && target == "US Quart" -> convertValue / 2
            source == "US Pint" && target == "US Cup" -> convertValue * 16
            source == "US Pint" && target == "US Fluid Ounce" -> convertValue * 16
            source == "US Pint" && target == "US Tablespoon" -> convertValue * 32
            source == "US Pint" && target == "US Teaspoon" -> convertValue * 96

            source == "US Cup" && target == "US Gallon" -> convertValue / 16
            source == "US Cup" && target == "US Quart" -> convertValue / 4
            source == "US Cup" && target == "US Pint" -> convertValue / 2
            source == "US Cup" && target == "US Fluid Ounce" -> convertValue * 8
            source == "US Cup" && target == "US Tablespoon" -> convertValue * 16
            source == "US Cup" && target == "US Teaspoon" -> convertValue * 48

            source == "US Fluid Ounce" && target == "US Gallon" -> convertValue / 128
            source == "US Fluid Ounce" && target == "US Quart" -> convertValue / 32
            source == "US Fluid Ounce" && target == "US Pint" -> convertValue / 16
            source == "US Fluid Ounce" && target == "US Cup" -> convertValue / 8
            source == "US Fluid Ounce" && target == "US Tablespoon" -> convertValue * 2
            source == "US Fluid Ounce" && target == "US Teaspoon" -> convertValue * 6

            source == "US Tablespoon" && target == "US Gallon" -> convertValue / 256
            source == "US Tablespoon" && target == "US Quart" -> convertValue / 64
            source == "US Tablespoon" && target == "US Pint" -> convertValue / 32
            source == "US Tablespoon" && target == "US Cup" -> convertValue / 16
            source == "US Tablespoon" && target == "US Fluid Ounce" -> convertValue / 2
            source == "US Tablespoon" && target == "US Teaspoon" -> convertValue * 3

            source == "US Teaspoon" && target == "US Gallon" -> convertValue / 768
            source == "US Teaspoon" && target == "US Quart" -> convertValue / 192
            source == "US Teaspoon" && target == "US Pint" -> convertValue / 96
            source == "US Teaspoon" && target == "US Cup" -> convertValue / 48
            source == "US Teaspoon" && target == "US Fluid Ounce" -> convertValue / 6
            source == "US Teaspoon" && target == "US Tablespoon" -> convertValue / 3

            else -> 999.0 // Default: return the same value if no conversion is defined
        }
    }
}
