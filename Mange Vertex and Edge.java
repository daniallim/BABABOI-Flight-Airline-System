
package bababoi_flight_system;

import java.util.Scanner;

public class BABABOI_Flight_System {
    private static int VertexCount = 0;
    private static String [] VertexNames = new String [100];
    private static double[][] DistanceMatrix = new double [100][100];
    private static double[][] TimeMatrix = new double [100][100];
    
    // ====================Sample Only===========================
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int mainOption=0;
        
        do {
            System.out.println("=========================");
            System.out.println("       MAIN MENU         ");
            System.out.println("=========================");
            System.out.println("1. Manage Vertex");
            System.out.println("2. Manage Edge");
            System.out.println("3. Exit");
            mainOption = readInt(input, "Please select option: ");
            switch(mainOption) {
                case 1: Manage_Vertex(); break;
                case 2: Manage_Edge(); break;
                case 3: System.out.println("Exiting..."); break;
                default: System.out.println("Invalid option.");
            }
        } while(mainOption != 3);
    }
    
    // =======================Mange Vertex======================
    public static void Manage_Vertex(){
        Scanner input = new Scanner(System.in);
       
        int VertexOption=0;
        int RemoveID;
        int SearchID;
        String AirportName;
        
        while(VertexOption != 4){
            System.out.println("=========================");
            System.out.println("       MANAGE VERTEX     ");
            System.out.println("=========================");
            System.out.println("1. Add Vertex");
            System.out.println("2. Remove Vertex");
            System.out.println("3. Search Vertex");
            System.out.println("4. Back to Main Menu");
            System.out.println("=========================");
            VertexOption = readInt(input, "Please enter your option: ");
                
            switch (VertexOption){
                case 1:
                    System.out.print("Enter new airport name: ");
                    AirportName = input.nextLine();
                    Add_Vertex(AirportName);
                    System.out.print("Press Enter to continue...");
                    input.nextLine();
                    break;
                        
                case 2:
                    Show_Active_Airport();
                    System.out.println("=========================");
                    RemoveID = readInt(input, "Enter airport ID to remove: ");

                    Remove_Vertex(RemoveID);
                    System.out.print("Press Enter to continue...");
                    input.nextLine();
                    break;
                        
                case 3:
                    Show_Active_Airport();
                    System.out.println("=========================");
                    SearchID = readInt(input, "Enter airport ID to search: ");
                    
                    Search_Vertex(SearchID);
                    System.out.print("Press Enter to continue...");
                    input.nextLine();
                    break;
                        
                case 4:
                    System.out.println("\nReturning to Main Menu...");
                    return;
                        
                default:
                    System.out.println("\nInvalid option. Please select an option between 1 to 4.");
                    break;
            } 
        }   
    }
    
    public static void Show_Active_Airport(){
        System.out.println("=========================");
        System.out.println("Current Active Airport ");
        System.out.println("=========================");
        
        for(int i=1;i<=VertexCount;i++){
            if(!VertexNames[i].equals("DELETED"))
                System.out.println("ID: " + i + "\tAirport Name: " + VertexNames[i]);
        }
    }
    
    public static void Add_Vertex(String AirportName){
        VertexCount++;
        VertexNames[VertexCount] = AirportName;
        System.out.println("\nAirport Name: " + AirportName + " is added with ID: " + VertexCount);
    }
    
    public static void Remove_Vertex(int ID){
        String VertexToRemove;
        
        if(ID < 1 || ID > VertexCount){
            System.out.println("\nError: The airport is not found!");
            return;
        }
        
        VertexToRemove = VertexNames[ID];
        
        if(VertexNames[ID].equals("DELETED")){
            System.out.println("\nThe airport " + VertexToRemove + " is already removed!");
            return;
        }
        for(int i=1;i<=VertexCount;i++){
            DistanceMatrix[ID][i] = 0;
            DistanceMatrix[i][ID] = 0;
            TimeMatrix[ID][i] = 0;
            TimeMatrix[i][ID] = 0;
        }
        
        VertexNames[ID] = "DELETED";
        System.out.println("\nAirport " + VertexToRemove + " is removed successfully!");
    }
    
    public static void Search_Vertex(int ID){
        if(ID < 1 || ID > VertexCount){
            System.out.println("\nError: The airport is not found!");
            return;
        }
        
        System.out.println("\nAirport " + VertexNames[ID] + " (ID: " + ID + ")");
        
        boolean hasRoute=false;
        System.out.println("=========================");
        System.out.println("All the routes from " + VertexNames[ID]);
        System.out.println("=========================");
        
        for(int i=1; i<= VertexCount;i++){
            if(DistanceMatrix[ID][i] != 0 && ID != i){
                System.out.println("--> " + VertexNames[i] + "(Distance: " + DistanceMatrix[ID][i] + " km, Time: " + TimeMatrix[ID][i] + " hour)" );
                hasRoute = true;
            }
        }
        if(hasRoute == false)
            System.out.println("\nError: No any route from the airport.");
    }
    
    // =======================Exception Handling=======================
    // Exception Handling for integer
    public static int readInt(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("\nInvalid input. Please enter a valid integer.");
            }
        }
    }
    
    // Exception handling for double
    public static double readDouble(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("\nInvalid input. Please enter a valid number.");
            }
        }
    }
    
    // ========================Manage Edge==========================
    public static void Manage_Edge(){
        Scanner input = new Scanner(System.in);
        int From;
        int To;
        double Distance;
        double FlightTime;
        double NewDistance;
        double NewFlightTime;
        int EdgeOption=0;
        
        while(EdgeOption != 5){
            System.out.println("=========================");
            System.out.println("        MANAGE EDGE      ");
            System.out.println("=========================");
            System.out.println("1. Add Edge");
            System.out.println("2. Remove Edge");
            System.out.println("3. Update Edge");
            System.out.println("4. Back to Main Menu");
            System.out.println("=========================");
            EdgeOption = readInt(input, "Please enter your option: ");
                
            switch (EdgeOption){
                case 1:
                    Show_Active_Airport();
                    System.out.println("=========================");
                    From = readInt(input, "Enter source airport ID(from): ");
                    To = readInt(input, "Enter destination airport ID(to): ");
                    Distance = readDouble(input, "Enter the distance(km): ");
                    FlightTime = readDouble(input, "Enter the flight time(hour): ");
                    Add_Edge(From,To,Distance,FlightTime);
                    System.out.print("Press Enter to continue...");
                    input.nextLine();
                    break;
                        
                case 2:
                    Show_Direct_Edge();
                    System.out.println("=========================");
                    From = readInt(input, "Enter source airport ID(from): ");
                    To = readInt(input, "Enter destination airport ID(to): ");
                    Remove_Edge(From,To);    
                    System.out.print("Press Enter to continue...");
                    input.nextLine();
                    break;
                        
                case 3:
                    Show_Direct_Edge();
                    System.out.println("=========================");
                    From = readInt(input, "Enter source airport ID(from): ");
                    To = readInt(input, "Enter destination airport ID(to): ");
                    if(Verify_Edge(From,To)){
                        if(DistanceMatrix[From][To] > 0){
                            NewDistance = readDouble(input, "Enter the new distance(km): ");
                            NewFlightTime = readDouble(input, "Enter the new flight time (hour): ");
                            Update_Edge(From,To,NewDistance,NewFlightTime);
                        }
                        else 
                            System.out.println("Error: No route exists to update.");
                    }
                    System.out.print("Press Enter to continue...");
                    input.nextLine();
                    break;
                        
                case 4:
                    System.out.println("\nReturning to Main Menu...");
                    return;
                        
                default:
                    System.out.println("\nInvalid option. Please select an option between 1 to 4.");
                    break;
            } 
        }
    }
    
    public static void Add_Edge(int From, int To,double Distance,double FlightTime){
        if(Verify_Edge(From,To) == false)
            return;
        
        if(DistanceMatrix[From][To] > 0){
            System.out.println("\nError: The route is already exists.");
            return;
        }
        
        DistanceMatrix[From][To] = Distance;
        TimeMatrix[From][To] = FlightTime;
        System.out.println( "\nThe route from " + VertexNames[From] + "  -->  " + VertexNames[To] + " (Distance: " + Distance + " km, Time: " + FlightTime + " hour)");
        
    }
    
    public static void Remove_Edge(int From,int To){
        if(Verify_Edge(From,To) == false)
            return;
        
        if(DistanceMatrix[From][To] == 0){
            System.out.println("\nError: The route from " + VertexNames[From] + " to " + VertexNames[To] + "  does not exist. Please try again.");
            return;
        }
        
        DistanceMatrix[From][To] = 0;
        TimeMatrix[From][To] = 0;
        System.out.println( "\nThe route from " + VertexNames[From] + " to " + VertexNames[To] + " has been removed successfully!");
    }
    
    public static void Update_Edge(int From, int To, double NewDistance, double NewFlightTime){
        if (NewDistance <= 0 || NewFlightTime <= 0) {
            System.out.println("\nError: Distance and time must be positive.");
            return;
        }
        DistanceMatrix[From][To] = NewDistance;
        TimeMatrix[From][To] = NewFlightTime;
        System.out.println("\nUpdating route: " + VertexNames[From] + " --> " + VertexNames[To] + " (Distance: " + NewDistance + " km, Time: " + NewFlightTime + " hour)");
}
    
    // Verify the edge
    public static boolean Verify_Edge(int From, int To){
        if(From < 1 || From > VertexCount || To < 1 || To > VertexCount){
            System.out.println("\nError: Invalid ID! Please use the ID from the screen." );
            return false;
        }
        
        if(VertexNames[From].equals("DELETED") || VertexNames[To].equals("DELETED")){
            System.out.println("\nError: One of the airports is deleted. Please try again.");
            return false;
        }
        
        if (From == To){
            System.out.println("\nError: Both of the airports are the same. Please try again.");
            return false;
        }
        return true;
    }
    
    public static void Show_Direct_Edge(){
        boolean find;
        find = false;
        
        System.out.println("=========================");
        System.out.println("   Direct Flight Route   ");
        System.out.println("=========================");
                        
        for(int i = 1; i <= VertexCount; i++){
            if(!VertexNames[i].equals("DELETED")){
                for(int j = 1; j <= VertexCount; j++){
                    if(DistanceMatrix[i][j] > 0){
                        System.out.println( VertexNames[i] + "(" + i + ") --> " + VertexNames[j] + "(" + j + ") (Distance: " + DistanceMatrix[i][j] + " km, Time: " + TimeMatrix[i][j] + " hour)");
                        find = true;
                    }
                }
            }
        }
                        
        if(find == false)
        System.out.println("\nNo direct route is available.");
    }   
}