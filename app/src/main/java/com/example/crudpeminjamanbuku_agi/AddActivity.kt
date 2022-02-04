package com.example.crudpeminjamanbuku_agi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import com.example.crudpeminjamanbuku_agi.room.Buku
import com.example.crudpeminjamanbuku_agi.room.BukuDb
import com.example.crudpeminjamanbuku_agi.room.Constant
import kotlinx.android.synthetic.main.activity_add.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddActivity : AppCompatActivity() {

    val db by lazy {BukuDb(this)}
    private var bukuId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        setupView()
        setupListener()
    }

    fun setupView(){
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val intentType = intent.getIntExtra("intent_type", 0)
        when(intentType){
            Constant.TYPE_CREAT -> {
                btn_update.visibility = View.GONE
            }
            Constant.TYPE_READ -> {
                btn_save.visibility = View.GONE
                btn_update.visibility = View.GONE
                getBuku()
            }
            Constant.TYPE_UPDATE -> {
                btn_save.visibility = View.GONE
                getBuku()
            }
        }
    }

    fun setupListener(){
        btn_save.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                db.bukuDao().addBuku(
                    Buku(0, et_name.text.toString(), et_nis.text.toString(), et_judul.text.toString(), et_kodebuku.text.toString(), et_tanggalpinjam.text.toString(), et_tanggalkembali.text.toString())
                )
                finish()
            }
        }
        btn_update.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                db.bukuDao().updateBuku(
                    Buku(bukuId, et_name.text.toString(), et_nis.text.toString(), et_judul.text.toString(), et_kodebuku.text.toString(), et_tanggalpinjam.text.toString(), et_tanggalkembali.text.toString())
                )
                finish()
            }
        }
    }

    fun getBuku(){
        bukuId = intent.getIntExtra("intent_id", 0)
        CoroutineScope(Dispatchers.IO).launch {
            val bukus = db.bukuDao().getBuku(bukuId)[0]
            et_name.setText(bukus.name)
            et_nis.setText(bukus.nis)
            et_judul.setText(bukus.judul)
            et_kodebuku.setText(bukus.kode)
            et_tanggalpinjam.setText(bukus.pinjam)
            et_tanggalkembali.setText(bukus.kembali)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}