case class Producto(nombre: String, categoria: String, precio: Double, stock: Int)

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
// 1. AGRUPACIÓN (groupBy)
// ============================================
println("=== 1. AGRUPACIÓN (groupBy) ===")
val porCategoria = productos.groupBy(_.categoria)
porCategoria.foreach { case (cat, prods) =>
  println("  " + cat + ": " + prods.size + " productos")
}

// ============================================
// 2. ORDENAMIENTO (sortBy)
// ============================================
println("\n=== 2. ORDENAMIENTO (sortBy) ===")
val masCaros = productos.sortBy(-_.precio).take(3).map(_.nombre)
println("Top 3 más caros: " + masCaros)

// ============================================
// 3. BÚSQUEDA (find)
// ============================================
println("\n=== 3. BÚSQUEDA (find) ===")
val monitor = productos.find(_.nombre == "Monitor")
println("¿Encontrado? " + monitor.isDefined)
println("Precio del Monitor: " + monitor.map(_.precio).getOrElse(0.0))

// ============================================
// 4. DIVISIÓN (partition)
// ============================================
println("\n=== 4. DIVISIÓN (partition) ===")
val (premium, economicos) = productos.partition(_.precio >= 100)
println("Premium (>= $100): " + premium.size + " productos")
println("Económicos (< $100): " + economicos.size + " productos")

// ============================================
// 5. COMBINACIÓN DE OPERACIONES
// ============================================
println("\n=== 5. COMBINACIÓN DE OPERACIONES ===")
val resultado = productos
  .groupBy(_.categoria)
  .map { case (cat, prods) =>
    val promedio = prods.map(_.precio).sum / prods.size
    val stockTotal = prods.map(_.stock).sum
    (cat, promedio, stockTotal)
  }
  .filter { case (_, avg, stock) => avg > 100 && stock >= 30 }
  .toList
  .sortBy(-_._3)

println("Categorías que cumplen la condición:")
resultado.foreach { case (cat, avg, stock) =>
  println("  - " + cat + " | Promedio: " + avg + " | Stock Total: " + stock)
}