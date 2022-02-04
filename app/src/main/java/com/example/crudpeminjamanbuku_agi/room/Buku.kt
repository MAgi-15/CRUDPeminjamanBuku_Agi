package com.example.crudpeminjamanbuku_agi.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Buku (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val nis: String,
    val judul: String,
    val kode: String,
    val pinjam: String,
    val kembali: String
)