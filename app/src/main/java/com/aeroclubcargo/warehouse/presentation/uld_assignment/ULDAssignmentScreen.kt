package com.aeroclubcargo.warehouse.presentation.uld_assignment

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.aeroclubcargo.warehouse.R
import com.aeroclubcargo.warehouse.domain.model.FlightScheduleModel
import com.aeroclubcargo.warehouse.presentation.components.top_bar.GetTopBar
import com.aeroclubcargo.warehouse.theme.*
import com.aeroclubcargo.warehouse.utils.toDurationTime
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ULDAssignmentScreen(navController: NavController,
                        scheduleModel: FlightScheduleModel?,
                        viewModel: ULDAssignmentViewModel = hiltViewModel()){
    viewModel.setFlightSchedule(scheduleModel)
    Scaffold(topBar = {
        GetTopBar(navController = navController, isDashBoard = false)
    }) {

        val updatePackageSheetState = rememberModalBottomSheetState(
            initialValue = ModalBottomSheetValue.Hidden,
            confirmStateChange = { it != ModalBottomSheetValue.HalfExpanded },
        )

        CheckPackageItemSheet(content = {
            GetCutOffTimeList(viewModel, navController, modalSheetState = updatePackageSheetState)
        }, modalSheetState = updatePackageSheetState, viewModel = viewModel)


    }
}

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterialApi::class)
@Composable
fun GetCutOffTimeList(
    viewModel: ULDAssignmentViewModel,
    navController: NavController,
    modalSheetState: ModalBottomSheetState,
) {
    val coroutineScope = rememberCoroutineScope()

    val keyboardController = LocalSoftwareKeyboardController.current
    val mContext = LocalContext.current

    val flightScheduleValue = viewModel.flightScheduleValue.collectAsState()

    val isLoading  = viewModel.isLoading.collectAsState()
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp),
    ) {
        Text(text = "ULD Assignment")
        Spacer(modifier = Modifier.height(5.dp))
        Box(modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {
                Row(modifier = Modifier
                    .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ){
                    Box(
                        modifier = Modifier
                            .weight(1f)

                    ) {
                        HeaderTile(
                            title = "Flight No",
                            desctiption = flightScheduleValue.value?.flightNumber?: "-"
                        )
                    }
                    Spacer(modifier = Modifier.width(6.dp))
                    Box(
                        modifier = Modifier
                            .weight(1f)

                    ) {
                        HeaderTile(
                            title = "Dept. Date",
                            desctiption =  flightScheduleValue.value?.scheduledDepartureDateTime?.split("T")?.first() ?: "-"
                        )
                    }
                    Spacer(modifier = Modifier.width(6.dp))
                    Box(
                        modifier = Modifier
                            .weight(1f)

                    ) {
                        HeaderTile(
                            title = "Dept. Time",
                            desctiption = flightScheduleValue.value?.scheduledDepartureDateTime?.split("T")?.last()?: "-"
                        )
                    }
                    Spacer(modifier = Modifier.width(6.dp))
                    Box(
                        modifier = Modifier
                            .weight(1f)

                    ) {
                        HeaderTile(
                            title = "Cut Off Time",
                            desctiption =flightScheduleValue.value?.cutoffTime?.split("T")?.last() ?: "-"
                        )
                    }

                    Spacer(modifier = Modifier.width(6.dp))
                    Box(
                        modifier = Modifier
                            .weight(1f)

                    ) {
                        HeaderTile(
                            title = "ORIG",
                            desctiption = flightScheduleValue.value?.originAirportCode?: "-"
                        )
                    }
                    Spacer(modifier = Modifier.width(6.dp))
                    Box(
                        modifier = Modifier
                            .weight(1f)

                    ) {
                        HeaderTile(
                            title = "DEST",
                            desctiption = flightScheduleValue.value?.destinationAirportCode?: "-"
                        )
                    }
                    Spacer(modifier = Modifier.width(6.dp))
                    Box(
                        modifier = Modifier
                            .weight(1f)

                    ) {
                        HeaderTile(
                            title = "Act Type",
                            desctiption = flightScheduleValue.value?.aircraftSubTypeName?: "-"
                        )
                    }
                    Spacer(modifier = Modifier.width(6.dp))
                    Box(
                        modifier = Modifier
                            .weight(1f)

                    ) {
                        HeaderTile(
                            title = "ULD Position",
                            desctiption =  flightScheduleValue.value?.uldPositionCount.toString()?: "0"
                        )
                    }
                    Spacer(modifier = Modifier.width(6.dp))
                    Box(
                        modifier = Modifier
                            .weight(1f)

                    ) {
                        HeaderTile(
                            title = "ULD Count",
                            desctiption =  flightScheduleValue.value?.uldCount.toString()?: "0",
                            textColor = Green
                        )
                    }

                }
                if (isLoading.value) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center,
                    ) {
                        CircularProgressIndicator()
                    }
                } else {
                    Spacer(modifier = Modifier.width(6.dp))
                    Divider(color = Gray4)
                    Spacer(modifier = Modifier.width(6.dp))
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp), horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "Added ULD")
                        Button(onClick = {
                            coroutineScope.launch {
                                if (modalSheetState.isVisible)
                                    modalSheetState.hide()
                                else {
                                    modalSheetState.animateTo(ModalBottomSheetValue.Expanded)
                                }
                            }

                        }) {
                            Text(text = "Add ULD", style = TextStyle(color = Color.White))
                        }
                    }
                    FlightsTable(viewModel= viewModel)
                }
            }

        }
    }
}

