package com.example.crudpeminjamanbuku_agi

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.crudpeminjamanbuku_agi.room.Buku
import com.example.crudpeminjamanbuku_agi.room.BukuDb
import com.example.crudpeminjamanbuku_agi.room.Constant
import kotlinx.android.synthetic.main.activity_add.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    val db by lazy { BukuDb(this) }
    lateinit var bukuAdapter: BukuAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupListener()
        setupRecyclerView()

        Toast.makeText(this,"Welcome", Toast.LENGTH_SHORT).show()

    }

    override fun onStart() {
        super.onStart()
        loadBuku()
    }

    fun loadBuku(){
        CoroutineScope(Dispatchers.IO).launch {
            val bukus = db.bukuDao().getBukus()
            Log.d("MainActivity", "dbResponse: $bukus")
            withContext(Dispatchers.Main) {
                bukuAdapter.setData(bukus)
            }
        }
    }

    fun setupListener(){
        btn_add.setOnClickListener {
            intentEdit(0, Constant.TYPE_CREAT)
        }
    }

    fun intentEdit(bukuId: Int, intentType: Int){
        startActivity(
            Intent(applicationContext, AddActivity::class.java)
                .putExtra("intent_id", bukuId)
                .putExtra("intent_type", intentType)
        )
    }

    private fun setupRecyclerView(){
        bukuAdapter = BukuAdapter(arrayListOf(), object : BukuAdapter.OnAdapterListener{
            override fun onClick(buku: Buku) {
                // read detail buku
                intentEdit(buku.id, Constant.TYPE_READ)
            }

            override fun onUpdate(buku: Buku) {
                intentEdit(buku.id, Constant.TYPE_UPDATE)
            }

            override fun onDelete(buku: Buku) {
                deleteDialog(buku)
            }

        })
        rv_buku.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = bukuAdapter
        }
    }

    private  fun deleteDialog(buku: Buku){
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.apply {
            setTitle("Konfirmasi")
            setMessage("Yakin hapus???")
            setNegativeButton("Batal") { dialogInterface, i ->
                dialogInterface.dismiss()
            }
            setPositiveButton("Hapus") { dialogInterface, i ->
                dialogInterface.dismiss()
                CoroutineScope(Dispatchers.IO).launch {
                    db.bukuDao().deleteBuku(buku)
                    loadBuku()
                }
            }
        }
        alertDialog.show()
    }
}