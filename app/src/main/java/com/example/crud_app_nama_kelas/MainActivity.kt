package com.example.crud_app_nama_kelas

import android.annotation.SuppressLint
import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.crud_app_nama_kelas.helper.DBHelper
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {
   lateinit var inputName: TextInputEditText
   lateinit var inputAge : TextInputEditText
   lateinit var btnAdd: Button
   lateinit var btnPrint: Button
   lateinit var textName: TextView
   lateinit var textAge : TextView
   lateinit var textId : TextView
   var progressDialog: ProgressDialog? = null

   @SuppressLint("Range")
   override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//       inisialisasikan widget
        inputName = findViewById(R.id.inputName)
        inputAge  = findViewById(R.id.inputAge)
        btnAdd = findViewById(R.id.btnAdd)
        btnPrint = findViewById(R.id.btnPrint)
        textName = findViewById(R.id.name)
        textAge = findViewById(R.id.age)
        textId = findViewById(R.id.id)

       // load handler =  ketika aplikasi di load tampilkan tabel
       loadHandler()

//       event ketika button add di click
       btnAdd.setOnClickListener {
           val db = DBHelper(this,null)
           val name = inputName.text.toString()
           val age = inputAge.text.toString()


           db.addProfile(name,age)

           inputAge.text!!.clear()
           inputName.text!!.clear()
       }
       //event ketika button print di click
       btnPrint.setOnClickListener {
         loadHandler()

       }

    }

    @SuppressLint("Range")
    fun loadHandler(){
        val db = DBHelper(this,null)
        val cursor = db.getProfile()

        progressDialog = ProgressDialog(this@MainActivity)
        progressDialog!!.setTitle("Loading")
        progressDialog!!.setMessage("Tunggu sebentar ... data akan muncul")
        progressDialog!!.max = 100
        progressDialog!!.progress=1
        progressDialog!!.show()

       Thread(Runnable {
           try {
               Thread.sleep(1000)
           }catch (e:Exception){
               e.printStackTrace()
           }
           progressDialog!!.dismiss()
       }) .start()


         if(cursor!!.moveToNext()) {
            textId.text = "No\n\n"
            textName.text = "Name\n\n"
            textAge.text = "Age\n\n"
             var no=1
            while (cursor!!.moveToNext()) {
                textId.append(
                    " $no\n\n")
                textName.append(
                    cursor!!.getString(
                        cursor!!.getColumnIndex(DBHelper.NAME_COL)
                    ) + "\n\n")
                textAge.append(
                    cursor!!.getString(
                        cursor!!.getColumnIndex(DBHelper.AGE_COL)
                    ) + "\n\n")
            no++
            }
        }


        cursor.close()

    }
}