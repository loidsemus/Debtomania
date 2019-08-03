package me.loidsemus.debtomania.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface DebtDao {

    @Insert
    suspend fun insert(debt: Debt)

    @Delete
    suspend fun delete(debt: Debt)

    @Update
    suspend fun update(debt: Debt)

    @Query("SELECT * from debts_table ORDER BY dateMillis ASC")
    fun getAll(): LiveData<List<Debt>>

    @Query("SELECT * from debts_table WHERE archived = 0 ORDER BY dateMillis ASC")
    fun getAllActive(): LiveData<List<Debt>>

    @Query("SELECT * from debts_table WHERE archived = 1 ORDER BY dateMillis ASC")
    fun getAllArchived(): LiveData<List<Debt>>

}