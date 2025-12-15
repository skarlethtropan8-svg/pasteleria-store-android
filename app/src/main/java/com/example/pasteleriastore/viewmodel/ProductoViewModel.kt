package com.example.pasteleriastore.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.pasteleriastore.data.PasteleriaStoreDatabase
import com.example.pasteleriastore.model.Producto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class ProductoViewModel (application: Application): AndroidViewModel(application){

    private val database = PasteleriaStoreDatabase.getDatabase(application)
    private val productoDao = database.productoDao()
    private val _productos = MutableStateFlow(emptyList<Producto>())
    val productos: StateFlow<List<Producto>> = _productos
    private val _categorias = MutableStateFlow(emptyList<String>())
    val categorias: StateFlow<List<String>> = _categorias



    fun cargarProductos(){
        viewModelScope.launch {
            val lista = productoDao.obtenerProductosActivos()
            _productos.value = lista
        }
    }

    fun cargarProductosPorCategoria(categoria: String) {
        viewModelScope.launch {
            val lista = productoDao.obtenerPorCategoria(categoria)
            _productos.value = lista
        }
    }
    suspend fun guardarProducto(producto: Producto): Boolean {
        return try {
            //Insertar nuevo producto
            productoDao.insertarProducto(producto)
            //Recarga la lista de productos despues de guardar
            cargarProductos()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
    //Cargar categorías unicas de nuestra  base de datos
    fun cargarCategorias() {
        viewModelScope.launch {
            val todosProductos = productoDao.obtenerProductosActivos()
            // Obtener categorías unicas y ordenadas
            val categoriasUnicas = todosProductos
                .map { it.categoria }
                .distinct()
                .sorted()
            _categorias.value = listOf("Todos") + categoriasUnicas
        }
    }
}