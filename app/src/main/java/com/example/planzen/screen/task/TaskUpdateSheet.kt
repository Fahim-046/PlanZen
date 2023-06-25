package com.example.planzen.screen.task

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskUpdateSheet(
    showSheet: MutableState<Boolean>,
    updateTask: (Int, String, Boolean) -> Unit,
    userId: Int,
    viewModel: TaskScreenViewModel,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false,
        confirmValueChange = { sheetState ->
            return@rememberModalBottomSheetState sheetState != SheetValue.Hidden
        }
    )
    val goBack: () -> Unit = {
        scope.launch {
            bottomSheetState.hide()
        }.invokeOnCompletion {
            if (!bottomSheetState.isVisible) {
                showSheet.value = false
            }
        }
    }

    ModalBottomSheet(
        onDismissRequest = { showSheet.value = false },
        sheetState = bottomSheetState
    ) {
        TaskUpdateSheetSkeleton(
            userId = userId,
            goBack = goBack,
            viewModel = viewModel,
            lifecycleOwner = lifecycleOwner
        )
    }
}

@Preview
@Composable
fun TaskUpdateSheetSkeletonPreview() {
    val viewModel: TaskScreenViewModel = hiltViewModel()
    TaskUpdateSheetSkeleton(
        0,
        {},
        viewModel = viewModel,
        LocalLifecycleOwner.current
    )
}

@Composable
fun TaskUpdateSheetSkeleton(
    userId: Int,
    goBack: () -> Unit = {},
    viewModel: TaskScreenViewModel,
    lifecycleOwner: LifecycleOwner
) {
    var task by remember { mutableStateOf("") }
    var taskStatus by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(key1 = Unit) {
        viewModel.specificTask(userId)
    }

    viewModel.singleTask.observe(lifecycleOwner) {
        task = it.task
        taskStatus = it.taskStatus
    }
    Scaffold(
        Modifier
            .navigationBarsPadding()
            .imePadding()
            .statusBarsPadding()
    ) { innerPadding ->
        Column(
            Modifier
                .padding(innerPadding)
                .padding(top = 8.dp, start = 16.dp, end = 16.dp)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Update Task",
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace
            )
            Spacer(modifier = Modifier.height(15.dp))
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = task,
                onValueChange = { task = it },
                label = { Text("Update your task") },
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default,
                keyboardActions = KeyboardActions.Default
            )
            Column(
                modifier = Modifier.fillMaxWidth()
                    .padding(top = 6.dp),
                horizontalAlignment = Alignment.End
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedButton(

                        onClick = {
                            viewModel.deleteTask(userId)
                            goBack()
                        }

                    ) {
                        Text(text = "Delete Task")
                    }
                    OutlinedButton(
                        modifier = Modifier.padding(start = 110.dp),

                        onClick = {
                            viewModel.updateTask(userId, task, taskStatus)
                            goBack()
                        }

                    ) {
                        Text(text = "Update Task")
                    }
                }
            }
        }
    }
}
