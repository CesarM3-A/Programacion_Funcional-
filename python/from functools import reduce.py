from functools import reduce

# Clase Producto
class Producto:
    def __init__(self, nombre, categoria, precio, stock):
        self.nombre = nombre
        self.categoria = categoria
        self.precio = precio
        self.stock = stock

    def __str__(self):
        return self.nombre

# Lista de 12 productos (la misma de Scala y Java)
productos = [
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
]

# ============================================
# 1. SELECCIÓN (filter)
# ============================================
print("=== 1. SELECCIÓN (filter) ===")
caros = list(filter(lambda p: p.precio > 100, productos))
print("Productos con precio > $100:", [p.nombre for p in caros])

# ============================================
# 2. TRANSFORMACIÓN (map)
# ============================================
print("\n=== 2. TRANSFORMACIÓN (map) ===")
con_descuento = list(map(lambda p: p.precio * 0.9, productos))
print("Primer producto con descuento (Laptop):", con_descuento[0])

# ============================================
# 3. VALIDACIÓN (all -> forall)
# ============================================
print("\n=== 3. VALIDACIÓN (forall / all) ===")
hay_stock = all(map(lambda p: p.stock > 0, productos))
print("¿Todos los productos tienen stock disponible?", hay_stock)

# ============================================
# 4. ACUMULACIÓN (reduce -> foldLeft)
# ============================================
print("\n=== 4. ACUMULACIÓN (foldLeft / reduce) ===")
valor_inventario = reduce(lambda acc, p: acc + (p.precio * p.stock), productos, 0.0)
print("Valor total del inventario:", valor_inventario)