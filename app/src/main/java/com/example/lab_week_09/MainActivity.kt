package com.example.lab_week_09

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.lab_week_09.ui.theme.*
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

data class Student(var name: String)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LAB_WEEK_09Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    App(navController)
                }
            }
        }
    }
}

@Composable
fun Home(
    navigateToResult: (List<Student>) -> Unit
) {
    val listData = remember {
        mutableStateListOf(
            Student("Tanu"),
            Student("Tina"),
            Student("Tono")
        )
    }

    var inputField by remember { mutableStateOf("") }

    HomeContent(
        listData = listData,
        inputValue = inputField,
        onInputValueChange = { inputField = it },
        onButtonClick = {
            // ✅ Cegah input kosong
            if (inputField.isNotBlank()) {
                listData.add(Student(inputField))
                inputField = ""
            }
        },
        navigateFromHomeToResult = {
            navigateToResult(listData)
        }
    )
}

@Composable
fun HomeContent(
    listData: SnapshotStateList<Student>,
    inputValue: String,
    onInputValueChange: (String) -> Unit,
    onButtonClick: () -> Unit,
    navigateFromHomeToResult: () -> Unit
) {
    LazyColumn {
        item {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OnBackgroundTitleText(text = stringResource(id = R.string.enter_item))

                TextField(
                    value = inputValue,
                    onValueChange = { onInputValueChange(it) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                )

                Row {
                    PrimaryTextButton(text = stringResource(id = R.string.button_click)) {
                        onButtonClick()
                    }
                    PrimaryTextButton(text = stringResource(id = R.string.button_navigate)) {
                        navigateFromHomeToResult()
                    }
                }
            }
        }

        items(listData) { item ->
            Column(
                modifier = Modifier
                    .padding(vertical = 4.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OnBackgroundItemText(text = item.name)
            }
        }
    }
}

@Composable
fun App(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            Home { list ->
                // ✅ Simpan data di SavedStateHandle
                navController.currentBackStackEntry?.savedStateHandle?.set("students", list)
                navController.navigate("result")
            }
        }

        composable("result") {
            // ✅ Ambil data dari SavedStateHandle
            val students =
                navController.previousBackStackEntry?.savedStateHandle?.get<List<Student>>("students")
                    ?: emptyList()
            ResultContent(students)
        }
    }
}

@Composable
fun ResultContent(students: List<Student>) {
    Column(
        modifier = Modifier
            .padding(vertical = 4.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OnBackgroundTitleText(text = "List of Students:")

        LazyColumn {
            items(students) { student ->
                OnBackgroundItemText(text = student.name)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHome() {
    LAB_WEEK_09Theme {
        Home {}
    }
}
