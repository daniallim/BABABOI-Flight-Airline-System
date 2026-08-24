# ✈️ BABABOI Airline Flight System

<p align="center">
  <strong>Graph-Based Airline Network & Route Analysis System</strong>
</p>

<p align="center">
  A Java application for managing and analysing domestic airline routes using Graph Data Structures and Algorithms.
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Java-JDK%2026-orange?logo=openjdk" alt="Java">
  <img src="https://img.shields.io/badge/NetBeans-31%2B-blue?logo=apache">
  <img src="https://img.shields.io/badge/Graph-Weighted%20Directed%20Graph-green">
  <img src="https://img.shields.io/badge/Algorithms-DFS%20%7C%20BFS%20%7C%20Dijkstra-purple">
</p>

---

## 📖 Overview

**BABABOI Airline Flight System** is a Java-based application designed to model and analyse domestic flight connections across **Peninsular Malaysia, Sabah, and Sarawak**.

The airline network is represented as a **Weighted Directed Graph**, where each airport is a **vertex** and each direct flight route is a **directed edge**. Flight routes are associated with information such as **flight number, distance, and duration**.

The system demonstrates the practical application of **Graph Theory, Data Structures, Graph Traversal, Shortest Path Algorithms, and Object-Oriented Programming** in a real-world airline network.

---

## ✨ Features

| Module                      | Description                                                           |
| --------------------------- | --------------------------------------------------------------------- |
| 🔐 **Login**                | Provides secure access to the airline system.                         |
| 🗺️ **Flight Network**      | Displays the airline network and airport connections.                 |
| 🏢 **Manage Vertex**        | Add, update, delete, and manage airport vertices.                     |
| ✈️ **Manage Flight**        | Manage flight routes and their associated information.                |
| 🔎 **DFS Search**           | Performs Depth-First Search to explore connected routes.              |
| 🔎 **BFS Search**           | Performs Breadth-First Search to explore routes level by level.       |
| 📍 **Dijkstra's Algorithm** | Determines the shortest path between airports based on route weights. |

---

## 🧠 Algorithms & Data Structures

### Weighted Directed Graph

The airline network is represented using a **Weighted Directed Graph**.

```text
Airport A ────────> Airport B
           Flight
        Distance / Duration
```

* **Vertex** → Airport
* **Directed Edge** → Flight Route
* **Weight** → Flight distance / duration
* **Graph** → Complete airline network

### DFS — Depth-First Search

Used to explore airport connections by travelling as deeply as possible along each route before backtracking.

### BFS — Breadth-First Search

Used to explore connected airports level by level from a selected starting airport.

### Dijkstra's Shortest Path

Used to determine the **shortest route between airports** based on the assigned edge weights.

---

## 🖥️ System Modules

### 🔐 Login Page

Provides the entry point to the system and authenticates users before accessing the main application.

### 🗺️ View Airline Flight Network

Provides an overview of the airport and flight network, allowing users to view the connections within the graph.

### 🏢 Manage Vertex (Airport)

Allows users to manage airport vertices within the graph, including airport information and network connections.

### ✈️ Manage Flight

Allows users to manage flight routes between airports, including flight-related information such as flight number, distance, and duration.

### 🔎 DFS Search Route

Allows users to perform a **Depth-First Search** to explore routes from a selected airport.

### 🔎 BFS Search Route

Allows users to perform a **Breadth-First Search** to explore reachable airports systematically.

### 📍 Dijkstra's Shortest Path

Allows users to calculate the shortest route between two airports using **Dijkstra's Algorithm**.

---

## 📁 Project Structure

```text
JAVA
└── Source Packages
    └── com.bababoi.airline.java
        ├── Login.java
        ├── Main.java
        └── Menu.java
```

### Core Classes

| Class        | Responsibility                                                |
| ------------ | ------------------------------------------------------------- |
| `Login.java` | Handles system login and user authentication.                 |
| `Main.java`  | Entry point of the application.                               |
| `Menu.java`  | Provides the main system menu and navigation between modules. |

---

## 🛠️ Technology Stack

* **Programming Language:** Java
* **JDK:** 26
* **IDE:** Apache NetBeans IDE 31+
* **Programming Paradigm:** Object-Oriented Programming
* **Data Structure:** Weighted Directed Graph
* **Algorithms:** DFS, BFS, Dijkstra's Shortest Path

---

## 🚀 Getting Started

### Prerequisites

Make sure the following are installed:

* **JDK 26**
* **Apache NetBeans IDE 31 or later**

Check your Java version:

```bash
java -version
```

### Installation

**1. Clone the repository**

```bash
git clone <repository-url>
```

Or download the project as a ZIP file.

**2. Open the project**

Open **Apache NetBeans IDE** and select:

```text
File → Open Project
```

Select the extracted project folder.

**3. Configure JDK**

Ensure the project is configured to use **JDK 26**.

**4. Run the application**

Open:

```text
Main.java
```

and run the application.

---

## ⚠️ Important Notes

> **Do not run the project directly from the ZIP file.**

* Extract the project before opening it in NetBeans.
* Maintain the original project structure.
* Ensure all source files are present.
* Use **JDK 26** for compatibility.
* **Apache NetBeans IDE 31 or later** is recommended.

---

## 🎓 Project Purpose

This project demonstrates how **graph-based data structures and algorithms** can be applied to a practical airline transportation network.

Through the implementation of **DFS, BFS, and Dijkstra's Algorithm**, the system provides different approaches to exploring airport connectivity and analysing flight routes.

---

## 👥 Project Information

**Project:** BABABOI Airline Flight System
**Language:** Java
**Graph Model:** Weighted Directed Graph
**Algorithms:** DFS · BFS · Dijkstra
**Region:** Peninsular Malaysia · Sabah · Sarawak

---

<p align="center">
  <strong>✈️ Explore Routes. Analyse Connections. Connect Malaysia.</strong>
</p>
