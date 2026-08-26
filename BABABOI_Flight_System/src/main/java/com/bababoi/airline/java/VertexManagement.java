/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bababoi.airline.java;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.table.TableColumnModel;

public class VertexManagement extends JFrame {

    // COLOURS
    private final Color NAVY = new Color(20, 35, 60);
    private final Color DARK_NAVY = new Color(15, 27, 48);
    private final Color BLUE = new Color(0, 122, 255);
    private final Color BACKGROUND = new Color(243, 246, 250);
    private final Color BORDER = new Color(220, 225, 232);
    private final Color ERROR = new Color(210, 65, 65);
    private final Color SUCCESS = new Color(40, 150, 90);
    private final Color FIELD_BG = new Color(250, 251, 253);
    private final Color SECONDARY_TEXT = new Color(105, 115, 130);
    private final Color TEXT = new Color(30, 35, 45);

    // STATE
    private final Menu parentMenu;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField nameField;
    private JTextField codeField;
    private JLabel statusLabel;
    private JLabel selectionLabel;
    private JButton removeButton;
    private JButton viewRoutesButton;
    private java.util.List<Integer> rowToId;

    // CONSTRUCTOR
    public VertexManagement(Menu parentMenu) {
        this.parentMenu = parentMenu;
        rowToId = new java.util.ArrayList<>();

        setTitle("BABABOI Airline Flight System - Manage Vertex (Airport)");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Create main content pane with BorderLayout
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(BACKGROUND);
        root.setOpaque(true);
        
        // Add components
        root.add(createHeader(), BorderLayout.NORTH);
        root.add(createMainContent(), BorderLayout.CENTER);
        root.add(createFooter(), BorderLayout.SOUTH);
        
        // Set as content pane
        setContentPane(root);

        refreshTable();
        updateSelectionState();
    }


    // HEADER 
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

        JLabel subtitle = new JLabel("Manage Vertex (Airport)");
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

