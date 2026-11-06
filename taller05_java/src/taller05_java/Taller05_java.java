
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

// Clase Producto
class Producto {

    String nombre, categoria;
    double precio;
    int stock;

    public Producto(String nombre, String categoria, double precio, int stock) {
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
        this.stock = stock;
    }

    public double getPrecio() {
        return precio;
    }

    public int getStock() {
        return stock;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public String toString() {
        return nombre;
    }
}

public class Taller05_java {

    public static void main(String[] args) {

        // Lista de 12 productos (la misma de Scala)
        List<Producto> productos = Arrays.asList(
                new Producto("Laptop", "Electrónica", 850.0, 10),
                new Producto("Mouse", "Electrónica", 25.0, 150),
                new Producto("Teclado", "Electrónica", 45.0, 80),
                new Producto("Monitor", "Electrónica", 200.0, 30),
                new Producto("Silla", "Muebles", 120.0, 20),
                new Producto("Escritorio", "Muebles", 250.0, 15),
                new Producto("Lámpara", "Muebles", 35.0, 60),
                new Producto("Café", "Abarrotes", 5.0, 500),
                new Producto("Arroz", "Abarrotes", 2.0, 1000),
                new Producto("Audífonos", "Electrónica", 150.0, 40),
                new Producto("Libreta", "Papelería", 3.0, 300),
                new Producto("Mochila", "Accesorios", 60.0, 45)
        );

        // ============================================
        // 1. SELECCIÓN (filter)
        // ============================================
        System.out.println("=== 1. SELECCIÓN (filter) ===");
        List<Producto> caros = productos.stream()
                .filter(p -> p.getPrecio() > 100)
                .collect(Collectors.toList());

        System.out.println("Productos con precio > $100: " + caros);

        // ============================================
        // 2. TRANSFORMACIÓN (map)
        // ============================================
        System.out.println("\n=== 2. TRANSFORMACIÓN (map) ===");
        List<Double> conDescuento = productos.stream()
                .map(p -> p.getPrecio() * 0.9)
                .collect(Collectors.toList());

        System.out.println("Primer producto con descuento (Laptop): " + conDescuento.get(0));

        // ============================================
        // 3. VALIDACIÓN (allMatch -> forall)
        // ============================================
        System.out.println("\n=== 3. VALIDACIÓN (forall / allMatch) ===");
        boolean hayStock = productos.stream().allMatch(p -> p.getStock() > 0);
        System.out.println("¿Todos los productos tienen stock disponible? " + hayStock);

        // ============================================
        // 4. ACUMULACIÓN (sum -> foldLeft)
        // ============================================
        System.out.println("\n=== 4. ACUMULACIÓN (foldLeft / sum) ===");
        double valorInventario = productos.stream()
                .mapToDouble(p -> p.getPrecio() * p.getStock())
                .sum();

        System.out.println("Valor total del inventario: " + valorInventario);
    }
}
