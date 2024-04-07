package com.oschmid.tivv.views.monthlyBudgetViews

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.oschmid.tivv.convertUnixToDate
import com.oschmid.tivv.database.TivwRoomDatabase
import com.oschmid.tivv.viewmodel.MonthlyFundsGUDViewModel
import com.oschmid.tivv.viewmodel.MonthlyFundsGUDViewModelFactory


///to display data of a particular month
@Composable
fun MonthlyIndividualLedgerDisplay(navController: NavController,id:Int, ){
    val context = LocalContext.current.applicationContext
    val dao = TivwRoomDatabase.getInstance(context = context).userBookDAO
    val factory = MonthlyFundsGUDViewModelFactory(dao,id)
    val viewModel: MonthlyFundsGUDViewModel = viewModel(factory = factory)
    val userData = viewModel.monthlyData.observeAsState(initial = null)
    val baseModifier = Modifier
        .verticalScroll(rememberScrollState())
        .fillMaxSize()

        .padding(8.dp)
    Column() {

            Text(userData.value?.amount.toString())
            userData.value?.description?.let { Text(text = it) }
            Text(convertUnixToDate(userData.value?.dateMills))

    }
}


