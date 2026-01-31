package com.example.ucp2_remedial.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.ucp2_remedial.room.kategori.Kategori
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(category: Kategori)

    @Update
    suspend fun update(category: Kategori)

    @Query("UPDATE tblKategori SET isDeleted = 1 WHERE id = :categoryId")
    suspend fun softDelete(categoryId: Int)

    @Query("SELECT * FROM tblKategori WHERE isDeleted = 0 ORDER BY name ASC")
    fun getAllCategories(): Flow<List<Kategori>>

    @Query("SELECT COUNT(*) FROM tblKategori WHERE parentId = :parentId AND isDeleted = 0")
    suspend fun countChildCategories(parentId: Int): Int
}