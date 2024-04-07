package com.oschmid.tivv.views.monthlyBudgetViews



import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.oschmid.tivv.database.TivwRoomDatabase
import com.oschmid.tivv.routes.NavigationConstants
import com.oschmid.tivv.viewmodel.MonthlyFundsGUDViewModel
import com.oschmid.tivv.viewmodel.MonthlyFundsGUDViewModelFactory
import com.oschmid.tivv.views.datePicker.RememberDatePickerUIEV

import java.util.*


/**
 * this view is for updating a particular ledger entry, for correcting amount, description
 * date or credit debit type
 * */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun UpdateIndividualLedgerView(navController:NavController,id:Int) {

    val context = LocalContext.current
    val userBookDAO = TivwRoomDatabase.getInstance(context).userBookDAO
    val viewmodelFactory = MonthlyFundsGUDViewModelFactory(userBookDAO,id)
    val viewmodel:MonthlyFundsGUDViewModel = viewModel(factory = viewmodelFactory)
    //intializing data
    val description by viewmodel.description.observeAsState()
    val amount by viewmodel.amount.observeAsState()
    val dateString by viewmodel.dateString.observeAsState()
    val creditDebit by viewmodel.transactionType.observeAsState()
    val navigate by viewmodel.navigate.observeAsState()
    Column() { 
        OutlinedTextField(value = description.toString(),onValueChange = {viewmodel.setDescription(it)}, label = {Text("description")})
            OutlinedTextField(value = amount.toString(),
        onValueChange = { viewmodel.setAmount(it) },
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
    )
        val datePicker = RememberDatePickerUIEV(viewModel = viewmodel, context = context)
        Row(verticalAlignment = Alignment.CenterVertically) {
            Button(onClick = {
                datePicker.show()
                //datePicker

            }) {
                Text(text = "Date")

            }
            dateString?.let { Text(text = it  ) }
        }

        val radioOptions = listOf( "debit","credit")

        creditDebit.let { cd->
            if(cd!=null){
                val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[cd] ) }

                Column {
                    radioOptions.forEach { text ->
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .selectable(
                                    selected = (text == selectedOption),
                                    onClick = {
                                        onOptionSelected(text)
                                    }
                                )
                                .padding(horizontal = 16.dp)
                        ) {
                            RadioButton(
                                selected = (text == selectedOption),
                                onClick = { onOptionSelected(text) },
                                colors = RadioButtonDefaults.colors(selectedColor = Color.Blue)
                            )
                            Text(
                                text = text,
                                style = MaterialTheme.typography.body1.merge(),
                                modifier = Modifier.padding(start = 16.dp)
                            )
                        }
                        viewmodel.updateRadioOption(selectedOption)
                    }


                }
            }

        }
        Button(onClick = {
            try{
                if(amount?.toInt()!! > 0 &&  description?.length!! >0){
                    viewmodel.updateIndividualLedger()
                     }
            }
            catch (exception:Exception){
                Log.v("uilv_update",exception.toString())
                Toast.makeText(context,"Some Error Happened",Toast.LENGTH_SHORT).show()
            }
            }) {
            Text("Update Ledger")

        }

        if(navigate == true){
            navController.popBackStack(NavigationConstants.USER_MONTHLY_LEDGER_LIST,inclusive = false)
        }

    }

}

//    OutlinedTextField(value = description,
//        onValueChange = {description = it},
//        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
//    )


