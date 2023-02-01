package com.example.myatm.roomDB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Database(entities = [AtmCorpusDetailModel::class, TransactionModel::class], version = 1)
abstract class AtmDataBase : RoomDatabase() {
    companion object {
        @Volatile
        private var INSTANCE: AtmDataBase? = null

        fun getDatabase(context: Context): AtmDataBase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AtmDataBase::class.java,
                    "ATM_DB"
                )
                    .addCallback(object : Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            GlobalScope.launch {
                                getDatabase(context).atmDetailDao().insertAtmDetail(
                                    AtmCorpusDetailModel(
                                        totalAmount = 50000,
                                        oneHundred = 75,
                                        twoHundred = 50,
                                        fiveHundred = 25,
                                        twoThousand = 10,
                                        fifty = 50,
                                        twenty = 50,
                                        ten = 50
                                    )
                                )
                            }
                        }
                    })
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }


    abstract fun atmDetailDao(): AtmDetailDao
    abstract fun transactionDao(): TransactionDao
}