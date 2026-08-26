<div align="center">

<img src="https://img.icons8.com/color/96/airplane-mode-on.png" width="90" alt="Airplane Logo"/>

# BABABOI Airline Flight System

### ✈️ Graph-Based Airline Network & Route Analysis System ✈️

A Java application for managing, exploring, and analysing domestic airline routes<br>
using <strong>Graph Data Structures, Graph Traversal, and Shortest Path Algorithms</strong>.

<br>

<img src="https://img.shields.io/badge/Java-JDK%2026-orange?logo=openjdk&logoColor=white" alt="Java">
<img src="https://img.shields.io/badge/Apache%20NetBeans-31%2B-blue?logo=apache&logoColor=white" alt="NetBeans">
<img src="https://img.shields.io/badge/Graph-Weighted%20Directed%20Graph-success" alt="Graph">
<img src="https://img.shields.io/badge/Algorithms-DFS%20%7C%20BFS%20%7C%20Dijkstra-purple" alt="Algorithms">
<img src="https://img.shields.io/badge/License-MIT-yellow.svg" alt="MIT License">

</div>

---

## 📖 Overview

**BABABOI Airline Flight System** is a Java-based application developed to demonstrate the practical implementation of **Graph Data Structures and Algorithms** through an airline route management system.

The system models domestic flight connections across **Peninsular Malaysia, Sabah, and Sarawak** using a **Weighted Directed Graph**. Each airport is represented as a **vertex**, while each flight route is represented as a **directed edge** containing information such as flight number, distance, and duration.

The project applies important computer science concepts, including:

* Graph Data Structures
* Graph Traversal
* Depth-First Search (DFS)
* Breadth-First Search (BFS)
* Dijkstra's Shortest Path Algorithm
* Object-Oriented Programming (OOP)
* Java Swing GUI Development

---

## ✨ Key Features

| Module                  | Description                                                             |
| ----------------------- | ----------------------------------------------------------------------- |
| 🔐 **User Login**       | Provides controlled access to the airline management system.            |
| 🗺️ **Flight Network**  | Displays the airline network and airport connections.                   |
| 🏢 **Manage Airports**  | Allows users to add, update, delete, and manage airport vertices.       |
| ✈️ **Manage Flights**   | Allows users to create and manage flight routes.                        |
| 🔎 **DFS Route Search** | Explores connected airports using Depth-First Search.                   |
| 🔎 **BFS Route Search** | Explores airport connections level by level using Breadth-First Search. |
| 📍 **Shortest Path**    | Finds the shortest route between airports using Dijkstra's Algorithm.   |

---

# 🧠 Algorithms & Data Structures

## Weighted Directed Graph

The airline network is represented using a **Weighted Directed Graph**.

```text
Airport A ───────────────► Airport B
           Flight Route
        Distance / Duration
```

### Graph Components

| Component         | Representation           |
| ----------------- | ------------------------ |
| **Vertex**        | Airport                  |
| **Directed Edge** | Flight Route             |
| **Weight**        | Distance or Duration     |
| **Graph**         | Complete Airline Network |

A directed graph is suitable for representing airline routes because flight connections have a specific departure and destination direction.

---

## 🔵 Depth-First Search (DFS)

DFS explores the airline network by travelling as deeply as possible along a route before backtracking.

It can be used to:

* Explore connected airports
* Identify reachable routes
* Traverse the airline network

---

## 🟢 Breadth-First Search (BFS)

BFS explores the airline network level by level, starting from a selected airport.

It can be used to:

* Identify directly connected airports
* Explore routes systematically
* Analyse airport connectivity

---

## 🟣 Dijkstra's Shortest Path Algorithm

Dijkstra's Algorithm is used to determine the shortest route between two airports based on the assigned edge weights.

```text
Start Airport
      │
      ▼
Calculate Route Costs
      │
      ▼
Compare Available Paths
      │
      ▼
Find Shortest Route
      │
      ▼
Destination Airport
```

---

# 🖥️ System Modules

### 🔐 User Login

Provides controlled access to the airline management system and serves as the entry point before accessing the main application.

### 🗺️ Flight Network

Displays the airline network and airport connections, providing users with an overview of the relationships between airports and flight routes.

### 🏢 Manage Airports

Allows users to add, update, delete, and manage airport vertices within the airline network.

### ✈️ Manage Flights

Allows users to create and manage flight routes between airports, including information such as:

* Flight Number
* Departure Airport
* Destination Airport
* Distance
* Duration

### 🔎 DFS Route Search

Allows users to explore connected airports using **Depth-First Search (DFS)**.

### 🔎 BFS Route Search

Allows users to explore airport connections level by level using **Breadth-First Search (BFS)**.

### 📍 Shortest Path

Allows users to find the shortest route between airports using **Dijkstra's Shortest Path Algorithm**.

---

# 📁 Project Structure

The main functional structure of the **BABABOI Airline Flight System** is organised as follows:

```text
BABABOI Airline Flight System
│
├── 🔐 User Login
│
├── 🗺️ Flight Network
│
├── 🏢 Manage Airports
│
├── ✈️ Manage Flights
│
├── 🔎 DFS Route Search
│
├── 🔎 BFS Route Search
│
└── 📍 Shortest Path
```

The Java source files are organised within the NetBeans source package:

```text
BABABOI-Flight-Airline-System
│
├── Source Packages
│   │
│   └── com.bababoi.airline.java
│       │
│       ├── Login.java
│       ├── Menu.java
│       ├── AirlineFlightNetwork.java
│       ├── VertexManagement.java
│       ├── EdgeManagement.java
│       ├── DFS_Search.java
│       ├── BFS_Search.java
│       ├── ShortestPath.java
│       └── Main.java
|       └── FlightNetworkData.java

│
├── nbproject
│
├── build.xml
│
├── manifest.mf
│
├── LICENSE
│
└── README.md
```

