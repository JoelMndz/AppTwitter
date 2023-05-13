package com.example.clontwitter.modelo
import com.google.firebase.Timestamp
import java.util.*

class TwittModelo(
    var descripcion: String,
    var fechaCreacion: Timestamp = Timestamp.now(),
    var id: String? = null
) {

    companion object {
        fun fromHashMap(hashMap: HashMap<String, Any>): TwittModelo {
            return TwittModelo(
                descripcion = hashMap["descripcion"] as String,
                fechaCreacion = hashMap["fechaCreacion"] as Timestamp,
                id = hashMap["id"] as String? ?: null
            )
        }
    }

    fun toHashMap(): HashMap<String, Any> {
        return hashMapOf(
            "descripcion" to descripcion,
            "fechaCreacion" to fechaCreacion
        )
    }
}
