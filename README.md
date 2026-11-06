# Trabajo de Consulta: Funciones Anónimas

**Materia:** Programación Funcional  
**Lenguajes:** Scala, Java y Python  
**Autor:** CesarM3-A  
**Fecha:** Noviembre de 2026

---

## Contenido

1. [Parte 1. Explorar: la programación funcional en el mundo real](#parte-1-explorar-la-programación-funcional-en-el-mundo-real)
2. [Parte 2. Entender: qué es una función anónima](#parte-2-entender-qué-es-una-función-anónima)
3. [Parte 3. Aplicar: tipos de problemas resueltos](#parte-3-aplicar-tipos-de-problemas-resueltos)
4. [Parte 4. Ampliar: cuatro operaciones nuevas](#parte-4-ampliar-cuatro-operaciones-nuevas)
5. [Parte 5. Comparar y concluir](#parte-5-comparar-y-concluir)
6. [Referencias](#referencias)

---

## Parte 1. Explorar: la programación funcional en el mundo real

### Caso 1: Apache Spark (procesamiento de Big Data)

| Aspecto | Descripción |
| :--- | :--- |
| Problema que resuelve | Procesar terabytes de datos distribuidos en clústeres de forma rápida, escalable y tolerante a fallos. |
| Lenguaje o herramienta | Scala (Apache Spark) |
| Ventaja del enfoque funcional | Spark trabaja con RDDs (*Resilient Distributed Datasets*). Las funciones anónimas (`map`, `filter`, `reduce`) transforman los datos sin modificar los originales, lo que permite repartir el procesamiento entre varios nodos y recalcular solo la parte perdida si alguno falla. |
| Fuente | [Documentación oficial de Apache Spark](https://spark.apache.org/docs/latest/rdd-programming-guide.html) |

Fragmento de código:

```scala
// Contar palabras en un archivo de texto distribuido
val conteo = sc.textFile("hdfs://datos/archivo.txt")
  .flatMap(linea => linea.split(" ")) // Lambda que separa cada línea en palabras
  .map(palabra => (palabra, 1))       // Lambda que asocia cada palabra con un 1
  .reduceByKey((a, b) => a + b)       // Lambda que suma los conteos por palabra
```

### Caso 2: Java Streams en sistemas empresariales (banca y fintech)

| Aspecto | Descripción |
| :--- | :--- |
| Problema que resuelve | Filtrar, transformar y agregar grandes volúmenes de transacciones o eventos de usuarios, por ejemplo en la detección de fraude. |
| Lenguaje o herramienta | Java (Streams API, desde Java 8) |
| Ventaja del enfoque funcional | Permite escribir código declarativo y fácil de paralelizar. En lugar de bucles `for` con estado mutable, se encadenan operaciones que pueden ejecutarse en varios hilos mediante `parallelStream()`. |
| Fuente | [Oracle Java Tutorials: Lambda Expressions](https://docs.oracle.com/javase/tutorial/java/javaOO/lambdaexpressions.html) |

---

## Parte 2. Entender: qué es una función anónima

### Definición y origen del nombre

Una función anónima es un bloque de código que cumple una tarea específica pero que no se asocia a un identificador al momento de declararse. El nombre "lambda" proviene del cálculo lambda, formulado por Alonzo Church en la década de 1930, un sistema formal para definir y aplicar funciones que sirve como base teórica de la programación funcional.

### Diferencia con una función nombrada

```scala
// Función nombrada: tiene un identificador explícito
def calcularIVA(precio: Double): Double = precio * 0.12

// Función anónima: no tiene nombre, se define y se usa en el mismo lugar
(precio: Double) => precio * 0.12
```

### Funciones como ciudadanos de primera clase

Que las funciones sean ciudadanos de primera clase significa que se tratan como cualquier otro valor: pueden asignarse a variables, pasarse como argumentos a otras funciones o devolverse como resultado. Por esta razón es posible pasar una lambda directamente a métodos como `map` o `filter`, ya que están diseñados para recibir una función como parámetro.

### Closure

Un closure es una función anónima que conserva y utiliza variables definidas fuera de su propio ámbito (*scope*) en el momento en que fue creada.

```scala
val tasaImpuesto = 0.15 // Variable externa

// Closure: la lambda captura la variable tasaImpuesto
val aplicarImpuesto = (precio: Double) => precio * tasaImpuesto
```

---

## Parte 3. Aplicar: tipos de problemas resueltos

Para mantener la consistencia, todos los ejemplos usan la misma lista de objetos `Producto(nombre: String, categoria: String, precio: Double, stock: Int)`, con 12 elementos.

### 1. Selección (`filter`)

**Situación:** una tienda necesita identificar los productos con precio mayor a $100 para ofrecer garantía extendida.

| Lenguaje | Solución |
| :--- | :--- |
| Scala | `productos.filter(p => p.precio > 100)` |
| Java | `productos.stream().filter(p -> p.getPrecio() > 100).collect(Collectors.toList())` |
| Python | `list(filter(lambda p: p.precio > 100, productos))` |

**Salida esperada (en los tres lenguajes):** lista con `[Laptop, Monitor, Silla, Escritorio, Audífonos]`.

### 2. Transformación (`map`)

**Situación:** durante el Black Friday se aplica un descuento del 10 % a todos los productos para generar las nuevas etiquetas de precio.

| Lenguaje | Solución |
| :--- | :--- |
| Scala | `productos.map(p => p.precio * 0.9)` |
| Java | `productos.stream().map(p -> p.getPrecio() * 0.9).collect(Collectors.toList())` |
| Python | `list(map(lambda p: p.precio * 0.9, productos))` |

**Salida esperada (en los tres lenguajes):** una lista de precios cuyo primer elemento (Laptop) es `765.0`.

### 3. Validación (`forall`)

**Situación:** el sistema de pago debe verificar que todos los productos del carrito tengan stock disponible (`stock > 0`) antes de procesar la compra.

| Lenguaje | Solución |
| :--- | :--- |
| Scala | `productos.forall(p => p.stock > 0)` |
| Java | `productos.stream().allMatch(p -> p.getStock() > 0)` |
| Python | `all(map(lambda p: p.stock > 0, productos))` |

**Salida esperada:** `true` (en Python se muestra como `True`).

### 4. Acumulación (`foldLeft` / `reduce`)

**Situación:** calcular el valor total del inventario de la bodega, sumando `precio * stock` de cada producto.

| Lenguaje | Solución |
| :--- | :--- |
| Scala | `productos.foldLeft(0.0)((acc, p) => acc + (p.precio * p.stock))` |
| Java | `productos.stream().mapToDouble(p -> p.getPrecio() * p.getStock()).sum()` |
| Python | `reduce(lambda acc, p: acc + (p.precio * p.stock), productos, 0.0)` (requiere `from functools import reduce`) |

**Salida esperada (en los tres lenguajes):** `101075.0`

---

## Parte 4. Ampliar: cuatro operaciones nuevas

El código completo y ejecutable de esta sección se encuentra en [`operaciones/OperacionesNuevas.scala`](operaciones/OperacionesNuevas.scala).

### Ficha 1: `groupBy`

| Aspecto | Descripción |
| :--- | :--- |
| Qué hace | Agrupa los elementos de una colección en un `Map`, donde la clave es el resultado de aplicar una función a cada elemento. |
| Lambda que recibe | `A => K` (función que devuelve la clave de agrupación) |
| Problema que resuelve | Agrupar productos por categoría para generar reportes de inventario por departamento (tipo: agrupación). |

```scala
productos.groupBy(_.categoria).map { case (c, p) => (c, p.size) }
```

### Ficha 2: `sortBy`

| Aspecto | Descripción |
| :--- | :--- |
| Qué hace | Ordena los elementos de la colección según el valor que devuelve una función. |
| Lambda que recibe | `A => B` (donde `B` es un tipo ordenable, como `Int` o `Double`) |
| Problema que resuelve | Mostrar los productos más caros en la parte superior de una tienda en línea (tipo: ordenamiento). |

```scala
productos.sortBy(-_.precio).take(3).map(_.nombre)
```

### Ficha 3: `find`

| Aspecto | Descripción |
| :--- | :--- |
| Qué hace | Devuelve el primer elemento que cumple una condición, envuelto en un `Option` (`Some` o `None`). |
| Lambda que recibe | `A => Boolean` (un predicado) |
| Problema que resuelve | Verificar si existe un producto con un nombre exacto para mostrar su ficha de detalle (tipo: búsqueda). |

```scala
productos.find(_.nombre == "Monitor").map(_.precio)
```

### Ficha 4: `partition`

| Aspecto | Descripción |
| :--- | :--- |
| Qué hace | Divide la colección en una tupla de dos listas: la primera con los elementos que cumplen la condición y la segunda con los que no. |
| Lambda que recibe | `A => Boolean` (un predicado) |
| Problema que resuelve | Separar el inventario en productos premium y económicos para aplicar estrategias de marketing distintas (tipo: división o selección dual). |

```scala
productos.partition(_.precio >= 100)
```

### Combinación de operaciones

**Pregunta:** ¿qué categorías tienen un precio promedio mayor a $100 y al menos 30 unidades en stock total, ordenadas por stock total de mayor a menor?

```scala
productos
  .groupBy(_.categoria)                                    // 1. Agrupa por categoría (nueva: groupBy)
  .map { case (cat, prods) =>
    (cat, prods.map(_.precio).sum / prods.size, prods.map(_.stock).sum)
  }                                                        // 2. Calcula promedio y stock total (vista en clase: map)
  .filter { case (_, avg, stock) => avg > 100 && stock >= 30 } // 3. Filtra las que cumplen ambas condiciones (vista en clase: filter)
  .toList
  .sortBy(-_._3)                                           // 4. Ordena por stock descendente (nueva: sortBy)
```

---

## Parte 5. Comparar y concluir

### 5.1 Comparación entre Scala, Java y Python

| Criterio | Scala | Java | Python |
| :--- | :--- | :--- | :--- |
| Sintaxis de la lambda | `(x) => expresion` | `(x) -> expresion` | `lambda x: expresion` |
| Declaración del tipo | Inferencia o tipo explícito `(x: Int)` | Inferencia por contexto o tipo explícito `(int x)` | No se declara (tipado dinámico) |
| Operaciones con listas | Nativas en `List` y `Seq`, con una API muy completa | A través de la API `Stream` y luego `collect` | Nativas (`map`, `filter`) o mediante `functools` |
| Variables capturadas | Pueden ser `val` o `var`, aunque lo recomendable es usar `val` | Deben ser `final` o efectivamente finales | Sin restricciones fuertes, pero hay que cuidar el *scope* |
| Legibilidad (opinión personal) | Alta: el encadenamiento de operaciones se lee con facilidad | Media: es más verbosa y requiere código adicional | Media-alta: es limpia, pero `lambda` es limitada |

### Reflexión personal

El lenguaje en el que resultó más natural resolver los problemas fue Scala. Al haber sido diseñado desde el inicio con soporte para programación funcional, encadenar un `filter` con un `map` resulta fluido y el código queda corto y claro.

Java se sintió más pesado. No es que no permita hacerlo, sino que exige más código auxiliar: crear el `Stream`, aplicar la operación y recordar llamar a `collect()` para volver a obtener una lista. Esto da la impresión de que la programación funcional se incorporó al lenguaje de forma posterior.

Python es muy legible, pero la restricción de que `lambda` admita una sola expresión resulta incómoda. Cuando la lógica se complica, es necesario definir una función con `def`, lo que le resta practicidad.

Las mayores diferencias se notaron en el problema de acumulación (`foldLeft` / `reduce`). En Scala y Python acumular valores es directo, mientras que en Java fue necesario pasar por `mapToDouble` antes de usar `sum()`, ya que los Streams de objetos no permiten sumar directamente y hay que convertirlos a un Stream de primitivos para evitar el *boxing* y *unboxing*.

### 5.2 Conclusión

Las funciones anónimas son una herramienta central en la manipulación moderna de datos. Permiten pasar de un enfoque imperativo, en el que se indica paso a paso cómo recorrer una lista, a uno declarativo, en el que se expresa qué resultado se desea obtener. Esto reduce los errores asociados a la mutación de estado y facilita la paralelización del código.

Como se vio en el Caso 1 (Apache Spark), esta característica es fundamental: al ser transformaciones sin efectos secundarios sobre datos inmutables, Spark puede repartirlas entre muchos nodos y, si alguno falla, volver a calcular solo la parte perdida sin alterar el resultado final.

---

## Referencias

- Documentación oficial de Scala: <https://docs.scala-lang.org>
- Apache Spark. RDD Programming Guide: <https://spark.apache.org/docs/latest/rdd-programming-guide.html>
- Oracle. Lambda Expressions (Java Tutorials): <https://docs.oracle.com/javase/tutorial/java/javaOO/lambdaexpressions.html>
- Python Software Foundation. Functional Programming HOWTO: <https://docs.python.org/3/howto/functional.html>
