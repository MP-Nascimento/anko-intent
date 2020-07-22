package com.example.anko

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.text.Editable
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.content_sms.*
import org.jetbrains.anko.sendSMS

class SMSActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sms)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)


        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        et_mensagem.text = Editable
            .Factory
            .getInstance()
            .newEditable( intent.getStringExtra("inicio_texto") )
    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val id = item.itemId
        if (id == android.R.id.home)
        {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    fun enviarSms(view: View?){
        sendSMS("119677973230", et_mensagem.text.toString())
    }
}