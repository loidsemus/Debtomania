package me.loidsemus.debtomania

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.loidsemus.debtomania.database.AppDatabase
import me.loidsemus.debtomania.database.Debt
import me.loidsemus.debtomania.database.DebtRepository

class SharedViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: DebtRepository

    val allActiveDebts: LiveData<List<Debt>>
    val allArchivedDebts: LiveData<List<Debt>>

    init {
        val debtDao = AppDatabase.getDatabase(application).debtDao()
        repository = DebtRepository(debtDao)
        allActiveDebts = repository.allActiveDebts
        allArchivedDebts = repository.allArchivedDebts
    }

    fun insert(debt: Debt) = viewModelScope.launch {
        repository.insert(debt)
    }

    fun delete(debt: Debt) = viewModelScope.launch {
        repository.delete(debt)
    }

    fun update(debt: Debt) = viewModelScope.launch {
        repository.update(debt)
    }

}