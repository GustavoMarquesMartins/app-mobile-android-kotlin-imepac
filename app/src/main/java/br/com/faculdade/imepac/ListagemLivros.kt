package br.com.faculdade.imepac

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.faculdade.imepac.Adapter.MyAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

data class Livro(
    val nome: String = "", val preco: String = ""
)

class ListagemLivros : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MyAdapter
    private var itemList: MutableList<Livro> = mutableListOf()
    private lateinit var campoBusca: EditText
    private lateinit var btnLinkCadastro: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buscar_livro)

        recyclerView = findViewById(R.id.listagem_livros)
        adapter = MyAdapter(itemList)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        campoBusca = findViewById(R.id.campo_busca_livro_item)
        btnLinkCadastro = findViewById(R.id.link_cadastro)

        fetchFirestoreData()
        detectTextChanges()
        openSignupLink()

    }

    private fun fetchFirestoreData() {

        val db = FirebaseFirestore.getInstance()
        val collectionRef = db.collection("Livros")
        val userId = FirebaseAuth.getInstance().currentUser?.uid

        db.collection("Livros").whereEqualTo("idUser", userId).get()
            .addOnSuccessListener { documents ->
                itemList.clear()
                for (document in documents.documents) {
                    val livro = document.toObject(Livro::class.java)
                    livro?.let { itemList.add(it) }
                }
            adapter.notifyDataSetChanged()
        }.addOnFailureListener { exception ->
            Log.w("Error getting documents: ", exception)
        }
    }

    private fun getBooksByNameAndId() {
        val name = campoBusca.text.toString().trim()
        val userId = FirebaseAuth.getInstance().currentUser?.uid

        if (userId != null && name.isNotEmpty()) {
            val db = FirebaseFirestore.getInstance()
            db.collection("Livros").whereEqualTo("nome", name).whereEqualTo("idUser", userId).get()
                .addOnSuccessListener { documents ->
                    itemList.clear()
                    for (document in documents.documents) {
                        val livro = document.toObject(Livro::class.java)
                        livro?.let { itemList.add(it) }
                    }
                    adapter.notifyDataSetChanged()
                }.addOnFailureListener { exception ->
                }
        }else{
            fetchFirestoreData()
        }
    }

    fun detectTextChanges() {
        campoBusca.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                getBooksByNameAndId()
            }
        })
    }

    fun openSignupLink() {
        btnLinkCadastro.setOnClickListener { task ->
            val intent = Intent(this, FormCadastroLivro::class.java)
            startActivity(intent)
        }
    }
}
