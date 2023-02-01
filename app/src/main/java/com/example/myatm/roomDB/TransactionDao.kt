package com.example.myatm.roomDB

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TransactionDao {
    @Query("SELECT * from transaction_list")
    fun getAllTransactions(): LiveData<List<TransactionModel>>

    @Query("SELECT * FROM transaction_list ORDER BY id DESC LIMIT 1")
    fun getLastTransactions(): LiveData<TransactionModel>

    @Insert
    suspend fun insertTransaction(transaction: TransactionModel)
}