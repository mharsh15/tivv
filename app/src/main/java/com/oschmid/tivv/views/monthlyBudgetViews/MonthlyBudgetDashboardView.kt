package com.oschmid.tivv.views.monthlyBudgetViews
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.oschmid.tivv.database.TivwRoomDatabase
import com.oschmid.tivv.R
import com.oschmid.tivv.convertUnixToDate
import com.oschmid.tivv.database.UserBookConstants
import com.oschmid.tivv.database.UserBookDAO
import com.oschmid.tivv.routes.NavigationConstants
import com.oschmid.tivv.ui.theme.LIGHTGREEN
import com.oschmid.tivv.ui.theme.TivvTheme
import com.oschmid.tivv.viewmodel.DashboardViewModel
import com.oschmid.tivv.viewmodel.DashboardViewModelFactory
import com.oschmid.tivv.views.cardVIew.DashNumberCard
import com.oschmid.tivv.views.cardVIew.DashStringCard

@Composable
fun MonthlyBudgetDashboard(navController: NavController,dao:UserBookDAO,viewModel:DashboardViewModel = viewModel(factory =DashboardViewModelFactory(dao))) {

    //val factory = DashboardViewModelFactory(dao)
    //val viewModel:DashboardViewModel = viewModel(factory = factory)

    //val userBookEntity by viewModel.userBookList.collectAsState()
    val userBookListVM by viewModel.userBookListVM.observeAsState(emptyList())
    val totalInvestment = viewModel.netAdditions.observeAsState(initial = 0)
    val totalDebit by viewModel.netDebit.observeAsState(0)
    val netAmount by viewModel.netMoney.observeAsState(0)
    val lastTransactionDate by viewModel.lastTransactionDate.observeAsState(0L)
    val painter: Painter =if(netAmount!= null)


    {
        if(netAmount!! > 0)
        {   painterResource(id = R.drawable.ic_baseline_thumb_up_24) }
        else {  painterResource(id = R.drawable.ic_baseline_thumb_down_24) }
    }
    else {
        painterResource(id = R.drawable.ic_baseline_dashboard_24)
    }
    val basicModifier = Modifier
        .fillMaxWidth()

        //.wrapContentWidth(Alignment.CenterHorizontally)

    Column(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(18.dp)
            .verticalScroll(rememberScrollState())

            ) {

        //Log.v("int", totalInvestment.toString())



        Image(painter = painter, contentDescription = "thumbs up", modifier= Modifier
            .size(size = 64.dp)
            .fillMaxWidth(), alignment = Alignment.Center)
        DashNumberCard(num = netAmount, description = "Balance")
        DashNumberCard(num = totalDebit, description = "Expense")
        DashNumberCard(num = totalInvestment.value, description = "Budget")
        DashStringCard(date = convertUnixToDate(lastTransactionDate), description = "Last Transaction")


//        button to take care of add, substract, reset, see all funds
        Row() {
            /* For adding amount */
            val dollarIcon  = painterResource(id = R.drawable.ic_baseline_attach_money_24)
            val addIcon = painterResource(id = R.drawable.ic_baseline_add_24)
            val substractIcon = painterResource(id = R.drawable.baseline_substract_24)
            val listIcon = painterResource(id = R.drawable.ic_baseline_format_list_numbered_24)
            val deleteIcon = painterResource(id = R.drawable.ic_baseline_delete_24)
            val buttonModifier = Modifier
                .weight(1F)
                .shadow(4.dp, shape = RectangleShape)
            //button to add funds
            Button(onClick = {
                navController.navigate(NavigationConstants.USER_MONTHLY_LEDGER_ADD_PRE+UserBookConstants.TRANSACTION_TYPE_CREDIT)
            },

                colors = ButtonDefaults.buttonColors(LIGHTGREEN),
                modifier = buttonModifier

            ) {

//                Image(painter = dollarButton , contentDescription = "add money")
                Image(painter = addIcon, contentDescription = "add", Modifier.size(42.dp))
            }

            /* For removing amount */
           Button(onClick = {
                navController.navigate(NavigationConstants.USER_MONTHLY_LEDGER_ADD_PRE+UserBookConstants.TRANSACTION_TYPE_DEBIT)
            },

               colors = ButtonDefaults.buttonColors(LIGHTGREEN),
               modifier = buttonModifier
            ) {

//                Image(painter = dollarButton , contentDescription = "remove money")
               Image(painter = substractIcon, contentDescription = "substract", Modifier.size(42.dp))
            }

            /* to delete a months data*/
            Button(onClick = {
                navController.navigate(NavigationConstants.USER_MONTHLY_LEDGER_LIST)

            },
                colors = ButtonDefaults.buttonColors(LIGHTGREEN),
                modifier = buttonModifier
            ) {

//                Image(painter = dollarButton , contentDescription = "remove money")
                Image(painter = listIcon, contentDescription = "see all", Modifier.size(42.dp))
            }


            /* For deleting data */
            Button(onClick = {
                viewModel.deleteAllData()
            },
                colors = ButtonDefaults.buttonColors(LIGHTGREEN),
                modifier = buttonModifier
            ) {

//                Image(painter = dollarButton , contentDescription = "remove money")
                Image(painter = deleteIcon, contentDescription = "Delete data", Modifier.size(42.dp))
            }


        }

    }



}

@Preview(showBackground = true)
@Composable
fun MonthlyBudgetDashboardPreview() {
    TivvTheme {
        val navController = rememberNavController()
        val context = LocalContext.current.applicationContext
        val dao = TivwRoomDatabase.getInstance(context = context).userBookDAO
        MonthlyBudgetDashboard(navController = navController,dao)
    }
}