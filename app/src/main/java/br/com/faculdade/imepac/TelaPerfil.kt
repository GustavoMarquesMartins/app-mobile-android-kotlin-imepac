package br.com.faculdade.imepac

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class TelaPerfil : AppCompatActivity() {
    private lateinit var emailUser: TextView
    private lateinit var usuarioUser: TextView
    private lateinit var btn_sair: Button
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela_perfil)
        InicializarComponentes()
        fetchAllNames()
        db = FirebaseFirestore.getInstance()
        btn_sair.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this@TelaPerfil, FormLogin::class.java);
            startActivity(intent);
            finish();
        };
    }

    override fun onStart() {
        super.onStart()
        val userEmail = FirebaseAuth.getInstance().currentUser?.email
        emailUser.setText(userEmail)
        if (userEmail != null) {
            buscarNomeDoEmail(userEmail)
        }
    }

    fun fetchAllNames() {
        val db = FirebaseFirestore.getInstance()
        val usuariosRef = db.collection("Usuarios")

        usuariosRef.get().addOnSuccessListener { querySnapshot ->
            for (document in querySnapshot.documents) {
                val nome = document.getString("nome")
                println("Nome: $nome")
            }
        }.addOnFailureListener { erro -> println("Erro ao buscar os nomes: ${erro.message}") }
    }

    fun buscarNomeDoEmail(email: String) {
        val usuarioRef = db.collection("Usuarios")

        val query = usuarioRef.whereEqualTo("email", email)

        query.get().addOnSuccessListener { querySnapshot ->
            if (!querySnapshot.isEmpty) {
                val documento = querySnapshot.documents[0]
                val nome = documento.getString("nome")
                if (nome != null) {
                    usuarioUser.setText(nome)
                } else {
                    println("Nome não encontrado para o e-mail $email")
                }
            } else {
                println("Nenhum documento encontrado para e-mail $email ")
            }
        }.addOnSuccessListener { e ->
            println("Erro ao buscar documento $e")

        }
    }

    fun InicializarComponentes() {
        emailUser = findViewById(R.id.textEmailUser)
        usuarioUser = findViewById(R.id.textNomeUser)
        btn_sair = findViewById(R.id.btn_sair)
    }
}

