package com.techwebdocs.main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.List;

public class AirlineFlightNetwork extends JFrame {

    private final Menu parentMenu;
    private MapPanel mapPanel;
    private JButton addButton, removeButton, backButton;
    private JLabel statusLabel;

    private static final Map<String, Point2D.Double> AIRPORT_COORDS = new HashMap<>();
    private static final String COORDS_FILE = "airport_coords.csv";

    public AirlineFlightNetwork(Menu parent) {
        this.parentMenu = parent;
        setTitle("Airline Flight Network - Malaysia");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout());

        loadCoords();

        mapPanel = new MapPanel();
        add(mapPanel, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        backButton = new JButton("Back");
        backButton.addActionListener(e -> goBack());
        controlPanel.add(backButton);

        addButton = new JButton("Add Airport to Map");
        addButton.addActionListener(e -> addAirportToMap());
        controlPanel.add(addButton);

        removeButton = new JButton("Remove Airport from Map");
        removeButton.addActionListener(e -> removeAirportFromMap());
        controlPanel.add(removeButton);

        statusLabel = new JLabel(" ");
        controlPanel.add(statusLabel);

        add(controlPanel, BorderLayout.SOUTH);

        mapPanel.loadMapImage();
        mapPanel.repaint();
    }

    private void goBack() {
        if (parentMenu != null) parentMenu.setVisible(true);
        dispose();
    }

    private void addAirportToMap() {
        List<String> codes = new ArrayList<>();
        for (int i = 1; i < FlightNetworkData.VertexCodes.size(); i++) {
            String code = FlightNetworkData.VertexCodes.get(i);
            if (code != null && !code.trim().isEmpty()) {
                codes.add(code);
            }
        }
        if (codes.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No airports available.");
            return;
        }
        String selected = (String) JOptionPane.showInputDialog(this,
                "Select airport code to place on map:",
                "Add Airport",
                JOptionPane.PLAIN_MESSAGE,
                null,
                codes.toArray(),
                codes.get(0));
        if (selected != null) {
            if (AIRPORT_COORDS.containsKey(selected)) {
                JOptionPane.showMessageDialog(this,
                        "Airport " + selected + " already has coordinates.\nUse Remove first if you want to reposition.");
                return;
            }
            JOptionPane.showMessageDialog(this,
                    "Click on the map to set the location for airport " + selected + ".",
                    "Set Location",
                    JOptionPane.INFORMATION_MESSAGE);
            mapPanel.setPendingAirport(selected);
        }
    }

    private void removeAirportFromMap() {
        if (AIRPORT_COORDS.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No airports on map to remove.");
            return;
        }
        String[] codes = AIRPORT_COORDS.keySet().toArray(new String[0]);
        String selected = (String) JOptionPane.showInputDialog(this,
                "Select airport to remove from map:",
                "Remove Airport",
                JOptionPane.PLAIN_MESSAGE,
                null,
                codes,
                codes[0]);
        if (selected != null) {
            AIRPORT_COORDS.remove(selected);
            saveCoords();
            mapPanel.repaint();
            JOptionPane.showMessageDialog(this, "Airport " + selected + " removed from map.");
        }
    }

