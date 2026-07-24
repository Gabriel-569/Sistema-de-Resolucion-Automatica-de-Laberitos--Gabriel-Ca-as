# Sistema de Resolución Automática de Laberintos

## 🕹️ Demostración del Sistema

![Vista previa de la interfaz](./screenshots/demostracion.png))

## 📌 Descripción del Proyecto
Este proyecto consiste en la simulación y resolución dinámica de laberintos mediante el **Análisis y Diseño Orientado a Objetos (ADOC)**, siguiendo el enfoque iterativo y evolutivo propuesto por **Craig Larman**. 

El sistema abstrae el laberinto como una red estructurada mediante el modelado de **grafos**, aplicando algoritmos de búsqueda y recorrido para encontrar rutas óptimas de salida de manera automatizada.

Desarrollado como proyecto académico para la asignatura **Técnicas de Programación 3** en la carrera de **Ingeniería en Informática** de la **Universidad Nacional Experimental de Guayana (UNEG)**.

---

## 📐 Enfoque de Diseño y Metodología (Craig Larman)
El desarrollo del software fue estructurado siguiendo las fases clave del desarrollo orientado a objetos:

* **Modelo del Dominio (Análisis):** Identificación de conceptos clave del problema real (Celdas, Pasillos, Obstáculos, Entradas/Salidas, Rutas) y sus relaciones conceptuales.
* **Casos de Uso:** Definición del comportamiento del sistema desde la perspectiva de la simulación (ej. *Generar Laberinto*, *Resolver Laberinto*, *Visualizar Recorrido*).
* **Diseño Orientado a Objetos:** Aplicación de patrones de asignación de responsabilidades (**GRASP**) para lograr un diseño con bajo acoplamiento y alta cohesión:
  * **Experto en Información:** Asignación de algoritmos de navegación a las clases que contienen la estructura del grafo.
  * **Controlador:** Manejo del flujo de eventos entre la interfaz gráfica y la lógica del laberinto.
* **Modelado UML:** Diagramas de clases de diseño, interacción y estados para respaldar la arquitectura del código en Java.

---

## 🚀 Características Principales
* **Representación mediante Grafos:** El laberinto es modelado como un conjunto de vértices (posiciones/celdas) y aristas (conexiones/caminos transitables).
* **Búsqueda Dinámica:** Aplicación de algoritmos de recorrido en grafos para determinar el camino más eficiente hasta la salida.
* **Interfaz de Simulación:** Visualización en tiempo real de la exploración y resolución del algoritmo.

---

## 🛠️ Tecnologías Utilizadas
* **Lenguaje:** Java
* **Metodología:** Análisis y Diseño Orientado a Objetos (Craig Larman) / Modelado UML
* **Conceptos:** Teoría de Grafos, Patrones GRASP, Estructuras de Datos Avanzadas.
* **IDE recomendado:** Eclipse / NetBeans / IntelliJ IDEA

---

## ⚙️ Instalación y Configuración

### 📋 Requisitos Previos
* **[Java SE Development Kit (JDK)](https://www.oracle.com/java/technologies/downloads/):** Versión 17 o superior.
* **[Apache NetBeans IDE](https://netbeans.apache.org/front/main/download/):** Versión 20 (o superior recomendada).

---

### 🚀 Pasos para Descargar e Instalar
1. **Clonar o descargar el repositorio:**
   * Haz clic en el botón verde `<> Code` en la parte superior derecha de esta página y selecciona **Download ZIP**.
   * Descomprime el archivo `.zip` en tu computadora.
2. **Abrir en NetBeans:**
   * Inicia **Apache NetBeans**.
   * Ve al menú superior y selecciona `File` ➔ `Open Project...` (o presiona `Ctrl + Shift + O`).
   * Busca la carpeta descompresora y selecciona la carpeta interna llamada **`Nodo`**.
3. **Depurar y verificar el proyecto:**
   * Para asegurarte de que todo compile correctamente antes de ejecutarlo, presiona **`Shift + F12`** (Mayús + F12) dentro de NetBeans para limpiar, reconstruir y depurar el proyecto.

---

## 🕹️ Instrucciones de Uso

Guía de Uso del Sistema

Sigue estos pasos para diseñar tu laberinto, elegir la estrategia de búsqueda y controlar la simulación:

### 1. Configurar el Tablero (Modo Edición)
Usa el panel derecho **Modo Edición** para estructurar el laberinto antes de iniciar la simulación:
* **Dibujar Paredes:** 
  * Selecciona la opción **Dibujar Paredes**.
  * Haz clic en las casillas blancas para transformarlas en Paredes / Obstáculos *(se pintarán de negro)*.
  * Vuelve a hacer clic sobre una pared si deseas quitarla y habilitar la casilla.
* **Establecer Entrada y Salida:** 
  * Selecciona la opción **Establecer E/S**.
  * Haz clic sobre una casilla vacía para fijar el **Punto de Entrada** *(se pintará de azul)*.
  * Haz clic sobre otra casilla vacía para fijar el **Punto de Salida** *(se pintará de rojo)*.

---

### 2. Seleccionar el Algoritmo
En el menú desplegable de la esquina superior izquierda (**Algoritmo**), elige la estrategia de búsqueda a evaluar:
* **BFS (Anchura):** Garantiza encontrar la ruta óptima (la más corta) evaluando los nodos por niveles concéntricos.
* **DFS (Profundidad):** Explora cada rama de la red hasta el fondo antes de realizar *backtracking*, encontrando una ruta válida pero no necesariamente la más corta.

---

### 3. Controlar la Simulación
Usa los botones del panel inferior para gestionar la ejecución:
* **Iniciar:** Arranca la animación en tiempo real. La ruta explorada/óptima se irá trazando celda por celda en color **verde**.
* **Pausar:** Congela el progreso de la simulación en el instante exacto. Haz clic en *Iniciar* para reanudar.
* **Reset:** Limpia el camino verde trazado y restablece la simulación para realizar una nueva prueba o cambiar de algoritmo.
* **Salir:** Cierra la aplicación.
---

## 👨‍💻 Autor
* **Gabriel Cañas** — *Técnicas de Programación 3, Profesora Dubraska Roca, Ingeniería en Informática, UNEG*
