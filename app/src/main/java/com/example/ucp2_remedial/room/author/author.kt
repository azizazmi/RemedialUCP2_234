package com.example.ucp2_remedial.room.author

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tblAuthor")
data class Author(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String
)