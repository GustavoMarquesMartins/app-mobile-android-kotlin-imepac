package br.com.faculdade.imepac

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.compose.material3.Snackbar
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class FormLogin : AppCompatActivity() {

    private lateinit var edit_email: EditText
    private lateinit var edit_senha: EditText
    private lateinit var btn_entrada: Button
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_login)

        InicializarComponentes()

        // Localiza o TextView que representa o link para a tela de cadastro
        val linkFormCadastro = findViewById<TextView>(R.id.text_tela_cadastro)

        // Define um listener para o clique no link de cadastro
        linkFormCadastro.setOnClickListener {
            val intent = Intent(this, FormCadastro::class.java)
            startActivity(intent)
        }

        btn_entrada.setOnClickListener {
            val email = edit_email.text.toString()
            val senha = edit_senha.text.toString()

            if (email.isEmpty() || senha.isEmpty()) {
                val mensagemErro = "Campos não preenchidos, tente novamente"
                val snackbar = Snackbar.make(it, mensagemErro, Snackbar.LENGTH_LONG)
                snackbar.show()
            } else {
                AutenticarUsuario();
            }
        }
    }

    fun AutenticarUsuario() {
        val email = edit_email.text.toString()
        val senha = edit_senha.text.toString()

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, senha)
            .addOnCompleteListener() { task ->
                if (task.isSuccessful) {
                    progressBar.visibility = View.GONE

                    val user = FirebaseAuth.getInstance().currentUser
                    val intent = Intent(this@FormLogin, TelaPerfil::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    val mensagemErro = task.exception?.message
                        Snackbar.make(
                            findViewById(android.R.id.content),
                            "Erro ao autenticar usuário :$mensagemErro",
                            Snackbar.LENGTH_LONG
                        ).show()
                    }

            }
    }

    fun InicializarComponentes() {
        edit_email = findViewById(R.id.edit_email)
        edit_senha = findViewById(R.id.edit_senha)
        btn_entrada = findViewById(R.id.btn_entrada)
        progressBar = findViewById(R.id.progressbar)

    }
}
