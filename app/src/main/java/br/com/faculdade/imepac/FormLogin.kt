package br.com.faculdade.imepac

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class FormLogin : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_login)

        val linkFormCadastro = findViewById<TextView>(R.id.text_tela_cadastro)
        linkFormCadastro.setOnClickListener {
            val telaCadastro = Intent(this, FormCadastro::class.java)
            startActivity(telaCadastro)
        }
    }
}