    // MAIN CONTENT
    private JScrollPane createMainContent() {
        JPanel container = new JPanel(new GridBagLayout());
        container.setBackground(BACKGROUND);
        container.setOpaque(true);
        container.setBorder(new EmptyBorder(30, 40, 30, 40));

        JPanel card = new JPanel(new BorderLayout(25, 0));
        card.setBackground(Color.WHITE);
        card.setOpaque(true);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER, 1),
                new EmptyBorder(28, 30, 28, 30)
        ));

        card.add(createTablePanel(), BorderLayout.CENTER);
        card.add(createControlPanel(), BorderLayout.EAST);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        container.add(card, gbc);

        JScrollPane scrollPane = new JScrollPane(container);
        scrollPane.setBorder(null);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        return scrollPane;
    }

    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        panel.setPreferredSize(new Dimension(650, 500));

        JLabel heading = new JLabel("Airports");
        heading.setFont(new Font("SansSerif", Font.BOLD, 20));
        heading.setForeground(TEXT);
        heading.setBorder(new EmptyBorder(0, 0, 12, 0));

        tableModel = new DefaultTableModel(
                new Object[]{"NO", "Airport Name", "Airport Code"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        table.setRowHeight(30);
        table.setFont(new Font("SansSerif", Font.PLAIN, 13));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setSelectionBackground(new Color(225, 238, 255));
        table.setSelectionForeground(TEXT);
        table.setGridColor(BORDER);
        table.setShowVerticalLines(false);
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 13));
        table.getTableHeader().setBackground(new Color(248, 249, 251));
        table.getTableHeader().setForeground(SECONDARY_TEXT);
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setResizingAllowed(false);

        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setMaxWidth(50);
        columnModel.getColumn(1).setMaxWidth(800);
        columnModel.getColumn(2).setMaxWidth(250);

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) updateSelectionState();
        });

        JScrollPane tableScroll = new JScrollPane(table);
        tableScroll.setBorder(BorderFactory.createLineBorder(BORDER, 1));

        panel.add(heading, BorderLayout.NORTH);
        panel.add(tableScroll, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createControlPanel() {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setPreferredSize(new Dimension(300, 500));

        panel.add(createAddSection());
        panel.add(Box.createVerticalStrut(20));
        panel.add(createDivider());
        panel.add(Box.createVerticalStrut(20));
        panel.add(createSelectionSection());
        panel.add(Box.createVerticalStrut(20));

        statusLabel = new JLabel(" ");
        statusLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        statusLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(statusLabel);
        panel.add(Box.createVerticalGlue());

        return panel;
    }

    private JComponent createDivider() {
        JSeparator sep = new JSeparator();
        sep.setForeground(BORDER);
        sep.setAlignmentX(Component.LEFT_ALIGNMENT);
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        return sep;
    }

    private JPanel createAddSection() {
        JPanel section = new JPanel();
        section.setOpaque(false);
        section.setLayout(new BoxLayout(section, BoxLayout.Y_AXIS));
        section.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel heading = new JLabel("Add New Airport");
        heading.setFont(new Font("SansSerif", Font.BOLD, 15));
        heading.setForeground(TEXT);
        heading.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel nameLabel = new JLabel("Airport Name (e.g Penang International Airport)") ;
        nameLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        nameLabel.setForeground(SECONDARY_TEXT);
        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        nameLabel.setBorder(new EmptyBorder(10, 0, 5, 0));
        nameField = new JTextField();
        nameField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        nameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        nameField.setAlignmentX(Component.LEFT_ALIGNMENT);
        nameField.setBackground(FIELD_BG);
        nameField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER, 1),
                new EmptyBorder(6, 10, 6, 10)
        ));

        JLabel codeLabel = new JLabel("Airport Code (e.g PEN)");
        codeLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        codeLabel.setForeground(SECONDARY_TEXT);
        codeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        codeLabel.setBorder(new EmptyBorder(10, 0, 5, 0));
        codeField = new JTextField();
        codeField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        codeField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        codeField.setAlignmentX(Component.LEFT_ALIGNMENT);
        codeField.setBackground(FIELD_BG);
        codeField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER, 1),
                new EmptyBorder(6, 10, 6, 10)
        ));

        JButton addButton = createStyledButton(
                "+ Add Airport",
                BLUE, Color.BLACK,
                new Color(200, 200, 200),
                BLUE
        );
        addButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        addButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        addButton.addActionListener(e -> handleAddAirport());

        section.add(heading);
        section.add(nameLabel);
        section.add(nameField);
        section.add(codeLabel);
        section.add(codeField);
        section.add(Box.createVerticalStrut(12));
        section.add(addButton);
        return section;
    }

    private JPanel createSelectionSection() {
        JPanel section = new JPanel();
        section.setOpaque(false);
        section.setLayout(new BoxLayout(section, BoxLayout.Y_AXIS));
        section.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel heading = new JLabel("Selected Airport");
        heading.setFont(new Font("SansSerif", Font.BOLD, 15));
        heading.setForeground(TEXT);
        heading.setAlignmentX(Component.LEFT_ALIGNMENT);

        selectionLabel = new JLabel("No airport selected");
        selectionLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
        selectionLabel.setForeground(SECONDARY_TEXT);
        selectionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        selectionLabel.setBorder(new EmptyBorder(8, 0, 12, 0));

        viewRoutesButton = createStyledButton("View Routes From Airport",
                Color.WHITE, TEXT, new Color(242, 244, 248), BORDER);
        viewRoutesButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        viewRoutesButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        viewRoutesButton.addActionListener(e -> handleViewRoutes());

        removeButton = createStyledButton("Remove Airport",
                Color.WHITE, ERROR, new Color(255, 242, 242), new Color(230, 205, 205));
        removeButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        removeButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        removeButton.addActionListener(e -> handleRemoveAirport());

        section.add(heading);
        section.add(selectionLabel);
        section.add(viewRoutesButton);
        section.add(Box.createVerticalStrut(10));
        section.add(removeButton);
        return section;
    }

    // ACTIONS
