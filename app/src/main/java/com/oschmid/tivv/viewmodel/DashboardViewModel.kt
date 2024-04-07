package com.oschmid.tivv.viewmodel

import android.util.Log
import android.widget.ViewSwitcher.ViewFactory
import androidx.lifecycle.*
import com.oschmid.tivv.database.UserBookDAO
import com.oschmid.tivv.database.UserBookEntity
import com.oschmid.tivv.repository.MonthlyDataRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DashboardViewModel(private val dao:UserBookDAO):ViewModel() {

    val _name=MutableLiveData<String>()
    private val _userBookList = MutableStateFlow(emptyList<UserBookEntity>())
    val userBookList:StateFlow<List<UserBookEntity>> = _userBookList
    val userBookListVM:LiveData<List<UserBookEntity>>
    get() = dao.getAllUserTransactionsVM()
    val netAdditions: LiveData<Int?>
    get() = dao.getUserTotalAddedMoney()
    val netDebit:LiveData<Int?>
        get()=dao.getUserTotalDebitedMoney()
    val netMoney:LiveData<Int?>
        get()=dao.getUserNetMoney()
    val lastTransactionDate =dao.getLastTransactionDate()
    val repository = MonthlyDataRepository(dao)

    val job = Job()
    val scope = CoroutineScope(Dispatchers.Main + job)

    override fun onCleared() {

        super.onCleared()
        job.cancel()
    }

    init {
        _name.value ="harsh"

        setUserList()
    }

    //for connecting to flow for observing any changes to data
    private fun setUserList(){
        viewModelScope.launch {
            dao.getAllUserTransactions().collect{ result ->
                _userBookList.value = result

            }

        }
    }

    // to delete entire data

    fun deleteAllData(){
        try {
            scope.launch(Dispatchers.IO) {

                repository.deleteUserMonthlyAllData()
            }
        }
        catch (exception:Exception){
            Log.v("dash_delete_all_exc",exception.toString())
        }

    }

}

class DashboardViewModelFactory(private val dao:UserBookDAO):ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

            if (modelClass.isAssignableFrom(DashboardViewModel::class.java))
            {
                return DashboardViewModel(dao) as T
            }
            throw IllegalArgumentException("THis is not FundsAD VM")

    }
}
