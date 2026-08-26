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
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BFS_Search extends JFrame {

    // Color scheme (same as DFSSearch)
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

    private final Menu parentMenu;
    private JComboBox<String> fromComboBox;
    private JComboBox<String> toComboBox;
    private JTextArea resultArea;
    private JLabel statusLabel;

    public BFS_Search(Menu parentMenu) {
        this.parentMenu = parentMenu;

        setTitle("BABABOI Airline Flight System - BFS Search Route");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(BACKGROUND);
        root.add(createHeader(), BorderLayout.NORTH);
        root.add(createMainContent(), BorderLayout.CENTER);
        root.add(createFooter(), BorderLayout.SOUTH);
        add(root);

        populateAirportComboBoxes();
    }

    private JPanel createHeader() {
        JPanel header = new JPanel();
        header.setBackground(NAVY);
        header.setLayout(new BorderLayout());
        header.setBorder(new EmptyBorder(22, 25, 22, 25));

        JPanel titleBlock = new JPanel();
        titleBlock.setOpaque(false);
        titleBlock.setLayout(new BoxLayout(titleBlock, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("BABABOI Airline Flight System");
        title.setFont(new Font("SansSerif", Font.BOLD, 26));
        title.setForeground(Color.WHITE);

        JLabel subtitle = new JLabel("Breadth-First Search (BFS) Route");
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 14));
        subtitle.setForeground(new Color(190, 200, 215));

        titleBlock.add(title);
        titleBlock.add(Box.createVerticalStrut(4));
        titleBlock.add(subtitle);

        JButton backButton = createStyledButton("‹ Back ", Color.WHITE, Color.BLACK,
                new Color(230, 230, 230), new Color(200, 200, 200));
        backButton.addActionListener(e -> goBackToMenu());

        header.add(titleBlock, BorderLayout.WEST);
        header.add(backButton, BorderLayout.EAST);

        return header;
    }

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

    private JScrollPane createMainContent() {
        JPanel container = new JPanel(new GridBagLayout());
        container.setBackground(BACKGROUND);
        container.setBorder(new EmptyBorder(35, 60, 35, 60));

        JPanel card = new JPanel();
        card.setBackground(CARD);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER, 1),
                new EmptyBorder(30, 35, 30, 35)
        ));
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

        JLabel heading = new JLabel("Find Route using BFS");
        heading.setFont(new Font("SansSerif", Font.BOLD, 22));
        heading.setForeground(TEXT);
        heading.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel description = new JLabel("Select departure and destination airports to find a route using Breadth-First Search.");
        description.setFont(new Font("SansSerif", Font.PLAIN, 13));
        description.setForeground(SECONDARY_TEXT);
        description.setAlignmentX(Component.LEFT_ALIGNMENT);

        card.add(heading);
        card.add(Box.createVerticalStrut(7));
        card.add(description);
        card.add(Box.createVerticalStrut(25));

        // From combo
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

        // To combo
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

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton findButton = createStyledButton("Find Route",
                BLUE, Color.BLACK, new Color(200, 200, 200), BLUE);
        findButton.setPreferredSize(new Dimension(180, 40));

        JButton clearButton = createStyledButton("Clear Results",
                Color.WHITE, TEXT, new Color(242, 244, 248), BORDER);
        clearButton.setPreferredSize(new Dimension(150, 40));

        buttonPanel.add(findButton);
        buttonPanel.add(Box.createHorizontalStrut(15));
        buttonPanel.add(clearButton);

        card.add(buttonPanel);
        card.add(Box.createVerticalStrut(15));

        // Status
        statusLabel = new JLabel("Select departure and destination airports to find a route.");
        statusLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        statusLabel.setForeground(SECONDARY_TEXT);
        statusLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(statusLabel);
        card.add(Box.createVerticalStrut(20));

        // Result area
        JLabel resultHeading = new JLabel("BFS Route Result");
        resultHeading.setFont(new Font("SansSerif", Font.BOLD, 16));
        resultHeading.setForeground(TEXT);
        resultHeading.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(resultHeading);
        card.add(Box.createVerticalStrut(10));

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
        resultArea.setText("Awaiting route search...\n\nSelect departure and destination airports and click 'Find Route'.");
        resultArea.setAlignmentX(Component.LEFT_ALIGNMENT);

        JScrollPane scrollPane = new JScrollPane(resultArea);
        scrollPane.setBorder(null);
        scrollPane.setPreferredSize(new Dimension(600, 250));
        scrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
        scrollPane.setMaximumSize(new Dimension(Integer.MAX_VALUE, 300));

        card.add(scrollPane);
        card.add(Box.createVerticalStrut(15));

        findButton.addActionListener(e -> findRoute());
        clearButton.addActionListener(e -> clearResults());

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

    private void populateAirportComboBoxes() {
        fromComboBox.removeAllItems();
        toComboBox.removeAllItems();

        fromComboBox.addItem("-- Select Airport --");
        toComboBox.addItem("-- Select Airport --");

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

    private int getAirportIdFromComboBox(JComboBox<String> comboBox) {
        String selected = (String) comboBox.getSelectedItem();
        if (selected == null || selected.equals("-- Select Airport --")) return -1;
        int start = selected.lastIndexOf("(");
        int end = selected.lastIndexOf(")");
        if (start == -1 || end == -1 || start >= end) return -1;
        String code = selected.substring(start + 1, end);
        for (int i = 1; i < FlightNetworkData.VertexCodes.size(); i++) {
            if (code.equals(FlightNetworkData.VertexCodes.get(i))) return i;
        }
        return -1;
    }

    private void findRoute() {
        int fromId = getAirportIdFromComboBox(fromComboBox);
        int toId = getAirportIdFromComboBox(toComboBox);

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
        if (FlightNetworkData.VertexNames.get(fromId).equals("DELETED") ||
                FlightNetworkData.VertexNames.get(toId).equals("DELETED")) {
            showStatus("One of the selected airports has been deleted.", ERROR);
            return;
        }

        List<Integer> path = bfs(fromId, toId);

        if (path.isEmpty()) {
            resultArea.setText("");
            resultArea.append("========================================\n");
            resultArea.append("  BFS ROUTE RESULT\n");
            resultArea.append("========================================\n\n");
            resultArea.append("✗ No route found from ");
            resultArea.append(FlightNetworkData.VertexCodes.get(fromId));
            resultArea.append(" to ");
            resultArea.append(FlightNetworkData.VertexCodes.get(toId));
            resultArea.append("!\n\n");
            resultArea.append("The destination airport is not reachable using BFS.\n");
            resultArea.append("========================================\n");
            showStatus("No route found.", ERROR);
            return;
        }

        displayRoute(path, fromId, toId);
        showStatus("Route found successfully!", SUCCESS);
    }

    private List<Integer> bfs(int start, int goal) {
        int size = FlightNetworkData.VertexNames.size();
        boolean[] visited = new boolean[size];
        int[] parent = new int[size];
        Arrays.fill(parent, -1);

        Queue<Integer> queue = new LinkedList<>();
        queue.add(start);
        visited[start] = true;

        while (!queue.isEmpty()) {
            int current = queue.poll();
            if (current == goal) break;

            for (int next = 1; next < size; next++) {
                if (!visited[next] && FlightNetworkData.DistanceMatrix[current][next] > 0) {
                    visited[next] = true;
                    parent[next] = current;
                    queue.add(next);
                }
            }
        }

        List<Integer> path = new ArrayList<>();
        if (visited[goal]) {
            int temp = goal;
            while (temp != -1) {
                path.add(temp);
                temp = parent[temp];
            }
            Collections.reverse(path);
        }
        return path;
    }

    private void displayRoute(List<Integer> path, int fromId, int toId) {
        resultArea.setText("");
        resultArea.append("========================================\n");
        resultArea.append("  BFS ROUTE RESULT\n");
        resultArea.append("========================================\n\n");

        resultArea.append("Route from ");
        resultArea.append(FlightNetworkData.VertexCodes.get(fromId));
        resultArea.append(" to ");
        resultArea.append(FlightNetworkData.VertexCodes.get(toId));
        resultArea.append(":\n\n");

        resultArea.append("  ");
        for (int i = 0; i < path.size(); i++) {
            int id = path.get(i);
            String code = FlightNetworkData.VertexCodes.get(id);
            if (i > 0) resultArea.append(" → ");
            resultArea.append(code);
        }
        resultArea.append("\n\n");

        resultArea.append("Detailed Route:\n");
        resultArea.append("----------------------------------------------------------------\n");
        double totalDistance = 0;
        double totalTime = 0;
        for (int i = 0; i < path.size(); i++) {
            int id = path.get(i);
            String name = FlightNetworkData.VertexNames.get(id);
            String code = FlightNetworkData.VertexCodes.get(id);
            resultArea.append(String.format("  %d. %s (%s)\n", i + 1, name, code));
            if (i < path.size() - 1) {
                int nextId = path.get(i + 1);
                double dist = FlightNetworkData.DistanceMatrix[id][nextId];
                double time = FlightNetworkData.TimeMatrix[id][nextId];
                String flightNo = FlightNetworkData.FlightNumbers[id][nextId];
                if (flightNo == null || flightNo.isEmpty()) flightNo = "N/A";
                totalDistance += dist;
                totalTime += time;
                resultArea.append(String.format("     └─→ Flight %s, %.1f km, %.2f hours\n", flightNo, dist, time));
            }
        }
        resultArea.append("----------------------------------------------------------------\n\n");
        resultArea.append("Summary:\n");
        resultArea.append("  • Total Distance: " + String.format("%.1f", totalDistance) + " km\n");
        resultArea.append("  • Total Time: " + String.format("%.2f", totalTime) + " hours\n");
        resultArea.append("  • Number of Flights: " + (path.size() - 1) + "\n");
        resultArea.append("  • Number of Stops: " + (path.size() - 2 > 0 ? path.size() - 2 : 0) + "\n");
        resultArea.append("\n========================================\n");
        resultArea.append("  * Route found using Breadth-First Search (BFS)\n");
        resultArea.append("========================================\n");
    }

    private void clearResults() {
        resultArea.setText("Awaiting route search...\n\nSelect departure and destination airports and click 'Find Route'.");
        showStatus("Results cleared.", SECONDARY_TEXT);
    }

    private void updateStatus() {
        int fromId = getAirportIdFromComboBox(fromComboBox);
        int toId = getAirportIdFromComboBox(toComboBox);
        if (fromId == -1 || toId == -1) {
            showStatus("Select departure and destination airports to find a route.", SECONDARY_TEXT);
        } else if (fromId == toId) {
            showStatus("Departure and destination airports cannot be the same.", ERROR);
        } else {
            showStatus("Ready to find route using BFS.", SUCCESS);
        }
    }

    private void showStatus(String msg, Color color) {
        statusLabel.setText(msg);
        statusLabel.setForeground(color);
    }

    private void goBackToMenu() {
        if (parentMenu != null) parentMenu.setVisible(true);
        dispose();
    }

    private JPanel createFooter() {
        JPanel footer = new JPanel(new BorderLayout());
        footer.setBackground(DARK_NAVY);
        footer.setBorder(new EmptyBorder(10, 30, 10, 30));
        JLabel left = new JLabel("BABABOI Airline Flight System");
        left.setFont(new Font("SansSerif", Font.PLAIN, 11));
        left.setForeground(new Color(180, 190, 205));
        JLabel right = new JLabel("Breadth-First Search (BFS)");
        right.setFont(new Font("SansSerif", Font.PLAIN, 11));
        right.setForeground(new Color(180, 190, 205));
        footer.add(left, BorderLayout.WEST);
        footer.add(right, BorderLayout.EAST);
        return footer;
    }
}