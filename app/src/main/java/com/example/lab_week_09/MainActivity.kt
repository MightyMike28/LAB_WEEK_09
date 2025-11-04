package com.example.lab_week_09

import android.os.Bundle
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
import com.example.lab_week_09.ui.theme.*

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
                    Home()
                }
            }
        }
    }
}

@Composable
fun Home() {
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
            if (inputField.isNotBlank()) {
                listData.add(Student(inputField))
                inputField = ""
            }
        }
    )
}

@Composable
fun HomeContent(
    listData: SnapshotStateList<Student>,
    inputValue: String,
    onInputValueChange: (String) -> Unit,
    onButtonClick: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Judul input
                OnBackgroundTitleText(
                    text = stringResource(id = R.string.enter_item)
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Input field
                TextField(
                    value = inputValue,
                    onValueChange = onInputValueChange,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Tombol Submit
                PrimaryTextButton(
                    text = stringResource(id = R.string.button_click),
                    onClick = onButtonClick
                )
            }
        }

        // Menampilkan daftar item
        items(listData) { item ->
            Column(
                modifier = Modifier
                    .padding(vertical = 4.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OnBackgroundItemText(text = item.name)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHome() {
    LAB_WEEK_09Theme {
        Home()
    }
}
