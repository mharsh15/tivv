package com.oschmid.tivv.views.monthlyBudgetViews

import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.oschmid.tivv.ui.theme.TivvTheme
import com.oschmid.tivv.database.TivwRoomDatabase
import com.oschmid.tivv.database.UserBookConstants
import com.oschmid.tivv.routes.NavigationConstants
import com.oschmid.tivv.viewmodel.FundsADViewModel
import com.oschmid.tivv.viewmodel.FundsADViewModelFactory
import com.oschmid.tivv.views.datePicker.RememberDatePicker
import java.util.*

/*
* this is view compose which creates view for adding/deleting funds
*  viewmodel factory is initiazlied for adding funds
* */
@Preview(showBackground = true)
@Composable
fun AddFundsPreview(){

    TivvTheme {
        val navController = rememberNavController()
        AddUserFunds(navController,0)
    }
}

///main view which holds card for display
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddUserFunds(navController: NavController,id:Int){
    ///for setting amount
    var amount by remember { mutableStateOf("") }
    ///for setting description
    var description by remember { mutableStateOf("") }

    val  context = LocalContext.current


    //initializing DAO
    val dao = TivwRoomDatabase.getInstance(context).userBookDAO
    val factory = FundsADViewModelFactory(dao)
    val viewModel: FundsADViewModel = viewModel(factory = factory )

    //for telling whether data is added
    val status by viewModel.state.observeAsState()

    //sets string date
    val date by viewModel.selectedDateStrnig.observeAsState()
    val dateMills by viewModel.selectedDate.observeAsState()
    ///for moving back to dashboard
    if(status == true){
        viewModel.setStatusFalse()
        navController.popBackStack()
        navController.navigate(NavigationConstants.USER_MONTHLY_LEDGER_DASH){
            popUpTo(NavigationConstants.USER_MONTHLY_LEDGER_DASH){
                inclusive = true
            }
        }

    }

    val display = if(id == UserBookConstants.TRANSACTION_TYPE_CREDIT )  "Add Amount to Balance" else "Remove funds"
    Column(
        Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        //for adding amount
        OutlinedTextField(
            value = amount,
            onValueChange = { amount = it },
            label = { Text(display) },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
        )
        OutlinedTextField(
            value = description,
            onValueChange = {description = it },
            label={ Text("Add Description for amount") }
        )

        //DatePicker(viewModel)

        //val datePicker = DatePicker(viewModel = viewModel)
        val datePicker = RememberDatePicker(viewModel = viewModel, context = context)
        Button(onClick = {
            datePicker.show()
            //datePicker

        }) {
            Text(text = "Date")

        }
        date?.let { Text(text = it  ) }

// onClick

        Button(onClick = {
            //checks whether amount is ok or not
            try {
                Toast.makeText(context,date.toString(),Toast.LENGTH_SHORT).show()
                val amountInt = amount.toInt()
                if (description != "" && dateMills !=null) {
                    if (id == UserBookConstants.TRANSACTION_TYPE_CREDIT) {
                        viewModel.addFunds(amount = amountInt, description = description, dateMills = dateMills!!)
                    } else {
                        viewModel.removeFunds(amount = amountInt, description = description, dateMills = dateMills!!)
                    }
                }
                else
                {
                    Toast.makeText(context,"Kindly make sure to add data in all fields", Toast.LENGTH_SHORT).show()
                }
            }
            catch (exception:Exception){
                Log.v("addfundserr",exception.toString())
                Toast.makeText(context,"Some error happened", Toast.LENGTH_SHORT).show()
            }

        }) {
            Text(text = "Add Amount")
        }

        //

    }

}