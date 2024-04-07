package com.oschmid.tivv.database

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

object UserBookConstants {

    const val TABLE_NAME = "user_transaction_table"
    const val TRANSACTION_TYPE_CREDIT = 1
    const val TRANSACTION_TYPE_DEBIT = 0
    const val amount="amount"
    const val nonIntegerAmount = "nd_amount"
    const val transactionType="transaction_type"
    const val description="user_description"
    const val dateMills="date_mills"

}