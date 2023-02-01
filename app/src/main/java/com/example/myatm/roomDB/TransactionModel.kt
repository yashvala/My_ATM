package com.example.myatm.roomDB

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transaction_list")
data class TransactionModel (
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @ColumnInfo(name = "atm_amount") val totalAmount: Int?,
    @ColumnInfo(name = "ten_note") val ten: Int,
    @ColumnInfo(name = "twenty_note") val twenty: Int,
    @ColumnInfo(name = "fifty_note") val fifty: Int,
    @ColumnInfo(name = "amount_hundred") val oneHundred: Int?,
    @ColumnInfo(name = "amount_two_hundred") val twoHundred: Int?,
    @ColumnInfo(name = "amount_five_hundred") val fiveHundred: Int?,
    @ColumnInfo(name = "amount_two_thousand") val twoThousand: Int?
    )