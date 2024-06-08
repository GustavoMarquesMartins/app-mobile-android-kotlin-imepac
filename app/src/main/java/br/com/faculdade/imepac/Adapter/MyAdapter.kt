package br.com.faculdade.imepac.Adapter

import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.compose.ui.graphics.Color
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import br.com.faculdade.imepac.Livro
import br.com.faculdade.imepac.R


class MyAdapter(private val itemList: List<Livro>) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewNome: TextView = itemView.findViewById(R.id.item_nome_livro)
        val textViewPreco: TextView = itemView.findViewById(R.id.item_preco_livro)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_tela_item_lista_livro, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = itemList[position]
        holder.textViewNome.text = currentItem.nome
        holder.textViewPreco.text = "R$: " + currentItem.preco
    }

    override fun getItemCount() = itemList.size
}
