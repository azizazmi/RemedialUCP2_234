package com.example.ucp2_remedial.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.ucp2_remedial.room.auditlog.AuditLog
import com.example.ucp2_remedial.room.author.Author
import com.example.ucp2_remedial.room.buku.Buku
import com.example.ucp2_remedial.room.buku.BukuFisik
import com.example.ucp2_remedial.room.bukuauthor.BukuAuthor
import com.example.ucp2_remedial.room.dao.CategoryDao
import com.example.ucp2_remedial.room.dao.DaoBuku
import com.example.ucp2_remedial.room.dao.AuditDao
import com.example.ucp2_remedial.room.kategori.Kategori

@Database(
    entities = [
        Kategori::class,
        Buku::class,
        BukuFisik::class,
        Author::class,
        BukuAuthor::class,
        AuditLog::class
    ],
    version = 1,
    exportSchema = false
)
abstract class PerpusDatabase : RoomDatabase() {

    abstract fun categoryDao(): CategoryDao
    abstract fun bookDao(): DaoBuku
    abstract fun auditDao(): AuditDao

    companion object {
        @Volatile
        private var Instance: PerpusDatabase? = null

        fun getDatabase(context: Context): PerpusDatabase {
            return (Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    PerpusDatabase::class.java,
                    "perpus_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            })
        }
    }
}