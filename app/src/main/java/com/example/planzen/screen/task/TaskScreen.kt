package com.example.planzen.screen.task

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.planzen.ui.theme.PlanZenTheme
import com.example.planzen.ui.theme.customColor

@Composable
fun TaskScreen(
    viewModel: TaskScreenViewModel,
    //goBack: () -> Unit
) {
    val openAddTaskSheet = rememberSaveable {
        mutableStateOf(false)
    }
    val state = viewModel.taskItems.observeAsState()

    LaunchedEffect(key1 = Unit) {
        viewModel.loadTask()
    }
    PlanZenTheme {
        TaskScreenSkeleton(
            //goBack = goBack,
            openAddTaskSheet = {
                openAddTaskSheet.value = !openAddTaskSheet.value
            },
            retryDataLoad = {
                viewModel.loadTask()
            }
        )
        if (openAddTaskSheet.value) {
            TaskAddSheet(
                showSheet = openAddTaskSheet,
                onSuccess = {
                    viewModel.loadTask()
                }
            )
        }
    }
}

@Preview
@Composable
fun TaskScreenSkeletonPreview() {
    PlanZenTheme {
        TaskScreenSkeleton(
            //goBack = {}

        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskScreenSkeleton(
    //goBack: () -> Unit,
    openAddTaskSheet: () -> Unit = {},
    retryDataLoad: () -> Unit = {}

) {
    Scaffold(
        modifier = Modifier
            .navigationBarsPadding()
            .imePadding()
            .statusBarsPadding(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "PlanZen",
                        fontSize = 24.sp,
                        textAlign = TextAlign.Center,
                        fontFamily = FontFamily.Cursive,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = customColor.AppColor
                )
            )
        },
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            FloatingActionButton(
                containerColor = customColor.AppColor,
                contentColor = Color.White,
                onClick = {
                    openAddTaskSheet()
                }
            ) {
                Icon(Icons.Filled.Add, contentDescription = null)
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {
//                items(list) { todo ->
//                    List(todo)
//                }
            }
        }
    }
}

// @Composable
// fun List() {
// //    val checkedState = remember {
// //        mutableStateOf(task.status)
// //    }
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(4.dp),
//        elevation = CardDefaults.cardElevation(8.dp),
//        colors = CardDefaults.cardColors(Color.White)
//    ) {
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(top = 2.dp)
//                .clickable { },
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            Checkbox(
//                checked = checkedState.value,
//                onCheckedChange = { checkedState.value = it },
//                Modifier.padding(end = 12.dp)
//            )
//            Text(
//                text = task.taskName,
//                fontSize = 16.sp,
//                fontWeight = FontWeight.Medium,
//                color = Color.Black
//            )
//        }
//    }
// }