> **Note:** The class names above should match the actual Java filenames in the project. If your Java classes use different names, replace them accordingly.

---

# 🧩 Core Classes

| Class / Module        | Responsibility                                                                |
| --------------------- | ----------------------------------------------------------------------------- |
| `Login.java`          | Handles user authentication and provides access to the system.                |
| `Menu.java`           | Provides the main navigation interface for the system.                        |
| `FlightNetwork.java`  | Displays and manages the overall airline network.                             |
| `ManageAirports.java` | Handles the creation, updating, deletion, and management of airport vertices. |
| `ManageFlights.java`  | Handles the creation and management of flight routes.                         |
| `DFS.java`            | Implements Depth-First Search for route exploration.                          |
| `BFS.java`            | Implements Breadth-First Search for route exploration.                        |
| `ShortestPath.java`   | Implements Dijkstra's Algorithm to determine the shortest route.              |
| `Main.java`           | Serves as the main entry point of the application.                            |

---

# 🛠️ Technology Stack

| Technology                  | Usage                          |
| --------------------------- | ------------------------------ |
| **Java**                    | Core programming language      |
| **JDK 26**                  | Java Development Kit           |
| **Apache NetBeans IDE 31+** | Development environment        |
| **Java Swing**              | Graphical User Interface       |
| **Weighted Directed Graph** | Airline network representation |
| **DFS**                     | Graph traversal                |
| **BFS**                     | Graph traversal                |
| **Dijkstra's Algorithm**    | Shortest path calculation      |

---

# 🚀 Getting Started

## Prerequisites

Before running the project, make sure the following software is installed:

* **Java Development Kit (JDK) 26**
* **Apache NetBeans IDE 31 or later**

Check your installed Java version:

```bash
java -version
```

---

## 📥 Installation

### 1. Download the Project

Clone the repository:

```bash
git clone https://github.com/YOUR-USERNAME/BABABOI-Flight-Airline-System.git
```

Alternatively, download the repository as a **ZIP file** from GitHub.

### 2. Extract the ZIP File

If you downloaded the project as a ZIP file:

1. Locate the downloaded ZIP file.
2. Right-click the ZIP file.
3. Select **Extract All**.
4. Choose a suitable location.
5. Wait for the extraction to complete.

> ⚠️ **Do not open or run the project directly from the ZIP file.**

### 3. Open the Project in NetBeans

Open **Apache NetBeans IDE 31 or later**.

Go to:

```text
File → Open Project
```

Select the extracted **BABABOI-Flight-Airline-System** project folder.

### 4. Configure JDK 26

Make sure the project is configured to use **JDK 26** in NetBeans.

### 5. Run the Application

Locate the project's main entry point:

```text
Main.java
```

Run the application from Apache NetBeans.

The system should start from the main entry point and provide access to the airline management modules.

---

# ⚠️ Important Notes

* Extract the project before opening it in NetBeans.
* Do not run the project directly from the ZIP file.
* Maintain the original project folder structure.
* Ensure all Java source files are included.
* Use **JDK 26** for compatibility.
* **Apache NetBeans IDE 31 or later** is recommended.
* Make sure the correct main class is configured before running the application.

---

# 🎓 Project Purpose

This project demonstrates how fundamental **Data Structures and Algorithms** can be applied to a practical airline transportation network.

The main objectives are to:

* Apply Graph Theory to a real-world airline network.
* Represent airports and flight routes using a graph structure.
* Implement DFS for graph traversal.
* Implement BFS for graph traversal.
* Implement Dijkstra's Algorithm for shortest path analysis.
* Apply Object-Oriented Programming principles.
* Develop an interactive Java-based graphical user interface.

---

# 🌏 Airline Network Coverage

The system focuses on domestic airport connections across Malaysia, including:

* 🇲🇾 **Peninsular Malaysia**
* 🇲🇾 **Sabah**
* 🇲🇾 **Sarawak**

The airline network can be expanded by adding additional airports and flight routes.

---

# 🔮 Future Improvements

* [ ] Interactive visual graph representation
* [ ] Database integration
* [ ] Flight scheduling functionality
* [ ] Flight booking functionality
* [ ] Advanced route filtering
* [ ] Alternative route recommendations
* [ ] Additional graph algorithms
* [ ] Analytics and reporting dashboard
* [ ] Real-time flight information

---

# 📄 License

This project is licensed under the **MIT License**.

You are free to use, modify, and distribute this project in accordance with the terms of the MIT License.

See the `LICENSE` file for more information.

---

# 👥 Project Information

|                          |                                       |
| ------------------------ | ------------------------------------- |
| **Project Name**         | BABABOI Airline Flight System         |
| **Programming Language** | Java                                  |
| **Java Version**         | JDK 26                                |
| **IDE**                  | Apache NetBeans IDE 31+               |
| **Data Structure**       | Weighted Directed Graph               |
| **Algorithms**           | DFS · BFS · Dijkstra's Algorithm      |
| **License**              | MIT License                           |
| **Region**               | Peninsular Malaysia · Sabah · Sarawak |

---

<p align="center">

### ✈️ Explore Routes · Analyse Connections · Connect Malaysia

**Built with Java, Graph Data Structures, and Algorithms.**

<br>

⭐ If you find this project useful, consider giving the repository a star!

</p>
