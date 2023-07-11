package com.sdevprem.roomresetdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.room.RoomDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

const val TAG = "tag.........tag"
class MainActivity : AppCompatActivity() {
    private val dummyStudents = arrayOf(
        Student(name = "A"),
        Student(name = "B"),
        Student(name = "C"),
        Student(name = "D"),
        Student(name = "E"),
    )

    private val db by lazy {
        AppDatabase.getInstance(applicationContext)
    }

    private val studentDao : StudentDao by lazy {
        db.getStudentDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lifecycleScope.launch {
            studentDao.getAll().collect {
                if(it.isEmpty())
                    Log.d(TAG,"No student in the room")
                else
                    it.forEach{student ->
                        Log.d(TAG,student.toString())
                    }
            }
        }

        insertDeleteInsert()

    }

    private fun insertDeleteInsert(){
        lifecycleScope.launch(Dispatchers.IO) {
            resetDb()
            studentDao.insertAll(*dummyStudents)
            resetDb()
            studentDao.insertAll(*dummyStudents)
        }
    }

    private suspend fun resetDb() = withContext(Dispatchers.IO){
        db.runInTransaction{
            runBlocking {
                db.clearAllTables()
                db.getDatabaseDao().clearPrimaryKeyIndex()
            }
        }
    }
}