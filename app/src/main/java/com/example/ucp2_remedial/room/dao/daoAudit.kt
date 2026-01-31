package com.example.ucp2_remedial.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.ucp2_remedial.room.auditlog.AuditLog
import kotlinx.coroutines.flow.Flow

@Dao
interface AuditDao {
    @Insert
    suspend fun insert(auditLog: AuditLog)

    @Query("SELECT * FROM tblAuditLog ORDER BY timestamp DESC")
    fun getAllLogs(): Flow<List<AuditLog>>
}