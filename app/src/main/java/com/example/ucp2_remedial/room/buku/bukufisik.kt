package com.example.ucp2_remedial.room.buku

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "physical_books",
    foreignKeys = [
        ForeignKey(
            entity = Buku::class,
            parentColumns = ["id"],
            childColumns = ["bukuId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["bookTitleId"])]
)
data class BukuFisik(
    @PrimaryKey
    val uniqueId: String,
    val bookTitleId: Int,
    val status: String,
    val isDeleted: Boolean = false
)