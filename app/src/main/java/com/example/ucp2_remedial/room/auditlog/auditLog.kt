package com.example.ucp2_remedial.room.auditlog

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tblAuditLog")
data class AuditLog(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val action: String,
    val tableName: String,
    val recordId: String,
    val timestamp: Long = System.currentTimeMillis()
)