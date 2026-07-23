# Sistema de Resolución Automática de Laberintos

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

## 👨‍💻 Autor
* **Gabriel Cañas** — *Ingeniería en Informática, UNEG*
