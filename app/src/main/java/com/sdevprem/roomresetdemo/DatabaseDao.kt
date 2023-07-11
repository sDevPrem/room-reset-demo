package com.sdevprem.roomresetdemo

import androidx.room.Dao
import androidx.room.Query

@Dao
interface DatabaseDao {

    @Query("DELETE FROM sqlite_sequence")
    fun clearPrimaryKeyIndex()
}