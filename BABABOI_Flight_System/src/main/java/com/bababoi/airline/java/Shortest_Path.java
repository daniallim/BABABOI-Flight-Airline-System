/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bababoi.airline.java;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Shortest_Path extends JFrame {

    // Color scheme 
    private final Color NAVY = new Color(20, 35, 60);
    private final Color DARK_NAVY = new Color(15, 27, 48);
    private final Color BLUE = new Color(0, 122, 255);
    private final Color BACKGROUND = new Color(243, 246, 250);
    private final Color CARD = new Color(255, 255, 255);
    private final Color TEXT = new Color(30, 35, 45);
    private final Color SECONDARY_TEXT = new Color(105, 115, 130);
    private final Color BORDER = new Color(220, 225, 232);
    private final Color ERROR = new Color(210, 65, 65);
    private final Color SUCCESS = new Color(40, 150, 90);
    private final Color FIELD_BG = new Color(250, 251, 253);

    // UI components
    private final Menu parentMenu;
    private JComboBox<String> fromComboBox;
    private JComboBox<String> toComboBox;
    private JTextArea resultArea;
    private JButton findPathButton;
    private JLabel statusLabel;

    // Constructor 
    public Shortest_Path(Menu parentMenu) {
        this.parentMenu = parentMenu;

        // Set up the window
        setTitle("BABABOI Airline Flight System - Dijkstra's Shortest Path");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Build the main layout
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(BACKGROUND);
        root.add(createHeader(), BorderLayout.NORTH);
        root.add(createMainContent(), BorderLayout.CENTER);
        root.add(createFooter(), BorderLayout.SOUTH);
        add(root);

        // Load airport data into dropdowns
        populateAirportComboBoxes();
    }

    // Creates the header with title and back button
    private JPanel createHeader() {
        JPanel header = new JPanel();
        header.setBackground(NAVY);
        header.setLayout(new BorderLayout());
        header.setBorder(new EmptyBorder(22, 25, 22, 25));

        // Title block on the left
        JPanel titleBlock = new JPanel();
        titleBlock.setOpaque(false);
        titleBlock.setLayout(new BoxLayout(titleBlock, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("BABABOI Airline Flight System");
        title.setFont(new Font("SansSerif", Font.BOLD, 26));
        title.setForeground(Color.WHITE);

        JLabel subtitle = new JLabel("Dijkstra's Shortest Path Algorithm");
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 14));
        subtitle.setForeground(new Color(190, 200, 215));

        titleBlock.add(title);
        titleBlock.add(Box.createVerticalStrut(4));
        titleBlock.add(subtitle);

        // Back button on the right
        JButton backButton = createStyledButton("‹ Back ", Color.WHITE, Color.BLACK,
                new Color(230, 230, 230), new Color(200, 200, 200));
        backButton.addActionListener(e -> goBackToMenu());

        header.add(titleBlock, BorderLayout.WEST);
        header.add(backButton, BorderLayout.EAST);

        return header;
    }

    // Styled button
    private JButton createStyledButton(String text, Color bg, Color fg, Color hoverBg, Color borderColor) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("SansSerif", Font.BOLD, 13));
        btn.setForeground(fg);
        btn.setBackground(bg);
        btn.setOpaque(true);
        btn.setContentAreaFilled(true);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(borderColor, 1),
                new EmptyBorder(9, 16, 9, 16)
        ));
        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (btn.isEnabled()) btn.setBackground(hoverBg);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(bg);
            }
        });
        return btn;
    }

    // Creates the main content area with input fields and result display
    private JScrollPane createMainContent() {
        JPanel container = new JPanel(new GridBagLayout());
        container.setBackground(BACKGROUND);
        container.setBorder(new EmptyBorder(35, 60, 35, 60));

        // Main card panel
        JPanel card = new JPanel();
        card.setBackground(CARD);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER, 1),
                new EmptyBorder(30, 35, 30, 35)
        ));
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

        // Page heading
        JLabel heading = new JLabel("Find Shortest Path by Distance");
        heading.setFont(new Font("SansSerif", Font.BOLD, 22));
        heading.setForeground(TEXT);
        heading.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel description = new JLabel("Select departure and destination airports to find the shortest route based on minimum flight distance.");
        description.setFont(new Font("SansSerif", Font.PLAIN, 13));
        description.setForeground(SECONDARY_TEXT);
        description.setAlignmentX(Component.LEFT_ALIGNMENT);

        card.add(heading);
        card.add(Box.createVerticalStrut(7));
        card.add(description);
        card.add(Box.createVerticalStrut(25));

        // Departure airport selection
        JPanel fromPanel = new JPanel(new BorderLayout(10, 0));
        fromPanel.setOpaque(false);
        fromPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        fromPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel fromLabel = new JLabel("From Airport:");
        fromLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        fromLabel.setForeground(TEXT);
        fromLabel.setPreferredSize(new Dimension(120, 30));

        fromComboBox = new JComboBox<>();
        fromComboBox.setFont(new Font("SansSerif", Font.PLAIN, 14));
        fromComboBox.setBackground(FIELD_BG);
        fromComboBox.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER, 1),
                new EmptyBorder(6, 10, 6, 10)
        ));
        fromComboBox.addActionListener(e -> updateStatus());

        fromPanel.add(fromLabel, BorderLayout.WEST);
        fromPanel.add(fromComboBox, BorderLayout.CENTER);

        // Destination airport selection
        JPanel toPanel = new JPanel(new BorderLayout(10, 0));
        toPanel.setOpaque(false);
        toPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        toPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel toLabel = new JLabel("To Airport:");
        toLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        toLabel.setForeground(TEXT);
        toLabel.setPreferredSize(new Dimension(120, 30));

        toComboBox = new JComboBox<>();
        toComboBox.setFont(new Font("SansSerif", Font.PLAIN, 14));
        toComboBox.setBackground(FIELD_BG);
        toComboBox.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER, 1),
                new EmptyBorder(6, 10, 6, 10)
        ));
        toComboBox.addActionListener(e -> updateStatus());

        toPanel.add(toLabel, BorderLayout.WEST);
        toPanel.add(toComboBox, BorderLayout.CENTER);

        card.add(fromPanel);
        card.add(Box.createVerticalStrut(15));
        card.add(toPanel);
        card.add(Box.createVerticalStrut(20));

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        findPathButton = createStyledButton("Find Shortest Path",
                BLUE, Color.BLACK,
                new Color(200, 200, 200),
                BLUE);
        findPathButton.setPreferredSize(new Dimension(180, 40));

        JButton clearButton = createStyledButton("Clear Results",
                Color.WHITE, TEXT,
                new Color(242, 244, 248),
                BORDER);
        clearButton.setPreferredSize(new Dimension(150, 40));

        buttonPanel.add(findPathButton);
        buttonPanel.add(Box.createHorizontalStrut(15));
        buttonPanel.add(clearButton);

        card.add(buttonPanel);
        card.add(Box.createVerticalStrut(15));

        // Status label
        statusLabel = new JLabel("Select departure and destination airports to find the shortest path.");
        statusLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        statusLabel.setForeground(SECONDARY_TEXT);
        statusLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(statusLabel);
        card.add(Box.createVerticalStrut(20));

        // Result area heading
        JLabel resultHeading = new JLabel("Shortest Path Result");
        resultHeading.setFont(new Font("SansSerif", Font.BOLD, 16));
        resultHeading.setForeground(TEXT);
        resultHeading.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(resultHeading);
        card.add(Box.createVerticalStrut(10));

        // Result text area
        resultArea = new JTextArea();
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        resultArea.setEditable(false);
        resultArea.setBackground(FIELD_BG);
        resultArea.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER, 1),
                new EmptyBorder(15, 15, 15, 15)
        ));
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);
        resultArea.setText("Awaiting route search...\n\nSelect departure and destination airports from the dropdowns above and click 'Find Shortest Path'.");
        resultArea.setAlignmentX(Component.LEFT_ALIGNMENT);

        JScrollPane scrollPane = new JScrollPane(resultArea);
        scrollPane.setBorder(null);
        scrollPane.setPreferredSize(new Dimension(600, 250));
        scrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
        scrollPane.setMaximumSize(new Dimension(Integer.MAX_VALUE, 300));

        card.add(scrollPane);
        card.add(Box.createVerticalStrut(15));

        // Button action listeners
        findPathButton.addActionListener(e -> findShortestPath());
        clearButton.addActionListener(e -> clearResults());

        // Add card to container with constraints
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.CENTER;
        container.add(card, gbc);

        JScrollPane mainScrollPane = new JScrollPane(container);
        mainScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        mainScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        mainScrollPane.setBorder(null);
        mainScrollPane.getVerticalScrollBar().setUnitIncrement(16);
        return mainScrollPane;
    }

    // Populates the airport dropdowns with data from FlightNetworkData
    private void populateAirportComboBoxes() {
        fromComboBox.removeAllItems();
        toComboBox.removeAllItems();

        // Add placeholder option
        fromComboBox.addItem("-- Select Airport --");
        toComboBox.addItem("-- Select Airport --");

        // Add all active airports
        for (int i = 1; i < FlightNetworkData.VertexNames.size(); i++) {
            String name = FlightNetworkData.VertexNames.get(i);
            String code = FlightNetworkData.VertexCodes.get(i);
            if (name != null && !name.isEmpty() && !name.equals("DELETED")) {
                String display = name + " (" + code + ")";
                fromComboBox.addItem(display);
                toComboBox.addItem(display);
            }
        }
    }

    // Extracts the airport ID from the selected combo box item
    private int getAirportIdFromComboBox(JComboBox<String> comboBox) {
        String selected = (String) comboBox.getSelectedItem();
        if (selected == null || selected.equals("-- Select Airport --")) {
            return -1;
        }

        // Extract airport code from display string (e.g., "Penang International Airport (PEN)" -> "PEN")
        int start = selected.lastIndexOf("(");
        int end = selected.lastIndexOf(")");
        if (start == -1 || end == -1 || start >= end) {
            return -1;
        }
        String code = selected.substring(start + 1, end);

        // Find the ID matching the code
        for (int i = 1; i < FlightNetworkData.VertexCodes.size(); i++) {
            if (code.equals(FlightNetworkData.VertexCodes.get(i))) {
                return i;
            }
        }
        return -1;
    }

    // Main method to find and display the shortest path
    private void findShortestPath() {
        int fromId = getAirportIdFromComboBox(fromComboBox);
        int toId = getAirportIdFromComboBox(toComboBox);

        // Validate inputs
        if (fromId == -1) {
            showStatus("Please select a departure airport.", ERROR);
            return;
        }
        if (toId == -1) {
            showStatus("Please select a destination airport.", ERROR);
            return;
        }
        if (fromId == toId) {
            showStatus("Departure and destination airports cannot be the same.", ERROR);
            return;
        }

        // Check if airports are deleted
        if (FlightNetworkData.VertexNames.get(fromId).equals("DELETED") ||
                FlightNetworkData.VertexNames.get(toId).equals("DELETED")) {
            showStatus("One of the selected airports has been deleted.", ERROR);
            return;
        }

        // Run Dijkstra's algorithm
        DijkstraResult result = dijkstra(fromId, toId);

        // Display result or error
        if (!result.found) {
            resultArea.setText("");
            resultArea.append("========================================\n");
            resultArea.append("  SHORTEST PATH RESULT\n");
            resultArea.append("========================================\n\n");
            resultArea.append("✗ No route found from ");
            resultArea.append(FlightNetworkData.VertexCodes.get(fromId));
            resultArea.append(" to ");
            resultArea.append(FlightNetworkData.VertexCodes.get(toId));
            resultArea.append("!\n\n");
            resultArea.append("The destination airport is not reachable from the departure airport.\n");
            resultArea.append("========================================\n");
            showStatus("No route found.", ERROR);
            return;
        }

        displayDijkstraResult(result, fromId, toId);
        showStatus("Shortest path found successfully!", SUCCESS);
    }

    // Find shortest path by distance
    private DijkstraResult dijkstra(int startId, int endId) {
        int size = FlightNetworkData.VertexNames.size();
        double[] dist = new double[size];      
        double[] time = new double[size];      
        int[] parent = new int[size];          
        boolean[] visited = new boolean[size]; // Track visited vertices

        final double INFINITY = 99999999.0;

        // Initialize all distances to infinity
        for (int i = 0; i < size; i++) {
            dist[i] = INFINITY;
            time[i] = 0;
            parent[i] = -1;
            visited[i] = false;
        }

        // Distance from start to itself is 0
        dist[startId] = 0;
        time[startId] = 0;

        // Main Dijkstra loop
        for (int i = 0; i < size; i++) {
            // Find unvisited vertex with minimum distance
            double minDist = INFINITY;
            int current = -1;

            for (int j = 0; j < size; j++) {
                if (!visited[j] && dist[j] < minDist) {
                    minDist = dist[j];
                    current = j;
                }
            }

            // If no reachable vertex found, break
            if (current == -1 || dist[current] == INFINITY) {
                break;
            }

            // Mark current vertex as visited
            visited[current] = true;

            // Relax edges from current vertex
            for (int next = 0; next < size; next++) {
                double weight = FlightNetworkData.DistanceMatrix[current][next];
                if (weight > 0 && !visited[next]) {
                    // If a shorter path is found, update
                    if (dist[current] + weight < dist[next]) {
                        dist[next] = dist[current] + weight;
                        time[next] = time[current] + FlightNetworkData.TimeMatrix[current][next];
                        parent[next] = current;
                    }
                }
            }
        }

        // Check if destination is reachable
        boolean found = dist[endId] != INFINITY;
        List<Integer> path = new ArrayList<>();

        // Reconstruct the path if found
        if (found) {
            int temp = endId;
            while (temp != -1) {
                path.add(temp);
                temp = parent[temp];
            }
            Collections.reverse(path);
        }

        return new DijkstraResult(found, path, dist[endId], time[endId]);
    }

    // Displays the Dijkstra result in the text area
    private void displayDijkstraResult(DijkstraResult result, int fromId, int toId) {
        resultArea.setText("");

        // Title
        resultArea.append("========================================\n");
        resultArea.append("  SHORTEST PATH RESULT\n");
        resultArea.append("========================================\n\n");

        // Route summary
        resultArea.append("Route from ");
        resultArea.append(FlightNetworkData.VertexCodes.get(fromId));
        resultArea.append(" to ");
        resultArea.append(FlightNetworkData.VertexCodes.get(toId));
        resultArea.append(":\n\n");

        // Path with airport codes
        resultArea.append("  ");
        for (int i = 0; i < result.path.size(); i++) {
            int id = result.path.get(i);
            String code = FlightNetworkData.VertexCodes.get(id);
            if (i > 0) {
                resultArea.append(" → ");
            }
            resultArea.append(code);
        }
        resultArea.append("\n\n");

        // Detailed route with full names and distances
        resultArea.append("Detailed Route:\n");
        resultArea.append("----------------------------------------------------------------\n");

        double totalDistance = 0;
        double totalTime = 0;

        for (int i = 0; i < result.path.size(); i++) {
            int id = result.path.get(i);
            String name = FlightNetworkData.VertexNames.get(id);
            String code = FlightNetworkData.VertexCodes.get(id);

            resultArea.append(String.format("  %d. %s (%s)\n", i + 1, name, code));

            if (i < result.path.size() - 1) {
                int nextId = result.path.get(i + 1);
                double dist = FlightNetworkData.DistanceMatrix[id][nextId];
                double time = FlightNetworkData.TimeMatrix[id][nextId];
                totalDistance += dist;
                totalTime += time;

                resultArea.append(String.format("     └─→ %.1f km, %.2f hours\n", dist, time));
            }
        }

        resultArea.append("----------------------------------------------------------------\n\n");

        // Summary statistics
        resultArea.append("Summary:\n");
        resultArea.append("  • Total Distance: " + String.format("%.1f", totalDistance) + " km\n");
        resultArea.append("  • Total Time: " + String.format("%.2f", totalTime) + " hours\n");
        resultArea.append("  • Number of Flights: " + (result.path.size() - 1) + "\n");
        if (result.path.size() - 2 >= 0) {
            resultArea.append("  • Number of Stops: " + (result.path.size() - 2) + "\n");
        } else {
            resultArea.append("  • Number of Stops: 0 (Direct Flight)\n");
        }
        resultArea.append("\n");
        resultArea.append("========================================\n");
        resultArea.append("  * Shortest path by minimum cumulative distance\n");
        resultArea.append("========================================\n");
    }

    // Clears the result area
    private void clearResults() {
        resultArea.setText("Awaiting route search...\n\nSelect departure and destination airports from the dropdowns above and click 'Find Shortest Path'.");
        showStatus("Results cleared.", SECONDARY_TEXT);
    }

    // Updates the status label 
    private void updateStatus() {
        int fromId = getAirportIdFromComboBox(fromComboBox);
        int toId = getAirportIdFromComboBox(toComboBox);

        if (fromId == -1 || toId == -1) {
            showStatus("Select departure and destination airports to find the shortest path.", SECONDARY_TEXT);
        } else if (fromId == toId) {
            showStatus("Departure and destination airports cannot be the same.", ERROR);
        } else {
            showStatus("Ready to find shortest path.", SUCCESS);
        }
    }

    // Displays a status message with specified color
    private void showStatus(String msg, Color color) {
        statusLabel.setText(msg);
        statusLabel.setForeground(color);
    }

    // Navigates to main menu
    private void goBackToMenu() {
        if (parentMenu != null) {
            parentMenu.setVisible(true);
        }
        dispose();
    }

    // Creates the footer with system name and page name
    private JPanel createFooter() {
        JPanel footer = new JPanel(new BorderLayout());
        footer.setBackground(DARK_NAVY);
        footer.setBorder(new EmptyBorder(10, 30, 10, 30));

        JLabel left = new JLabel("BABABOI Airline Flight System");
        left.setFont(new Font("SansSerif", Font.PLAIN, 11));
        left.setForeground(new Color(180, 190, 205));

        JLabel right = new JLabel("Dijkstra's Shortest Path");
        right.setFont(new Font("SansSerif", Font.PLAIN, 11));
        right.setForeground(new Color(180, 190, 205));

        footer.add(left, BorderLayout.WEST);
        footer.add(right, BorderLayout.EAST);
        return footer;
    }

    // Dijkstra algorithm results
    private static class DijkstraResult {
        boolean found;        
        List<Integer> path;     
        double distance;        
        double time;           

        DijkstraResult(boolean found, List<Integer> path, double distance, double time) {
            this.found = found;
            this.path = path;
            this.distance = distance;
            this.time = time;
        }
    }
}