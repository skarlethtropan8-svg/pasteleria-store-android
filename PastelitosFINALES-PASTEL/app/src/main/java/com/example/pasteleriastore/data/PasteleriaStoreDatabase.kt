package com.example.pasteleriastore.data

import androidx.room.Database
import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.pasteleriastore.model.DetallePedido
import com.example.pasteleriastore.model.Usuario
import com.example.pasteleriastore.model.Producto
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch




@Database(
    entities = [
        Usuario::class,
        Producto::class,
        DetallePedido::class
    ], version = 1 // cambiaria a version 2
)

abstract class PasteleriaStoreDatabase : RoomDatabase(){
    abstract fun usuarioDao(): UsuarioDao
    abstract fun productoDao(): ProductoDao

    abstract fun carritoDao(): CarritoDao


    companion object {
        @Volatile
        private var database: PasteleriaStoreDatabase? = null

        fun getDatabase(context: Context): PasteleriaStoreDatabase {
            return database?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PasteleriaStoreDatabase::class.java,
                    "pastelitos.db"
                ).addCallback(object : Callback(){
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            //SE LLAMA A INSERTAR DESPUES DE UN DELAY
                            CoroutineScope(Dispatchers.IO).launch(){
                                kotlinx.coroutines.delay(1000) // esperar para que la bd este lista
                                //obtener Instancia de bd
                                val dbInstance = getDatabase(context.applicationContext)
                                insertarDatosPorDefecto(dbInstance)
                            }
                        }
                    })
                    .fallbackToDestructiveMigration()//elimina base de datos al cambiar la version
                    .build()
                database = instance
                instance

            }
        }
        private suspend fun insertarDatosPorDefecto(db: PasteleriaStoreDatabase) {
            //Insertar a Usuarios
            val usuarioDao = db.usuarioDao()
            val usuario = listOf(
                Usuario(nombre = "admi", contrasena = "12345", correo = "admi@milsabores.cl"),
                Usuario(nombre = "cliente", contrasena = "12345", correo = "cliente@milsabores.cl"),
                Usuario(nombre = "cliente2", contrasena = "12345", correo = "cliente2@milsabores.cl"),
            )
            usuario.forEach { usuarioDao.insertarUsuario (it) }
            println("Usuarios insertados: ${usuario.size}")

            //Insertar los productos
            val productoDao = db.productoDao()
            val productos = listOf(
                Producto(
                    nombreProducto = "Torta Cuadrada de Chocolate",
                    descripcion = "Deliciosa torta de chocolate con capas de ganache y un toque de avellanas. Personalizable con mensajes especiales.",
                    precio = 45000.0,
                    imagen = "tortachocolate.png",
                    stock = 10,
                    categoria = "Tortas Cuadradas"
                ),
                Producto(
                     nombreProducto = "Torta Cuadrada de Frutas",
                     descripcion = "Una mezcla de frutas frescas y crema chantilly sobre un suave bizcocho de vainilla, ideal para celebraciones.",
                     precio = 50000.0,
                     imagen = "tortafruta.png",
                     stock = 8,
                     categoria = "Tortas Cuadradas"
                ),
                Producto(
                    nombreProducto = "Torta Circular de Vainilla",
                    descripcion = "Bizcocho de vainilla clásico relleno con crema pastelera y cubierto con un glaseado dulce, perfecto para cualquier ocasión.",
                    precio = 40000.0,
                    imagen = "tortavainilla.png",
                    stock = 12,
                    categoria = "Tortas Circulares"
                ),
                Producto(
                    nombreProducto = "Torta Circular de Manjar",
                    descripcion = "Torta tradicional chilena con manjar y nueces, un deleite para los amantes de los sabores dulces y clásicos.",
                    precio = 42000.0,
                    imagen = "tortamanjar.png",
                    stock = 9,
                    categoria = "Tortas Circulares"
                ),
                Producto(
                    nombreProducto = "Mousse de Chocolate",
                    descripcion = "Postre individual cremoso y suave, hecho con chocolate de alta calidad, ideal para los amantes del chocolate.",
                    precio = 5000.0,
                    imagen = "mousechoco.png",
                    stock = 20,
                    categoria = "Postres Individuales"
                ),
                Producto(
                    nombreProducto = "Tiramisú Clásico",
                    descripcion = "Un postre italiano individual con capas de café, mascarpone y cacao, perfecto para finalizar cualquier comida.",
                    precio = 5500.0,
                    imagen = "tiramisuclasico.png",
                    stock = 15,
                    categoria = "Postres Individuales"
                ),
                Producto(
                    nombreProducto = "Torta Sin Azúcar de Naranja",
                    descripcion = "Torta ligera y deliciosa, endulzada naturalmente, ideal para quienes buscan opciones más saludables.",
                    precio = 48000.0,
                    imagen = "tortanaranja.png",
                    stock = 7,
                    categoria = "Productos Sin Azúcar"
                ),
                Producto(
                    nombreProducto = "Cheesecake Sin Azúcar",
                    descripcion = "Suave y cremoso, este cheesecake es una opción perfecta para disfrutar sin culpa.",
                    precio = 47000.0,
                    imagen = "cheescake.png",
                    stock = 6,
                    categoria = "Productos Sin Azúcar"
                ),
                Producto(
                    nombreProducto = "Empanada de Manzana",
                    descripcion = "Pastelería tradicional rellena de manzanas especiadas, perfecta para un dulce desayuno o merienda.",
                    precio = 3000.0,
                    imagen = "empanadademanzana.png",
                    stock = 25,
                    categoria = "Pastelería Tradicional"
                ),
                Producto(
                    nombreProducto = "Tarta de Santiago",
                    descripcion = "Tradicional tarta española hecha con almendras, azúcar y huevos, una delicia para los amantes de los postres clásicos.",
                    precio = 6000.0,
                    imagen = "tartasantiago.png",
                    stock = 10,
                    categoria = "Pastelería Tradicional"
                ),
                Producto(
                    nombreProducto = "Brownie Sin Gluten",
                    descripcion = "Rico y denso, este brownie es perfecto para quienes necesitan evitar el gluten sin sacrificar el sabor.",
                    precio = 4000.0,
                    imagen = "browniesingluten.png",
                    stock = 18,
                    categoria = "Productos Sin Gluten"
                ),
                Producto(
                    nombreProducto = "Pan Sin Gluten",
                    descripcion = "Suave y esponjoso, ideal para sándwiches o para acompañar cualquier comida.",
                    precio = 3500.0,
                    imagen = "pansingluten.png",
                    stock = 30,
                    categoria = "Productos Sin Gluten"
                ),
                Producto(
                    nombreProducto = "Torta Vegana de Chocolate",
                    descripcion = "Torta de chocolate húmeda y deliciosa, hecha sin productos de origen animal, perfecta para veganos.",
                    precio = 50000.0,
                    imagen = "chocovegano.png",
                    stock = 5,
                    categoria = "Productos Veganos"
                ),
                Producto(
                    nombreProducto = "Galletas Veganas de Avena",
                    descripcion = "Crujientes y sabrosas, estas galletas son una excelente opción para un snack saludable y vegano.",
                    precio = 4500.0,
                    imagen = "galletavegana.png",
                    stock = 40,
                    categoria = "Productos Veganos"
                ),
                Producto(
                    nombreProducto = "Torta Especial de Cumpleaños",
                    descripcion = "Diseñada especialmente para celebraciones, personalizable con decoraciones y mensajes únicos.",
                    precio = 55000.0,
                    imagen = "cumplecolorido.png",
                    stock = 4,
                    categoria = "Tortas Especiales"
                ),
                Producto(
                    nombreProducto = "Torta Especial de Boda",
                    descripcion = "Elegante y deliciosa, esta torta está diseñada para ser el centro de atención en cualquier boda.",
                    precio = 60000.0,
                    imagen = "boda.png",
                    stock = 3,
                    categoria = "Tortas Especiales"
                )

            )
            productos.forEach { productoDao.insertarProducto(it) }
            println("Productos insertados: ${productos.size}")

            kotlinx.coroutines.delay(500)



           // Agregar productos al carrito del cliente

            val carritoDao = db.carritoDao()
            val cliente = usuarioDao.obtenerUsuarioPorNombre("cliente")
            val cliente2 = usuarioDao.obtenerUsuarioPorNombre("cliente2")



          // Obtener productos recién insertados

            val productosDisponibles = productoDao.obtenerProducto()
            val tortaChoco = productosDisponibles.find { it.nombreProducto == "Torta Cuadrada de Chocolate" }
            val tortaFruta = productosDisponibles.find { it.nombreProducto == "Torta Cuadrada de Frutas" }
            val mousseChoco = productosDisponibles.find { it.nombreProducto == "Mousse de Chocolate" }
            val tiramisu = productosDisponibles.find { it.nombreProducto == "Tiramisú Clásico" }
            val cheesecake = productosDisponibles.find { it.nombreProducto == "Cheesecake Sin Azúcar" }
            val brownie = productosDisponibles.find { it.nombreProducto == "Brownie Sin Gluten" }
            val tortaVegana = productosDisponibles.find { it.nombreProducto == "Torta Vegana de Chocolate" }
            val galletasVeg = productosDisponibles.find { it.nombreProducto == "Galletas Veganas de Avena" }


            println("Productos disponibles: ${productosDisponibles.size}")
            println("Cliente encontrado: ${cliente != null}")
            println("Cliente2 encontrado: ${cliente2 != null}")

            // Carrito para el cliente

            cliente?.let { usuario ->
                println("Configurando carrito para cliente (ID: ${usuario.id})")

                tortaChoco?.let { producto ->
                    carritoDao.insertar(
                        DetallePedido(
                            usuarioId = usuario.id,
                            productoId = producto.productoId,
                            cantidad = 1
                        )
                    )
                    println("Agregado Torta Cuadrada de Chocolate al carrito del cliente")
                }

                tortaFruta?.let { producto ->
                    carritoDao.insertar(
                        DetallePedido(
                            usuarioId = usuario.id,
                            productoId = producto.productoId,
                            cantidad = 1
                        )
                    )
                    println("Agregado Torta Cuadrada de Frutas al carrito del cliente")
                }

                mousseChoco?.let { producto ->
                    carritoDao.insertar(
                        DetallePedido(
                            usuarioId = usuario.id,
                            productoId = producto.productoId,
                            cantidad = 3
                        )
                    )
                    println("Agregados 3 Mousse de Chocolate al carrito del cliente")
                }
            }
            // Carrito para cliente2 (diferentes productos)

            cliente2?.let { usuario ->
                println("Configurando carrito para cliente2 (ID: ${cliente2.id})")

                tiramisu?.let { producto ->
                    carritoDao.insertar(
                        DetallePedido(
                            usuarioId = usuario.id,
                            productoId = producto.productoId,
                            cantidad = 2
                        )
                    )
                    println("Agregado Tiramisú Clásico (x2) al carrito de cliente2")
                }

                cheesecake?.let { producto ->
                    carritoDao.insertar(
                        DetallePedido(
                            usuarioId = usuario.id,
                            productoId = producto.productoId,
                            cantidad = 1
                        )
                    )
                    println("Agregado Cheesecake Sin Azúcar al carrito de cliente2")
                }

                brownie?.let { producto ->
                    carritoDao.insertar(
                        DetallePedido(
                            usuarioId = usuario.id,
                            productoId = producto.productoId,
                            cantidad = 4
                        )
                    )
                    println("Agregados 4 Brownie Sin Gluten al carrito de cliente2")
                }

                tortaVegana?.let { producto ->
                    carritoDao.insertar(
                        DetallePedido(
                            usuarioId = usuario.id,
                            productoId = producto.productoId,
                            cantidad = 1
                        )
                    )
                    println("Agregada Torta Vegana de Chocolate al carrito de usuario2")
                }
            }
        }
    }
}




