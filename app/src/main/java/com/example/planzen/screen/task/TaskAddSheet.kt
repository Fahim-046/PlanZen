package com.example.planzen.screen.task

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskAddSheet(
    showSheet: MutableState<Boolean>,
    onSuccess: () -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false,
        confirmValueChange = { sheetState ->
            return@rememberModalBottomSheetState sheetState != SheetValue.Hidden
        }
    )
    // val state by viewModel.state.collectAsState()

    val goBack: () -> Unit = {
        scope.launch {
            bottomSheetState.hide()
        }.invokeOnCompletion {
            if (!bottomSheetState.isVisible) {
                showSheet.value = false
            }
        }
    }

//    LaunchedEffect(state.addUserSuccess ){
//
//    }
    ModalBottomSheet(
        onDismissRequest = { showSheet.value = false },
        sheetState = bottomSheetState
    ) {
        TaskAddSheetSkeleton(
            goBack = goBack,
            addTask = { task, taskstatus ->
//                viewModel.addTask(
//                    task,
//                    taskStatus
//                )
            },
            onCancelClick = goBack

        )
    }
}

@Preview
@Composable
fun UserAddSheetSkeletonPreview() {
    TaskAddSheetSkeleton(
        onCancelClick = {}
    )
}

@Composable
fun TaskAddSheetSkeleton(
    goBack: () -> Unit = {},
    addTask: (
        task: String,
        taskStatus: Boolean
    ) -> Unit = { _, _ -> },
    onCancelClick: () -> Unit
) {
    var task by rememberSaveable { mutableStateOf("") }

    var taskStatus by remember { mutableStateOf(false) }

    Scaffold(
        Modifier
            .navigationBarsPadding()
            .imePadding()
            .statusBarsPadding()
            .height(300.dp)
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
                text = "Add Task",
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
                label = { Text("Add your task") },
                singleLine = true
            )
            Column(
                modifier = Modifier.fillMaxWidth()
                    .padding(top = 6.dp),
                horizontalAlignment = Alignment.End
            ) {
                OutlinedButton(
                    modifier = Modifier.padding(top = 4.dp),
                    onClick = {
                        addTask(
                            task,
                            taskStatus
                        )
                        onCancelClick()
                    }

                ) {
                    Text(text = "Save Task")
                }
            }
        }
    }
}
