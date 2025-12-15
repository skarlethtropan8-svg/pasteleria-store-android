package com.example.pasteleriastore.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.pasteleriastore.data.PasteleriaStoreDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CarritoViewModel (application: Application) : AndroidViewModel(application) {
    // Base de datos de la Pastelería Mil Sabores
    private val database = PasteleriaStoreDatabase.getDatabase(application)
    private val carritoDao = database.carritoDao()
    private val productoDao = database.productoDao()
    private val usuarioDao = database.usuarioDao()

    private val _carrito = MutableStateFlow<List<com.example.pasteleriastore.model.Carrito>>(emptyList())
    val Carrito: StateFlow<List<com.example.pasteleriastore.model.Carrito>> = _carrito

    // CARGAR CARRITO DEL USUARIO
    fun cargarCarrito(usuarioNombre: String) {
        viewModelScope.launch {
            val usuario = usuarioDao.obtenerUsuarioPorNombre(usuarioNombre) ?: return@launch
            val itemsCarrito = carritoDao.obtenerCarritoPorUsuario(usuario.id)

            val carritoUI = itemsCarrito.mapNotNull { detalleCarrito ->
                val producto = productoDao.obtenerPorId(detalleCarrito.productoId)
                producto?.let {
                    com.example.pasteleriastore.model.Carrito(
                        producto = it,
                        cantidad = detalleCarrito.cantidad,
                        usuarioNombre = usuarioNombre
                    )
                }
            }
            _carrito.value = carritoUI
        }
    }
    // AGREGAR PRODUCTO AL CARRITO

    fun agregarProducto(usuarioNombre: String, producto: com.example.pasteleriastore.model.Producto) {
        viewModelScope.launch {
            val usuario = usuarioDao.obtenerUsuarioPorNombre(usuarioNombre) ?: return@launch
            val itemExistente = carritoDao.obtenerItem(usuario.id, producto.productoId)

            if (itemExistente != null) {
                // Aumentar cantidad
                carritoDao.actualizarCantidad(
                    itemExistente.detalleCarritoId,
                    itemExistente.cantidad + 1
                )
            } else {
                // Insertar producto nuevo en carrito
                carritoDao.insertar(
                    com.example.pasteleriastore.model.DetallePedido(
                        usuarioId = usuario.id,
                        productoId = producto.productoId,
                        cantidad = 1
                    )
                )
            }
            cargarCarrito(usuarioNombre)
        }
    }

    // ELIMINAR PRODUCTO DEL CARRITO

    fun eliminarProducto(usuarioNombre: String, productoId: Int) {
        viewModelScope.launch {
            val usuario = usuarioDao.obtenerUsuarioPorNombre(usuarioNombre) ?: return@launch
            val item = carritoDao.obtenerItem(usuario.id, productoId)
            item?.let {
                carritoDao.eliminar(it)
                cargarCarrito(usuarioNombre)
            }
        }
    }
    // CALCULAR TOTAL DEL CARRITO
    fun calcularTotal(): Double {
        return _carrito.value.sumOf { it.getSubtotal() }
    }
}
