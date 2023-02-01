package com.example.myatm.roomDB

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface AtmDetailDao {
    @Insert
    suspend fun insertAtmDetail(atmDetail: AtmCorpusDetailModel)

    @Query("SELECT * from atm_detail")
    fun getAtmDetailsLive(): LiveData<AtmCorpusDetailModel>

    @Query("SELECT * from atm_detail")
    suspend fun getAtmDetails(): AtmCorpusDetailModel

    @Update
    suspend fun updateAtmDetail(atmDetail: AtmCorpusDetailModel)
}