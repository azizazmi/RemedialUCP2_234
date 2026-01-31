package com.example.ucp2_remedial.room.bukuauthor

import androidx.room.Entity
import androidx.room.Index

@Entity(
    tableName = "tblRelasiBukuAuthor",
    primaryKeys = ["bookId", "authorId"],
    indices = [Index(value = ["bookId"]), Index(value = ["authorId"])]
)
data class BukuAuthor(
    val bookId: Int,
    val authorId: Int
)