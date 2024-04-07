package com.oschmid.tivv

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.compose.rememberNavController
import com.oschmid.tivv.routes.NavGraph

import com.oschmid.tivv.ui.theme.LIGHTGREEN
import com.oschmid.tivv.ui.theme.TivvTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TivvTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = LIGHTGREEN
                ) {
                    val basicModifier = Modifier
                        .fillMaxWidth()
                    val navController = rememberNavController()
                    Column() {
                        Text(text = "Tivv", textAlign = TextAlign.Center, modifier = basicModifier, style = MaterialTheme.typography.h1)
                        NavGraph(navController = navController)


                    }

                }
            }
        }
    }
}

