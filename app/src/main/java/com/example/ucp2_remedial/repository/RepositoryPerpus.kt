package com.example.ucp2_remedial.repository

import com.example.ucp2_remedial.room.auditlog.AuditLog
import com.example.ucp2_remedial.room.buku.BukuFisik
import com.example.ucp2_remedial.room.dao.AuditDao
import com.example.ucp2_remedial.room.dao.DaoBuku
import com.example.ucp2_remedial.room.dao.CategoryDao
import com.example.ucp2_remedial.room.kategori.Kategori
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RepositoryPerpus(
    private val categoryDao: CategoryDao,
    private val bookDao: DaoBuku,
    private val auditDao: AuditDao
) {

    suspend fun deleteCategoryWithLogic(categoryId: Int, deleteBooks: Boolean) {
        withContext(Dispatchers.IO) {
            val borrowedCount = bookDao.countBorrowedBooksByCategory(categoryId)

            if (borrowedCount > 0) {
                throw IllegalStateException("GAGAL: Ada $borrowedCount buku yang sedang dipinjam di kategori ini.")
            }

            if (deleteBooks) {
                bookDao.softDeleteBooksByCategory(categoryId)
                recordLog("SOFT_DELETE_CASCADE", "Category & Books", categoryId.toString())
            } else {
                bookDao.releaseCategoryRelation(categoryId)
                recordLog("SOFT_DELETE_UNLINK", "Category Only", categoryId.toString())
            }

            categoryDao.softDelete(categoryId)
        }
    }

    suspend fun validateAndUpdateCategory(category: Kategori, newParentId: Int?) {
        if (newParentId != null) {
            if (category.id == newParentId) {
                throw IllegalArgumentException("Error: Kategori tidak bisa menjadi induk dirinya sendiri.")
            }
        }
        categoryDao.update(category.copy(parentId = newParentId))
        recordLog("UPDATE", "Category", category.id.toString())
    }

    private suspend fun recordLog(action: String, table: String, id: String) {
        val log = AuditLog(
            action = action,
            tableName = table,
            recordId = id,
            timestamp = System.currentTimeMillis()
        )
        auditDao.insert(log)
    }

    fun getAllCategories() = categoryDao.getAllCategories()

    suspend fun insertCategory(category: Kategori) {
        categoryDao.insert(category)
        recordLog("INSERT", "Category", category.name)
    }

    suspend fun insertPhysicalBook(book: BukuFisik) {
        bookDao.insertPhysicalBook(book)
        recordLog("INSERT", "PhysicalBook", book.uniqueId)
    }
}