package com.oschmid.tivv.routes

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.oschmid.tivv.database.TivwRoomDatabase
import com.oschmid.tivv.views.Dashboard
import com.oschmid.tivv.views.monthlyBudgetViews.*


@Composable
fun NavGraph(navController: NavHostController){
    val context = LocalContext.current.applicationContext
    val dao = TivwRoomDatabase.getInstance(context = context).userBookDAO
    NavHost(navController = navController, startDestination = NavigationConstants.DASHBOARD )

    {
        //starting dash board
        composable(NavigationConstants.DASHBOARD)
        {
            Dashboard(navController)
        }
        // dashboard for monnthly balance of user
        composable(NavigationConstants.USER_MONTHLY_LEDGER_DASH){
            MonthlyBudgetDashboard(navController = navController,dao)
        }
        //for adding and substracting user monthly budget
        composable(NavigationConstants.USER_MONTHLY_LEDGER_ADD){backStackEntry ->
            AddUserFunds(navController = navController,id = backStackEntry.arguments?.getString("id")!!.toInt())

        }
        
        //for showing list of all budget in a month
        composable(NavigationConstants.USER_MONTHLY_LEDGER_LIST){
            MonthlyBudgetDisplayAll(navController = navController)
        }

        //for seeing a particular user budget data
        composable(NavigationConstants.USER_INDIVIDUAL_LEDGER_VIEW){backStackEntry->
            MonthlyIndividualLedgerDisplay(navController = navController, id =backStackEntry.arguments?.getString("id")!!.toInt() )
        }

        //for showing menu updating or deleting a budget
        composable(NavigationConstants.USER_INDIVIDUAL_LEDGER_UPDATE){navBackStackEntry ->
            UpdateIndividualLedgerView(navController = navController, id = navBackStackEntry.arguments?.getString("id")!!.toInt())

        }
    }

}