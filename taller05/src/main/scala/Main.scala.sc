// Definición de la clase (esto va al inicio)
case class Producto(nombre: String, categoria: String, precio: Double, stock: Int)

// Lista de productos
val productos = List(
  Producto("Laptop", "Electrónica", 850.0, 10),
  Producto("Mouse", "Electrónica", 25.0, 150),
  Producto("Teclado", "Electrónica", 45.0, 80),
  Producto("Monitor", "Electrónica", 200.0, 30),
  Producto("Silla", "Muebles", 120.0, 20),
  Producto("Escritorio", "Muebles", 250.0, 15),
  Producto("Lámpara", "Muebles", 35.0, 60),
  Producto("Café", "Abarrotes", 5.0, 500),
  Producto("Arroz", "Abarrotes", 2.0, 1000),
  Producto("Audífonos", "Electrónica", 150.0, 40),
  Producto("Libreta", "Papelería", 3.0, 300),
  Producto("Mochila", "Accesorios", 60.0, 45)
)

// ============================================
// 1. SELECCIÓN (filter)
// ============================================
println("=== 1. SELECCIÓN (filter) ===")
val caros = productos.filter(p => p.precio > 100)
println("Productos con precio > $100:")
caros.foreach(p => println("  - " + p.nombre + ": " + p.precio))

// ============================================
// 2. TRANSFORMACIÓN (map)
// ============================================
println("\n=== 2. TRANSFORMACIÓN (map) ===")
val conDescuento = productos.map(p => p.copy(precio = p.precio * 0.9))
println("Primer producto con descuento: " + conDescuento.head.nombre + " - " + conDescuento.head.precio)
println("Top 3 precios con descuento:")
conDescuento.take(3).foreach(p => println("  - " + p.nombre + ": " + p.precio))

// ============================================
// 3. VALIDACIÓN (forall)
// ============================================
println("\n=== 3. VALIDACIÓN (forall) ===")
val hayStock = productos.forall(p => p.stock > 0)
println("¿Todos los productos tienen stock disponible? " + hayStock)

// ============================================
// 4. ACUMULACIÓN (foldLeft)
// ============================================
println("\n=== 4. ACUMULACIÓN (foldLeft) ===")
val valorInventario = productos.foldLeft(0.0)((acc, p) => acc + (p.precio * p.stock))
println("Valor total del inventario: $" + valorInventario)