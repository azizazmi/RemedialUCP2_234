package com.example.ucp2_remedial.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.ucp2_remedial.room.buku.Buku
import com.example.ucp2_remedial.room.buku.BukuFisik

@Dao
interface DaoBuku {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertBookTitle(bookTitle: Buku): Long

    @Query("UPDATE tblBuku SET isDeleted = 1 WHERE categoryId = :categoryId")
    suspend fun softDeleteBooksByCategory(categoryId: Int)

    @Query("UPDATE tblBuku SET categoryId = NULL WHERE categoryId = :categoryId")
    suspend fun releaseCategoryRelation(categoryId: Int)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPhysicalBook(physicalBook: BukuFisik)

    @Query("""
        SELECT COUNT(*) FROM tblBukuFisik P
        INNER JOIN tblBuku B ON P.bookTitleId = B.id
        WHERE B.categoryId = :categoryId 
        AND P.status = 'BORROWED' 
        AND P.isDeleted = 0
    """)
    suspend fun countBorrowedBooksByCategory(categoryId: Int): Int
}