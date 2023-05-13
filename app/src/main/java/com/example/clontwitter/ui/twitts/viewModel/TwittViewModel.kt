package com.example.clontwitter.ui.twitts.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.clontwitter.modelo.TwittModelo
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.type.DateTime

class TwittViewModel:ViewModel() {
    private val db = Firebase.firestore
    private val nombreColeccion = "twitts"
    private var TAG="FIREBASE - TWITTS"
    val twittsLiveData = MutableLiveData<List<TwittModelo>>()

    val descripcion = MutableLiveData("")
    val id = MutableLiveData("")
    val estado = MutableLiveData("")

    fun obtenerTwitts(){
        db.collection(nombreColeccion)
            .orderBy("fechaCreacion",Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { result ->
                val twitts = mutableListOf<TwittModelo>()
                for (document in result) {
                    val data = document.data
                    val twittModelo = TwittModelo.fromHashMap(data as HashMap<String, Any>)
                    twittModelo.id = document.id
                    twitts.add(twittModelo)
                }
                twittsLiveData.value = twitts
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error Al cargar los twitts", exception)
            }
    }

    fun guardarInformacion(){
        if(estado.value == EDITAR_TWITT){
            editarTwitt()
        }else if(estado.value == INACTIVO){
            agregarTwitt()
        }
    }

    private fun agregarTwitt(){
        val nuevoTwitt = TwittModelo(descripcion.value!!)
        descripcion.value = ""
        // Add a new document with a generated ID
        db.collection(nombreColeccion)
            .add(nuevoTwitt.toHashMap())
            .addOnSuccessListener { documentReference ->
                limpiarEstado()
                limpiarCampos()
                Log.w(TAG,"Twitt agregado!")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error al agregar el documento", e)
            }
    }

    fun eliminarTwitt(twittActual:TwittModelo){
        db.collection(nombreColeccion)
            .document(twittActual.id!!)
            .delete()
            .addOnSuccessListener {
                val listaActual = twittsLiveData.value?.toMutableList() ?: mutableListOf()
                listaActual.remove(twittActual)
                twittsLiveData.value = listaActual
                Log.d(TAG, "DocumentSnapshot successfully deleted!")
            }
            .addOnFailureListener { e -> Log.w(TAG, "Error deleting document", e) }
    }

    private fun editarTwitt(){
        db.collection(nombreColeccion)
            .document(id.value!!)
            .set(hashMapOf("descripcion" to descripcion.value), SetOptions.merge())
            .addOnSuccessListener {
                limpiarEstado()
                limpiarCampos()
                Log.d(TAG, "DocumentSnapshot successfully updated!")
            }
            .addOnFailureListener { e -> Log.w(TAG, "Error update document", e) }
    }

    fun setTwittActual(twittActual:TwittModelo){
        descripcion.value = twittActual.descripcion
        id.value = twittActual.id
    }

    fun limpiarEstado(){
        estado.value = INACTIVO
    }

    fun limpiarCampos(){
        descripcion.value = ""
        id.value = ""
    }

    companion object{
        val Factory = viewModelFactory {
            initializer {
                TwittViewModel()
            }
        }
        const val EDITAR_TWITT = "Editar twitt"
        const val INACTIVO = ""
    }
}