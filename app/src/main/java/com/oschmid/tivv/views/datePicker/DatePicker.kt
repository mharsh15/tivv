package com.oschmid.tivv.views.datePicker

import android.app.DatePickerDialog
import android.content.Context
import android.os.Build
import android.widget.CalendarView
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.oschmid.tivv.viewmodel.FundsADViewModel
import com.oschmid.tivv.viewmodel.MonthlyFundsGUDViewModel
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DatePicker(viewModel:FundsADViewModel){
    Row(modifier = Modifier
        .padding(6.dp),
        verticalAlignment = Alignment.Top) {

        AndroidView(
            { CalendarView(it) },
            modifier = Modifier.wrapContentWidth(),
            update = { views ->

                views.setOnDateChangeListener { calendarView, year, month, dayOfMonth ->
                    val cal = Calendar.getInstance()
                    cal.set(year, month, dayOfMonth)
                    viewModel.setSelectedDate(cal.timeInMillis/1000)

                }
            }
        )

        Text(
            text = "Today",
            modifier = Modifier
                .wrapContentWidth(),

            fontWeight = FontWeight.SemiBold,
            fontSize = 10.sp,
            color = Color(0xFF0A70C4),
            textAlign = TextAlign.Center,
        )

    }

}
@Composable
fun RememberDatePicker(viewModel: FundsADViewModel, context: Context): DatePickerDialog {
    val cal = Calendar.getInstance()
    val context = context
    val datePickerDialog = DatePickerDialog(
        context,

        { _, year: Int, month: Int, dayOfMonth: Int ->
            println("$year, $month, $dayOfMonth")
            val cal = Calendar.getInstance()
            cal.set(year, month, dayOfMonth)

            viewModel.setSelectedDate(cal.timeInMillis/1000)

        },
        cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)
    )

    return remember { datePickerDialog }
}

@Composable
fun RememberDatePickerUIEV(viewModel: MonthlyFundsGUDViewModel, context: Context): DatePickerDialog {
    val cal = Calendar.getInstance()
    val context = context
    val datePickerDialog = DatePickerDialog(
        context,

        { _, year: Int, month: Int, dayOfMonth: Int ->
            println("$year, $month, $dayOfMonth")
            val cal = Calendar.getInstance()
            cal.set(year, month, dayOfMonth)

            viewModel.setSelectedDate(cal.timeInMillis/1000)

        },
        cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)
    )

    return remember { datePickerDialog }
}
