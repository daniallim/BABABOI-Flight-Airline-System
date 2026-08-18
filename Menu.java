package com.bababoi.airline.java;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Menu extends JFrame {

    // =========================================================
    // COLOURS
    // =========================================================

    private final Color NAVY = new Color(20, 35, 60);
    private final Color DARK_NAVY = new Color(15, 27, 48);

    private final Color BLUE = new Color(0, 122, 255);

    private final Color BACKGROUND =
            new Color(243, 246, 250);

    private final Color CARD =
            new Color(255, 255, 255);

    private final Color TEXT =
            new Color(30, 35, 45);

    private final Color SECONDARY_TEXT =
            new Color(105, 115, 130);

    private final Color BORDER =
            new Color(220, 225, 232);


    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public Menu() {

        setTitle("BABABOI Airline Flight System");

       // =====================================================
// WINDOW SETTINGS
// =====================================================

setDefaultCloseOperation(
        JFrame.EXIT_ON_CLOSE
);

// Allow user to resize window
setResizable(true);

// Open window maximized
setExtendedState(
        JFrame.MAXIMIZED_BOTH
);


        // =====================================================
        // ROOT PANEL
        // =====================================================

        JPanel root =
                new JPanel(
                        new BorderLayout()
                );

        root.setBackground(
                BACKGROUND
        );


        // =====================================================
        // ADD HEADER
        // =====================================================

        root.add(
                createHeader(),
                BorderLayout.NORTH
        );


        // =====================================================
        // ADD MAIN CONTENT
        // =====================================================

        root.add(
                createMainContent(),
                BorderLayout.CENTER
        );


        // =====================================================
        // ADD FOOTER
        // =====================================================

        root.add(
                createFooter(),
                BorderLayout.SOUTH
        );


        add(root);
    }


    // =========================================================
    // HEADER
    // =========================================================

    private JPanel createHeader() {

        JPanel header =
                new JPanel();

        header.setBackground(
                NAVY
        );

        header.setLayout(
                new BoxLayout(
                        header,
                        BoxLayout.Y_AXIS
                )
        );

        header.setBorder(
                new EmptyBorder(
                        28,
                        20,
                        28,
                        20
                )
        );


        // =====================================================
        // MAIN TITLE
        // =====================================================

        JLabel title =
                new JLabel(
                        "BABABOI Airline Flight System"
                );

        title.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        32
                )
        );

        title.setForeground(
                Color.WHITE
        );

        title.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );


        // =====================================================
        // SUBTITLE
        // =====================================================

        JLabel subtitle =
                new JLabel(
                        "Main Menu"
                );

        subtitle.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        15
                )
        );

        subtitle.setForeground(
                new Color(
                        190,
                        200,
                        215
                )
        );

        subtitle.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );


        // =====================================================
        // ADD TO HEADER
        // =====================================================

        header.add(title);

        header.add(
                Box.createVerticalStrut(5)
        );

        header.add(subtitle);


        return header;
    }


    // =========================================================
    // MAIN CONTENT
    // =========================================================

    private JScrollPane createMainContent() {

        JPanel container =
                new JPanel(
                        new GridBagLayout()
                );

        container.setBackground(
                BACKGROUND
        );

        container.setBorder(
                new EmptyBorder(
                        35,
                        60,
                        35,
                        60
                )
        );


        // =====================================================
        // MAIN CARD
        // =====================================================

        JPanel card =
                new JPanel();

        card.setBackground(
                CARD
        );

        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                BORDER,
                                1
                        ),
                        new EmptyBorder(
                                30,
                                35,
                                30,
                                35
                        )
                )
        );

        card.setLayout(
                new BoxLayout(
                        card,
                        BoxLayout.Y_AXIS
                )
        );


        // =====================================================
        // CARD TITLE
        // =====================================================

        JLabel welcome =
                new JLabel(
                        "Flight Network Management"
                );

        welcome.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        25
                )
        );

        welcome.setForeground(
                TEXT
        );

        welcome.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );


        // =====================================================
        // CARD DESCRIPTION
        // =====================================================

        JLabel description =
                new JLabel(
                        "Select an option below to manage and analyse the airline flight network."
                );

        description.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        14
                )
        );

        description.setForeground(
                SECONDARY_TEXT
        );

        description.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );


        // =====================================================
        // ADD TITLE
        // =====================================================

        card.add(welcome);

        card.add(
                Box.createVerticalStrut(7)
        );

        card.add(description);

        card.add(
                Box.createVerticalStrut(25)
        );


        // =====================================================
        // CREATE BUTTONS
        // =====================================================

        JButton networkButton =
                createMenuButton(
                        "✈",
                        "View Airline Flight Network",
                        "View all airports and direct flight connections"
                );


        JButton vertexButton =
                createMenuButton(
                        "●",
                        "Manage Vertex (Airport)",
                        "Add, remove and manage airport vertices"
                );


        JButton edgeButton =
                createMenuButton(
                        "↔",
                        "Manage Edge (Flight)",
                        "Add, remove and manage flight connections"
                );


        JButton dfsButton =
                createMenuButton(
                        "⇩",
                        "DFS Search Route",
                        "Search routes using Depth-First Search"
                );


        JButton bfsButton =
                createMenuButton(
                        "⇩",
                        "BFS Search Route",
                        "Search routes using Breadth-First Search"
                );


        JButton dijkstraButton =
                createMenuButton(
                        "⌁",
                        "Dijkstra's Shortest Path",
                        "Find the shortest route between airports"
                );


        // =====================================================
        // ADD BUTTONS
        // =====================================================

        card.add(networkButton);

        card.add(
                Box.createVerticalStrut(10)
        );

        card.add(vertexButton);

        card.add(
                Box.createVerticalStrut(10)
        );

        card.add(edgeButton);

        card.add(
                Box.createVerticalStrut(10)
        );

        card.add(dfsButton);

        card.add(
                Box.createVerticalStrut(10)
        );

        card.add(bfsButton);

        card.add(
                Box.createVerticalStrut(10)
        );

        card.add(dijkstraButton);


        // =====================================================
        // SEPARATOR
        // =====================================================

        card.add(
                Box.createVerticalStrut(25)
        );

        JSeparator separator =
                new JSeparator();

        separator.setForeground(
                BORDER
        );

        separator.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        card.add(separator);


        card.add(
                Box.createVerticalStrut(18)
        );


        // =====================================================
        // EXIT BUTTON
        // =====================================================

        JButton exitButton =
                createExitButton();

        card.add(exitButton);


        // =====================================================
        // CARD POSITION
        // =====================================================

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;

        gbc.weightx = 1;
        gbc.weighty = 1;

        gbc.fill =
                GridBagConstraints.BOTH;

        gbc.anchor =
                GridBagConstraints.CENTER;


        container.add(
                card,
                gbc
        );


        // =====================================================
        // BUTTON ACTIONS
        // =====================================================

        networkButton.addActionListener(e -> {

            JOptionPane.showMessageDialog(
                    this,
                    "View Airline Flight Network",
                    "Airline Flight Network",
                    JOptionPane.INFORMATION_MESSAGE
            );

        });


        vertexButton.addActionListener(e -> {

            JOptionPane.showMessageDialog(
                    this,
                    "Manage Vertex (Airport)",
                    "Vertex Management",
                    JOptionPane.INFORMATION_MESSAGE
            );

        });


        edgeButton.addActionListener(e -> {

            JOptionPane.showMessageDialog(
                    this,
                    "Manage Edge (Flight)",
                    "Edge Management",
                    JOptionPane.INFORMATION_MESSAGE
            );

        });


        dfsButton.addActionListener(e -> {

            JOptionPane.showMessageDialog(
                    this,
                    "DFS Search Route",
                    "Depth-First Search",
                    JOptionPane.INFORMATION_MESSAGE
            );

        });


        bfsButton.addActionListener(e -> {

            JOptionPane.showMessageDialog(
                    this,
                    "BFS Search Route",
                    "Breadth-First Search",
                    JOptionPane.INFORMATION_MESSAGE
            );

        });


        dijkstraButton.addActionListener(e -> {

            JOptionPane.showMessageDialog(
                    this,
                    "Dijkstra's Shortest Path",
                    "Dijkstra Algorithm",
                    JOptionPane.INFORMATION_MESSAGE
            );

        });


        // =====================================================
        // EXIT ACTION
        // =====================================================

        exitButton.addActionListener(e -> {

            int result =
                    JOptionPane.showConfirmDialog(
                            this,
                            "Are you sure you want to exit?",
                            "Exit Application",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE
                    );

            if (result ==
                    JOptionPane.YES_OPTION) {

                System.exit(0);
            }

        });


        // =====================================================
        // CREATE SCROLL PANE
        // =====================================================

        JScrollPane scrollPane =
                new JScrollPane(
                        container
                );

        scrollPane.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
        );

        scrollPane.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );

        scrollPane.setBorder(null);

        // Mouse wheel scrolling speed
        scrollPane.getVerticalScrollBar()
                .setUnitIncrement(16);


        return scrollPane;
    }


    // =========================================================
    // CREATE MENU BUTTON
    // =========================================================

    private JButton createMenuButton(
            String icon,
            String title,
            String description
    ) {

        JButton button =
                new JButton();


        // =====================================================
        // BUTTON LAYOUT
        // =====================================================

        button.setLayout(
                new BorderLayout(
                        15,
                        0
                )
        );


        // =====================================================
        // BUTTON APPEARANCE
        // =====================================================

        button.setBackground(
                new Color(
                        250,
                        251,
                        253
                )
        );

        button.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                BORDER,
                                1
                        ),
                        new EmptyBorder(
                                12,
                                18,
                                12,
                                18
                        )
                )
        );

        button.setFocusPainted(false);

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );


        // =====================================================
        // ICON
        // =====================================================

        JLabel iconLabel =
                new JLabel(icon);

        iconLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        22
                )
        );

        iconLabel.setForeground(
                BLUE
        );

        iconLabel.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        iconLabel.setPreferredSize(
                new Dimension(
                        45,
                        45
                )
        );


        // =====================================================
        // TEXT PANEL
        // =====================================================

        JPanel textPanel =
                new JPanel();

        textPanel.setOpaque(false);

        textPanel.setLayout(
                new BoxLayout(
                        textPanel,
                        BoxLayout.Y_AXIS
                )
        );


        // =====================================================
        // BUTTON TITLE
        // =====================================================

        JLabel titleLabel =
                new JLabel(title);

        titleLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        15
                )
        );

        titleLabel.setForeground(
                TEXT
        );


        // =====================================================
        // BUTTON DESCRIPTION
        // =====================================================

        JLabel descriptionLabel =
                new JLabel(description);

        descriptionLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        12
                )
        );

        descriptionLabel.setForeground(
                SECONDARY_TEXT
        );


        textPanel.add(
                titleLabel
        );

        textPanel.add(
                Box.createVerticalStrut(3)
        );

        textPanel.add(
                descriptionLabel
        );


        // =====================================================
        // ARROW
        // =====================================================

        JLabel arrow =
                new JLabel("›");

        arrow.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        25
                )
        );

        arrow.setForeground(
                new Color(
                        150,
                        160,
                        175
                )
        );


        // =====================================================
        // ADD COMPONENTS
        // =====================================================

        button.add(
                iconLabel,
                BorderLayout.WEST
        );

        button.add(
                textPanel,
                BorderLayout.CENTER
        );

        button.add(
                arrow,
                BorderLayout.EAST
        );


        // =====================================================
        // BUTTON SIZE
        // =====================================================

        button.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        button.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        72
                )
        );

        button.setPreferredSize(
                new Dimension(
                        600,
                        72
                )
        );


        // =====================================================
        // HOVER EFFECT
        // =====================================================

        button.addMouseListener(
                new MouseAdapter() {

                    @Override
                    public void mouseEntered(
                            MouseEvent e
                    ) {

                        button.setBackground(
                                new Color(
                                        240,
                                        246,
                                        255
                                )
                        );

                        button.setBorder(
                                BorderFactory.createCompoundBorder(
                                        BorderFactory.createLineBorder(
                                                BLUE,
                                                1
                                        ),
                                        new EmptyBorder(
                                                12,
                                                18,
                                                12,
                                                18
                                        )
                                )
                        );
                    }


                    @Override
                    public void mouseExited(
                            MouseEvent e
                    ) {

                        button.setBackground(
                                new Color(
                                        250,
                                        251,
                                        253
                                )
                        );

                        button.setBorder(
                                BorderFactory.createCompoundBorder(
                                        BorderFactory.createLineBorder(
                                                BORDER,
                                                1
                                        ),
                                        new EmptyBorder(
                                                12,
                                                18,
                                                12,
                                                18
                                        )
                                )
                        );
                    }
                }
        );


        return button;
    }


    // =========================================================
    // EXIT BUTTON
    // =========================================================

    private JButton createExitButton() {

        JButton button =
                new JButton(
                        "Exit Application"
                );


        button.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        14
                )
        );


        button.setForeground(
                new Color(
                        210,
                        65,
                        65
                )
        );


        button.setBackground(
                Color.WHITE
        );


        button.setFocusPainted(false);


        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );


        button.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(
                                        230,
                                        205,
                                        205
                                )
                        ),
                        new EmptyBorder(
                                12,
                                20,
                                12,
                                20
                        )
                )
        );


        button.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );


        button.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        48
                )
        );


        // =====================================================
        // HOVER
        // =====================================================

        button.addMouseListener(
                new MouseAdapter() {

                    @Override
                    public void mouseEntered(
                            MouseEvent e
                    ) {

                        button.setBackground(
                                new Color(
                                        255,
                                        242,
                                        242
                                )
                        );
                    }


                    @Override
                    public void mouseExited(
                            MouseEvent e
                    ) {

                        button.setBackground(
                                Color.WHITE
                        );
                    }
                }
        );


        return button;
    }


    // =========================================================
    // FOOTER
    // =========================================================

    private JPanel createFooter() {

        JPanel footer =
                new JPanel(
                        new BorderLayout()
                );


        footer.setBackground(
                DARK_NAVY
        );


        footer.setBorder(
                new EmptyBorder(
                        10,
                        30,
                        10,
                        30
                )
        );


        // =====================================================
        // LEFT FOOTER
        // =====================================================

        JLabel left =
                new JLabel(
                        "BABABOI Airline Flight System"
                );


        left.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        11
                )
        );


        left.setForeground(
                new Color(
                        180,
                        190,
                        205
                )
        );


        // =====================================================
        // RIGHT FOOTER
        // =====================================================

        JLabel right =
                new JLabel(
                        "Flight Network Management"
                );


        right.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        11
                )
        );


        right.setForeground(
                new Color(
                        180,
                        190,
                        205
                )
        );


        // =====================================================
        // ADD FOOTER COMPONENTS
        // =====================================================

        footer.add(
                left,
                BorderLayout.WEST
        );


        footer.add(
                right,
                BorderLayout.EAST
        );


        return footer;
    }


    // =========================================================
    // MAIN METHOD
    // =========================================================

    public static void main(
            String[] args
    ) {

        // =====================================================
        // SYSTEM LOOK AND FEEL
        // =====================================================

        try {

            UIManager.setLookAndFeel(
                    UIManager
                            .getSystemLookAndFeelClassName()
            );

        } catch (Exception e) {

            e.printStackTrace();
        }


        // =====================================================
        // START APPLICATION
        // =====================================================

        SwingUtilities.invokeLater(() -> {

            Menu menu =
                    new Menu();

            menu.setVisible(true);
        });
    }
}