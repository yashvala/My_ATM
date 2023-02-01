package com.example.myatm.ui.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.myatm.repository.AtmRepository
import com.example.myatm.roomDB.AtmCorpusDetailModel
import com.example.myatm.roomDB.TransactionModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AtmViewModel(val appContext: Application) : AndroidViewModel(appContext) {
    private val availableCurrency = intArrayOf(2000, 500, 200, 100, 50, 20, 10)

    private val atmRepo = AtmRepository(appContext)

    var atmDetails: LiveData<AtmCorpusDetailModel>? = null
    var lastTransaction: LiveData<TransactionModel>? = null
    var allTransaction: LiveData<List<TransactionModel>>? = null
    var amount = ObservableField("")

    init {
        viewModelScope.launch {
            atmDetails = getAtmDetailLive()
            lastTransaction = getLastTransactions()
            allTransaction = getAllTransactions()
        }
    }

    fun withdraw() {
        if (amount.get().isNullOrEmpty()) {
            Toast.makeText(appContext, "Please enter the amount for withdraw", Toast.LENGTH_SHORT)
                .show()
        } else if ((amount.get()?.toIntOrNull() ?: 0) <= 0) {
            Toast.makeText(
                appContext,
                "Please enter the valid amount for withdraw",
                Toast.LENGTH_SHORT
            ).show()
        }else{
          val temp = amount.get()?.toIntOrNull() ?:0
            val t:Int = temp/10
            val pmet =t*10
            if (temp == pmet){
                withdrawCash(amount.get()?.toIntOrNull() ?: 0)
            }else{
                Toast.makeText(
                    appContext,
                    "Please enter amount only in multiple of 100.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun withdrawCash(withdrawAmount: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val atmDetail = getAtmDetail()

            val count = intArrayOf(0, 0, 0, 0, 0, 0, 0)

            val availableNote = intArrayOf(
                atmDetail.twoThousand,
                atmDetail.fiveHundred,
                atmDetail.twoHundred,
                atmDetail.oneHundred,
                atmDetail.fifty,
                atmDetail.twenty,
                atmDetail.ten
            )
            var amount = withdrawAmount
            if (amount <= atmDetail.totalAmount) {
                for (i in availableCurrency.indices) {
                    if (availableCurrency[i] <= amount) {
                        val noteCount: Int = amount / availableCurrency[i]
                        if (availableNote[i] > 0) {
                            count[i]=
                                if (noteCount >= availableNote[i]) availableNote[i] else noteCount
                            availableNote[i] =
                                if (noteCount >= availableNote[i]) 0 else availableNote[i] - noteCount
                            amount -= count[i] * availableCurrency[i]
                        }
                    }
                }
                updateAtmDetails(
                    AtmCorpusDetailModel(
                        id = atmDetail.id,
                        totalAmount = atmDetail.totalAmount - withdrawAmount,
                        ten = availableNote[6],
                        twenty = availableNote[5],
                        fifty = availableNote[4],
                        oneHundred = availableNote[3],
                        twoHundred = availableNote[2],
                        fiveHundred = availableNote[1],
                        twoThousand = availableNote[0]
                    )
                )
                insertTransaction(
                    TransactionModel(
                        totalAmount = withdrawAmount,
                        ten = count[6],
                        twenty = count[5],
                        fifty = count[4],
                        oneHundred = count[3],
                        twoHundred = count[2],
                        fiveHundred = count[1],
                        twoThousand = count[0],
                    )
                )
                this@AtmViewModel.amount.set("")
            } else {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        appContext,
                        "you can't withdraw amount greater then total amount",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun getAtmDetailLive(): LiveData<AtmCorpusDetailModel> = atmRepo.getAtmDetailsLive()
    private suspend fun getAtmDetail(): AtmCorpusDetailModel = atmRepo.getAtmDetail()
    private suspend fun updateAtmDetails(atmCorpusDetailModel: AtmCorpusDetailModel) =
        atmRepo.updateAtmDetail(atmCorpusDetailModel)

    private suspend fun insertTransaction(transactionModel: TransactionModel) =
        atmRepo.insertTransaction(transactionModel)

    private fun getAllTransactions(): LiveData<List<TransactionModel>> =
        atmRepo.getAllTransactions()

    private fun getLastTransactions(): LiveData<TransactionModel> = atmRepo.getLastTransactions()
}