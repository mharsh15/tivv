package com.oschmid.tivv.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.oschmid.tivv.R
// for heading
val Lobster = FontFamily(
    Font(R.font.lobster)
)

val FireSansRegularFont = FontFamily(
    Font(R.font.fire_sans_regular)

)

val Orbiton = FontFamily(Font(R.font.orbitron))
val SecularOneFont = FontFamily(Font(R.font.secular_one_regualar))
// Set of Material typography styles to start with
val Typography = Typography(

    body1 = TextStyle(
        fontFamily = FireSansRegularFont,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    h1 = TextStyle(fontFamily = Lobster, fontSize = 32.sp ),
    h2 = TextStyle(fontFamily = SecularOneFont, fontSize = 36.sp )

    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)
