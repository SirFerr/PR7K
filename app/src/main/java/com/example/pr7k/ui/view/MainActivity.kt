@file:Suppress("DEPRECATION")
@file:OptIn(
    ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class
)

package com.example.pr7k.ui.view

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pr7k.ui.theme.PR7KTheme
import kotlinx.coroutines.launch
import kotlin.system.exitProcess

class MainActivity : ComponentActivity() {
    object TextStyles {
        val testTextStyle = TextStyle(
            fontSize = 24.sp,
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Light,
            color = Color.White
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PR7KTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background

                ) {
                    drawerDefaults()
                }
            }
        }
    }
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun drawerDefaults() {

    val drawerState = rememberDrawerState(DrawerValue.Closed)

    ModalNavigationDrawer(
        drawerState = drawerState,


        drawerContent = {
            Row(
                modifier = Modifier

                    .fillMaxSize(),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(250.dp)
                        .background(MaterialTheme.colorScheme.surface),

                    ) {
                    for (i in 0..4)
                        Text("Item $i")

                }

            }
        },


        content = { scaffoldDefaults(drawerState) }


    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun scaffoldDefaults(drawerState: DrawerState) {
    val scope = rememberCoroutineScope()


    var navController: NavHostController? = null

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("URL to Image") },
                navigationIcon = {
                    IconButton(onClick = {
                        scope.launch {
                            drawerState.open()
                        }
                    })
                    {
                        Icon(
                            Icons.Filled.Menu, contentDescription =
                            "Menu"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { exitProcess(-1) }) {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = "Localized description"
                        )
                    }
                },

                )
        },
        content = { innerPadding ->
            navController = navigation(innerPadding)
        },
        bottomBar = {
            BottomAppBar {

                IconButton(onClick = {
                    navController?.navigate("urlToImage")
                })
                {
                    Icon(
                        Icons.Filled.Home, contentDescription =
                        "Home"
                    )
                }
                IconButton(onClick = {
                    navController?.navigate("second")

                })
                {
                    Icon(
                        Icons.Filled.Star, contentDescription =
                        "Second"
                    )
                }


            }


        }
    )
}


@Composable
fun navigation(innerPadding: PaddingValues): NavHostController {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "urlToImage") {
        composable("urlToImage") { urlToImage(modifier = Modifier.padding(innerPadding)) }
        composable("second") { second(modifier = Modifier.padding(innerPadding)) }
    }
    return navController
}




