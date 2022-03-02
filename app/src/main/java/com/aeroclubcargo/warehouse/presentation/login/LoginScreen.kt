package com.aeroclubcargo.warehouse.presentation.login

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.aeroclubcargo.warehouse.R
import com.aeroclubcargo.warehouse.presentation.theme.BlueLight
import com.aeroclubcargo.warehouse.presentation.theme.Gray1
import com.aeroclubcargo.warehouse.presentation.theme.Gray2
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun LoginScreen(
    navController: NavController?,
) {

    Scaffold() {
        MainUI()
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Preview(device = Devices.AUTOMOTIVE_1024p)
@Composable
fun MainUI() {
    val keyboardController = LocalSoftwareKeyboardController.current

    val image = painterResource(id = R.drawable.ic_login_img)

    var emailValue by remember { mutableStateOf("") }
    var passwordValue by remember { mutableStateOf("") }

    val passwordVisibility = remember { mutableStateOf(false) }

    val focusRequesterEmail = remember { FocusRequester() }
    val focusRequesterPassword = remember { FocusRequester() }

    val scrollState = rememberScrollState()
    val checkState = remember {
        mutableStateOf(true)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
//            .clickable {
//                keyboardController?.hide()
//            }
            .background(color = Color.White),
        horizontalAlignment = Alignment.End
    ) {
        Text(text = "English", modifier = Modifier.padding(8.dp))
        Spacer(modifier = Modifier.height(20.dp))
        Column(
            modifier = Modifier
                .fillMaxSize()
                .scrollable(state = scrollState, orientation = Orientation.Vertical),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .height(70.dp)
                    .width(140.dp),
                contentAlignment = Alignment.TopCenter
            ) {
                Image(painter = image, contentDescription = null)
            }
            Spacer(modifier = Modifier.height(19.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.hello),
                    style = MaterialTheme.typography.h4,
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = stringResource(R.string.enter_credentials_msg),
                    style = MaterialTheme.typography.body2,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.padding(10.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.6f)
                        .padding(
                            start = 16.dp,
                            end = 16.dp
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    OutlinedTextField(
                        value = emailValue,
                        onValueChange = { emailValue = it },
                        label = { Text(text = "Email") },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email,
                            imeAction = ImeAction.Next
                        ),
                        placeholder = { Text(text = "Email") },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .focusRequester(focusRequester = focusRequesterEmail),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            unfocusedBorderColor = Gray2
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = {
                                CoroutineScope(Default).launch {
                                    keyboardController?.hide()
                                    delay(400)
                                    focusRequesterPassword.requestFocus()
                                }
                            }
                        )
                    )
                    Spacer(modifier = Modifier.padding(5.dp))
                    OutlinedTextField(
                        value = passwordValue,
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            unfocusedBorderColor = Gray2
                        ),
                        onValueChange = { passwordValue = it },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Done
                        ),
                        trailingIcon = {
                            IconButton(onClick = {
                                passwordVisibility.value = !passwordVisibility.value
                            }) {
                                Icon(
                                    painter = painterResource(id = if (passwordVisibility.value) R.drawable.ic_baseline_visibility_24 else R.drawable.ic_baseline_visibility_off_24),
                                    contentDescription = "Visibility button",
                                )
                            }
                        },
                        label = { Text("Password") },
                        placeholder = { Text(text = "Password") },
                        singleLine = true,
                        visualTransformation = if (passwordVisibility.value) VisualTransformation.None
                        else PasswordVisualTransformation(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .focusRequester(focusRequester = focusRequesterPassword),
                        keyboardActions = KeyboardActions(
                            onDone = { keyboardController?.hide() }
                        ),
                    )
                    Spacer(modifier = Modifier.padding(25.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.width(intrinsicSize = IntrinsicSize.Max),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(checked = checkState.value, onCheckedChange = {
                                checkState.value = it
                            })
                            Text(
                                "Remember me",
                                maxLines = 1,
                                style = MaterialTheme.typography.body2.copy(color = Gray1)
                            )
                        }
                        Box(
                            contentAlignment = Alignment.CenterEnd,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            OutlinedButton(
                                onClick = {
                                    // TODO
                                },
                                border = BorderStroke(0.5.dp, BlueLight),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text(
                                    text = stringResource(id = R.string.sign_in),
                                    fontSize = 20.sp,
                                    color = BlueLight
                                )
                            }
                        }


                    }
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 8.dp),
                contentAlignment = Alignment.BottomCenter
            ) {
                Text(
                    text = "© 2022 Aero Club Solutions INC. All rights reserved.",
                    style = MaterialTheme.typography.caption.copy(color = Gray1),
                )
            }
        }
    }
}