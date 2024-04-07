package com.oschmid.tivv.views.cardVIew

import android.graphics.drawable.shapes.Shape
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.fontResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.oschmid.tivv.R
import com.oschmid.tivv.ui.theme.Orbiton
import com.oschmid.tivv.views.datePicker.DatePicker


@Composable
@Preview
fun CardDisplay(){
    DashNumberCard(num = 32000, description = "Total Funds")
}

@Composable
fun DashNumberCard( num:Int?, description:String){
    var setNum = 0
    if(num == null){setNum = 0} else {setNum = num}
    Card(elevation = 6.dp, modifier = Modifier
        .padding(8.dp) ) {
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally, ) {
            Text(text = setNum.toString(),style = MaterialTheme.typography.h2, textAlign = TextAlign.Center)
            Text(text = description.uppercase(), fontSize = 12.sp)

        }
    }

}

@Composable
fun DashStringCard( date:String, description:String){

    Card(elevation = 6.dp, modifier = Modifier
        .padding(8.dp) ) {
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally, ) {
            Text(text = date,style = MaterialTheme.typography.h2, textAlign = TextAlign.Center)
            Text(text = description.uppercase(), fontSize = 12.sp)

        }
    }

}