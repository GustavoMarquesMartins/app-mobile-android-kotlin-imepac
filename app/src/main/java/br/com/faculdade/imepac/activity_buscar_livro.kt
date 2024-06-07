package br.com.faculdade.imepac

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot

class activity_buscar_livro : AppCompatActivity() {

    private lateinit var nomeLivro: TextView
    private lateinit var precoLivro: TextView
    private lateinit var btnBuscar: Button
    private lateinit var campoBuscar: Button
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buscar_livro)

        nomeLivro = findViewById(R.id.valor_campo_nome)
        nomeLivro = findViewById(R.id.valor_campo_livro)
        btnBuscar = findViewById(R.id.btn_buscar_livro)
        campoBuscar = findViewById(R.id.campo_busca)


    }

    fun buscarLivroPorNome() {
        btnBuscar.setOnClickListener {
            var campoBusca = campoBuscar.text.toString().trim()
            if (campoBusca.isNotEmpty() && campoBusca.isNotBlank()) {
                db.collection("Livros").whereEqualTo("nome", nomeLivro).get()
                    .addOnSuccessListener { result ->
                        processarResultado(result)
                    }.addOnFailureListener { exception ->
                        Toast.makeText(
                            this, "Livro não encontrado!: ${exception.message}", Toast.LENGTH_LONG
                        ).show()
                    }
            }
        }
    }

    fun processarResultado(result: QuerySnapshot){
    }
}