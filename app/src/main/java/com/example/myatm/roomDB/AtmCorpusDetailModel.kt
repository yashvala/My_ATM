package com.example.myatm.roomDB

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "atm_detail")
data class AtmCorpusDetailModel (

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @ColumnInfo(name = "atm_amount") val totalAmount: Int,
    @ColumnInfo(name = "ten_note") val ten: Int,
    @ColumnInfo(name = "twenty_note") val twenty: Int,
    @ColumnInfo(name = "fifty_note") val fifty: Int,
    @ColumnInfo(name = "one_hundred_note") val oneHundred: Int,
    @ColumnInfo(name = "two_hundred_note") val twoHundred: Int,
    @ColumnInfo(name = "five_hundred_note") val fiveHundred: Int,
    @ColumnInfo(name = "two_thousand_note") val twoThousand: Int
    )