package com.example.pasteleriastore.model



data class Carrito (
    val producto: Producto,
    val cantidad: Int,
    val usuarioNombre: String
){
    fun getSubtotal(): Double = producto.precio * cantidad
}