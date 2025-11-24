package com.example.tiendaoly.Modelos
import com.example.tiendaoly.Contratos.ContratoProducto

 class ProductoModelo: ContratoProducto.modeloProd {

     lateinit var lista: List<Producto>

     override fun loadProducto(): List<Producto> {
         lista=listOf(


                 Producto("Coca.png", 1, "Coca-Cola 600ml", "Refresco Coca-Cola botella 600ml", 18.00, 17, "1"),
                 Producto("Pepsi.png", 2, "Pepsi 600ml", "Refresco Pepsi botella 600ml", 17.50, 38, "1"),
                 Producto("Sabritas.png", 3, "Papas Fritas 150g", "Bolsa de papas fritas sabor queso", 25.00, 28, "2"),
                 Producto("Yogurt.png", 4, "Yogurt Natural 1L", "Yogurt natural bajo en grasa 1 litro", 32.00, 20, "3"),
                 Producto("Pan.png", 5, "Pan Blanco 680g", "Pan de caja blanco rebanado 680g", 28.00, 25, "4"),
                 Producto("Arroz.png", 6, "Arroz Integral 1kg", "Arroz integral bolsa 1kg", 35.00, 40, "5"),
                 Producto("Detergente.png", 7, "Detergente Líquido 1L", "Detergente líquido multiusos 1 litro", 45.00, 15, "6"),
                 Producto("Jamon.png", 8, "Jamón de Pavo 250g", "Jamón de pavo bajo en sal", 48.00, 20, "7"),
                 Producto("Manzana.png", 9, "Manzana Golden Kg", "Manzana Golden fresca por kg", 42.00, 35, "8"),

         )
         return lista
     }
 }
