package com.example.pasteleriastore.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "detalledulce")

data class DetallePedido(
    @PrimaryKey(autoGenerate = true)
    val detalleCarritoId: Int = 0,
    val usuarioId: Int,
    val productoId: Int,
    val cantidad: Int
)


