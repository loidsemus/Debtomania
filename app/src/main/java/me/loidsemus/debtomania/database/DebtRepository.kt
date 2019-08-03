package me.loidsemus.debtomania.database

import androidx.lifecycle.LiveData

class DebtRepository(private val debtDao: DebtDao) {

    val allActiveDebts: LiveData<List<Debt>> = debtDao.getAllActive()
    val allArchivedDebts: LiveData<List<Debt>> = debtDao.getAllArchived()

    suspend fun insert(debt: Debt) {
        debtDao.insert(debt)
    }

    suspend fun delete(debt: Debt) {
        debtDao.delete(debt)
    }

    suspend fun update(debt: Debt) {
        debtDao.update(debt)
    }

}