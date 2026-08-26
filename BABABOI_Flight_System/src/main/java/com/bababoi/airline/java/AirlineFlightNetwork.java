/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bababoi.airline.java;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Line2D;
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

    private JButton addButton;
    private JButton removeButton;
    private JButton connectButton;
    private JButton backButton;
    private JButton recenterButton;
    private JButton filterButton;
    private JButton clearFilterButton;

    private JLabel statusLabel;

    // Filter state
    private String filterAirport = null;

    // Airport coordinates
    private static final Map<String, Point2D.Double> AIRPORT_COORDS =
            new HashMap<>();

    private static final String COORDS_FILE =
            "airport_coords.csv";

    // Flight CSV
    private static final String FLIGHT_FILE =
            "flight_data.csv";

    // CONNECTED FLIGHTS FILE 
    private static final String CONNECTED_FLIGHTS_FILE =
            "connected_flights.csv";

    // All routes from CSV
    private static final List<FlightInfo> FLIGHT_DATA =
            new ArrayList<>();



    private static final List<FlightInfo> CONNECTED_FLIGHTS =
            new ArrayList<>();


    // Constructor
    public AirlineFlightNetwork(Menu parent) {

        this.parentMenu = parent;

        setTitle("Airline Flight Network - Malaysia");

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        setExtendedState(
                JFrame.MAXIMIZED_BOTH
        );

        setLayout(
                new BorderLayout()
        );

        // Load airport coordinates
        loadCoords();

        // Load flight data from CSV
        loadFlightData();

        // Load connected flights from CSV (NEW)
        loadConnectedFlights();

        // Map
        mapPanel = new MapPanel();

        add(
                mapPanel,
                BorderLayout.CENTER
        );

        // Bottom control panel
        JPanel controlPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT
                        )
                );

        // Back
        backButton =
                new JButton("Back");

        backButton.addActionListener(
                e -> goBack()
        );

        controlPanel.add(
                backButton
        );

        // Add Airport
        addButton =
                new JButton("Add Airport to Map");

        addButton.addActionListener(
                e -> addAirportToMap()
        );

        controlPanel.add(
                addButton
        );

        // Remove Airport
        removeButton =
                new JButton("Remove Airport from Map");

        removeButton.addActionListener(
                e -> removeAirportFromMap()
        );

        controlPanel.add(
                removeButton
        );

        // CONNECT AIRPORT
        connectButton =
                new JButton("Connect Airport");

        connectButton.addActionListener(
                e -> connectAirport()
        );

        controlPanel.add(
                connectButton
        );

        // FILTER ROUTES
        filterButton =
                new JButton("Filter by Airport");

        filterButton.addActionListener(
                e -> filterByAirport()
        );

        controlPanel.add(
                filterButton
        );

        // CLEAR FILTER
        clearFilterButton =
                new JButton("Clear Filter");

        clearFilterButton.addActionListener(
                e -> clearFilter()
        );

        controlPanel.add(
                clearFilterButton
        );

        // RECENTER MAP
        recenterButton =
                new JButton("Recenter Map");

        recenterButton.addActionListener(
                e -> mapPanel.recenterMap()
        );

        controlPanel.add(
                recenterButton
        );

        // Status
        statusLabel =
                new JLabel(
                        "Select two airports to create a one-way connection."
                );

        controlPanel.add(
                statusLabel
        );

        add(
                controlPanel,
                BorderLayout.SOUTH
        );

        // Load map
        mapPanel.loadMapImage();

        mapPanel.repaint();

        // Update status with loaded connections count
        if (!CONNECTED_FLIGHTS.isEmpty()) {
            statusLabel.setText(
                    "Loaded " + CONNECTED_FLIGHTS.size() + " connected routes."
            );
        }
    }

    // BACK
    private void goBack() {

        // Save connected flights before closing (NEW)
        saveConnectedFlights();

        if (parentMenu != null) {
            parentMenu.setVisible(true);
        }

        dispose();
    }

    // SAVE CONNECTED FLIGHTS 
    private void saveConnectedFlights() {

        if (CONNECTED_FLIGHTS.isEmpty()) {

            // Delete the file if it exists and there are no connections
            try {
                Files.deleteIfExists(Paths.get(CONNECTED_FLIGHTS_FILE));
            } catch (IOException e) {
                // Ignore
            }
            return;
        }

        try (BufferedWriter writer =
                     Files.newBufferedWriter(
                             Paths.get(CONNECTED_FLIGHTS_FILE)
                     )) {

            // Write header
            writer.write("fromCode,toCode,flightNumber,distance,duration");
            writer.newLine();

            for (FlightInfo flight : CONNECTED_FLIGHTS) {

                writer.write(
                        flight.fromCode + ","
                                + flight.toCode + ","
                                + flight.flightNumber + ","
                                + flight.distance + ","
                                + flight.duration
                );
                writer.newLine();
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error saving connected flights: " + e.getMessage());
        }
    }


    // LOAD CONNECTED FLIGHTS 
    private void loadConnectedFlights() {

        CONNECTED_FLIGHTS.clear();

        Path path = Paths.get(CONNECTED_FLIGHTS_FILE);

        if (!Files.exists(path)) {
            return;
        }

        try (BufferedReader reader =
                     Files.newBufferedReader(path)) {

            String line;
            boolean isHeader = true;

            while ((line = reader.readLine()) != null) {

                line = line.trim();

                if (line.isEmpty()) {
                    continue;
                }

                // Skip header
                if (isHeader) {
                    isHeader = false;
                    continue;
                }

                String[] parts = line.split(",");

                if (parts.length == 5) {

                    try {

                        String fromCode = parts[0].trim();
                        String toCode = parts[1].trim();
                        String flightNumber = parts[2].trim();
                        double distance = Double.parseDouble(parts[3].trim());
                        double duration = Double.parseDouble(parts[4].trim());

                        // Find the original FlightInfo from FLIGHT_DATA
                        FlightInfo originalFlight = findFlight(fromCode, toCode);

                        if (originalFlight != null) {

                            // Use the original FlightInfo to maintain all data
                            CONNECTED_FLIGHTS.add(originalFlight);

                        } else {

                            // Create a new FlightInfo if not found (shouldn't happen normally)
                            int fromIndex = getAirportIndex(fromCode);
                            int toIndex = getAirportIndex(toCode);

                            if (fromIndex != -1 && toIndex != -1) {

                                FlightInfo flight = new FlightInfo(
                                        fromIndex,
                                        toIndex,
                                        fromCode,
                                        toCode,
                                        distance,
                                        duration,
                                        flightNumber
                                );

                                CONNECTED_FLIGHTS.add(flight);
                            }
                        }

                    } catch (NumberFormatException e) {
                        System.out.println("Invalid line in connected_flights.csv: " + line);
                    }
                }
            }

            System.out.println("Loaded " + CONNECTED_FLIGHTS.size() + " connected flights.");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error loading connected flights: " + e.getMessage());
        }
    }

    // GET AIRPORT INDEX (NEW)

    private int getAirportIndex(String code) {

        for (int i = 0; i < FlightNetworkData.VertexCodes.size(); i++) {

            String airportCode = FlightNetworkData.VertexCodes.get(i);

            if (airportCode != null && airportCode.equals(code)) {
                return i;
            }
        }

        return -1;
    }

    // FILTER BY AIRPORT
    private void filterByAirport() {

        if (CONNECTED_FLIGHTS.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "No flights connected yet. Please connect some flights first."
            );

            return;
        }

        // Get all airports that have connections
        Set<String> airportSet = new HashSet<>();

        for (FlightInfo flight : CONNECTED_FLIGHTS) {

            airportSet.add(flight.fromCode);
            airportSet.add(flight.toCode);
        }

        if (airportSet.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "No airports with connections."
            );

            return;
        }

        String[] airports = airportSet.toArray(new String[0]);
        Arrays.sort(airports);

        String selected =
                (String) JOptionPane.showInputDialog(
                        this,
                        "Select airport to filter routes:\n\n"
                                + "Only routes connected to this airport will be shown.",
                        "Filter by Airport",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        airports,
                        airports[0]
                );

        if (selected != null) {

            filterAirport = selected;

            statusLabel.setText(
                    "Showing routes connected to: "
                            + selected
                            + " ("
                            + countFilteredFlights(selected)
                            + " routes)"
            );

            mapPanel.repaint();
        }
    }

    // CLEAR FILTER
    private void clearFilter() {

        filterAirport = null;

        statusLabel.setText(
                "Showing all connected routes ("
                        + CONNECTED_FLIGHTS.size()
                        + " routes)"
        );

        mapPanel.repaint();
    }

    // COUNT FILTERED FLIGHTS
    private int countFilteredFlights(String airportCode) {

        int count = 0;

        for (FlightInfo flight : CONNECTED_FLIGHTS) {

            if (flight.fromCode.equals(airportCode)
                    || flight.toCode.equals(airportCode)) {

                count++;
            }
        }

        return count;
    }

    // CHECK IF FLIGHT SHOULD BE SHOWN
    private boolean shouldShowFlight(FlightInfo flight) {

        if (filterAirport == null) {
            return true;
        }

        return flight.fromCode.equals(filterAirport)
                || flight.toCode.equals(filterAirport);
    }


    // ADD AIRPORT
    private void addAirportToMap() {

        List<String> codes =
                new ArrayList<>();

        for (
                int i = 1;
                i < FlightNetworkData.VertexCodes.size();
                i++
        ) {

            String code =
                    FlightNetworkData.VertexCodes.get(i);

            if (
                    code != null &&
                            !code.trim().isEmpty()
            ) {

                codes.add(code);
            }
        }

        if (codes.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "No airports available."
            );

            return;
        }

        String selected =
                (String) JOptionPane.showInputDialog(
                        this,
                        "Select airport code to place on map:",
                        "Add Airport",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        codes.toArray(),
                        codes.get(0)
                );

        if (selected != null) {

            if (
                    AIRPORT_COORDS.containsKey(selected)
            ) {

                JOptionPane.showMessageDialog(
                        this,
                        "Airport " + selected
                                + " already has coordinates."
                );

                return;
            }

            JOptionPane.showMessageDialog(
                    this,
                    "Click on the map to set the location for airport "
                            + selected + ".",
                    "Set Location",
                    JOptionPane.INFORMATION_MESSAGE
            );

            mapPanel.setPendingAirport(
                    selected
            );
        }
    }


    // REMOVE AIRPORT
    private void removeAirportFromMap() {

        if (
                AIRPORT_COORDS.isEmpty()
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "No airports on map to remove."
            );

            return;
        }

        String[] codes =
                AIRPORT_COORDS.keySet()
                        .toArray(
                                new String[0]
                        );

        String selected =
                (String) JOptionPane.showInputDialog(
                        this,
                        "Select airport to remove from map:",
                        "Remove Airport",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        codes,
                        codes[0]
                );

        if (selected != null) {

            AIRPORT_COORDS.remove(
                    selected
            );

            // Also remove connections involving this airport
            CONNECTED_FLIGHTS.removeIf(
                    flight ->
                            flight.fromCode.equals(selected)
                                    ||
                                    flight.toCode.equals(selected)
            );

            // If filter was set to this airport, clear it
            if (filterAirport != null && filterAirport.equals(selected)) {
                filterAirport = null;
            }

            saveCoords();
            saveConnectedFlights(); // SAVE after removing (NEW)

            mapPanel.repaint();

            JOptionPane.showMessageDialog(
                    this,
                    "Airport " + selected
                            + " removed from map."
            );
        }
    }

    // CONNECT AIRPORTS MANUALLY
    private void connectAirport() {

        /*
         * Need at least two airports placed on map.
         */

        if (
                AIRPORT_COORDS.size() < 2
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please add at least two airports to the map first."
            );

            return;
        }

        String[] availableAirports =
                AIRPORT_COORDS.keySet()
                        .toArray(
                                new String[0]
                        );

        // SELECT FROM AIRPORT
        String from =
                (String) JOptionPane.showInputDialog(
                        this,
                        "Select FROM airport:",
                        "Connect Airport",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        availableAirports,
                        availableAirports[0]
                );

        if (from == null) {
            return;
        }

        // SELECT TO AIRPORT
        String to =
                (String) JOptionPane.showInputDialog(
                        this,
                        "Select TO airport:",
                        "Connect Airport",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        availableAirports,
                        availableAirports[0]
                );

        if (to == null) {
            return;
        }

        // Same airport
        if (
                from.equals(to)
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "FROM and TO airports cannot be the same."
            );

            return;
        }

        // Find EXACT route in CSV
        FlightInfo selectedFlight =
                findFlight(
                        from,
                        to
                );

        if (
                selectedFlight == null
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "No flight route found in flight_data.csv:\n\n"
                            + from
                            + " → "
                            + to
                            + "\n\n"
                            + "The route must exist in the CSV file."
            );

            return;
        }

        // Check duplicate
        for (
                FlightInfo flight
                : CONNECTED_FLIGHTS
        ) {

            if (
                    flight.fromCode.equals(from)
                            &&
                            flight.toCode.equals(to)
            ) {

                JOptionPane.showMessageDialog(
                        this,
                        "This connection already exists:\n"
                                + from
                                + " → "
                                + to
                );

                return;
            }
        }


        CONNECTED_FLIGHTS.add(
                selectedFlight
        );

        saveConnectedFlights(); 

        mapPanel.repaint();

        statusLabel.setText(
                "Connected: "
                        + selectedFlight.fromCode
                        + " → "
                        + selectedFlight.toCode
                        + " | Flight "
                        + selectedFlight.flightNumber
        );

        JOptionPane.showMessageDialog(
                this,
                "Connection created successfully!\n\n"
                        + "From: "
                        + selectedFlight.fromCode
                        + "\n"
                        + "To: "
                        + selectedFlight.toCode
                        + "\n"
                        + "Flight: "
                        + selectedFlight.flightNumber
                        + "\n"
                        + "Distance: "
                        + selectedFlight.distance
                        + " km\n"
                        + "Duration: "
                        + formatDuration(
                                selectedFlight.duration
                        )
        );
    }

    // FIND FLIGHT FROM CSV
    private FlightInfo findFlight(
            String fromCode,
            String toCode
    ) {

        for (
                FlightInfo flight
                : FLIGHT_DATA
        ) {

            if (
                    flight.fromCode.equals(fromCode)
                            &&
                            flight.toCode.equals(toCode)
            ) {

                return flight;
            }
        }

        return null;
    }

    // LOAD AIRPORT COORDINATES
    private void loadCoords() {

        Path path =
                Paths.get(
                        COORDS_FILE
                );

        if (
                !Files.exists(path)
        ) {

            return;
        }

        try (
                BufferedReader reader =
                        Files.newBufferedReader(path)
        ) {

            String line;

            while (
                    (line = reader.readLine())
                            != null
            ) {

                line = line.trim();

                if (
                        line.isEmpty()
                ) {

                    continue;
                }

                String[] parts =
                        line.split(",");

                if (
                        parts.length == 3
                ) {

                    String code =
                            parts[0].trim();

                    double lon =
                            Double.parseDouble(
                                    parts[1].trim()
                            );

                    double lat =
                            Double.parseDouble(
                                    parts[2].trim()
                            );

                    AIRPORT_COORDS.put(
                            code,
                            new Point2D.Double(
                                    lon,
                                    lat
                            )
                    );
                }
            }

        } catch (
                Exception e
        ) {

            e.printStackTrace();
        }
    }

    // SAVE AIRPORT COORDINATES
    private void saveCoords() {

        try (
                BufferedWriter writer =
                        Files.newBufferedWriter(
                                Paths.get(
                                        COORDS_FILE
                                )
                        )
        ) {

            for (
                    Map.Entry<String, Point2D.Double> entry
                    : AIRPORT_COORDS.entrySet()
            ) {

                writer.write(
                        entry.getKey()
                                + ","
                                + entry.getValue().getX()
                                + ","
                                + entry.getValue().getY()
                );

                writer.newLine();
            }

        } catch (
                Exception e
        ) {

            e.printStackTrace();
        }
    }

    // LOAD FLIGHTS FROM CSV
    private void loadFlightData() {

        FLIGHT_DATA.clear();

        Path path =
                Paths.get(
                        FLIGHT_FILE
                );

        if (
                !Files.exists(path)
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "flight_data.csv not found!\n"
                            + "Please place it in your project root."
            );

            return;
        }

        try (
                BufferedReader reader =
                        Files.newBufferedReader(path)
        ) {

            String line;

            while (
                    (line = reader.readLine())
                            != null
            ) {

                line = line.trim();

                if (
                        line.isEmpty()
                ) {

                    continue;
                }

                // Only route lines
                if (
                        !line.startsWith("route|")
                ) {

                    continue;
                }

                String[] parts =
                        line.split("\\|");

                if (
                        parts.length < 6
                ) {

                    continue;
                }

                try {

                    int from =
                            Integer.parseInt(
                                    parts[1].trim()
                            );

                    int to =
                            Integer.parseInt(
                                    parts[2].trim()
                            );

                    double distance =
                            Double.parseDouble(
                                    parts[3].trim()
                            );

                    double duration =
                            Double.parseDouble(
                                    parts[4].trim()
                            );

                    String flightNumber =
                            parts[5].trim();

                    String fromCode =
                            getAirportCode(
                                    from
                            );

                    String toCode =
                            getAirportCode(
                                    to
                            );

                    if (
                            fromCode == null
                                    ||
                                    toCode == null
                    ) {

                        continue;
                    }

                    FlightInfo flight =
                            new FlightInfo(
                                    from,
                                    to,
                                    fromCode,
                                    toCode,
                                    distance,
                                    duration,
                                    flightNumber
                            );

                    FLIGHT_DATA.add(
                            flight
                    );

                } catch (
                        NumberFormatException e
                ) {

                    System.out.println(
                            "Invalid route: "
                                    + line
                    );
                }
            }

        } catch (
                IOException e
        ) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(
                    this,
                    "Error reading flight_data.csv:\n"
                            + e.getMessage()
            );
        }
    }

    // GET AIRPORT CODE
    private String getAirportCode(
            int index
    ) {

        if (
                index < 0
                        ||
                        index >= FlightNetworkData.VertexCodes.size()
        ) {

            return null;
        }

        String code =
                FlightNetworkData.VertexCodes.get(
                        index
                );

        if (
                code == null
                        ||
                        code.trim().isEmpty()
        ) {

            return null;
        }

        return code.trim();
    }

    // FORMAT DURATION
    private String formatDuration(
            double hours
    ) {

        int totalMinutes =
                (int) Math.round(
                        hours * 60
                );

        int h =
                totalMinutes / 60;

        int m =
                totalMinutes % 60;

        if (
                h > 0
        ) {

            return h
                    + " hr "
                    + m
                    + " min";

        } else {

            return m
                    + " min";
        }
    }

    // FLIGHT INFORMATION
    private static class FlightInfo {

        int from;
        int to;

        String fromCode;
        String toCode;

        double distance;
        double duration;

        String flightNumber;

        public FlightInfo(
                int from,
                int to,
                String fromCode,
                String toCode,
                double distance,
                double duration,
                String flightNumber
        ) {

            this.from = from;
            this.to = to;

            this.fromCode =
                    fromCode;

            this.toCode =
                    toCode;

            this.distance =
                    distance;

            this.duration =
                    duration;

            this.flightNumber =
                    flightNumber;
        }
    }

    // MAP PANEL
    private class MapPanel
            extends JPanel {

        private BufferedImage mapImage;

        private double scale = 1.0;

        private int offsetX = 0;
        private int offsetY = 0;

        private Point dragStart;

        private String pendingAirport = null;

        private FlightInfo hoveredFlight = null;

        // Malaysia coordinates
        private final double MIN_LON = 98.0;
        private final double MAX_LON = 120.0;

        private final double MIN_LAT = 0.5;
        private final double MAX_LAT = 8.0;

        // Constructor
        public MapPanel() {

            setBackground(
                    Color.WHITE
            );

            setPreferredSize(
                    new Dimension(
                            1200,
                            800
                    )
            );

            // Zoom
            addMouseWheelListener(
                    e -> {

                        if (
                                e.getWheelRotation() < 0
                        ) {

                            scale *= 1.1;

                        } else {

                            scale *= 0.9;
                        }

                        if (
                                scale < 0.1
                        ) {

                            scale = 0.1;
                        }

                        if (
                                scale > 10
                        ) {

                            scale = 10;
                        }

                        repaint();
                    }
            );

            // Mouse
            addMouseListener(
                    new MouseAdapter() {

                        @Override
                        public void mousePressed(
                                MouseEvent e
                        ) {

                            dragStart =
                                    e.getPoint();
                        }

                        @Override
                        public void mouseReleased(
                                MouseEvent e
                        ) {

                            dragStart =
                                    null;
                        }

                        @Override
                        public void mouseClicked(
                                MouseEvent e
                        ) {

                            if (
                                    pendingAirport != null
                            ) {

                                Point p =
                                        e.getPoint();

                                double imgX =
                                        (
                                                p.getX()
                                                        - offsetX
                                        )
                                                / scale;

                                double imgY =
                                        (
                                                p.getY()
                                                        - offsetY
                                        )
                                                / scale;

                                double lon =
                                        MIN_LON
                                                +
                                                (
                                                        imgX
                                                                / getImageWidth()
                                                )
                                                *
                                                (
                                                        MAX_LON
                                                                - MIN_LON
                                                );

                                double lat =
                                        MAX_LAT
                                                -
                                                (
                                                        imgY
                                                                / getImageHeight()
                                                )
                                                *
                                                (
                                                        MAX_LAT
                                                                - MIN_LAT
                                                );

                                AIRPORT_COORDS.put(
                                        pendingAirport,
                                        new Point2D.Double(
                                                lon,
                                                lat
                                        )
                                );

                                saveCoords();

                                JOptionPane.showMessageDialog(
                                        AirlineFlightNetwork.this,
                                        "Airport "
                                                + pendingAirport
                                                + " placed successfully."
                                );

                                pendingAirport =
                                        null;

                                repaint();
                            }
                        }
                    }
            );

            // Mouse movement
            addMouseMotionListener(
                    new MouseAdapter() {

                        @Override
                        public void mouseDragged(
                                MouseEvent e
                        ) {

                            if (
                                    dragStart != null
                            ) {

                                offsetX +=
                                        e.getX()
                                                - dragStart.x;

                                offsetY +=
                                        e.getY()
                                                - dragStart.y;

                                dragStart =
                                        e.getPoint();

                                repaint();
                            }
                        }

                        @Override
                        public void mouseMoved(
                                MouseEvent e
                        ) {

                            FlightInfo flight =
                                    getFlightAtPoint(
                                            e.getPoint()
                                    );

                            if (
                                    flight != hoveredFlight
                            ) {

                                hoveredFlight =
                                        flight;

                                if (
                                        flight != null
                                ) {

                                    statusLabel.setText(
                                            "Flight "
                                                    + flight.flightNumber
                                                    + " | "
                                                    + flight.fromCode
                                                    + " → "
                                                    + flight.toCode
                                                    + " | "
                                                    + flight.distance
                                                    + " km | "
                                                    + formatDuration(
                                                            flight.duration
                                                    )
                                    );

                                } else {

                                    if (filterAirport != null) {

                                        statusLabel.setText(
                                                "Showing routes connected to: "
                                                        + filterAirport
                                                        + " ("
                                                        + countFilteredFlights(filterAirport)
                                                        + " routes)"
                                        );

                                    } else {

                                        statusLabel.setText(
                                                "Select two airports to create a one-way connection."
                                        );
                                    }
                                }

                                repaint();
                            }
                        }
                    }
            );
        }

        // Pending airport
        public void setPendingAirport(
                String code
        ) {

            this.pendingAirport =
                    code;

            repaint();
        }

        // Image width
        public int getImageWidth() {

            return (
                    mapImage != null
            )
                    ? mapImage.getWidth()
                    : 1200;
        }

        // Image height
        public int getImageHeight() {

            return (
                    mapImage != null
            )
                    ? mapImage.getHeight()
                    : 800;
        }

        // Load map
        public void loadMapImage() {

            try {

                File imgFile =
                        new File(
                                "malaysia_map.png"
                        );

                if (
                        imgFile.exists()
                ) {

                    mapImage =
                            ImageIO.read(
                                    imgFile
                            );

                    return;
                }

                java.net.URL url =
                        getClass().getResource(
                                "/malaysia_map.png"
                        );

                if (
                        url != null
                ) {

                    mapImage =
                            ImageIO.read(
                                    url
                            );

                    return;
                }

                // Placeholder
                mapImage =
                        new BufferedImage(
                                800,
                                600,
                                BufferedImage.TYPE_INT_RGB
                        );

                Graphics2D g =
                        mapImage.createGraphics();

                g.setColor(
                        Color.LIGHT_GRAY
                );

                g.fillRect(
                        0,
                        0,
                        800,
                        600
                );

                g.setColor(
                        Color.RED
                );

                g.drawString(
                        "Map image not found.",
                        50,
                        280
                );

                g.drawString(
                        "Place malaysia_map.png in project root.",
                        50,
                        310
                );

                g.dispose();

            } catch (
                    IOException e
            ) {

                e.printStackTrace();
            }
        }

        // Get screen position

        private Point getScreenPoint(
                String code
        ) {

            Point2D.Double coord =
                    AIRPORT_COORDS.get(
                            code
                    );

            if (
                    coord == null
            ) {

                return null;
            }

            double imgX =
                    (
                            coord.getX()
                                    - MIN_LON
                    )
                            /
                            (
                                    MAX_LON
                                            - MIN_LON
                            )
                            *
                            getImageWidth();

            double imgY =
                    (
                            MAX_LAT
                                    - coord.getY()
                    )
                            /
                            (
                                    MAX_LAT
                                            - MIN_LAT
                            )
                            *
                            getImageHeight();

            int screenX =
                    (int)
                            (
                                    imgX * scale
                                            + offsetX
                            );

            int screenY =
                    (int)
                            (
                                    imgY * scale
                                            + offsetY
                            );

            return new Point(
                    screenX,
                    screenY
            );
        }


      private void drawArrow(
        Graphics2D g2d,
        Point from,
        Point to,
        boolean highlighted
) {

    double dx =
            to.x - from.x;

    double dy =
            to.y - from.y;

    double length =
            Math.sqrt(
                    dx * dx
                            +
                            dy * dy
            );

    if (
            length == 0
    ) {

        return;
    }

    double unitX =
            dx / length;

    double unitY =
            dy / length;


    int startX =
            (int)
                    (
                            from.x
                                    + unitX * 8
                    );

    int startY =
            (int)
                    (
                            from.y
                                    + unitY * 8
                    );

    int endX =
            (int)
                    (
                            to.x
                                    - unitX * 10
                    );

    int endY =
            (int)
                    (
                            to.y
                                    - unitY * 10
                    );


    // Line - CHANGED TO BLACK
    if (
            highlighted
    ) {

        g2d.setColor(
                Color.ORANGE
        );

        g2d.setStroke(
                new BasicStroke(
                        4.0f
                )
        );

    } else {

        g2d.setColor(
                Color.BLACK  // <-- Changed from RED to BLACK
        );

        g2d.setStroke(
                new BasicStroke(
                        2.5f
                )
        );
    }

    g2d.drawLine(
            startX,
            startY,
            endX,
            endY
    );

 
    // Arrow head

    double arrowSize =
            highlighted
                    ? 12
                    : 10;

    double angle =
            Math.atan2(
                    dy,
                    dx
            );

    double angle1 =
            angle
                    +
                    Math.toRadians(
                            150
                    );

    double angle2 =
            angle
                    -
                    Math.toRadians(
                            150
                    );

    int x1 =
            (int)
                    (
                            endX
                                    +
                                    arrowSize
                                    *
                                    Math.cos(
                                            angle1
                                    )
                    );

    int y1 =
            (int)
                    (
                            endY
                                    +
                                    arrowSize
                                    *
                                    Math.sin(
                                            angle1
                                    )
                    );

    int x2 =
            (int)
                    (
                            endX
                                    +
                                    arrowSize
                                    *
                                    Math.cos(
                                            angle2
                                    )
                    );

    int y2 =
            (int)
                    (
                            endY
                                    +
                                    arrowSize
                                    *
                                    Math.sin(
                                            angle2
                                    )
                    );

    Polygon arrowHead =
            new Polygon();

    arrowHead.addPoint(
            endX,
            endY
    );

    arrowHead.addPoint(
            x1,
            y1
    );

    arrowHead.addPoint(
            x2,
            y2
    );


    if (highlighted) {
        g2d.setColor(Color.ORANGE);
    } else {
        g2d.setColor(Color.BLACK);  
    }

    g2d.fillPolygon(
            arrowHead
    );
}

 
        // Find hovered flight
        private FlightInfo getFlightAtPoint(
                Point mousePoint
        ) {

            final double HOVER_DISTANCE =
                    8.0;


            for (
                    FlightInfo flight
                    : CONNECTED_FLIGHTS
            ) {

             
                if (!shouldShowFlight(flight)) {
                    continue;
                }

                Point from =
                        getScreenPoint(
                                flight.fromCode
                        );

                Point to =
                        getScreenPoint(
                                flight.toCode
                        );

                if (
                        from == null
                                ||
                        to == null
                ) {

                    continue;
                }

                Line2D line =
                        new Line2D.Double(
                                from,
                                to
                        );

                if (
                        line.ptSegDist(
                                mousePoint
                        )
                                <= HOVER_DISTANCE
                ) {

                    return flight;
                }
            }

            return null;
        }

        // RECENTER MAP
        public void recenterMap() {

            scale = 1.0;

            offsetX = 0;
            offsetY = 0;

            hoveredFlight = null;

            if (filterAirport != null) {

                statusLabel.setText(
                        "Showing routes connected to: "
                                + filterAirport
                                + " ("
                                + countFilteredFlights(filterAirport)
                                + " routes)"
                );

            } else {

                statusLabel.setText(
                        "Hover over a flight route to view flight details."
                );
            }

            repaint();
        }


        // Paint
        @Override
        protected void paintComponent(
                Graphics g
        ) {

            super.paintComponent(
                    g
            );

            Graphics2D g2d =
                    (Graphics2D)
                            g.create();

            g2d.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            // MAP
            if (
                    mapImage != null
            ) {

                g2d.translate(
                        offsetX,
                        offsetY
                );

                g2d.scale(
                        scale,
                        scale
                );

                g2d.drawImage(
                        mapImage,
                        0,
                        0,
                        this
                );

                g2d.scale(
                        1 / scale,
                        1 / scale
                );

                g2d.translate(
                        -offsetX,
                        -offsetY
                );
            }

            //MANUALLY CONNECTED ROUTES

            for (
                    FlightInfo flight
                    : CONNECTED_FLIGHTS
            ) {

                // Apply filter
                if (!shouldShowFlight(flight)) {
                    continue;
                }

                Point from =
                        getScreenPoint(
                                flight.fromCode
                        );

                Point to =
                        getScreenPoint(
                                flight.toCode
                        );

                if (
                        from == null
                                ||
                        to == null
                ) {

                    continue;
                }

                boolean highlighted =
                        flight == hoveredFlight;

                drawArrow(
                        g2d,
                        from,
                        to,
                        highlighted
                );
            }


            // Highlight filtered airport
            if (filterAirport != null) {

                Point p = getScreenPoint(filterAirport);

                if (p != null) {

                    // Glow effect
                    g2d.setColor(
                            new Color(255, 200, 0, 80)
                    );

                    g2d.fillOval(
                            p.x - 20,
                            p.y - 20,
                            40,
                            40
                    );

                    // Outer ring
                    g2d.setColor(
                            new Color(255, 200, 0, 180)
                    );

                    g2d.setStroke(
                            new BasicStroke(2.5f)
                    );

                    g2d.drawOval(
                            p.x - 15,
                            p.y - 15,
                            30,
                            30
                    );
                }
            }

            // AIRPORT NODES
            for (
                    Map.Entry<String, Point2D.Double> entry
                    : AIRPORT_COORDS.entrySet()
            ) {

                String code =
                        entry.getKey();

                Point p =
                        getScreenPoint(
                                code
                        );

                if (
                        p == null
                ) {

                    continue;
                }

                // Check if this airport is the filter
                boolean isFiltered = code.equals(filterAirport);

                // Airport circle
                if (isFiltered) {

                    g2d.setColor(
                            new Color(255, 200, 0)
                    );

                } else {

                    g2d.setColor(
                            Color.RED
                    );
                }

                g2d.fillOval(
                        p.x - 7,
                        p.y - 7,
                        14,
                        14
                );

                // Border
                g2d.setColor(
                        Color.BLACK
                );

                g2d.setStroke(
                        new BasicStroke(
                                1.5f
                        )
                );

                g2d.drawOval(
                        p.x - 7,
                        p.y - 7,
                        14,
                        14
                );

                // Airport code
                g2d.setColor(
                        isFiltered ? new Color(200, 150, 0) : Color.BLACK
                );

                g2d.setFont(
                        new Font(
                                "SansSerif",
                                isFiltered ? Font.BOLD : Font.PLAIN,
                                isFiltered ? 14 : 12
                        )
                );

                g2d.drawString(
                        code,
                        p.x + 10,
                        p.y - 5
                );
            }

            // FLIGHT TOOLTIP
            if (
                    hoveredFlight != null
            ) {

                drawFlightTooltip(
                        g2d,
                        hoveredFlight
                );
            }

            // Filter info on map
            if (filterAirport != null) {

                g2d.setColor(
                        new Color(0, 0, 0, 180)
                );

                g2d.fillRoundRect(
                        10,
                        10,
                        250,
                        40,
                        10,
                        10
                );

                g2d.setColor(
                        Color.WHITE
                );

                g2d.setFont(
                        new Font(
                                "SansSerif",
                                Font.BOLD,
                                14
                        )
                );

                g2d.drawString(
                        "Filter: "
                                + filterAirport
                                + " ("
                                + countFilteredFlights(filterAirport)
                                + " routes)",
                        20,
                        38
                );
            }

            // Pending airport
            if (
                    pendingAirport != null
            ) {

                g2d.setColor(
                        Color.RED
                );

                g2d.setFont(
                        new Font(
                                "SansSerif",
                                Font.BOLD,
                                14
                        )
                );

                g2d.drawString(
                        "Click on the map to place "
                                + pendingAirport,
                        50,
                        50
                );
            }

            g2d.dispose();
        }


        // FLIGHT TOOLTIP
        private void drawFlightTooltip(
                Graphics2D g2d,
                FlightInfo flight
        ) {

            int width = 250;
            int height = 125;

            int x = 20;
            int y = 20;

            // Background
            g2d.setColor(
                    new Color(
                            255,
                            255,
                            255,
                            235
                    )
            );

            g2d.fillRoundRect(
                    x,
                    y,
                    width,
                    height,
                    15,
                    15
            );

            // Border
            g2d.setColor(
                    Color.RED
            );

            g2d.setStroke(
                    new BasicStroke(
                            2
                    )
            );

            g2d.drawRoundRect(
                    x,
                    y,
                    width,
                    height,
                    15,
                    15
            );


            // Flight number
            g2d.setColor(
                    Color.BLACK
            );

            g2d.setFont(
                    new Font(
                            "SansSerif",
                            Font.BOLD,
                            15
                    )
            );

            g2d.drawString(
                    "Flight "
                            + flight.flightNumber,
                    x + 15,
                    y + 25
            );


            // From
            g2d.setFont(
                    new Font(
                            "SansSerif",
                            Font.PLAIN,
                            13
                    )
            );

            g2d.drawString(
                    "From: "
                            + flight.fromCode,
                    x + 15,
                    y + 48
            );

            // To
            g2d.drawString(
                    "To: "
                            + flight.toCode,
                    x + 15,
                    y + 68
            );

            // Distance
            g2d.drawString(
                    "Distance: "
                            + flight.distance
                            + " km",
                    x + 15,
                    y + 88
            );

            // Duration
            g2d.drawString(
                    "Duration: "
                            + formatDuration(
                                    flight.duration
                            ),
                    x + 15,
                    y + 108
            );
        }
    }
}