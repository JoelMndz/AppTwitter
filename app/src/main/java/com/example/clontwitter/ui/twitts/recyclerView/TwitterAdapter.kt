package com.example.clontwitter.ui.twitts.recyclerView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.clontwitter.databinding.ItemTwittBinding
import com.example.clontwitter.modelo.TwittModelo

class TwitterAdapter(
    private val accionEliminar:(TwittModelo) -> Unit,
    private val accionEditar:(TwittModelo) -> Unit):RecyclerView.Adapter<TwitterHolder>() {
    val twitts = ArrayList<TwittModelo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TwitterHolder {
        val binding = ItemTwittBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TwitterHolder(binding)
    }

    override fun getItemCount(): Int {
        return twitts.size
    }

    override fun onBindViewHolder(holder: TwitterHolder, position: Int) {
        val item = twitts[position]
        holder.bind(item, accionEliminar, accionEditar)
    }

    fun setData(lista:List<TwittModelo>){
        twitts.clear()
        twitts.addAll(lista)
        notifyDataSetChanged()
    }
}