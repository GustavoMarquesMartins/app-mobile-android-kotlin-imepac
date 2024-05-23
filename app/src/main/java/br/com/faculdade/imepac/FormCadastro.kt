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

    // Declaração das variáveis de layout e de identificação do usuário
    private lateinit var edit_nome: EditText
    private lateinit var edit_email: EditText
    private lateinit var edit_senha: EditText
    private lateinit var btn_cadastrar: Button
    private lateinit var usuarioId: String;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_cadastro)

        // Associação dos elementos do layout às variáveis
        edit_nome = findViewById(R.id.edit_nome)
        edit_email = findViewById(R.id.edit_email)
        edit_senha = findViewById(R.id.edit_senha)
        btn_cadastrar = findViewById(R.id.bt_cadastrar)

        // Função para salvar os dados do usuário no Firebase Firestore

        fun salvarDadosUsuario() {
            val db = FirebaseFirestore.getInstance();
            val nome = edit_nome.text.toString().trim();
            val usuarioID = FirebaseAuth.getInstance().currentUser?.uid;
            val email = FirebaseAuth.getInstance().currentUser?.email;

            if(usuarioID != null && email != null) {
                val usuarios = hashMapOf(
                    "nome" to nome,
                    "email" to email,
                    "uid" to usuarioID
                )
                db.collection("Usuarios")
                    .add(usuarios)
                    .addOnSuccessListener { documentReference -> println("Documento adicionado com o ID: ${documentReference.id}")}
                    .addOnFailureListener{erro -> println("Ocorreu um erro ao adicionar documento $erro")}
            }else{
                println("Erro na autentificação")
            }
        }

        // Função para cadastrar um novo usuário no Firebase Authentication
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

        // Listener para o botão de cadastro
        btn_cadastrar.setOnClickListener {
            val nome = edit_nome.text.trim()
            val email = edit_email.text.trim()
            val senha = edit_senha.text.trim()

            // Verifica se todos os campos estão preenchidos
            if (nome.isEmpty() || email.isEmpty() || senha.isEmpty()) {
                val mensagemErro = "Campos não preenchidos, tente novamente"
                val snackbar = Snackbar.make(it, mensagemErro, Snackbar.LENGTH_LONG)
                snackbar.show();
            } else {
                // Chama a função de cadastro de usuário
                cadastrarUsuario(it);
            }
        }
    }
}
