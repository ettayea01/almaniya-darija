package com.almaniya.darija

import android.app.Application
import com.almaniya.darija.data.local.AppDatabase
import com.almaniya.darija.data.repository.AlmaniyaRepositoryImpl
import com.almaniya.darija.data.seed.DatabaseSeeder
import com.almaniya.darija.domain.repository.AlmaniyaRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AlmaniyaApp : Application() {

    lateinit var repository: AlmaniyaRepository
        private set

    override fun onCreate() {
        super.onCreate()
        val database = AppDatabase.getInstance(this)
        repository = AlmaniyaRepositoryImpl(database)

        // Seed initial database in background coroutine if empty
        CoroutineScope(Dispatchers.IO).launch {
            DatabaseSeeder.seedIfEmpty(
                database.topicDao(),
                database.wordDao(),
                database.progressDao()
            )
        }
    }
}
