<div align="center">

<img src="https://img.icons8.com/color/96/airplane-mode-on.png" width="100" alt="Airplane Logo"/>

# ✈️ BABABOI Airline Flight System

### A Graph-Based Airline Route Management & Analysis System

[![Java](https://img.shields.io/badge/Java-JDK%2026-orange?logo=openjdk&logoColor=white)](https://adoptium.net/)
[![NetBeans](https://img.shields.io/badge/Apache%20NetBeans-31%2B-blue?logo=apache&logoColor=white)](https://netbeans.apache.org/)
[![Graph](https://img.shields.io/badge/Graph-Weighted%20Directed-success)](https://en.wikipedia.org/wiki/Directed_graph)
[![Algorithms](https://img.shields.io/badge/Algorithms-DFS%20%7C%20BFS%20%7C%20Dijkstra-purple)](https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)
[![PRs Welcome](https://img.shields.io/badge/PRs-welcome-brightgreen.svg)](http://makeapullrequest.com)

</div>

---

## 📋 Table of Contents
- [Overview](#-overview)
- [Features](#-key-features)
- [Algorithms](#-algorithms--data-structures)
- [System Modules](#-system-modules)
- [Project Structure](#-project-structure)
- [Technology Stack](#-technology-stack)
- [Getting Started](#-getting-started)
- [Screenshots](#-screenshots)
- [Future Improvements](#-future-improvements)
- [License](#-license)
- [Contributing](#-contributing)

---

## 📖 Overview

**BABABOI Airline Flight System** is a sophisticated Java application demonstrating the practical implementation of **Graph Data Structures and Algorithms** through an airline route management system.

### 🎯 Core Concepts Applied
- **Weighted Directed Graphs** - Modeling airline networks
- **Depth-First Search (DFS)** - Route exploration
- **Breadth-First Search (BFS)** - Level-by-level connectivity analysis
- **Dijkstra's Algorithm** - Shortest path optimization
- **Object-Oriented Programming (OOP)** - Clean, maintainable code
- **Java Swing** - Interactive GUI development

### 🌏 Network Coverage
The system models domestic flight connections across:
- 🇲🇾 **Peninsular Malaysia**
- 🇲🇾 **Sabah**
- 🇲🇾 **Sarawak**

---

## ✨ Key Features

<table>
<tr>
<td width="33%">

### 🔐 **User Login**
- Secure authentication system
- Controlled access management
- Entry point protection

</td>
<td width="33%">

### 🗺️ **Flight Network**
- Visual network representation
- Airport connections overview
- Route relationship display

</td>
<td width="33%">

### 🏢 **Airport Management**
- Add/Update/Delete airports
- Vertex management
- Airport information storage

</td>
</tr>
<tr>
<td>

### ✈️ **Flight Management**
- Create flight routes
- Manage flight details
- Route optimization

</td>
<td>

### 🔎 **DFS Route Search**
- Depth-first exploration
- Connected airport discovery
- Route pathfinding

</td>
<td>

### 🔎 **BFS Route Search**
- Level-by-level exploration
- Direct connections identification
- Systematic connectivity analysis

</td>
</tr>
<tr>
<td colspan="3" align="center">

### 📍 **Shortest Path Analysis**
- Dijkstra's Algorithm implementation
- Distance optimization
- Duration calculation

</td>
</tr>
</table>

---

## 🧠 Algorithms & Data Structures

### 📊 Weighted Directed Graph

The airline network uses a **Weighted Directed Graph** structure:

```mermaid
graph LR
    A[Airport A] -->|Flight Route| B[Airport B]
    B -->|Distance/Duration| C[Airport C]
    A --> D[Airport D]
    D -->|Weighted Edge| B
