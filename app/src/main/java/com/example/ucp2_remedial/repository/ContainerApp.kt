package com.example.ucp2_remedial.dependencies // Sesuaikan package

import android.app.Application
import android.content.Context
import com.example.ucp2_remedial.repository.RepositoryPerpus
import com.example.ucp2_remedial.room.PerpusDatabase
import kotlin.getValue

interface AppContainer {
    val repository: RepositoryPerpus
}

class AppDataContainer(private val context: Context) : AppContainer {
    private val database: PerpusDatabase by lazy {
        PerpusDatabase.getDatabase(context)
    }

    override val repository: RepositoryPerpus by lazy {
        RepositoryPerpus(
            categoryDao = database.categoryDao(),
            bookDao = database.bookDao(),
            auditDao = database.auditDao()
        )
    }
}

class AplikasiPerpus : Application() {
    lateinit var container: AppContainer

    override fun onCreate(){
        super.onCreate()
        container = AppDataContainer(this)
    }
}