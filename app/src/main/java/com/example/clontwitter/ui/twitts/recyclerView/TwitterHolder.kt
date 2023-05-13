package com.example.clontwitter.ui.twitts.recyclerView

import androidx.recyclerview.widget.RecyclerView
import com.example.clontwitter.databinding.ItemTwittBinding
import com.example.clontwitter.modelo.TwittModelo
import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class TwitterHolder(private val binding:ItemTwittBinding):RecyclerView.ViewHolder(binding.root) {
    fun bind(
        twitt:TwittModelo,
        accionEliminar:(TwittModelo) -> Unit,
        accionEditar:(TwittModelo) -> Unit){

        binding.descripcion.text = twitt.descripcion
        binding.fechaCreacion.text = formatearFecha(twitt.fechaCreacion)

        binding.btnEliminar.setOnClickListener{
            accionEliminar(twitt)
        }

        binding.btnEditar.setOnClickListener{
            accionEditar(twitt)
        }
    }

    private fun formatearFecha(timestamp: Timestamp): String {
        val date = Date(timestamp.seconds * 1000L)
        val format = SimpleDateFormat("HH:mm 'el' d MMMM yyyy", Locale.getDefault())
        return format.format(date)
    }
}