package com.techwebdocs.main;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class EdgeManagement extends JFrame {

    // =========================================================
    // COLOURS
    // =========================================================
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

    // =========================================================
    // STATE
    // =========================================================
    private final Menu parentMenu;
    private JTable table;
    private DefaultTableModel tableModel;
    private JComboBox<AirportItem> fromCombo;
    private JComboBox<AirportItem> toCombo;
    private JTextField flightNumberField;
    private JTextField distanceField;
    private JTextField timeField;
    private JPanel addFormPanel;
    private JPanel selectPanel;
    private JLabel selectionLabel;
    private JTextField updateFlightNumberField;
    private JTextField updateDistanceField;
    private JTextField updateTimeField;
    private JButton updateButton;
    private JButton removeButton;
    private JLabel statusLabel;
    private static class AirportItem {
        int id;
        String name;
        AirportItem(int id, String name) {
            this.id = id;
            this.name = name;
        }
        @Override
        public String toString() {
            return name;
        }
    }
    private static class AirportCell {
        int id;
        String code;
        AirportCell(int id, String code) {
            this.id = id;
            this.code = code;
        }
        @Override
        public String toString() {
            return code;
        }
    }

    // =========================================================
    // CONSTRUCTOR
    // =========================================================
    public EdgeManagement(Menu parentMenu) {
        this.parentMenu = parentMenu;

        setTitle("BABABOI Airline Flight System - Manage Edge (Flight)");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(BACKGROUND);

        root.add(createHeader(), BorderLayout.NORTH);
        root.add(createMainContent(), BorderLayout.CENTER);
        root.add(createFooter(), BorderLayout.SOUTH);

        add(root);

        refreshAirportCombos();
        refreshTable();
        selectPanel.setVisible(false);
        addFormPanel.setVisible(true);
        updateSelectionState();
    }

    // =========================================================
    // HEADER
    // =========================================================
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

        JLabel subtitle = new JLabel("Manage Edge (Flight Route)");
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

    // =========================================================
    // MAIN CONTENT
    // =========================================================
    private JScrollPane createMainContent() {
        JPanel container = new JPanel(new GridBagLayout());
        container.setBackground(BACKGROUND);
        container.setBorder(new EmptyBorder(30, 40, 30, 40));

        JPanel card = new JPanel(new BorderLayout(25, 0));
        card.setBackground(CARD);
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
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        return scrollPane;
    }

    // =========================================================
    // TABLE PANEL (5 columns)
    // =========================================================
    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        panel.setPreferredSize(new Dimension(700, 500));

        JLabel heading = new JLabel("Flight Routes");
        heading.setFont(new Font("SansSerif", Font.BOLD, 20));
        heading.setForeground(TEXT);
        heading.setBorder(new EmptyBorder(0, 0, 12, 0));

        tableModel = new DefaultTableModel(
                new Object[]{"From", "To", "Flight Numbers", "Distance (km)", "Time (hour)"}, 0
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

        javax.swing.table.TableColumnModel colModel = table.getColumnModel();
        for (int i = 0; i < colModel.getColumnCount(); i++) {
            colModel.getColumn(i).setPreferredWidth(140);
            colModel.getColumn(i).setMinWidth(100);
        }

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                updateSelectionState();
            }
        });

        JScrollPane tableScroll = new JScrollPane(table);
        tableScroll.setBorder(BorderFactory.createLineBorder(BORDER, 1));

        panel.add(heading, BorderLayout.NORTH);
        panel.add(tableScroll, BorderLayout.CENTER);
        return panel;
    }

    // =========================================================
    // CONTROL PANEL
    // =========================================================
    private JPanel createControlPanel() {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setPreferredSize(new Dimension(320, 500));
        JPanel addPanel = createAddPanel();
        panel.add(addPanel);

        JSeparator separator = new JSeparator();
        separator.setForeground(BORDER);
        separator.setAlignmentX(Component.LEFT_ALIGNMENT);
        separator.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        panel.add(separator);
        panel.add(Box.createVerticalStrut(15));

        selectPanel = createSelectPanel();
        selectPanel.setVisible(false);
        panel.add(selectPanel);

        panel.add(Box.createVerticalStrut(15));
        statusLabel = new JLabel(" ");
        statusLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        statusLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(statusLabel);
        panel.add(Box.createVerticalGlue());

        return panel;
    }

    private JPanel createAddPanel() {
        JPanel section = new JPanel();
        section.setOpaque(false);
        section.setLayout(new BoxLayout(section, BoxLayout.Y_AXIS));
        section.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel heading = new JLabel("Add New Route");
        heading.setFont(new Font("SansSerif", Font.BOLD, 15));
        heading.setForeground(TEXT);
        heading.setAlignmentX(Component.LEFT_ALIGNMENT);
        heading.setCursor(new Cursor(Cursor.HAND_CURSOR));
        heading.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // 取消表格选中，回到添加模式
                table.clearSelection();
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                heading.setForeground(BLUE);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                heading.setForeground(TEXT);
            }
        });
        section.add(heading);

        addFormPanel = new JPanel();
        addFormPanel.setOpaque(false);
        addFormPanel.setLayout(new BoxLayout(addFormPanel, BoxLayout.Y_AXIS));
        addFormPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        fromCombo = new JComboBox<>();
        toCombo = new JComboBox<>();
        flightNumberField = new JTextField();
        distanceField = new JTextField();
        timeField = new JTextField();

        addFormPanel.add(labeled("From Airport", fromCombo));
        addFormPanel.add(labeled("To Airport", toCombo));
        addFormPanel.add(labeled("Flight Number", flightNumberField));
        addFormPanel.add(labeled("Distance (km)", distanceField));
        addFormPanel.add(labeled("Flight Time (hour)", timeField));

        JButton addButton = createPrimaryButton("+ Add Route");
        addButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        addButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        addButton.addActionListener(e -> handleAddEdge());

        addFormPanel.add(Box.createVerticalStrut(6));
        addFormPanel.add(addButton);

        section.add(addFormPanel);
        return section;
    }

    private JPanel createSelectPanel() {
        JPanel section = new JPanel();
        section.setOpaque(false);
        section.setLayout(new BoxLayout(section, BoxLayout.Y_AXIS));
        section.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel heading = new JLabel("Selected Route");
        heading.setFont(new Font("SansSerif", Font.BOLD, 15));
        heading.setForeground(TEXT);
        heading.setAlignmentX(Component.LEFT_ALIGNMENT);

        selectionLabel = new JLabel("No route selected");
        selectionLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
        selectionLabel.setForeground(SECONDARY_TEXT);
        selectionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        selectionLabel.setBorder(new EmptyBorder(8, 0, 12, 0));

        updateFlightNumberField = new JTextField();
        updateDistanceField = new JTextField();
        updateTimeField = new JTextField();

        JPanel updateFields = new JPanel();
        updateFields.setOpaque(false);
        updateFields.setLayout(new BoxLayout(updateFields, BoxLayout.Y_AXIS));
        updateFields.setAlignmentX(Component.LEFT_ALIGNMENT);
        updateFields.add(labeled("New Flight Number", updateFlightNumberField));
        updateFields.add(labeled("New Distance (km)", updateDistanceField));
        updateFields.add(labeled("New Flight Time (hour)", updateTimeField));

        updateButton = createSecondaryButton("Update Route");
        updateButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        updateButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        updateButton.addActionListener(e -> handleUpdateEdge());

        removeButton = createDangerButton("Remove Route");
        removeButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        removeButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        removeButton.addActionListener(e -> handleRemoveEdge());

        section.add(heading);
        section.add(selectionLabel);
        section.add(updateFields);
        section.add(Box.createVerticalStrut(6));
        section.add(updateButton);
        section.add(Box.createVerticalStrut(10));
        section.add(removeButton);
        return section;
    }

    private JPanel labeled(String labelText, JComponent field) {
        JPanel wrapper = new JPanel();
        wrapper.setOpaque(false);
        wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));
        wrapper.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("SansSerif", Font.PLAIN, 12));
        label.setForeground(SECONDARY_TEXT);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        label.setBorder(new EmptyBorder(10, 0, 5, 0));

        field.setFont(new Font("SansSerif", Font.PLAIN, 14));
        field.setAlignmentX(Component.LEFT_ALIGNMENT);
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));

        if (field instanceof JTextField) {
            field.setBackground(FIELD_BG);
            field.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(BORDER, 1),
                    new EmptyBorder(6, 10, 6, 10)
            ));
        } else if (field instanceof JComboBox) {
            field.setBackground(FIELD_BG);
        }

        wrapper.add(label);
        wrapper.add(field);
        return wrapper;
    }

    // =========================================================
    // BUTTON STYLES
    // =========================================================
    private JButton createPrimaryButton(String text) {
        JButton button = styledButton(text, BLUE, Color.WHITE, new Color(0, 105, 225));
        button.setBorder(new EmptyBorder(10, 16, 10, 16));
        return button;
    }

    private JButton createSecondaryButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("SansSerif", Font.BOLD, 13));
        button.setForeground(TEXT);
        button.setBackground(Color.WHITE);
        button.setOpaque(true);
        button.setContentAreaFilled(true);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER, 1),
                new EmptyBorder(9, 16, 9, 16)
        ));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (button.isEnabled()) button.setBackground(new Color(242, 244, 248));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(Color.WHITE);
            }
        });
        return button;
    }

    private JButton createDangerButton(String text) {
        JButton button = styledButton(text, Color.WHITE, ERROR, new Color(255, 242, 242));
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230, 205, 205), 1),
                new EmptyBorder(9, 16, 9, 16)
        ));
        return button;
    }

    private JButton styledButton(String text, Color bg, Color fg, Color hoverBg) {
        JButton button = new JButton(text);
        button.setFont(new Font("SansSerif", Font.BOLD, 13));
        button.setForeground(fg);
        button.setBackground(bg);
        button.setOpaque(true);
        button.setContentAreaFilled(true);
        button.setBorderPainted(bg == Color.WHITE);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        Color baseBg = bg;
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (button.isEnabled()) button.setBackground(hoverBg);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(baseBg);
            }
        });
        return button;
    }

    // =========================================================
    // AIRPORT COMBO HELPERS
    // =========================================================
    private void refreshAirportCombos() {
        String previousFrom = (fromCombo.getSelectedItem() != null)
                ? ((AirportItem) fromCombo.getSelectedItem()).name : null;
        String previousTo = (toCombo.getSelectedItem() != null)
                ? ((AirportItem) toCombo.getSelectedItem()).name : null;

        fromCombo.removeAllItems();
        toCombo.removeAllItems();

        for (int i = 1; i < FlightNetworkData.VertexNames.size(); i++) {
            String name = FlightNetworkData.VertexNames.get(i);
            if (name != null && !name.isEmpty()) {
                AirportItem item = new AirportItem(i, name);
                fromCombo.addItem(item);
                toCombo.addItem(item);
            }
        }

        if (previousFrom != null) {
            for (int i = 0; i < fromCombo.getItemCount(); i++) {
                if (fromCombo.getItemAt(i).name.equals(previousFrom)) {
                    fromCombo.setSelectedIndex(i);
                    break;
                }
            }
        }
        if (previousTo != null) {
            for (int i = 0; i < toCombo.getItemCount(); i++) {
                if (toCombo.getItemAt(i).name.equals(previousTo)) {
                    toCombo.setSelectedIndex(i);
                    break;
                }
            }
        }
    }

    private int getIdFromAirportItem(AirportItem item) {
        return (item != null) ? item.id : -1;
    }

    // =========================================================
    // TABLE / SELECTION HELPERS
    // =========================================================
    private void refreshTable() {
        tableModel.setRowCount(0);
        refreshAirportCombos();

        int n = FlightNetworkData.VertexNames.size();
        for (int i = 1; i < n; i++) {
            for (int j = 1; j < n; j++) {
                if (FlightNetworkData.DistanceMatrix[i][j] > 0) {
                    String flightNo = FlightNetworkData.FlightNumbers[i][j];
                    if (flightNo == null) flightNo = "";
                    tableModel.addRow(new Object[]{
                            new AirportCell(i, FlightNetworkData.VertexCodes.get(i)),
                            new AirportCell(j, FlightNetworkData.VertexCodes.get(j)),
                            flightNo,
                            FlightNetworkData.DistanceMatrix[i][j],
                            FlightNetworkData.TimeMatrix[i][j]
                    });
                }
            }
        }
    }

    private int[] getSelectedPair() {
        int row = table.getSelectedRow();
        if (row == -1) {
            showStatus("Please select a route from the table first.", ERROR);
            return null;
        }
        AirportCell fromCell = (AirportCell) tableModel.getValueAt(row, 0);
        AirportCell toCell = (AirportCell) tableModel.getValueAt(row, 1);
        if (fromCell == null || toCell == null) {
            showStatus("Invalid route data.", ERROR);
            return null;
        }
        return new int[]{fromCell.id, toCell.id};
    }

    private void updateSelectionState() {
        int row = table.getSelectedRow();
        boolean hasSelection = (row != -1);

        addFormPanel.setVisible(!hasSelection);
        selectPanel.setVisible(hasSelection);

        if (hasSelection) {
            AirportCell fromCell = (AirportCell) tableModel.getValueAt(row, 0);
            AirportCell toCell = (AirportCell) tableModel.getValueAt(row, 1);
            String flightNo = (String) tableModel.getValueAt(row, 2);
            double dist = (double) tableModel.getValueAt(row, 3);
            double time = (double) tableModel.getValueAt(row, 4);

            selectionLabel.setText(fromCell.toString() + "  →  " + toCell.toString());
            selectionLabel.setForeground(TEXT);
            updateFlightNumberField.setText(flightNo);
            updateDistanceField.setText(String.valueOf(dist));
            updateTimeField.setText(String.valueOf(time));

            updateButton.setEnabled(true);
            removeButton.setEnabled(true);
        } else {
            updateFlightNumberField.setText("");
            updateDistanceField.setText("");
            updateTimeField.setText("");
            updateButton.setEnabled(false);
            removeButton.setEnabled(false);
        }

        addFormPanel.revalidate();
        addFormPanel.repaint();
        selectPanel.revalidate();
        selectPanel.repaint();
    }

    private void showStatus(String msg, Color color) {
        statusLabel.setText(msg);
        statusLabel.setForeground(color);
    }

    // =========================================================
    // ACTIONS
    // =========================================================
    private void handleAddEdge() {
        if (fromCombo.getItemCount() < 2) {
            showStatus("Need at least 2 airports.", ERROR);
            return;
        }

        AirportItem fromItem = fromCombo.getItemAt(fromCombo.getSelectedIndex());
        AirportItem toItem = toCombo.getItemAt(toCombo.getSelectedIndex());
        int from = getIdFromAirportItem(fromItem);
        int to = getIdFromAirportItem(toItem);

        if (!verifyEdge(from, to)) return;

        if (FlightNetworkData.DistanceMatrix[from][to] > 0) {
            showStatus("Route already exists. Use Update.", ERROR);
            return;
        }

        String flightNo = flightNumberField.getText().trim();
        if (flightNo.isEmpty()) {
            showStatus("Please enter Flight Number.", ERROR);
            return;
        }

        Double distance = parsePositiveDouble(distanceField.getText(), "distance");
        if (distance == null) return;
        Double time = parsePositiveDouble(timeField.getText(), "flight time");
        if (time == null) return;

        FlightNetworkData.DistanceMatrix[from][to] = distance;
        FlightNetworkData.TimeMatrix[from][to] = time;
        FlightNetworkData.FlightNumbers[from][to] = flightNo;

        flightNumberField.setText("");
        distanceField.setText("");
        timeField.setText("");

        refreshTable();
        table.clearSelection();
        showStatus("Route added: " + FlightNetworkData.VertexNames.get(from)
                + " -> " + FlightNetworkData.VertexNames.get(to) + " (" + flightNo + ")", SUCCESS);
        FlightNetworkData.save();
    }

    private void handleRemoveEdge() {
        int[] pair = getSelectedPair();
        if (pair == null) return;
        int from = pair[0];
        int to = pair[1];

        int confirm = JOptionPane.showConfirmDialog(this,
                "Remove route from " + FlightNetworkData.VertexNames.get(from)
                        + " to " + FlightNetworkData.VertexNames.get(to) + "?",
                "Confirm", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        FlightNetworkData.DistanceMatrix[from][to] = 0;
        FlightNetworkData.TimeMatrix[from][to] = 0;
        FlightNetworkData.FlightNumbers[from][to] = null;

        refreshTable();
        table.clearSelection();
        showStatus("Route removed.", SUCCESS);
        FlightNetworkData.save();
    }

    private void handleUpdateEdge() {
        int[] pair = getSelectedPair();
        if (pair == null) return;
        int from = pair[0];
        int to = pair[1];

        String newFlight = updateFlightNumberField.getText().trim();
        if (newFlight.isEmpty()) {
            showStatus("Please enter new Flight Number.", ERROR);
            return;
        }

        Double distance = parsePositiveDouble(updateDistanceField.getText(), "distance");
        if (distance == null) return;
        Double time = parsePositiveDouble(updateTimeField.getText(), "flight time");
        if (time == null) return;

        FlightNetworkData.DistanceMatrix[from][to] = distance;
        FlightNetworkData.TimeMatrix[from][to] = time;
        FlightNetworkData.FlightNumbers[from][to] = newFlight;

        refreshTable();
        table.clearSelection();
        showStatus("Route updated.", SUCCESS);
        FlightNetworkData.save();
    }

    private boolean verifyEdge(int from, int to) {
        if (!FlightNetworkData.isValidId(from) || !FlightNetworkData.isValidId(to)) {
            showStatus("Invalid airport ID.", ERROR);
            return false;
        }
        if (from == to) {
            showStatus("From and To must be different.", ERROR);
            return false;
        }
        return true;
    }

    private Double parsePositiveDouble(String text, String fieldName) {
        try {
            double val = Double.parseDouble(text.trim());
            if (val <= 0) {
                showStatus("Enter positive " + fieldName + ".", ERROR);
                return null;
            }
            return val;
        } catch (NumberFormatException e) {
            showStatus("Enter valid number for " + fieldName + ".", ERROR);
            return null;
        }
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

        JLabel right = new JLabel("Flight Route (Edge) Management");
        right.setFont(new Font("SansSerif", Font.PLAIN, 11));
        right.setForeground(new Color(180, 190, 205));

        footer.add(left, BorderLayout.WEST);
        footer.add(right, BorderLayout.EAST);
        return footer;
    }
}