private void handleAddAirport() {
    String name = nameField.getText().trim();
    String code = codeField.getText().trim();
    
    if (name.isEmpty() || code.isEmpty()) {
        showStatus("Please enter both Airport Name and Airport Code.", ERROR);
        return;
    }
    
    // Validate airport code
    if (!code.matches("^[A-Z]{3}$")) {
        showStatus("Airport Code must be 3 uppercase letters (e.g., JFK)).", ERROR);
        return;
    }
    
    // Check for duplicate airport code
    for (int i = 1; i < FlightNetworkData.VertexCodes.size(); i++) {
        String existingCode = FlightNetworkData.VertexCodes.get(i);
        if (existingCode != null && existingCode.equals(code)) {
            showStatus("Airport code \"" + code + "\" already exists. Please use a unique code.", ERROR);
            return;
        }
    }

    FlightNetworkData.VertexNames.add(name);
    FlightNetworkData.VertexCodes.add(code);
    int newId = FlightNetworkData.VertexNames.size() - 1;
    FlightNetworkData.resizeMatrices(FlightNetworkData.VertexNames.size());
    nameField.setText("");
    codeField.setText("");
    refreshTable();
    selectRowById(newId);
    showStatus("Airport \"" + name + "\" (" + code + ") added.", SUCCESS);
    FlightNetworkData.save();
}

    private void handleRemoveAirport() {
        int id = getSelectedId();
        if (id == -1) return;
        String name = FlightNetworkData.VertexNames.get(id);
        int result = JOptionPane.showConfirmDialog(
                this,
                "Remove airport: " + name + " (ID: " + id + ")?\nAll routes will be cleared.",
                "Confirm Remove",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );
        if (result != JOptionPane.YES_OPTION) return;

        FlightNetworkData.removeAirport(id);
        refreshTable();
        updateSelectionState();
        showStatus("Airport \"" + name + "\" removed.", SUCCESS);
        FlightNetworkData.save();
    }

    private void handleViewRoutes() {
        int id = getSelectedId();
        if (id == -1) return;

        String fromName = FlightNetworkData.VertexNames.get(id);
        java.util.List<Object[]> routeData = new java.util.ArrayList<>();
        for (int i = 1; i < FlightNetworkData.VertexNames.size(); i++) {
            if (i == id) continue;
            double dist = FlightNetworkData.DistanceMatrix[id][i];
            if (dist > 0) {
                String destName = FlightNetworkData.VertexNames.get(i);
                String destCode = FlightNetworkData.VertexCodes.get(i);
                double time = FlightNetworkData.TimeMatrix[id][i];
                routeData.add(new Object[]{destName, destCode, dist, time});
            }
        }

        if (routeData.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "No direct routes from " + fromName + ".",
                    "Routes",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String[] columns = {"Destination Airport", "Code", "Distance (km)", "Time (hour)"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        for (Object[] row : routeData) {
            model.addRow(row);
        }

        JTable routeTable = new JTable(model);
        routeTable.setRowHeight(25);
        routeTable.setFont(new Font("SansSerif", Font.PLAIN, 13));
        routeTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 13));
        routeTable.setGridColor(BORDER);
        routeTable.setShowVerticalLines(false);

        TableColumnModel colModel = routeTable.getColumnModel();
        colModel.getColumn(0).setPreferredWidth(300);
        colModel.getColumn(1).setPreferredWidth(100);
        colModel.getColumn(2).setPreferredWidth(120);
        colModel.getColumn(3).setPreferredWidth(120);

        JScrollPane scrollPane = new JScrollPane(routeTable);
        scrollPane.setPreferredSize(new Dimension(650, 300));

        JOptionPane.showMessageDialog(this,
                scrollPane,
                "Routes from " + fromName,
                JOptionPane.PLAIN_MESSAGE);
    }

    private void refreshTable() {
        int selectedId = getSelectedId();
        tableModel.setRowCount(0);
        rowToId.clear();

        int rowNum = 1;
        for (int i = 1; i < FlightNetworkData.VertexNames.size(); i++) {
            String name = FlightNetworkData.VertexNames.get(i);
            if (name == null || name.isEmpty()) continue;
            String code = FlightNetworkData.VertexCodes.get(i);
            tableModel.addRow(new Object[]{rowNum, name, code});
            rowToId.add(i);
            rowNum++;
        }

        if (selectedId != -1) selectRowById(selectedId);
    }

    private void selectRowById(int id) {
        int index = rowToId.indexOf(id);
        if (index != -1) {
            table.setRowSelectionInterval(index, index);
            table.scrollRectToVisible(table.getCellRect(index, 0, true));
        }
    }

    private int getSelectedId() {
        int row = table.getSelectedRow();
        if (row == -1 || row >= rowToId.size()) return -1;
        return rowToId.get(row);
    }

    private void updateSelectionState() {
        int row = table.getSelectedRow();
        if (row == -1 || row >= rowToId.size()) {
            selectionLabel.setText("No airport selected");
            selectionLabel.setForeground(SECONDARY_TEXT);
            removeButton.setEnabled(false);
            viewRoutesButton.setEnabled(false);
            return;
        }
        int id = rowToId.get(row);
        String name = (String) tableModel.getValueAt(row, 1);
        selectionLabel.setText("ID " + id + " - " + name);
        selectionLabel.setForeground(TEXT);
        removeButton.setEnabled(true);
        viewRoutesButton.setEnabled(true);
    }

    private void showStatus(String msg, Color color) {
        statusLabel.setText(msg);
        statusLabel.setForeground(color);
    }

    // NAVIGATION & FOOTER
    private void goBackToMenu() {
        if (parentMenu != null) parentMenu.setVisible(true);
        dispose();
    }

    private JPanel createFooter() {
        JPanel footer = new JPanel(new BorderLayout());
        footer.setBackground(DARK_NAVY);
        footer.setOpaque(true);
        footer.setBorder(new EmptyBorder(10, 30, 10, 30));
        JLabel left = new JLabel("BABABOI Airline Flight System");
        left.setFont(new Font("SansSerif", Font.PLAIN, 11));
        left.setForeground(new Color(180, 190, 205));
        JLabel right = new JLabel("Airport (Vertex) Management");
        right.setFont(new Font("SansSerif", Font.PLAIN, 11));
        right.setForeground(new Color(180, 190, 205));
        footer.add(left, BorderLayout.WEST);
        footer.add(right, BorderLayout.EAST);
        return footer;
    }
}