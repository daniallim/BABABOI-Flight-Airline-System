package com.bababoi.airline.java;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Login extends JFrame {

    // COLOURS
    private final Color NAVY = new Color(20, 35, 60);
    private final Color DARK_NAVY = new Color(15, 27, 48);
    private final Color BLUE = new Color(0, 122, 255);
    private final Color BACKGROUND = new Color(243, 246, 250);
    private final Color TEXT = new Color(30, 35, 45);
    private final Color SECONDARY_TEXT = new Color(105, 115, 130);
    private final Color PLACEHOLDER = new Color(150, 155, 165);
    private final Color BORDER = new Color(220, 225, 232);
    private final Color ERROR = new Color(210, 65, 65);


    // DEFAULT STAFF ACCOUNT
    private final String DEFAULT_STAFF_ID = "staff001";
    private final String DEFAULT_PASSWORD = "bababoi123";

    // COMPONENTS
    private JTextField staffIdField;
    private JPasswordField passwordField;
    private JLabel messageLabel;
    private boolean passwordPlaceholder = true;


    // CONSTRUCTOR
    public Login() {
        setTitle("BABABOI Airline Flight System - Staff Login");
        setDefaultCloseOperation(
        JFrame.EXIT_ON_CLOSE
        );
    setResizable(true);

    // Open Login maximized
    setExtendedState(JFrame.MAXIMIZED_BOTH);


        // ROOT PANEL
        JPanel root = new JPanel( new BorderLayout() );
        root.setBackground( BACKGROUND );
        root.add(createHeader(), BorderLayout.NORTH);
        root.add(createLoginArea(), BorderLayout.CENTER);
        root.add(createFooter(), BorderLayout.SOUTH);
        add(root);
    }


    // HEADER
    private JPanel createHeader() {

        JPanel header = new JPanel(new GridBagLayout()
                );

        header.setBackground(
                NAVY
        );

        header.setBorder(
                new EmptyBorder(
                        25,
                        20,
                        25,
                        20
                )
        );


        JPanel titlePanel =
                new JPanel();

        titlePanel.setOpaque(false);

        titlePanel.setLayout(
                new BoxLayout(
                        titlePanel,
                        BoxLayout.Y_AXIS
                )
        );


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


        JLabel subtitle =
                new JLabel(
                        "Staff Login"
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


        titlePanel.add(title);

        titlePanel.add(
                Box.createVerticalStrut(5)
        );

        titlePanel.add(subtitle);


        header.add(titlePanel);


        return header;
    }


    // LOGIN AREA
    private JPanel createLoginArea() {

        JPanel area =
                new JPanel(
                        new GridBagLayout()
                );

        area.setBackground(
                BACKGROUND
        );


        // CARD
        JPanel card =
                new JPanel(
                        new GridBagLayout()
                );

        card.setBackground(
                Color.WHITE
        );

        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                BORDER,
                                1
                        ),
                        new EmptyBorder(
                                35,
                                50,
                                35,
                                50
                        )
                )
        );


        // CARD CONSTRAINT
        GridBagConstraints cardGbc =
                new GridBagConstraints();

        cardGbc.gridx = 0;

        cardGbc.weightx = 1;

        cardGbc.fill =
                GridBagConstraints.HORIZONTAL;


        // WELCOME TITLE
        JLabel welcome =
                new JLabel(
                        "Welcome Back",
                        SwingConstants.CENTER
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


        cardGbc.gridy = 0;

        card.add(
                welcome,
                cardGbc
        );


        // DESCRIPTION
        JLabel description =
                new JLabel(
                        "Sign in to access the flight network system",
                        SwingConstants.CENTER
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


        cardGbc.gridy = 1;

        cardGbc.insets =
                new Insets(
                        7,
                        0,
                        30,
                        0
                );


        card.add(
                description,
                cardGbc
        );



        // FORM PANEL
        JPanel form =
                new JPanel(
                        new GridBagLayout()
                );

        form.setOpaque(false);


        form.setPreferredSize(
                new Dimension(
                        500,
                        250
                )
        );


        // FORM CONSTRAINT
        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.gridx = 0;

        gbc.weightx = 1;

        gbc.fill =
                GridBagConstraints.HORIZONTAL;


        // STAFF ID LABEL
        JLabel staffLabel =
                new JLabel(
                        "Staff ID"
                );

        staffLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        14
                )
        );

        staffLabel.setForeground(
                TEXT
        );


        gbc.gridy = 0;

        gbc.insets =
                new Insets(
                        0,
                        0,
                        7,
                        0
                );


        form.add(
                staffLabel,
                gbc
        );


        // STAFF ID FIELD
        staffIdField =
                createStaffIdField();


        gbc.gridy = 1;

        gbc.insets =
                new Insets(
                        0,
                        0,
                        20,
                        0
                );


        form.add(
                staffIdField,
                gbc
        );


        // PASSWORD LABEL
        JLabel passwordLabel =
                new JLabel(
                        "Password"
                );

        passwordLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        14
                )
        );

        passwordLabel.setForeground(
                TEXT
        );


        gbc.gridy = 2;

        gbc.insets =
                new Insets(
                        0,
                        0,
                        7,
                        0
                );


        form.add(
                passwordLabel,
                gbc
        );


        // PASSWORD PANEL
        JPanel passwordPanel =
                createPasswordPanel();


        gbc.gridy = 3;

        gbc.insets =
                new Insets(
                        0,
                        0,
                        10,
                        0
                );


        form.add(
                passwordPanel,
                gbc
        );


        // MESSAGE
        messageLabel =
                new JLabel(
                        " ",
                        SwingConstants.CENTER
                );

        messageLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        12
                )
        );

        messageLabel.setForeground(
                ERROR
        );


        gbc.gridy = 4;

        gbc.insets =
                new Insets(
                        0,
                        0,
                        10,
                        0
                );


        form.add(
                messageLabel,
                gbc
        );


        // BUTTONS
        JPanel buttonPanel =
                createButtonPanel();


        gbc.gridy = 5;

        gbc.insets =
                new Insets(
                        0,
                        0,
                        0,
                        0
                );


        form.add(
                buttonPanel,
                gbc
        );


        // ADD FORM TO CARD
        cardGbc.gridy = 2;

        cardGbc.insets =
                new Insets(
                        0,
                        0,
                        0,
                        0
                );


        card.add(
                form,
                cardGbc
        );


        // STAFF NOTICE
        JLabel notice =
                new JLabel(
                        "Staff access only",
                        SwingConstants.CENTER
                );

        notice.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        12
                )
        );

        notice.setForeground(
                SECONDARY_TEXT
        );


        cardGbc.gridy = 3;

        cardGbc.insets =
                new Insets(
                        20,
                        0,
                        0,
                        0
                );


        card.add(
                notice,
                cardGbc
        );



        // ADD CARD TO AREA
        GridBagConstraints areaGbc =
                new GridBagConstraints();

        areaGbc.gridx = 0;

        areaGbc.gridy = 0;

        areaGbc.weightx = 1;

        areaGbc.weighty = 1;

        areaGbc.fill =
                GridBagConstraints.NONE;

        areaGbc.anchor =
                GridBagConstraints.CENTER;


        area.add(
                card,
                areaGbc
        );


        return area;
    }



    // STAFF ID FIELD
    private JTextField createStaffIdField() {

        JTextField field =
                new JTextField();


        field.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        15
                )
        );


        field.setForeground(
                PLACEHOLDER
        );


        field.setBackground(
                new Color(
                        250,
                        251,
                        253
                )
        );


        field.setText(
                "Example: staff001"
        );


        field.setPreferredSize(
                new Dimension(
                        500,
                        48
                )
        );


        field.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                BORDER,
                                1
                        ),
                        new EmptyBorder(
                                10,
                                12,
                                10,
                                12
                        )
                )
        );


        field.addFocusListener(
                new FocusAdapter() {

                    @Override
                    public void focusGained(
                            FocusEvent e
                    ) {

                        if (field.getText().equals(
                                "Example: staff001"
                        )) {

                            field.setText("");

                            field.setForeground(
                                    TEXT
                            );
                        }

                        setFocusBorder(field);
                    }


                    @Override
                    public void focusLost(
                            FocusEvent e
                    ) {

                        if (field.getText().trim().isEmpty()) {

                            field.setText(
                                    "Example: staff001"
                            );

                            field.setForeground(
                                    PLACEHOLDER
                            );
                        }

                        setNormalBorder(field);
                    }
                }
        );


        return field;
    }


    // PASSWORD PANEL
    private JPanel createPasswordPanel() {

        JPanel panel =
                new JPanel(
                        new BorderLayout()
                );


        panel.setPreferredSize(
                new Dimension(
                        500,
                        48
                )
        );


        panel.setBackground(
                new Color(
                        250,
                        251,
                        253
                )
        );


        panel.setBorder(
                BorderFactory.createLineBorder(
                        BORDER,
                        1
                )
        );


        // PASSWORD FIELD
        passwordField =
                new JPasswordField();


        passwordField.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        15
                )
        );


        passwordField.setForeground(
                PLACEHOLDER
        );


        passwordField.setBackground(
                new Color(
                        250,
                        251,
                        253
                )
        );


        passwordField.setBorder(
                BorderFactory.createEmptyBorder(
                        10,
                        12,
                        10,
                        5
                )
        );


        passwordField.setText(
                "Enter your password"
        );


        passwordPlaceholder = true;


        // SHOW BUTTON
        JButton showButton =
                new JButton(
                        "Show"
                );


        showButton.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        12
                )
        );


        showButton.setForeground(
                BLUE
        );


        showButton.setBackground(
                new Color(
                        250,
                        251,
                        253
                )
        );


        showButton.setFocusPainted(
                false
        );


        showButton.setBorder(
                BorderFactory.createEmptyBorder(
                        5,
                        12,
                        5,
                        12
                )
        );


        showButton.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );


        // SHOW / HIDE PASSWORD
        showButton.addActionListener(e -> {

            if (passwordPlaceholder) {
                return;
            }


            if (passwordField.getEchoChar() == 0) {

                passwordField.setEchoChar(
                        '●'
                );

                showButton.setText(
                        "Show"
                );

            } else {

                passwordField.setEchoChar(
                        (char) 0
                );

                showButton.setText(
                        "Hide"
                );
            }
        });



        passwordField.addFocusListener(
                new FocusAdapter() {

                    @Override
                    public void focusGained(
                            FocusEvent e
                    ) {

                        if (passwordPlaceholder) {

                            passwordField.setText("");

                            passwordField.setForeground(
                                    TEXT
                            );

                            passwordField.setEchoChar(
                                    '●'
                            );

                            passwordPlaceholder = false;
                        }


                        panel.setBorder(
                                BorderFactory.createLineBorder(
                                        BLUE,
                                        1
                                )
                        );
                    }


                    @Override
                    public void focusLost(
                            FocusEvent e
                    ) {

                        if (passwordField.getPassword().length == 0) {

                            passwordField.setEchoChar(
                                    (char) 0
                            );

                            passwordField.setText(
                                    "Enter your password"
                            );

                            passwordField.setForeground(
                                    PLACEHOLDER
                            );

                            passwordPlaceholder = true;
                        }


                        panel.setBorder(
                                BorderFactory.createLineBorder(
                                        BORDER,
                                        1
                                )
                        );
                    }
                }
        );


        panel.add(
                passwordField,
                BorderLayout.CENTER
        );


        panel.add(
                showButton,
                BorderLayout.EAST
        );


        return panel;
    }


    // BUTTON PANEL
    private JPanel createButtonPanel() {

        JPanel panel =
                new JPanel(
                        new GridLayout(
                                1,
                                2,
                                12,
                                0
                        )
                );


        panel.setOpaque(false);


        panel.setPreferredSize(
                new Dimension(
                        500,
                        48
                )
        );


        JButton loginButton =
                createLoginButton();


        JButton clearButton =
                createClearButton();


        panel.add(
                loginButton
        );


        panel.add(
                clearButton
        );


        // LOGIN ACTION
        loginButton.addActionListener(
                e -> performLogin()
        );


        // CLEAR ACTION
        clearButton.addActionListener(e -> {

            staffIdField.setText(
                    "Example: staff001"
            );

            staffIdField.setForeground(
                    PLACEHOLDER
            );


            passwordField.setText(
                    "Enter your password"
            );

            passwordField.setForeground(
                    PLACEHOLDER
            );

            passwordField.setEchoChar(
                    (char) 0
            );


            passwordPlaceholder = true;


            messageLabel.setText(
                    " "
            );


            staffIdField.requestFocus();
        });


        return panel;
    }


    // LOGIN BUTTON
    private JButton createLoginButton() {

        JButton button =
                new JButton(
                        "Login"
                );


        button.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        14
                )
        );


        button.setForeground(
                Color.WHITE
        );


        button.setBackground(
                new Color(
                        0,
                        122,
                        255
                )
        );


        button.setOpaque(true);

        button.setContentAreaFilled(true);

        button.setBorderPainted(false);

        button.setFocusPainted(false);


        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );


        button.setBorder(
                BorderFactory.createEmptyBorder(
                        10,
                        20,
                        10,
                        20
                )
        );



        button.addMouseListener(
                new MouseAdapter() {

                    @Override
                    public void mouseEntered(
                            MouseEvent e
                    ) {

                        button.setBackground(
                                new Color(
                                        0,
                                        105,
                                        225
                                )
                        );


                        button.setForeground(
                                Color.WHITE
                        );
                    }


                    @Override
                    public void mouseExited(
                            MouseEvent e
                    ) {

                        button.setBackground(
                                new Color(
                                        0,
                                        122,
                                        255
                                )
                        );


                        button.setForeground(
                                Color.WHITE
                        );
                    }
                }
        );


        return button;
    }



    private JButton createClearButton() {

        JButton button =
                new JButton(
                        "Clear"
                );


        button.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        14
                )
        );


        button.setForeground(
                TEXT
        );


        button.setBackground(
                Color.WHITE
        );


        button.setOpaque(true);

        button.setContentAreaFilled(true);

        button.setFocusPainted(false);


        button.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                BORDER,
                                1
                        ),
                        new EmptyBorder(
                                9,
                                20,
                                9,
                                20
                        )
                )
        );


        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
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
                                        242,
                                        244,
                                        248
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


    // LOGIN VALIDATION
    private void performLogin() {

        String staffId =
                staffIdField
                        .getText()
                        .trim();


        if (staffId.equals(
                "Example: staff001"
        )) {

            staffId = "";
        }


        String password = "";


        if (!passwordPlaceholder) {

            password =
                    new String(
                            passwordField.getPassword()
                    );
        }


        // STAFF ID VALIDATION
        if (staffId.isEmpty()) {

            messageLabel.setForeground(
                    ERROR
            );


            messageLabel.setText(
                    "Please enter your Staff ID."
            );


            staffIdField.requestFocus();


            return;
        }


        // PASSWORD VALIDATION
        if (password.isEmpty()) {

            messageLabel.setForeground(
                    ERROR
            );


            messageLabel.setText(
                    "Please enter your password."
            );


            passwordField.requestFocus();


            return;
        }


        // LOGIN CHECK
        if (staffId.equals(
                DEFAULT_STAFF_ID
        )
                && password.equals(
                        DEFAULT_PASSWORD
                )) {

            // DIRECTLY OPEN MAIN MENU
            openMainMenu();

        } else {

            messageLabel.setForeground(
                    ERROR
            );


            messageLabel.setText(
                    "Invalid Staff ID or password."
            );


            passwordField.setText("");


            passwordPlaceholder = false;


            passwordField.requestFocus();
        }
    }



    private void openMainMenu() {

        Menu mainMenu =
                new Menu();


        mainMenu.setVisible(
                true
        );


        dispose();
    }



    private void setFocusBorder(
            JTextField field
    ) {

        field.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                BLUE,
                                1
                        ),
                        new EmptyBorder(
                                10,
                                12,
                                10,
                                12
                        )
                )
        );
    }



    private void setNormalBorder(
            JTextField field
    ) {

        field.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                BORDER,
                                1
                        ),
                        new EmptyBorder(
                                10,
                                12,
                                10,
                                12
                        )
                )
        );
    }


    // FOOTER
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


        JLabel right =
                new JLabel(
                        "Authorised Staff Access"
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


    // MAIN
    public static void main(
            String[] args
    ) {

        try {

            UIManager.setLookAndFeel(
                    UIManager
                            .getSystemLookAndFeelClassName()
            );

        } catch (Exception e) {

            e.printStackTrace();
        }


        SwingUtilities.invokeLater(() -> {

            Login login =
                    new Login();


            login.setVisible(
                    true
            );
        });
    }
}