package com.example.ejemplomvvm.data

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

object ProductRepository {

    private val _products = MutableStateFlow(getInitialProducts())
    val products = _products.asStateFlow()

    fun addRatingToProduct(productCode: String, rating: Int) {
        val currentProducts = _products.value.toMutableList()
        val productIndex = currentProducts.indexOfFirst { it.code == productCode }
        if (productIndex != -1) {
            val product = currentProducts[productIndex]
            product.ratings.add(rating)
            _products.value = currentProducts.toList() // Emite una nueva lista para la actualización
        }
    }

    private fun getInitialProducts(): List<Product> {
        return listOf(
            Product(
                code = "JM001",
                category = Category.JUEGOS_DE_MESA,
                name = "Catan",
                price = 29990.0,
                description = "Un clásico juego de estrategia...",
                imageUrl = "https://devirinvestments.s3.eu-west-1.amazonaws.com/img/catalog/product/8436017220100-1200-face3d.jpg"
            ),
            Product(
                code = "JM002",
                category = Category.JUEGOS_DE_MESA,
                name = "Carcassonne",
                price = 24990.0,
                description = "Un juego de colocación de fichas...",
                imageUrl = "https://m.media-amazon.com/images/I/81YADvXR5UL._AC_UF894,1000_QL80_.jpg"
            ),
            Product(
                code = "AC001",
                category = Category.ACCESORIOS,
                name = "Controlador Inalámbrico Xbox Series X",
                price = 59990.0,
                description = "Ofrece una experiencia de juego cómoda...",
                imageUrl = "https://prophonechile.cl/wp-content/uploads/2023/11/purpleeee.png"
            ),
            Product(
                code = "AC002",
                category = Category.ACCESORIOS,
                name = "Auriculares Gamer HyperX Cloud II",
                price = 79990.0,
                description = "Proporcionan un sonido envolvente de calidad...",
                imageUrl = "https://todoclick.cl/6730625-large_default/audifonosgamerhyperxcloudiiwirelessredusb71ps4xboxonepcymac.jpg"
            ),
            Product(
                code = "CO001",
                category = Category.CONSOLAS,
                name = "PlayStation 5",
                price = 549990.0,
                description = "La consola de última generación de Sony...",
                imageUrl = "https://clsonyb2c.vtexassets.com/arquivos/ids/465172-800-800?v=638658958190900000&width=800&height=800&aspect=true"
            ),
            Product(
                code = "CG001",
                category = Category.COMPUTADORES_GAMERS,
                name = "PC Gamer ASUS ROG Strix",
                price = 1299990.0,
                description = "Un potente equipo diseñado para los gamers...",
                imageUrl = "https://www.gsmpro.cl/cdn/shop/files/asus-rog-strix-g16-g614jv-as73.jpg?v=1747340739"
            ),
            Product(
                code = "SG001",
                category = Category.SILLAS_GAMERS,
                name = "Silla Gamer Secretlab Titan",
                price = 349990.0,
                description = "Diseñada para el máximo confort...",
                imageUrl = "https://images-na.ssl-images-amazon.com/images/I/41vyYB3rS9L.jpg"
            ),
            Product(
                code = "MS001",
                category = Category.MOUSE,
                name = "Mouse Gamer Logitech G502 HERO",
                price = 49990.0,
                description = "Con sensor de alta precisión...",
                imageUrl = "https://tienda.lancenter.cl/674-large_default/g502-heroe.jpg"
            ),
            Product(
                code = "MP001",
                category = Category.MOUSEPAD,
                name = "Mousepad Razer Goliathus Extended Chroma",
                price = 29990.0,
                description = "Ofrece un área de juego amplia...",
                imageUrl = "https://www.weplay.cl/pub/media/catalog/product/cache/3f1b140c3c9f36fbf6b01dffb521c246/8/8/88864193180712_1.jpeg"
            ),
            Product(
                code = "PP001",
                category = Category.POLERAS_PERSONALIZADAS,
                name = "Polera Gamer Personalizada 'Level-Up'",
                price = 14990.0,
                description = "Una camiseta cómoda y estilizada...",
                imageUrl = "https://cdnx.jumpseller.com/wiwilatienda/image/40833729/resize/810/810?1710050804"
            )
        )
    }
}
