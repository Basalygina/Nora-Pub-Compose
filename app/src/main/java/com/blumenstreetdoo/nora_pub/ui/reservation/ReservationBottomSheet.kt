package com.blumenstreetdoo.nora_pub.ui.reservation

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.blumenstreetdoo.nora_pub.R
import com.blumenstreetdoo.nora_pub.ui.reservation.ReservationState.ReservationStatus
import com.blumenstreetdoo.nora_pub.ui.theme.NoraColors
import com.blumenstreetdoo.nora_pub.ui.theme.NoraTypography
import java.text.SimpleDateFormat
import java.util.Locale

@SuppressLint("DefaultLocale")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReservationBottomSheet(
    viewModel: ReservationViewModel,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    val state by viewModel.reservationState.collectAsState()
    val resources = LocalContext.current.resources

    var showDatePicker by rememberSaveable { mutableStateOf(false) }
    var showTimePicker by rememberSaveable { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()
    val timePickerState = rememberTimePickerState()
    var guestInput by remember { mutableStateOf(state.guests?.toString() ?: "") }

    val snackbarHostState = remember { SnackbarHostState() }

    // Handling snackbar
    LaunchedEffect(state.statusVersion) {
        snackbarHostState.currentSnackbarData?.dismiss()
        when (val status = state.status) {
            is ReservationStatus.Success -> {
                snackbarHostState.showSnackbar(
                    message = resources.getString(R.string.reservation_success),
                    duration = SnackbarDuration.Long
                )
            }

            is ReservationStatus.Error -> {
                status.message?.let { message ->
                    snackbarHostState.showSnackbar(
                        message = message,
                        duration = SnackbarDuration.Long
                    )
                }
            }

            else -> {}
        }
    }

    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        containerColor = Color.White,
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Date Picker Button
            Column {
                val dateInteractionSource = remember { MutableInteractionSource() }
                val isDatePressed by dateInteractionSource.collectIsPressedAsState()
                OutlinedButton(
                    onClick = { showDatePicker = true },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = NoraColors.onBackground),
                    border = BorderStroke(
                        width = if (isDatePressed) 2.dp else 1.dp,
                        color = if (state.dateErrorRes != null) NoraColors.error else if (isDatePressed) NoraColors.primary else NoraColors.onBackground
                    ),
                    interactionSource = dateInteractionSource
                ) {
                    Text(
                        text = state.date ?: stringResource(R.string.select_date),
                        style = NoraTypography.labelLarge
                    )
                }
                if (state.dateErrorRes != null) {
                    Text(
                        text = stringResource(state.dateErrorRes!!),
                        color = NoraColors.error,
                        style = NoraTypography.bodySmall,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
            }

            // Time Picker Button
            Column {
                val timeInteractionSource = remember { MutableInteractionSource() }
                val isTimePressed by timeInteractionSource.collectIsPressedAsState()
                OutlinedButton(
                    onClick = { showTimePicker = true },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = state.date != null,
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = NoraColors.onBackground),
                    border = BorderStroke(
                        width = if (isTimePressed) 2.dp else 1.dp,
                        color = if (state.timeErrorRes != null) NoraColors.error else if (isTimePressed) NoraColors.primary else NoraColors.onBackground
                    ),
                    interactionSource = timeInteractionSource
                ) {
                    Text(
                        text = state.time ?: stringResource(R.string.select_time),
                        style = NoraTypography.labelLarge
                    )
                }
                if (state.timeErrorRes != null) {
                    Text(
                        text = stringResource(state.timeErrorRes!!),
                        color = NoraColors.error,
                        style = NoraTypography.bodySmall,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
            }

            // Number of guests
            OutlinedTextField(
                value = guestInput,
                onValueChange = { newValue ->
                    guestInput = newValue
                    val guestCount = newValue.toIntOrNull()
                    if (newValue.isEmpty()) {
                        viewModel.setGuestCount(null)
                    } else if (guestCount != null && guestCount in 1..10) {
                        viewModel.setGuestCount(guestCount)
                    } else {
                        viewModel.setGuestCount(null)
                    }
                },
                label = {
                    Text(
                        stringResource(R.string.guest_count),
                        style = NoraTypography.labelMedium
                    )
                },
                modifier = Modifier
                    .fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        viewModel.submitReservation()
                    }
                ),
                textStyle = NoraTypography.bodyLarge,
                isError = (guestInput.isNotEmpty() && guestInput.toIntOrNull() !in 1..10),
                supportingText = {
                    if (guestInput.isNotEmpty() && guestInput.toIntOrNull() !in 1..10) {
                        Text(
                            text = resources.getString(R.string.invalid_guest_count),
                            color = NoraColors.error
                        )
                    }
                }
            )

            // Submit Button
            Button(
                onClick = {
                    viewModel.submitReservation()
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = state.status !is ReservationStatus.Error && state.date != null && state.time != null,
                colors = ButtonDefaults.buttonColors(
                    containerColor = NoraColors.primary,
                    contentColor = NoraColors.onPrimary
                )
            ) {
                Text(
                    stringResource(R.string.confirm_reservation),
                    style = NoraTypography.labelLarge
                )
            }

            // Date Picker Dialog
            if (showDatePicker) {
                DatePickerDialog(
                    onDismissRequest = { showDatePicker = false },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                showDatePicker = false
                                val selectedDate =
                                    SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                                        .format(
                                            datePickerState.selectedDateMillis
                                                ?: System.currentTimeMillis()
                                        )
                                viewModel.setDate(selectedDate)
                            },
                            colors = ButtonDefaults.textButtonColors(contentColor = NoraColors.onSurface)
                        ) {
                            Text(
                                stringResource(android.R.string.ok),
                                style = NoraTypography.labelMedium
                            )
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = { showDatePicker = false },
                            colors = ButtonDefaults.textButtonColors(contentColor = NoraColors.onSurface)
                        ) {
                            Text(
                                stringResource(android.R.string.cancel),
                                style = NoraTypography.labelMedium
                            )
                        }
                    },
                ) {
                    DatePicker(state = datePickerState)
                }
            }

            // Time Picker Dialog
            if (showTimePicker) {
                AlertDialog(
                    onDismissRequest = { showTimePicker = false },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                showTimePicker = false
                                val selectedTime = String.format(
                                    "%d:%02d",
                                    timePickerState.hour,
                                    timePickerState.minute
                                )
                                viewModel.setTime(selectedTime)
                            },
                            colors = ButtonDefaults.textButtonColors(contentColor = NoraColors.primary)
                        ) {
                            Text(
                                stringResource(android.R.string.ok),
                                style = NoraTypography.labelMedium
                            )
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = { showTimePicker = false },
                            colors = ButtonDefaults.textButtonColors(contentColor = NoraColors.onBackground)
                        ) {
                            Text(
                                stringResource(android.R.string.cancel),
                                style = NoraTypography.labelMedium
                            )
                        }
                    },
                    title = {
                        Text(
                            stringResource(R.string.select_time),
                            style = NoraTypography.titleMedium
                        )
                    },
                    text = { TimePicker(state = timePickerState) },
                    containerColor = NoraColors.background
                )
            }

            // Custom SnackbarHost
            SnackbarHost(
                hostState = snackbarHostState,
            ) { data ->
                val isError = state.status is ReservationStatus.Error
                Snackbar(
                    containerColor = if (isError) NoraColors.error else NoraColors.tertiary,
                    contentColor = if (isError) NoraColors.onError else NoraColors.onTertiary,
                    snackbarData = data
                )
            }
        }
    }
}