package com.blumenstreetdoo.nora_pub.ui.craft

import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.blumenstreetdoo.nora_pub.R
import com.blumenstreetdoo.nora_pub.ui.common.CustomRangeSlider
import com.blumenstreetdoo.nora_pub.ui.craft.CraftFilterState.Companion.MAX_ABV
import com.blumenstreetdoo.nora_pub.ui.craft.CraftFilterState.Companion.MAX_IBU
import com.blumenstreetdoo.nora_pub.ui.craft.CraftFilterState.Companion.MIN_ABV
import com.blumenstreetdoo.nora_pub.ui.craft.CraftFilterState.Companion.MIN_IBU

@Composable
fun CraftFilterScreen(
    filterState: CraftFilterState,
    defaultFilter: CraftFilterState,
    countries: List<String>,
    onApply: (CraftFilterState) -> Unit,
    onReset: () -> Unit
) {
    var selectedCountry by remember { mutableStateOf(filterState.country.orEmpty()) }
    var abvRange by remember {
        mutableStateOf(
            (filterState.minAbv ?: defaultFilter.minAbv ?: MIN_ABV).toFloat()..
                    (filterState.maxAbv ?: defaultFilter.maxAbv ?: MAX_ABV).toFloat()
        )
    }
    var ibuRange by remember {
        mutableStateOf(
            (filterState.minIbu ?: defaultFilter.minIbu ?: MIN_IBU).toFloat()..
                    (filterState.maxIbu ?: defaultFilter.maxIbu ?: MAX_IBU).toFloat()
        )
    }

    LaunchedEffect(filterState) {
        selectedCountry = filterState.country.orEmpty()
        abvRange = (filterState.minAbv ?: defaultFilter.minAbv ?: MIN_ABV).toFloat()..
                (filterState.maxAbv ?: defaultFilter.maxAbv ?: MAX_ABV).toFloat()
        ibuRange = (filterState.minIbu ?: defaultFilter.minIbu ?: MIN_IBU).toFloat()..
                (filterState.maxIbu ?: defaultFilter.maxIbu ?: MAX_IBU).toFloat()
    }

    val currentFilter = remember(selectedCountry, abvRange, ibuRange) {
        CraftFilterState(
            searchQuery = filterState.searchQuery,
            country = selectedCountry.takeIf { it.isNotEmpty() },
            minAbv = abvRange.start.toDouble(),
            maxAbv = abvRange.endInclusive.toDouble(),
            minIbu = ibuRange.start.toDouble(),
            maxIbu = ibuRange.endInclusive.toDouble()
        )
    }

    val isFilterChanged = currentFilter != filterState
    val isDefaultFilter = currentFilter == defaultFilter

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Dropdown
        CountryDropdown(
            selectedCountry = selectedCountry,
            countries = countries,
            onCountrySelected = { newCountry ->
                selectedCountry = newCountry
            }
        )

        // ABV RangeSlider
        CustomRangeSlider(
            title = "ABV",
            valueRange = MIN_ABV.toFloat()..MAX_ABV.toFloat(),
            value = abvRange,
            onValueChange = { newRange -> abvRange = newRange },
            valueFormatter = { "%.1f%%".format(it) }
        )

        // IBU RangeSlider
        CustomRangeSlider(
            title = "IBU",
            valueRange = MIN_IBU.toFloat()..MAX_IBU.toFloat(),
            value = ibuRange,
            onValueChange = { newRange -> ibuRange = newRange },
            valueFormatter = { value -> value.toInt().toString() }
        )

        // Buttons
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            OutlinedButton(
                onClick = { onReset() },
                modifier = Modifier.weight(1f),
                enabled = !isDefaultFilter
            ) {
                Text(stringResource(R.string.reset))
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(
                onClick = { onApply(currentFilter) },
                modifier = Modifier.weight(1f),
                enabled = isFilterChanged && !isDefaultFilter
            ) {
                Text(stringResource(R.string.apply))
            }
        }
    }
}

@Composable
fun CountryDropdown(
    selectedCountry: String,
    countries: List<String>,
    onCountrySelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    // For setting a flexible dropdown width
    var fieldWidth by remember { mutableStateOf(0.dp) }
    val density = LocalDensity.current

    Column(modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    fieldWidth = with(density) { coordinates.size.width.toDp() }
                }
        ) {
            OutlinedTextField(
                value = selectedCountry,
                onValueChange = { },
                label = { Text(stringResource(R.string.select_country)) },
                trailingIcon = {
                    Row() {
                        // Clear button if country is selected
                        if (selectedCountry.isNotEmpty()) {
                            IconButton(onClick = { onCountrySelected("") }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_clear),
                                    contentDescription = stringResource(id = R.string.clear),
                                    tint = Color.Black
                                )
                            }
                        }
                        // Arrow to open/close
                        IconButton(onClick = { expanded = !expanded }) {
                            Icon(
                                imageVector = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                                contentDescription = null,
                                tint = Color.Black
                            )
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .focusable(false),
                readOnly = true,
                enabled = false,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    disabledContainerColor = Color.White,
                    focusedIndicatorColor = Color.Gray,
                    unfocusedIndicatorColor = Color.Gray,
                    disabledIndicatorColor = Color.Gray,
                    disabledTextColor = Color.Black,
                    disabledLabelColor = Color.Black
                )
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(fieldWidth)
                .background(Color.White)
        ) {
            countries.forEach { country ->
                DropdownMenuItem(
                    text = { Text(country) },
                    onClick = {
                        onCountrySelected(country)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun CraftFilterScreenPreview() {
    CraftFilterScreen(
        filterState = CraftFilterState(),
        defaultFilter = CraftFilterState(),
        countries = listOf("USA", "Germany", "Belgium"),
        onApply = {},
        onReset = {}
    )
}