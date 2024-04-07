package com.oschmid.tivv.repository

import com.oschmid.tivv.database.UserBookDAO
import com.oschmid.tivv.database.UserBookEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/* Repository dealing with a users monthly addition, updation, deletion of funds
*
* */
class MonthlyDataRepository(private val dao:UserBookDAO) {
    val monhtlyDataEntry = dao.getAllUserTransactionsVM()

//for getting individual data from a monthly log
    suspend fun getParticularEntryOfAMonth(id:Int):UserBookEntity{
        return withContext(Dispatchers.IO){
             dao.getIndividualUserEntry(id)
        }
    }

    //for updating a particular id
    suspend fun updateALedger(userBookEntity:UserBookEntity){
        return withContext(Dispatchers.IO){
            dao.updatLedger(userBookEntity)
        }
    }

    //for deleting a particular record
    suspend fun deleteUserMonthlyEntry(id:Int){

    }

    suspend fun deleteUserMonthlyAllData(){
        withContext(Dispatchers.IO) {
            dao.deleteAllRecords()
        }

    }

}