package com.oschmid.tivv.views.allLogsView

import com.oschmid.tivv.R
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.oschmid.tivv.convertUnixToDate
import com.oschmid.tivv.database.UserBookEntity
import com.oschmid.tivv.routes.NavigationConstants
import com.oschmid.tivv.ui.theme.*


@Composable
fun UserBookCardsList(userBookList:List<UserBookEntity>,context:Context,navController: NavController){
    LazyColumn{
        items(userBookList, key ={userBook-> userBook.id
        } ){ userBook->
            UserBookCardView(userBook = userBook, context = context, navController = navController)

        }
    }

}
// card view for UserBook
@Composable
fun UserBookCardView(userBook:UserBookEntity,context:Context,navController: NavController){
    val modifyAmount = Modifier
        .padding(bottom = 10.dp)
        .fillMaxHeight()
        .wrapContentWidth()

Card(elevation = 4.dp, modifier = modifyAmount.fillMaxWidth()) {
    val color = if(userBook.amount>=0)Color.Green else Color.Red
    val infoIcon = painterResource(id = R.drawable.ic_baseline_info_24)
    val editIcon = painterResource(id = R.drawable.ic_baseline_edit_24)
    Column() {
        Row( modifier = modifyAmount
            .fillMaxWidth()
            .clickable {
                Toast
                    .makeText(context, userBook.description, Toast.LENGTH_SHORT)
                    .show()

            }) {

            Divider(
                color = color,
                thickness =24.dp,
                modifier = Modifier
                    .fillMaxHeight() //fill the max height
                    .width(8.dp)

            )
            Spacer(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(2.dp)
                    .background(Color.Black)
            )

            Text(text = "Rs "+ userBook.amount.toString(), fontFamily = SecularOneFont,fontSize = 20.sp, modifier = modifyAmount)
            Text(text = convertUnixToDate(userBook.dateMills) , fontFamily = Orbiton, fontSize = 16.sp, modifier = modifyAmount.fillMaxWidth(), textAlign = TextAlign.End)

        }
        //for navigating to individual ledger based on id
        Row(){
            Button(
                onClick = {navController.navigate(NavigationConstants.USER_INDIVIDUAL_LEDGER_VIEW_PRE+userBook.id)},
                colors = ButtonDefaults.buttonColors(LIGHTGREEN),
            ){
                Image(painter = infoIcon, contentDescription = "info")
            }

            Button(
                onClick = {
                    navController.navigate(NavigationConstants.USER_INDIVIDUAL_LEDGER_UPDATE_PRE+userBook.id)
                },
                colors = ButtonDefaults.buttonColors(LIGHTGREEN)

            ){
                Image(painter = editIcon, contentDescription = "edit")
            }
        }


    }

}


}

@Preview(showBackground = true)
@Composable
fun UserBookLisrPreview(){
    TivvTheme() {
        val cards = listOf<UserBookEntity>(
            UserBookEntity(0,100,100,0,"This is Test",1654609370),
                    UserBookEntity(1,100,100,0,"This is Test",1654609370)
        )
            val context = LocalContext.current
            UserBookCardsList(cards, context, navController = NavController(context))
    }

}