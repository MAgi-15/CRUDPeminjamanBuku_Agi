package com.example.crudpeminjamanbuku_agi.room

import androidx.room.*

@Dao
interface BukuDao {

    @Insert
    suspend fun addBuku(buku: Buku)

    @Update
    suspend fun updateBuku(buku: Buku)

    @Delete
    suspend fun deleteBuku(buku: Buku)

    @Query("SELECT * FROM buku")
    suspend fun getBukus():List<Buku>

    @Query("SELECT * FROM buku WHERE id=:buku_id")
    suspend fun getBuku(buku_id: Int): List<Buku>
}