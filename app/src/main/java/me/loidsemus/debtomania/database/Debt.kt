package me.loidsemus.debtomania.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "debts_table")
data class Debt(
    @PrimaryKey(autoGenerate = true) var id: Int,
    var amount: Double,
    var person: String,
    var dateMillis: Long,
    var archived: Boolean = false
)