package com.oschmid.tivv.views.monthlyBudgetViews

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.oschmid.tivv.database.TivwRoomDatabase
import com.oschmid.tivv.viewmodel.DashboardViewModel
import com.oschmid.tivv.viewmodel.DashboardViewModelFactory
import com.oschmid.tivv.views.allLogsView.UserBookCardsList


@Composable()
fun MonthlyBudgetDisplayAll(navController: NavController, viewModel:DashboardViewModel= viewModel(factory =DashboardViewModelFactory(TivwRoomDatabase.getInstance(LocalContext.current.applicationContext).userBookDAO))){

    val context = LocalContext.current.applicationContext
    val dao = TivwRoomDatabase.getInstance(context).userBookDAO
    val viewModelFactory = DashboardViewModelFactory(dao)
    //val viewModel:DashboardViewModel = viewModel(factory = viewModelFactory)

    //for setting all data
    val userMonthlyBookList by viewModel.userBookListVM.observeAsState(emptyList())
   //val  userMonthlyBookList by viewModel.userBookList.collectAsState()
    UserBookCardsList(userBookList = userMonthlyBookList, context = context,navController)
}