package com.oschmid.tivv.database
import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Entity(tableName = UserBookConstants.TABLE_NAME)
data class UserBookEntity(
    @PrimaryKey(autoGenerate = true) val id:Int=0,
    @ColumnInfo(name=UserBookConstants.amount)
    var amount:Int,
    @ColumnInfo(name=UserBookConstants.nonIntegerAmount)
    val nonIntegerAmount:Int,
    @ColumnInfo(name = UserBookConstants.transactionType)
    val transactionType:Int,
    @ColumnInfo(name = UserBookConstants.description)
    var description:String,
    @ColumnInfo(name = UserBookConstants.dateMills)
    val dateMills: Long
)
@Dao
interface UserBookDAO{
    //add data
    @Insert
    fun addEntry(entry:UserBookEntity)

    //get all data
    @Query("SELECT * FROM ${UserBookConstants.TABLE_NAME}")
    fun getAllUserTransactions(): Flow<List<UserBookEntity>>

    //get all data
    @Query("SELECT * FROM ${UserBookConstants.TABLE_NAME}")
    fun getAllUserTransactionsVM(): LiveData<List<UserBookEntity>>

    //get a particular entry
    @Query("SELECT * from ${UserBookConstants.TABLE_NAME} WHERE id=:id")
    fun getIndividualUserEntry(id:Int):UserBookEntity

    //get sum of all money invested
    @Query("SELECT SUM(${UserBookConstants.amount}) FROM ${UserBookConstants.TABLE_NAME} WHERE ${UserBookConstants.transactionType}=${UserBookConstants.TRANSACTION_TYPE_CREDIT}")
    fun getUserTotalAddedMoney():LiveData<Int?>

    //get all money debited
    @Query("SELECT SUM(${UserBookConstants.amount}) FROM ${UserBookConstants.TABLE_NAME} WHERE ${UserBookConstants.transactionType}=${UserBookConstants.TRANSACTION_TYPE_DEBIT}")
    fun getUserTotalDebitedMoney():LiveData<Int?>

    //new money
    //get all money debited
    @Query("SELECT SUM(${UserBookConstants.amount}) FROM ${UserBookConstants.TABLE_NAME}")
    fun getUserNetMoney():LiveData<Int?>

    //get latest date of amount added
    @Query("SELECT ${UserBookConstants.dateMills} FROM ${UserBookConstants.TABLE_NAME} ORDER BY ${UserBookConstants.dateMills} DESC LIMIT 1")
    fun getLastTransactionDate():LiveData<Long?>

    //update a particular entry
    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updatLedger(item: UserBookEntity): Int

    //deletes all records
    @Query("DELETE  FROM ${UserBookConstants.TABLE_NAME}")
    fun deleteAllRecords()

}