    private void loadCoords() {
        Path path = Paths.get(COORDS_FILE);
        if (!Files.exists(path)) return;
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String code = parts[0].trim();
                    double lon = Double.parseDouble(parts[1].trim());
                    double lat = Double.parseDouble(parts[2].trim());
                    AIRPORT_COORDS.put(code, new Point2D.Double(lon, lat));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveCoords() {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(COORDS_FILE))) {
            for (Map.Entry<String, Point2D.Double> entry : AIRPORT_COORDS.entrySet()) {
                writer.write(entry.getKey() + "," + entry.getValue().getX() + "," + entry.getValue().getY());
                writer.newLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // =========================================================
    // 地图面板（核心绘图）
    // =========================================================
    private class MapPanel extends JPanel {
        private BufferedImage mapImage;
        private double scale = 1.0;
        private int offsetX = 0, offsetY = 0;
        private Point dragStart;
        private String pendingAirport = null;

        private final double MIN_LON = 98.0;
        private final double MAX_LON = 120.0;
        private final double MIN_LAT = 0.5;
        private final double MAX_LAT = 8.0;

        public MapPanel() {
            setBackground(Color.WHITE);
            setPreferredSize(new Dimension(1200, 800));

            addMouseWheelListener(e -> {
                if (e.getWheelRotation() < 0) scale *= 1.1;
                else scale *= 0.9;
                if (scale < 0.1) scale = 0.1;
                if (scale > 10) scale = 10;
                repaint();
            });

            addMouseListener(new MouseAdapter() {
                @Override public void mousePressed(MouseEvent e) { dragStart = e.getPoint(); }
                @Override public void mouseReleased(MouseEvent e) { dragStart = null; }
                @Override public void mouseClicked(MouseEvent e) {
                    if (pendingAirport != null) {
                        Point p = e.getPoint();
                        double imgX = (p.getX() - offsetX) / scale;
                        double imgY = (p.getY() - offsetY) / scale;
                        double lon = MIN_LON + (imgX / getImageWidth()) * (MAX_LON - MIN_LON);
                        double lat = MAX_LAT - (imgY / getImageHeight()) * (MAX_LAT - MIN_LAT);
                        AIRPORT_COORDS.put(pendingAirport, new Point2D.Double(lon, lat));
                        saveCoords();
                        JOptionPane.showMessageDialog(AirlineFlightNetwork.this,
                                "Airport " + pendingAirport + " placed at (lon=" + lon + ", lat=" + lat + ")");
                        pendingAirport = null;
                        repaint();
                    }
                }
            });
            addMouseMotionListener(new MouseAdapter() {
                @Override public void mouseDragged(MouseEvent e) {
                    if (dragStart != null) {
                        offsetX += e.getX() - dragStart.x;
                        offsetY += e.getY() - dragStart.y;
                        dragStart = e.getPoint();
                        repaint();
                    }
                }
            });
        }

        public void setPendingAirport(String code) { this.pendingAirport = code; }

        public int getImageWidth()  { return (mapImage != null) ? mapImage.getWidth()  : 1200; }
        public int getImageHeight() { return (mapImage != null) ? mapImage.getHeight() : 800; }

        public void loadMapImage() {
            try {
                File imgFile = new File("malaysia_map.png");
                if (imgFile.exists()) {
                    mapImage = ImageIO.read(imgFile);
                    return;
                }

                java.net.URL url = getClass().getResource("/malaysia_map.png");
                if (url != null) {
                    mapImage = ImageIO.read(url);
                    return;
                }

                url = getClass().getResource("/malaysia_map.png");
                if (url != null) {
                    mapImage = ImageIO.read(url);
                    return;
                }

                mapImage = new BufferedImage(800, 600, BufferedImage.TYPE_INT_RGB);
                Graphics2D g = mapImage.createGraphics();
                g.setColor(Color.LIGHT_GRAY);
                g.fillRect(0, 0, 800, 600);
                g.setColor(Color.RED);
                g.drawString("Map image not found. Place malaysia_map.png in project root or src/resources.", 50, 300);
                g.dispose();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            if (mapImage != null) {
                g2d.translate(offsetX, offsetY);
                g2d.scale(scale, scale);
                g2d.drawImage(mapImage, 0, 0, this);
                g2d.scale(1/scale, 1/scale);
                g2d.translate(-offsetX, -offsetY);
            }

            Map<String, Point> codeToScreen = new HashMap<>();
            for (Map.Entry<String, Point2D.Double> entry : AIRPORT_COORDS.entrySet()) {
                String code = entry.getKey();
                Point2D.Double coord = entry.getValue();
                double imgX = ((coord.getX() - MIN_LON) / (MAX_LON - MIN_LON)) * getImageWidth();
                double imgY = ((MAX_LAT - coord.getY()) / (MAX_LAT - MIN_LAT)) * getImageHeight();
                int screenX = (int)(imgX * scale + offsetX);
                int screenY = (int)(imgY * scale + offsetY);
                codeToScreen.put(code, new Point(screenX, screenY));
            }

            g2d.setColor(Color.BLUE);
            g2d.setStroke(new BasicStroke(2.0f));
            int n = FlightNetworkData.VertexNames.size();
            for (int i = 1; i < n; i++) {
                String fromCode = FlightNetworkData.VertexCodes.get(i);
                if (fromCode == null || fromCode.isEmpty()) continue;
                Point fromPoint = codeToScreen.get(fromCode);
                if (fromPoint == null) continue;
                for (int j = 1; j < n; j++) {
                    if (FlightNetworkData.DistanceMatrix[i][j] > 0) {
                        String toCode = FlightNetworkData.VertexCodes.get(j);
                        if (toCode == null || toCode.isEmpty()) continue;
                        Point toPoint = codeToScreen.get(toCode);
                        if (toPoint != null) {
                            g2d.drawLine(fromPoint.x, fromPoint.y, toPoint.x, toPoint.y);
                        }
                    }
                }
            }

            for (Map.Entry<String, Point> entry : codeToScreen.entrySet()) {
                String code = entry.getKey();
                Point p = entry.getValue();
                g2d.setColor(Color.RED);
                g2d.fillOval(p.x - 6, p.y - 6, 12, 12);
                g2d.setColor(Color.BLACK);
                g2d.drawOval(p.x - 6, p.y - 6, 12, 12);
                g2d.setColor(Color.BLACK);
                g2d.setFont(new Font("SansSerif", Font.BOLD, 12));
                g2d.drawString(code, p.x + 10, p.y - 5);
            }

            if (pendingAirport != null) {
                g2d.setColor(Color.RED);
                g2d.setFont(new Font("SansSerif", Font.BOLD, 14));
                g2d.drawString("Click on the map to place " + pendingAirport, 50, 50);
            }
        }
    }
}