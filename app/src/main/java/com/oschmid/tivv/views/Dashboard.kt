package com.oschmid.tivv.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.oschmid.tivv.routes.NavigationConstants

@Composable
fun Dashboard(navcotroller:NavController){

    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        Button(onClick = { navcotroller.navigate(NavigationConstants.USER_MONTHLY_LEDGER_DASH) }) {
            Text("Click Here to check Monthly Balance")
        }
        Text("version: V_0_0_4")
    }

}