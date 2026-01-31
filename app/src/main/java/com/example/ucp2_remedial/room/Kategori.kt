package com.example.ucp2_remedial.room

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "tblKategori",
    foreignKeys = [
        ForeignKey(
            entity = Kategori::class,
            parentColumns = ["id"],
            childColumns = ["parentId"],
            onDelete = ForeignKey.RESTRICT
        )],
    indices = [Index(value = ["parentId"])]
)

data class Kategori(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val parentId: Int? = null,
    val isDeleted: Boolean = false
)