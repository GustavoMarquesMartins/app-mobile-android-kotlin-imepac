package br.com.faculdade.imepac.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.faculdade.imepac.R
import br.com.faculdade.imepac.User


class MyAdapter(private val itemList: List<User>) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewNome: TextView = itemView.findViewById(R.id.nome_usuario_lista)
        val textViewEmail: TextView = itemView.findViewById(R.id.email_usuario_lista)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.activity_tela_item_lista_usuarios, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = itemList[position]
        holder.textViewNome.text = currentItem.nome
        holder.textViewEmail.text = currentItem.email
    }

    override fun getItemCount() = itemList.size
}
