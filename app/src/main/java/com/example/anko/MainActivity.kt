package com.example.anko

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle

import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import com.maxcruz.reactivePermissions.ReactivePermissions
import com.maxcruz.reactivePermissions.entity.Permission
import org.jetbrains.anko.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    val REQUEST_CODE = 554
    val reacPermissions = ReactivePermissions(this, REQUEST_CODE)



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)


        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val toggle = ActionBarDrawerToggle(this, drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        val navigationView: NavigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)
    }

    override fun onBackPressed() {
        val drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
        if( drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START)
        }
        else
        {
            super.onBackPressed()
        }
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == R.id.nav_contato_urgente)
        {
            makeCallPhone()
        }

        else if (id == R.id.nav_sms){
            startActivity<SMSActivity>("inicio_texto" to "Olá")
        }
        else if (id == R.id.nav_email){
            email("mig.nascipereira@gmail.com","Tenho um Trabalho Android","Ola?\n\n")
        }
        else if(id == R.id.nav_compartilhar){
            share("https://github.com/MP-Nascimento?tab=repositories","Miguel[Desenvolvedor]")
        }
        else if (id == R.id.nav_site){
            browse("https://github.com/MP-Nascimento?tab=repositories")
        }



        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    private fun makeCallPhone(){
        val phone = Permission(
            Manifest.permission.CALL_PHONE, /* A PERMISSÃO QUE SERÁ SOLICITADA */
            R.string.explicacao_permissao, /* TEXTO QUE É EXIBIDO NA SEGUNDA TENTATIVA, CASO O USÁRIO NEGUE A PERMISSÃO */
            true /* CASO O USUÁRIO VENHA NEGAR A PERMISSÃO, ELE AINDA PODERÁ PERMANECER UTILIZANDO O APLICATIVO */
        )
        val permissions = listOf(phone)
        reacPermissions.observeResultPermissions().subscribe {
            event ->

            if (event.second)
            {
            makeCall("11967797230")
            }
        }
        reacPermissions.evaluate(permissions)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(requestCode == REQUEST_CODE){

            reacPermissions.receive(permissions, grantResults)
        }
    }
}