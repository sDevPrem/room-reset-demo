package com.sdevprem.roomresetdemo

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface StudentDao {
    @Query("SELECT * FROM students")
    fun getAll(): Flow<List<Student>>
    
    @Insert
    suspend fun insertAll(vararg students: Student)

    @Delete
    suspend fun delete(student: Student)

    @Query("DELETE FROM students")
    suspend fun deleteAll()

    @Query("DELETE FROM sqlite_sequence WHERE name = 'students'")
    suspend fun deletePrimaryKeyIndex()

}