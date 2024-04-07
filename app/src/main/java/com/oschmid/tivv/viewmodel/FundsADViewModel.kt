package com.oschmid.tivv.viewmodel

import android.util.Log
import androidx.compose.runtime.setValue
import androidx.lifecycle.*
import com.oschmid.tivv.convertUnixToDate
import com.oschmid.tivv.database.UserBookConstants
import com.oschmid.tivv.database.UserBookDAO
import com.oschmid.tivv.database.UserBookEntity
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.*

class FundsADViewModel(private val dao: UserBookDAO):ViewModel() {

    var _funds = MutableStateFlow(emptyList<UserBookEntity>())
    var funds :StateFlow<List<UserBookEntity>> = _funds

    private val _selectedDate = MutableLiveData<Long>()
    val selectedDate:LiveData<Long>
    get() = _selectedDate

    private val _selectedDateString = MutableLiveData<String>()
    val selectedDateStrnig:LiveData<String>
        get() = _selectedDateString

    private val viewModelJob = Job()
    private val scope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _state = MutableLiveData<Boolean>()
    val state: LiveData<Boolean>
        get() = _state



    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    init {
       //Log.v("val", dao.getAllUserTransactions().value.toString())
        _state.value =false
        _selectedDate.value = (Calendar.getInstance().timeInMillis/1000)
        _selectedDateString.value = convertUnixToDate(Calendar.getInstance().timeInMillis/1000)
        getFunds()

    }
    //for adding Funds
    fun addFunds(amount:Int, description:String,dateMills:Long){
        val entry = UserBookEntity(amount = amount, description = description, nonIntegerAmount = amount, transactionType = UserBookConstants.TRANSACTION_TYPE_CREDIT, dateMills = dateMills)
        scope.launch {
            _state.value = addFundsJob(entry)
        }
    }
    //for removing funds
    fun removeFunds(amount:Int,description:String,dateMills: Long){
        val debit = UserBookEntity(amount = (-1 *amount), description = description, nonIntegerAmount = amount, transactionType = UserBookConstants.TRANSACTION_TYPE_DEBIT, dateMills = dateMills)
        scope.launch {
            _state.value = addFundsJob(entry = debit)
        }

    }

    //setting status false
    fun setStatusFalse(){
        _state.value =false
    }
    //for getting list of all fubds
    fun getFunds(){
        viewModelScope.launch {
            dao.getAllUserTransactions().collect { response ->
                _funds.value = response

                Log.v("adr",funds.value.size.toString())
            }
        }
    }

    //setting date
    fun setSelectedDate(newDate:Long){
        _selectedDate.value = newDate
        _selectedDateString.value = convertUnixToDate(newDate)
    }

    private suspend fun addFundsJob(entry:UserBookEntity):Boolean {

           return withContext(Dispatchers.IO){
                try {
                    dao.addEntry(entry)
                    return@withContext true
                }
                catch (exception:Exception){
                    Log.v("FundsADVM",exception.toString())
                    return@withContext false
                }
            }



    }
}

class FundsADViewModelFactory(private val dao: UserBookDAO): ViewModelProvider.Factory{
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FundsADViewModel::class.java))
        {
                return FundsADViewModel(dao) as T
        }
        throw IllegalArgumentException("THis is not FundsAD VM")
    }
}