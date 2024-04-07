package br.com.faculdade.imepac

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class FormCadastro : AppCompatActivity() {

    private lateinit var edit_nome: EditText
    private lateinit var edit_email: EditText
    private lateinit var edit_senha: EditText
    private lateinit var btn_cadastrar: Button
    private lateinit var usuarioId: String;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_cadastro)

        edit_nome = findViewById(R.id.edit_nome)
        edit_email = findViewById(R.id.edit_email)
        edit_senha = findViewById(R.id.edit_senha)
        btn_cadastrar = findViewById(R.id.bt_cadastrar)

        fun salvarDadosUsuario() {
            val nome = edit_nome.text.toString().trim()

            val db = FirebaseFirestore.getInstance();
            val usuarios = hashMapOf(
                "nome" to nome
            )

            val usuarioId = FirebaseAuth.getInstance().currentUser?.uid

            if (usuarioId != null) {
                db.collection("usuarios").add(usuarios).addOnSuccessListener { documentReference ->
                    println("Documento adicionado com o ID: ${documentReference.id}");

                }.addOnFailureListener { e ->
                    println("Erro ao adicionar documento: $e");
                }
            } else {
                val telaCadastro = Intent(this, FormCadastro::class.java)
                startActivity(telaCadastro)
            }
        }

        fun cadastrarUsuario(it: View) {
            val email = edit_email.text.toString().trim()
            val senha = edit_senha.text.toString().trim()
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, senha)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        salvarDadosUsuario();
                        val mensagemOk = "Cadastro realizado com sucesso"
                        val snackbar = Snackbar.make(it, mensagemOk, Snackbar.LENGTH_LONG)
                        snackbar.show()
                    } else {
                        val mensagemErro = "Erro ao cadastrar o usuário"
                        val snackbar = Snackbar.make(it, mensagemErro, Snackbar.LENGTH_LONG)
                        snackbar.show()
                    }
                }
        }

        btn_cadastrar.setOnClickListener {
            val nome = edit_nome.text.trim()
            val email = edit_email.text.trim()
            val senha = edit_senha.text.trim()

            if (nome.isEmpty() || email.isEmpty() || senha.isEmpty()) {
                val mensagemErro = "Campos não preenchidos, tente novamente"
                val snackbar = Snackbar.make(it, mensagemErro, Snackbar.LENGTH_LONG)
                snackbar.show();
            } else {
                cadastrarUsuario(it);
            }
        }
    }
}