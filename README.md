# Trabajo de Consulta: Funciones Anónimas
**Materia:** Programación Funcional  
**Lenguajes:** Scala • Java • Python  
**Autor:** [Tu nombre]  
**Fecha:** Noviembre 2026  

---

## Parte 1 – Explorar: La programación funcional en el mundo real

### Caso 1: Apache Spark (Procesamiento de Big Data)
* **Problema que resuelven:** Procesar terabytes de datos distribuidos en clústeres de manera rápida y tolerante a fallos.
* **Lenguaje/Herramienta:** Scala (Apache Spark).
* **Ventaja del enfoque funcional:** Spark usa RDDs (Resilient Distributed Datasets) y DataFrames. Las funciones anónimas (`map`, `filter`, `reduce`) permiten transformar datos de forma inmutable y distribuir el procesamiento en múltiples nodos sin efectos secundarios.
* **Fuente:** Documentación oficial de Apache Spark (https://spark.apache.org/docs/latest/rdd-programming-guide.html).
* **Fragmento de código real:**
```scala
// Contar palabras en un archivo de texto distribuido
val conteo = sc.textFile("hdfs://datos/archivo.txt")
  .flatMap(linea => linea.split(" ")) // Lambda que aplana las líneas en palabras
  .map(palabra => (palabra, 1))       // Lambda que mapea cada palabra a una tupla
  .reduceByKey((a, b) => a + b)       // Lambda que acumula los conteos
```

### Caso 2: Java Streams en Sistemas Empresariales (Netflix / Banca)
* **Problema que resuelven:** Filtrar, transformar y agregar grandes volúmenes de transacciones o eventos de usuarios en tiempo real.
* **Lenguaje/Herramienta:** Java (Java 8+ Streams API).
* **Ventaja del enfoque funcional:** Permite escribir código declarativo y paralelo. En lugar de bucles `for` mutables, se encadenan operaciones que el motor de Streams puede optimizar y ejecutar en múltiples hilos (`parallelStream()`).
* **Fuente:** Blog técnico de Netflix sobre su migración a arquitecturas reactivas y uso intensivo de Java Streams para procesamiento de eventos.

---

## Parte 2 – Entender: ¿Qué es una función anónima?

### ¿Qué es y de dónde viene el nombre lambda?
Una función anónima es un bloque de código que realiza una tarea específica pero que no está vinculada a un identificador (nombre) al ser declarada. El nombre "lambda" viene del *Cálculo Lambda* de Alonzo Church (1930), un sistema formal para definir funciones y aplicarlas, que es la base teórica de la programación funcional.

### Diferencia con una función nombrada
```scala
// Función nombrada: tiene un identificador (calcularIVA)
def calcularIVA(precio: Double): Double = precio * 0.12

// Función anónima (Lambda): no tiene nombre, se define y usa en el momento
(precio: Double) => precio * 0.12
```

### Ciudadanos de primera clase
Significa que las funciones pueden ser tratadas como cualquier otra variable: pueden ser asignadas a variables, pasadas como argumentos a otras funciones, o devueltas como resultado. Esto permite pasar una lambda directamente a métodos como `map` o `filter` para definir *cómo* procesar los datos sin necesidad de crear métodos auxiliares en la clase.

### ¿Qué es un closure?
Es una función anónima que "recuerda" y utiliza variables definidas fuera de su propio ámbito (scope).
```scala
val tasaImpuesto = 0.15 // Variable externa
val aplicarImpuesto = (precio: Double) => precio * tasaImpuesto // Closure: captura tasaImpuesto
```

---

## Parte 3 – Aplicar: Tipos de problemas resueltos

Para todos los ejemplos usamos la misma lista de objetos `Producto` (12 elementos) para mantener la consistencia. El código completo y ejecutable está en las carpetas `scala/`, `java/` y `python/`.

### 1. Selección (filter)
* **Situación real:** Un banco necesita detectar las transacciones (productos) mayores a $100 para aplicar un seguro.
* **Solución:** Filtrar la lista usando una lambda que evalúe `precio > 100`.

📸 **CAPTURA 1:** Aquí va la salida de la consola de los 3 lenguajes mostrando los productos con precio > 100.
![Selección - Scala](img/01_filter_scala.png)
![Selección - Java](img/02_filter_java.png)
![Selección - Python](img/03_filter_python.png)

### 2. Transformación (map)
* **Situación real:** Black Friday. Necesitamos aplicar un descuento del 10% a todos los productos para generar las nuevas etiquetas de precio.
* **Solución:** Mapear cada producto multiplicando su precio por `0.9`.

📸 **CAPTURA 2:** Aquí va la salida mostrando el primer producto con el precio ya con descuento.
![Transformación - Scala](img/04_map_scala.png)
![Transformación - Java](img/05_map_java.png)
![Transformación - Python](img/06_map_python.png)

### 3. Validación (forall)
* **Situación real:** El sistema de checkout debe verificar si *todos* los productos en el carrito tienen stock disponible (`stock > 0`) antes de procesar el pago.
* **Solución:** Usar `forall` con una lambda que retorne `producto.stock > 0`.

📸 **CAPTURA 3:** Aquí va la salida mostrando `true` (todos tienen stock).
![Validación - Scala](img/07_forall_scala.png)
![Validación - Java](img/08_forall_java.png)
![Validación - Python](img/09_forall_python.png)

### 4. Acumulación (foldLeft / reduce)
* **Situación real:** Calcular el valor total del inventario de la bodega (sumar `precio * stock` de cada producto).
* **Solución:** Acumular el valor inicial en 0 y sumar el valor de cada producto paso a paso.

📸 **CAPTURA 4:** Aquí va la salida mostrando el valor total del inventario (debe dar el mismo número en los 3 lenguajes).
![Acumulación - Scala](img/10_fold_scala.png)
![Acumulación - Java](img/11_fold_java.png)
![Acumulación - Python](img/12_fold_python.png)

---

## Parte 4 – Ampliar: Cuatro operaciones nuevas

El código completo está en `operaciones/OperacionesNuevas.scala`.

### Ficha 1: `groupBy`
1. **Qué hace:** Agrupa los elementos de una colección en un Map según el resultado de aplicar una función a cada elemento.
2. **Lambda que recibe:** Recibe una función `A => K` (donde K es la clave de agrupación).
3. **Problema que resuelve:** Agrupar productos por categoría para generar reportes por departamento. (Tipo: *Agrupación*).

📸 **CAPTURA 5:** Salida del `groupBy` mostrando cuántos productos hay por categoría.
![groupBy](img/13_groupBy.png)

### Ficha 2: `sortBy`
1. **Qué hace:** Ordena los elementos de la colección según el valor devuelto por una función de comparación.
2. **Lambda que recibe:** Recibe una función `A => B` donde B es un tipo ordenable (Int, Double, String).
3. **Problema que resuelve:** Mostrar los productos más caros en la parte superior de la página web. (Tipo: *Ordenamiento*).

📸 **CAPTURA 6:** Salida del `sortBy` mostrando el top 3 de productos más caros.
![sortBy](img/14_sortBy.png)

### Ficha 3: `find`
1. **Qué hace:** Devuelve el primer elemento que cumple una condición, envuelto en un `Option` (Some o None).
2. **Lambda que recibe:** Recibe un predicado `A => Boolean`.
3. **Problema que resuelve:** Buscar si existe un producto específico por su ID o nombre exacto para mostrar su detalle. (Tipo: *Búsqueda*).

📸 **CAPTURA 7:** Salida del `find` buscando el producto "Monitor".
![find](img/15_find.png)

### Ficha 4: `partition`
1. **Qué hace:** Divide la colección en dos tuplas de listas: la primera con los elementos que cumplen la condición, la segunda con los que no.
2. **Lambda que recibe:** Recibe un predicado `A => Boolean`.
3. **Problema que resuelve:** Separar rápidamente productos con descuento (precio < 50) de productos premium (precio >= 50). (Tipo: *División/Selección dual*).

📸 **CAPTURA 8:** Salida del `partition` mostrando cuántos productos quedaron en cada grupo.
![partition](img/16_partition.png)

### Combinación de operaciones
**Pregunta:** ¿Qué categorías tienen un precio promedio mayor a $100 y al menos 30 unidades en stock total, ordenadas por stock total de mayor a menor?

**Cadena de operaciones:**
```scala
productos
  .groupBy(_.categoria)                                    // 1. Agrupa por categoría (Nueva)
  .map { case (cat, prods) => 
    (cat, prods.map(_.precio).sum / prods.size, prods.map(_.stock).sum) 
  }                                                        // 2. Calcula promedio y stock total (Vista en clase: map)
  .filter { case (_, avg, stock) => avg > 100 && stock >= 30 } // 3. Filtra (Vista en clase)
  .toList
  .sortBy(-_._3)                                           // 4. Ordena por stock descendente (Nueva)
```

📸 **CAPTURA 9:** Salida de la combinación mostrando las categorías filtradas y ordenadas.
![Combinación](img/17_combinacion.png)

---

## Parte 5 – Comparar y concluir

### 5.1 Scala, Java y Python frente a frente

| Criterio | Scala | Java | Python |
| :--- | :--- | :--- | :--- |
| **Sintaxis de la lambda** | `(x) => expresion` (súper directa) | `(x) -> expresion` (la flecha gorda) | `lambda x: expresion` |
| **Cómo se declara el tipo** | El compilador casi siempre lo adivina, pero puedes forzarlo `(x: Int)` | A veces lo infiere, pero toca ser explícito `(int x)` | No se declara nada, es dinámico. |
| **Operaciones con listas** | Vienen nativas y son súper potentes (`List`, `Seq`). | Tocó usar la API de `Stream` y luego `collect`. | Nativas como `map`/`filter` o tirando de `functools`. |
| **Restricción del lenguaje** | Las variables que captura el closure deben ser `val` (inmutables). | Las variables externas tienen que ser `final` o "efectivamente final". | No hay restricciones fuertes, pero hay que cuidar el scope. |
| **Legibilidad (mi opinión)** | ⭐⭐⭐⭐⭐ La que mejor fluye, se lee casi como inglés. | ⭐⭐⭐ Un poco verbosa, mucho código de relleno. | ⭐⭐⭐⭐ Muy limpia, pero el `lambda` se siente limitado. |

**Reflexión personal:**

La verdad, el lenguaje en el que se sintió más natural resolver todo fue **Scala**. Como está hecho para esto desde el principio, encadenar un `.filter` con un `.map` se siente fluido y el código queda cortito y al grano. 

**Java** se sintió un poco más pesado. No porque no pueda hacerlo, sino por todo el "boilerplate" que toca escribir: crear el `Stream`, hacer la operación y luego acordarse de hacer el `.collect()` para volver a tener una lista. Se nota que la programación funcional se le añadió después, no nació con ella.

**Python** es súper limpio y fácil de leer, pero me chocó un poco que la función `lambda` esté restringida a una sola expresión. Si la lógica se complica un poquito, toca salirse del `lambda` y crear una función normal con `def`, lo que le quita un poco la gracia a hacerlo todo en una línea.

**¿Dónde noté más diferencias?**  
Sin duda, en el problema de **Acumulación** (`foldLeft` / `reduce`). En Scala y Python es bastante directo sumar o acumular valores. En Java, en cambio, tuve que pelearme un poco más con `mapToDouble` y `.sum()`, o escribir un acumulador más largo, porque manejar tipos primitivos dentro de los Streams a veces da dolores de cabeza con el boxing/unboxing.

### 5.2 Conclusión
Las funciones anónimas son el motor de la manipulación de datos moderna. Transforman la programación de listas de un enfoque imperativo (decirle a la máquina *cómo* hacer el bucle paso a paso) a un enfoque declarativo (decirle a la máquina *qué* queremos obtener). Esto no solo reduce drásticamente los errores por mutación de estado, sino que permite a los compiladores y motores de ejecución (como Spark o Java Streams) paralelizar el código automáticamente. Como vimos en el caso de Apache Spark, sin funciones anónimas puras e inmutables, el procesamiento distribuido de Big Data tal como lo conocemos hoy no sería posible.

---

## Referencias
* Documentación oficial de Scala: https://docs.scala-lang.org
* Apache Spark RDD Guide: https://spark.apache.org/docs/latest/rdd-programming-guide.html
* Java Lambda Expressions: https://docs.oracle.com/javase/tutorial/java/javaOO/lambdaexpressions.html
* Python Functional Programming: https://docs.python.org/3/howto/functional.html
