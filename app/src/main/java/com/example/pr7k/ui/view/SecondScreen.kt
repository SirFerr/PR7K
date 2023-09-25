package com.example.pr7k.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun second(modifier: Modifier) {
    Column(

        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally,

        modifier = modifier
            .padding(16.dp)
            .fillMaxSize(),

        ) {
        Text(style = MainActivity.TextStyles.testTextStyle, text = "Shillo Sergey IKBO-25-21")
    }

}