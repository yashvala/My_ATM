package com.example.myatm.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.myatm.roomDB.AtmCorpusDetailModel
import com.example.myatm.roomDB.AtmDataBase
import com.example.myatm.roomDB.TransactionModel

class AtmRepository(context: Context) {
    private val db = AtmDataBase.getDatabase(context)

    fun getAtmDetailsLive(): LiveData<AtmCorpusDetailModel> = db.atmDetailDao().getAtmDetailsLive()
    suspend fun getAtmDetail(): AtmCorpusDetailModel = db.atmDetailDao().getAtmDetails()
    suspend fun updateAtmDetail(atmCorpusDetailModel: AtmCorpusDetailModel) =
        db.atmDetailDao().updateAtmDetail(atmCorpusDetailModel)

    suspend fun insertTransaction(transactionModel: TransactionModel) =
        db.transactionDao().insertTransaction(transactionModel)

    fun getAllTransactions(): LiveData<List<TransactionModel>> =
        db.transactionDao().getAllTransactions()

    fun getLastTransactions(): LiveData<TransactionModel> =
        db.transactionDao().getLastTransactions()
}