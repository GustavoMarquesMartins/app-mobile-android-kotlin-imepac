package br.com.faculdade.imepac

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class TelaPrincipal : AppCompatActivity() {

    private lateinit var btnSair: Button
    private lateinit var btnVerPerfil: Button
    private lateinit var btnListagem: Button
    private lateinit var db: FirebaseFirestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela_principal)

        btnVerPerfil = findViewById(R.id.btn_ver_perfi)
        btnSair = findViewById(R.id.btn_deslogar)
        btnListagem = findViewById(R.id.btn_listagem)

        db = FirebaseFirestore.getInstance()

        btnSair.setOnClickListener { sair() }
        btnVerPerfil.setOnClickListener { verPerfil() }
        btnListagem.setOnClickListener { listagem() }
    }

    fun verPerfil(){
        val intent = Intent(this@TelaPrincipal, TelaPerfil::class.java)
        startActivity(intent)
    }

    fun listagem(){
        val intent = Intent(this@TelaPrincipal, ListagemUsuarios::class.java)
        startActivity(intent)
    }

    fun sair(){
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this@TelaPrincipal, FormLogin::class.java);
            startActivity(intent);
            finish();
        }
    }
