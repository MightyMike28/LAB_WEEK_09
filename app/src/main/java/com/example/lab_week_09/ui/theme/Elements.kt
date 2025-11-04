package com.example.lab_week_09.ui.theme

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

// UI Element for displaying a title
@Composable
fun OnBackgroundTitleText(text: String) {
    TitleText(
        text = text,
        color = MaterialTheme.colorScheme.onBackground
    )
}

// Title style using typography
@Composable
fun TitleText(text: String, color: Color) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleLarge,
        color = color
    )
}

// UI Element for displaying an item list
@Composable
fun OnBackgroundItemText(text: String) {
    ItemText(
        text = text,
        color = MaterialTheme.colorScheme.onBackground
    )
}

// Item style using bodySmall typography
@Composable
fun ItemText(text: String, color: Color) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodySmall,
        color = color
    )
}

// UI Element for displaying a button
@Composable
fun PrimaryTextButton(text: String, onClick: () -> Unit) {
    CustomTextButton(
        text = text,
        textColor = Color.White,
        onClick = onClick
    )
}

// Custom reusable button with consistent theme
@Composable
fun CustomTextButton(text: String, textColor: Color, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.padding(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.DarkGray,
            contentColor = textColor
        )
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelMedium
        )
    }
}
