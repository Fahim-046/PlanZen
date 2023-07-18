package com.example.planzen.screen.task

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import com.example.planzen.models.TaskEntity
import com.example.planzen.ui.theme.PlanZenTheme
import com.example.planzen.ui.theme.customColor
import org.w3c.dom.Text

@Composable
fun TaskScreen(
    viewModel: TaskScreenViewModel,
    navController: NavController,
    // goBack: () -> Unit
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
) {
    val openAddTaskSheet = rememberSaveable {
        mutableStateOf(false)
    }

    val openUpdateSheet = rememberSaveable {
        mutableStateOf(false)
    }

    val userId = viewModel.userId.observeAsState()

    val tasksList = viewModel.taskItems.observeAsState()

    LaunchedEffect(key1 = Unit) {
        viewModel.loadTask()
    }

    TaskScreenSkeleton(
        // goBack = goBack,
        openAddTaskSheet = {
            openAddTaskSheet.value = !openAddTaskSheet.value
        },
        retryDataLoad = {
            viewModel.loadTask()
        },
        itemList = tasksList.value,
        updateTask = { id, task, status ->
            viewModel.updateTask(
                id,
                task,
                status
            )
        },
        openUpdateSheet = {
            openUpdateSheet.value = !openUpdateSheet.value
        },
        getUserId = { taskId ->
            viewModel.getUserId(taskId)
        }
    )

    if (openAddTaskSheet.value) {
        TaskAddSheet(
            showSheet = openAddTaskSheet,
            addTask = { task, status ->
                viewModel.addTask(
                    task,
                    status
                )
            },
            onSuccess = {
                viewModel.loadTask()
            },
            viewModel = viewModel

        )
    }

    if (openUpdateSheet.value) {
        TaskUpdateSheet(
            showSheet = openUpdateSheet,
            updateTask = { id, task, status ->
                viewModel.updateTask(
                    id,
                    task,
                    status
                )
            },
            userId = userId.value ?: 0,
            viewModel = viewModel
        )
    }
}

@Preview
@Composable
fun TaskScreenSkeletonPreview() {
    PlanZenTheme {
        TaskScreenSkeleton(
            itemList = listOf(
                TaskEntity(1, "Fahim", false),
                TaskEntity(2, "Fahim", false),
                TaskEntity(3, "Fahim", false)
            )
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskScreenSkeleton(
    // goBack: () -> Unit,
    openAddTaskSheet: () -> Unit = {},
    retryDataLoad: () -> Unit = {},
    itemList: List<TaskEntity>?,
    updateTask: (Int, String, Boolean) -> Unit = { _, _, _ -> },
    openUpdateSheet: () -> Unit = {},
    getUserId: (Int) -> Unit = { _ -> }

) {
    Scaffold(
        modifier = Modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "PlanZen",
                        fontFamily = FontFamily.Cursive,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = Color.White
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
        },
        containerColor = Color.Red
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            if (itemList != null) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    items(itemList) { todo ->
                        List(todo, updateTask, retryDataLoad, openUpdateSheet, getUserId)
                    }
                }
            } else {
                Text("No Todo!")
            }
        }
    }
}

@Composable
fun List(
    item: TaskEntity,
    updateTask: (Int, String, Boolean) -> Unit,
    retryDataLoad: () -> Unit,
    openUpdateSheet: () -> Unit,
    getUserId: (Int) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .clickable {
            },
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 2.dp)
                .clickable {
                    getUserId(item.id!!)
                    openUpdateSheet()
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = item.taskStatus,
                onCheckedChange = {
                    updateTask(item.id!!, item.task, !item.taskStatus)
                    retryDataLoad()
                },
                Modifier.padding(end = 12.dp)
            )
            Text(
                text = item.task,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )
        }
    }
}
