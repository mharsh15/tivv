package com.oschmid.tivv.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.oschmid.tivv.convertUnixToDate
import com.oschmid.tivv.database.UserBookConstants
import com.oschmid.tivv.database.UserBookDAO
import com.oschmid.tivv.database.UserBookEntity
import com.oschmid.tivv.repository.MonthlyDataRepository
import kotlinx.coroutines.*

/**
 * GUD stands for Get(individualID, Update IndividualID, delete Individual ID
 *
 *  */
private lateinit var repository: MonthlyDataRepository
class MonthlyFundsGUDViewModel(userBookDao:UserBookDAO, private val id:Int):ViewModel() {

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(viewModelJob+Dispatchers.Main)
    private val _monthlyData=MutableLiveData<UserBookEntity?>()
    val monthlyData: MutableLiveData<UserBookEntity?>
    get() = _monthlyData

    private val _description=MutableLiveData<String>()
    val description:LiveData<String>
    get() = _description

    private val _amount = MutableLiveData<String>()
    val amount:LiveData<String>
    get() = _amount

    private val _nonIntegerAmount = MutableLiveData<Int>()
    val nonIntegerAmount:LiveData<Int>
    get() = _nonIntegerAmount


    private val _dateString = MutableLiveData<String>()
    val dateString:LiveData<String>
    get() = _dateString

    private val _dateMills=MutableLiveData<Long>()


    private val _transactionType=MutableLiveData<Int>()
    val transactionType:LiveData<Int>
        get() = _transactionType

    private val _radioOption=MutableLiveData<String>()
    val radioOption:LiveData<String>
        get() = _radioOption

    private val _navigate=MutableLiveData<Boolean>()
    val navigate:LiveData<Boolean>
        get() = _navigate
    init{
        repository= MonthlyDataRepository(userBookDao)
        getIndividualData()
        _navigate.value =false
    }

     private fun getIndividualData(){
        uiScope.launch{
            val data = getIndividualDataCoroutine()
             _monthlyData.value =data
            _description.value = data?.description
            _amount.value = data?.nonIntegerAmount.toString()
            _dateMills.value = data?.dateMills
            _dateString.value = convertUnixToDate(data?.dateMills)
            _nonIntegerAmount.value =data?.nonIntegerAmount
            _transactionType.value = data?.transactionType

        }

    }

    //setDescription
    fun setDescription(description:String){
        _description.value=description
    }

    //update amount
    fun setAmount(amt:String){
        _amount.value = amt
    }
    //setting date
    fun setSelectedDate(newDate:Long){
        _dateMills.value = newDate
        _dateString.value = convertUnixToDate(newDate)
    }

    fun updateRadioOption(option:String){
        _radioOption.value = option
    }
    suspend fun getIndividualDataCoroutine():UserBookEntity?{
        return withContext(Dispatchers.IO) { return@withContext repository.getParticularEntryOfAMonth(id) }

    }
     fun updateIndividualLedger(){
        uiScope.launch {
            val amt = if(_radioOption.value === "debit") _amount.value!!.toInt() * -1 else _amount.value!!.toInt()
            val transType = if(_radioOption.value == "credit") UserBookConstants.TRANSACTION_TYPE_CREDIT else UserBookConstants.TRANSACTION_TYPE_DEBIT
            if(_dateMills.value != null){
                val userBook = UserBookEntity(
                    id =id,  nonIntegerAmount = _amount.value!!.toInt(),
                    amount = amt, description = _description.value.toString(), transactionType = transType, dateMills = _dateMills.value!!
                )
                val response = repository.updateALedger(userBook)
                _navigate.value =true
                Log.v("mfGUDVM",response.toString())


            }

        }



    }



}


class MonthlyFundsGUDViewModelFactory(val userBookDao:UserBookDAO,val id:Int):ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MonthlyFundsGUDViewModel::class.java)){
                return MonthlyFundsGUDViewModel(userBookDao,id) as T
            }
            throw IllegalArgumentException("This is not MonthlyFundsGUDVIewModel")

} }