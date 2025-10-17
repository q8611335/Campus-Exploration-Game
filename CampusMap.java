
import enums.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import utils.*;



public class CampusMap {
    private final int rows;
    private final int cols;
    private final String mapFile;
    private final String eventsFile;
    private final int sessionId;
    private ArrayList<ArrayList<MapPosition>> map; // TODO: convert this to an arraylist
    private int numOfEvents;
    private int minVisits;


    public CampusMap(int rows, int cols, int sessionId, String mapFile, String eventsFile) throws InvalidMapException {
        this.rows = rows;
        this.cols = cols;
        this.sessionId = sessionId;
        this.map = new ArrayList<ArrayList<MapPosition>>();
        this.mapFile = mapFile;
        this.eventsFile = eventsFile;
        this.numOfEvents = 0;
        this.minVisits = 0;
        
        

        if (eventsFile.contains("data/events/")) {
            if (eventsFile.endsWith(".txt")) {
                String eventName = eventsFile.replace("data/events/", "").replace(".txt", "");
            }
        }

        FileHandler fileHandler = new FileHandler();
        if (mapFile.contains("data/maps/")) {
            if (mapFile.endsWith(".txt")) {
                String mapName = mapFile.replace("data/maps/", "").replace(".txt", "");
                try {
                    fileHandler.readFile(this.mapFile);
                } catch (IOException e) {
                    System.out.println("Unable to process file. Exiting program."); 
                    return;
                }
            }
        }
        initialiseMap();
        try {
            fillMap(this.mapFile);
        } catch (IOException e) {
            throw new InvalidMapException("Failed to fill map file: " + this.mapFile); // not sure
        }
        // try {
        //     fillEvents(this.eventsFile);
        // } catch (IOException e) {
        //     throw new InvalidMapException("Failed to fill events file: " + this.eventsFile);
        // }
        

    }
    

    private void initialiseMap() {
        //TODO: initialise map here based on row/col boundary edges
        for (int row = 0; row < this.rows; row++) {
            ArrayList<MapPosition> rowList = new ArrayList<>();
            for (int col = 0; col < this.cols; col++) {
                MapPositionType type;
                if (row == 1 && col == 1) {
                    type = MapPositionType.START;
                } else if (row == 0 || col == 0 || row == this.rows - 1 || col == this.cols - 1) {
                    type = MapPositionType.BOUNDARY;
                } else {
                    type = MapPositionType.OPEN;
                }
                rowList.add(new MapPosition(type));
            }
            this.map.add(rowList);
        }
    }
     private void fillMap(String mapFile) throws IOException {
        File file = new File(mapFile);
        Scanner scanner = new Scanner(file);
        if (scanner.hasNextLine()){
            scanner.nextLine(); //skip the first line
        }

        while(scanner.hasNextLine()){
            String line = scanner.nextLine();
            String[] parts = line.split(",");
            int row = Integer.parseInt(parts[0]);
            int col = Integer.parseInt(parts[1]);
            PlaceType placeType = PlaceType.valueOf(parts[2].toUpperCase());
            String placeName = parts[3];
            double score = Double.parseDouble(parts[4]);

            Place place = new Place(placeType, placeName, score);
            MapPosition position = this.map.get(row).get(col);
            position.setPlace(place);
            position.setType(MapPositionType.PLACE);
        }
    }


   

    private void printMap(Student student) {
        System.out.println("Map Name: " + this.mapFile.replace("data/maps/", "").replace(".txt", ""));
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                MapPosition currentPos = this.map.get(i).get(j);
                if (student != null && i == student.getRow() && j == student.getCol()) {
                    System.out.print(Constants.PLAYER_SYMBOL + " ");
                } else if (currentPos.getType() != MapPositionType.PLACE) {
                    char symbol = Constants.getSymbol(currentPos.getType());
                    System.out.print(symbol + " ");
                } else {
                    MapPosition position = currentPos;
                    if (position.getPlace().getPlaceType() == PlaceType.CAFETERIA) {
                        System.out.print(Constants.CAFETERIA_SYMBOL + " ");
                    } else if (position.getPlace().getPlaceType() == PlaceType.LIBRARY) {
                        System.out.print(Constants.LIBRARY_SYMBOL + " ");
                    } else if (position.getPlace().hasEvents()) {
                        System.out.print(Constants.getSymbol(position.getType()) + " ");
                    } else {
                        System.out.print(Constants.EMPTY_EVENT_SYMBOL + " ");
                    }
                }
            }
            System.out.println();
        }
    }

    // public void handleVisit(Scanner scanner, Student student) {
    //     MapPosition current = this.map.get(student.getRow()).get(student.getCol());

    //     if (current.getType() == MapPositionType.RESTRICTED || current.getType() == MapPositionType.BOUNDARY) {
    //         System.out.println("You cannot enter that area.");
    //         return;
    //     }

    //     if (current.getType() == MapPositionType.PLACE) {
    //         Place place = current.getPlace();             
    //         this.minVisits = this.minVisits - (place.isVisited() ? 0 : 1);
    //         current.markPlaceVisited(); 
    //         if (place.getPlaceType() == PlaceType.CAFETERIA) {
    //             System.out.println("You can eat here if you are hungry.");
    //             student.addScore(place);
    //         } else if (place.getPlaceType() == PlaceType.LIBRARY) {
    //             System.out.println("Study hard here.");
    //             student.addScore(place);
    //         } else if (place.getPlaceType() == PlaceType.SPORTS_CENTRE) {
    //             System.out.println("You can exercise here.");
    //             student.addScore(place);
    //         } else if (place.hasEvents()) {
    //             visitEvent(scanner, current, student);
    //         } else {
    //             System.out.println("Nothing happening here.");
    //         }
    //     }
    // }

    /**
     * Visit the campus map
     *
     * @param scanner Scanner object to take inputs during the navigation/visiting events
     * @param student Student object to update.
     */
    public void visitCampus(Scanner scanner, Student student) {
        printMap(student);
        boolean sessionOver = false;
        while (!sessionOver) {
            Messages.printMovementOptions();
            String input = scanner.nextLine().toUpperCase();

            if (input.equals("Q")) {
                student.setSessionPaused(true);
                System.out.println("Session paused.");
                sessionOver = true;
            // } else {
            //     boolean validMove = student.move(input, getMap());
            //     if (validMove) {
            //         handleVisit(scanner, student);
            //     }
            //     if (this.numOfEvents == 0 && this.minVisits == 0) {
            //         System.out.println("No more places left to visit on campus. Please visit another time.");
            //         student.reset();
            //         //reset();
            //         sessionOver = true;
            //     } else {
            //         printMap(student);
            //     }
            }
        }
    }

    /**
     * return a deep copy of the map with objects constructed using copy constructors to avoid privacy leaks
     */
    public ArrayList<ArrayList<MapPosition>> getMap() {
        ArrayList<ArrayList<MapPosition>> copyMap = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            ArrayList<MapPosition> row = new ArrayList<>();
            for (int j = 0; j < cols; j++) {
                row.add(new MapPosition(this.map.get(i).get(j)));
            }
            copyMap.add(row);
        }
    
        return copyMap;
    }

    
   
}

