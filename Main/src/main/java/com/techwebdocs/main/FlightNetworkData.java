package com.techwebdocs.main;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class FlightNetworkData {

    public static ArrayList<String> VertexNames = new ArrayList<>();
    public static ArrayList<String> VertexCodes = new ArrayList<>();
    public static double[][] DistanceMatrix = new double[0][0];
    public static double[][] TimeMatrix = new double[0][0];
    public static String[][] FlightNumbers = new String[0][0]; // 新增

    private static final String DATA_FILE = "flight_data.csv";

    static {
        VertexNames.add("");
        VertexCodes.add("");
        resizeMatrices(1);
    }

    public static void resizeMatrices(int newSize) {
        double[][] newDist = new double[newSize][newSize];
        double[][] newTime = new double[newSize][newSize];
        String[][] newFlight = new String[newSize][newSize];
        int oldSize = DistanceMatrix.length;
        int copySize = Math.min(oldSize, newSize);
        for (int i = 0; i < copySize; i++) {
            System.arraycopy(DistanceMatrix[i], 0, newDist[i], 0, copySize);
            System.arraycopy(TimeMatrix[i], 0, newTime[i], 0, copySize);
            System.arraycopy(FlightNumbers[i], 0, newFlight[i], 0, copySize);
        }
        DistanceMatrix = newDist;
        TimeMatrix = newTime;
        FlightNumbers = newFlight;
    }

    public static boolean isValidId(int id) {
        return id >= 1 && id < VertexNames.size();
    }

    public static int activeAirportCount() {
        return VertexNames.size() - 1;
    }

    public static void removeAirport(int id) {
        if (id < 1 || id >= VertexNames.size()) return;

        VertexNames.remove(id);
        VertexCodes.remove(id);

        int newSize = VertexNames.size();
        double[][] newDist = new double[newSize][newSize];
        double[][] newTime = new double[newSize][newSize];
        String[][] newFlight = new String[newSize][newSize];
        int newI = 1;
        for (int i = 1; i < VertexNames.size() + 1; i++) {
            if (i == id) continue;
            int newJ = 1;
            for (int j = 1; j < VertexNames.size() + 1; j++) {
                if (j == id) continue;
                newDist[newI][newJ] = DistanceMatrix[i][j];
                newTime[newI][newJ] = TimeMatrix[i][j];
                newFlight[newI][newJ] = FlightNumbers[i][j];
                newJ++;
            }
            newI++;
        }
        DistanceMatrix = newDist;
        TimeMatrix = newTime;
        FlightNumbers = newFlight;
    }

    public static void save() {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(DATA_FILE))) {
            writer.write("count|" + activeAirportCount());
            writer.newLine();
            for (int i = 1; i < VertexNames.size(); i++) {
                writer.write("airports|" + VertexNames.get(i) + "|" + VertexCodes.get(i));
                writer.newLine();
            }

            // 写入边（包括航班号）
            int n = VertexNames.size();
            for (int i = 1; i < n; i++) {
                for (int j = 1; j < n; j++) {
                    if (DistanceMatrix[i][j] > 0) {
                        String flightNo = (FlightNumbers[i][j] == null) ? "" : FlightNumbers[i][j];
                        writer.write("route|" + i + "|" + j + "|" + DistanceMatrix[i][j] + "|" + TimeMatrix[i][j] + "|" + flightNo);
                        writer.newLine();
                    }
                }
            }
            System.out.println("[SAVE] Saved with flight numbers.");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "保存数据失败！", "错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void load() {
        Path path = Paths.get(DATA_FILE);
        if (!Files.exists(path)) {
            VertexNames.clear();
            VertexCodes.clear();
            VertexNames.add("");
            VertexCodes.add("");
            resizeMatrices(1);
            return;
        }

        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String firstLine = reader.readLine();
            if (firstLine == null) return;

            if (firstLine.startsWith("count|")) {
                int count = Integer.parseInt(firstLine.split("\\|")[1]);
                VertexNames.clear();
                VertexCodes.clear();
                VertexNames.add("");
                VertexCodes.add("");
                resizeMatrices(count + 1);

                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split("\\|");
                    if (parts[0].equals("airports")) {
                        VertexNames.add(parts[1]);
                        VertexCodes.add(parts[2]);
                    } else if (parts[0].equals("route")) {
                        int from = Integer.parseInt(parts[1]);
                        int to = Integer.parseInt(parts[2]);
                        double dist = Double.parseDouble(parts[3]);
                        double time = Double.parseDouble(parts[4]);
                        String flight = (parts.length > 5) ? parts[5] : "";
                        if (from < DistanceMatrix.length && to < DistanceMatrix.length) {
                            DistanceMatrix[from][to] = dist;
                            TimeMatrix[from][to] = time;
                            FlightNumbers[from][to] = flight;
                        }
                    }
                }
            } else {
                loadOldFormat(reader, firstLine);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void loadOldFormat(BufferedReader reader, String firstLine) throws IOException {
        VertexNames.clear();
        VertexCodes.clear();
        VertexNames.add("");
        VertexCodes.add("");
        String line = firstLine;
        do {
            if (line == null) break;
            String[] parts = line.split("\\|");
            if (parts[0].equals("airports")) {
                int id = Integer.parseInt(parts[1]);
                String name = parts[2];
                String code = parts[3];
                while (VertexNames.size() <= id) {
                    VertexNames.add("");
                    VertexCodes.add("");
                }
                VertexNames.set(id, name);
                VertexCodes.set(id, code);
            } else if (parts[0].equals("route")) {
                int from = Integer.parseInt(parts[1]);
                int to = Integer.parseInt(parts[2]);
                double dist = Double.parseDouble(parts[3]);
                double time = Double.parseDouble(parts[4]);
                // 旧格式无航班号，置空
                if (from < DistanceMatrix.length && to < DistanceMatrix.length) {
                    DistanceMatrix[from][to] = dist;
                    TimeMatrix[from][to] = time;
                    FlightNumbers[from][to] = "";
                }
            }
            line = reader.readLine();
        } while (line != null);

        int size = VertexNames.size();
        resizeMatrices(size);
        System.out.println("[LOAD] Old format loaded, flight numbers set to empty.");
    }
}