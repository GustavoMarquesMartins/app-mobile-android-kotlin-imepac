package br.com.faculdade.imepac

import android.content.ClipData.Item
import android.nfc.Tag
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.faculdade.imepac.Adapter.MyAdapter
import br.com.faculdade.imepac.R
import com.google.firebase.firestore.FirebaseFirestore

data class User(val nome: String = "", val email: String = "")

class ListagemUsuarios : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MyAdapter
    private var itemList: MutableList<User> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView(R.layout.activity_tela_listagem_usuarios)

        fetchFirestoreData()

        recyclerView = findViewById(R.id.listagem_usuarios)
        adapter = MyAdapter(itemList)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

    }

    private fun fetchFirestoreData() {
        val db = FirebaseFirestore.getInstance()
        val collectionRef = db.collection("Usuarios")

        collectionRef.get().addOnSuccessListener { querySnapshot ->
            for (document in querySnapshot.documents) {
                val user = document.toObject(User::class.java)
                if (user != null) {
                    itemList.add(user)
                }
            }
            adapter.notifyDataSetChanged()
        }.addOnFailureListener { exception ->
            Log.w("Error getting documents: ", exception)
        }
    }
}