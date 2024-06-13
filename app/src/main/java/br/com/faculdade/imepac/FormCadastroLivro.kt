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

class FormCadastroLivro : AppCompatActivity() {

    private lateinit var editNome: EditText
    private lateinit var editPreco: EditText
    private lateinit var btn_salvar: Button
    private lateinit var btn_voltar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_cadastro_livro)

        editNome = findViewById(R.id.edit_nome_livro)
        editPreco = findViewById(R.id.edit_preco)
        btn_salvar = findViewById(R.id.btn_salvar)
        btn_voltar = findViewById(R.id.btn_voltar)

        btn_salvar.setOnClickListener {
            val nome = editNome.text.trim()
            val preco = editPreco.text.trim()

            if (nome.isEmpty() || preco.isEmpty()) {
                val mensagemErro = "Campos não preenchidos, tente novamente"
                val snackbar = Snackbar.make(it, mensagemErro, Snackbar.LENGTH_LONG)
                snackbar.show();
            } else {
                salvarLivro(it);
            }
        }

        btn_voltar.setOnClickListener {
            val intent = Intent(this, ListagemLivros::class.java)
            startActivity(intent)
        }
    }

    override fun onBackPressed() {
        if (isTaskRoot) {
            moveTaskToBack(true)
        } else {
            super.onBackPressed()
        }
    }

    fun salvarLivro(it: View) {
        val db = FirebaseFirestore.getInstance()
        val nome = editNome.text.toString().trim()
        val preco = editPreco.text.toString().trim()
        val userId = FirebaseAuth.getInstance().currentUser?.uid

        if (nome.isNotEmpty() && preco.isNotEmpty()) {
            val livro = hashMapOf(
                "nome" to nome, "preco" to preco, "idUser" to userId
            )

            db.collection("Livros").add(livro).addOnSuccessListener { documentReference ->
                val mensagemOk = "Cadastro realizado com sucesso!"
                val snackbar = Snackbar.make(
                    it, mensagemOk, Snackbar.LENGTH_LONG
                )
                snackbar.show()

                editNome.setText("")
                editPreco.setText("")

            }.addOnFailureListener { erro ->
                println("Ocorreu um erro ao adicionar documento $erro")
            }
        } else {
            println("Erro ao tentar salvar!")
        }
    }
}