@Composable
fun HeaderTile(title:String,desctiption:String,textColor: Color =  Color.Black) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .background(BlueLight3)
        .border(1.dp, BlueLight5, RoundedCornerShape(5.dp))
        .padding(8.dp)
    ) {
        Column() {
            Text(text = title, style = MaterialTheme.typography.subtitle2.copy(color = Gray6, fontSize = 12.sp),)
            Text(text = desctiption,style = MaterialTheme.typography.subtitle1.copy(color = textColor,fontSize = 14.sp))
        }
    }
}




@Composable
fun FlightsTable(viewModel: ULDAssignmentViewModel) {
    val mContext = LocalContext.current
    val todoListState = viewModel.todoListFlow.collectAsState()
    val headerStyle = MaterialTheme.typography.body2.copy(color = Black)
    val showAlert = remember { mutableStateOf(false) }

    if (showAlert.value) {
        AlertDialog(
            onDismissRequest = { showAlert.value = false },
            title = { Text("Error") },
            text = { Text("cut-off time should be Below the scheduled time") },
            confirmButton = {
                Button(
                    onClick = { showAlert.value = false }
                ) {
                    Text(stringResource(R.string.ok))
                }
            },
            modifier = Modifier.padding(16.dp)
        )
    }
    LazyColumn(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // header
        item {
            Row(
                Modifier
                    .fillMaxWidth()
                    .background(color = Gray5),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                TableCell(text = "ULD number", weight = column2Weight, style = headerStyle)
                TableCell(text = "ULD Type", weight = column1Weight, style = headerStyle)
                TableCell(text = "Dimensions", weight = column2Weight, style = headerStyle)
                TableCell(text = "Max Weight", weight = column3Weight, style = headerStyle)
                TableCell(text = "Received Weight", weight = column10Weight, style = headerStyle)
                TableCell(text = "Max Volume", weight = column5Weight, style = headerStyle)
                TableCell(text = "Received Volume", weight = column6Weight, style = headerStyle)
                TableCell(text = "Actions", weight = column8Weight, style = headerStyle)
            }
        }
        // data
        items(todoListState.value) {booking->
            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                TableCell(text = "PAG 20006 4G", weight =  column2Weight)
                TableCell(text = "Palette", weight =  column1Weight)
                TableCell(text = "317.5 x 122.5 x 55", weight =  column2Weight)
                TableCell(text =  "1814 Kg", weight =  column3Weight)
                TableCell(text = "-", weight =  column10Weight)
                TableCell(text = "0.01 m3", weight =  column5Weight)
                TableCell(text = "-", weight =  column6Weight)
                Row(Modifier.weight(column8Weight)) {
                    IconButton(
                        onClick = {

                        }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_view),
                            contentDescription = "edit",
                            modifier = Modifier
                                .size(24.dp)
                                .padding(3.dp),
                            tint = BlueLight
                        )
                    }
                    IconButton(
                        onClick = {

                        }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_edit),
                            contentDescription = "edit",
                            modifier = Modifier
                                .size(24.dp)
                                .padding(3.dp),
                            tint = BlueLight
                        )
                    }
                    IconButton(
                        onClick = {

                        }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_add),
                            contentDescription = "edit",
                            modifier = Modifier
                                .size(24.dp)
                                .padding(3.dp),
                            tint = BlueLight
                        )
                    }
                    IconButton(
                        onClick = {

                        }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_delete),
                            contentDescription = "edit",
                            modifier = Modifier
                                .size(24.dp)
                                .padding(3.dp),
                            tint = BlueLight
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun RowScope.TableCell(
    text: String,
    weight: Float,
    style: TextStyle = MaterialTheme.typography.body2
) {
    Text(
        text = text,
        Modifier
            .weight(weight)
            .padding(8.dp),
        style = style,
        textAlign = TextAlign.Center
    )
}



