import enums.*;
import events.*;
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
    private final String mapName;
    private ArrayList<ArrayList<MapPosition>> map;
    private ArrayList<Event> allEvents;
    private ArrayList<Integer> eventRows;
    private ArrayList<Integer> eventCols;
    private int numOfPlacesToVisit;
    private SessionManager sessionManager;

    public CampusMap(int rows, int cols, int sessionId, String mapFile, String eventsFile) throws InvalidMapException, IOException {
        this.rows = rows;
        this.cols = cols;
        this.sessionId = sessionId;
        this.map = new ArrayList<>();
        this.mapFile = mapFile;
        this.eventsFile = eventsFile;
        this.allEvents = new ArrayList<>();
        this.eventRows = new ArrayList<>();
        this.eventCols = new ArrayList<>();
        this.numOfPlacesToVisit = 0;

        this.mapName = extractMapName(mapFile);
        this.sessionManager = new SessionManager(sessionId, this.mapName);

        initialiseMap();
        loadMapData();
        loadEventsData();
        loadSessionScores();
    }

    private String extractMapName(String filePath) {
        String name = filePath;
        if (name.contains("data/maps/")) {
            name = name.replace("data/maps/", "");
        }
        if (name.endsWith(".txt")) {
            name = name.replace(".txt", "");
        }
        return name;
    }

    private void initialiseMap() {
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

    private void loadMapData() throws IOException, InvalidMapException {
        FileHandler fileHandler = new FileHandler();
        ArrayList<String[]> mapData = fileHandler.readMapFile(this.mapFile, this.rows, this.cols);

        for (String[] data : mapData) {
            int row = Integer.parseInt(data[0].trim());
            int col = Integer.parseInt(data[1].trim());
            
            MapPosition position = this.map.get(row).get(col);
            
            if (position.getType() == MapPositionType.PLACE || 
                (position.getPlace() != null)) {
                throw new InvalidMapException("Map position [" + row + "," + col + "] already occupied. Exiting program.");
            }

            PlaceType placeType = PlaceType.valueOf(data[2].trim().toUpperCase());
            String placeName = data[3].trim();
            double score = Double.parseDouble(data[4].trim());
            String restricted = data[5].trim();

            Place place = new Place(placeType, placeName, score);
            position.setPlace(place);
            
            if (restricted.equalsIgnoreCase("yes")) {
                position.setType(MapPositionType.RESTRICTED);
            } else {
                position.setType(MapPositionType.PLACE);
                this.numOfPlacesToVisit++;
            }
        }
    }

    private void loadEventsData() throws IOException, InvalidMapException {
        FileHandler fileHandler = new FileHandler();
        ArrayList<String[]> eventsData = fileHandler.readEventsFile(this.eventsFile, this.rows, this.cols);

        for (String[] data : eventsData) {
            int row = Integer.parseInt(data[0].trim());
            int col = Integer.parseInt(data[1].trim());
            
            MapPosition position = this.map.get(row).get(col);
            
            if (position.getType() != MapPositionType.PLACE && position.getType() != MapPositionType.RESTRICTED) {
                throw new InvalidMapException("No valid place at given location. Exiting program.");
            }

            Place place = position.getPlace();
            PlaceType placeType = place.getPlaceType();

            String eventTypeStr = data[2].trim().toUpperCase();
            
            if (position.getType() == MapPositionType.RESTRICTED) {
                continue;
            }

            if ((eventTypeStr.equals("SEMINAR") || eventTypeStr.equals("EXAM")) && placeType != PlaceType.EVENT_HALL) {
                throw new InvalidMapException("Event cannot be added to the place. Exiting program.");
            }

            if (eventTypeStr.equals("LECTURE") && placeType != PlaceType.LECTURE_HALL && placeType != PlaceType.EVENT_HALL) {
                throw new InvalidMapException("Event cannot be added to the place. Exiting program.");
            }

            String date = data[3].trim();
            String startTime = data[4].trim();
            String endTime = data[5].trim();
            double score = Double.parseDouble(data[6].trim());
            String name = data[7].trim();
            String speakerInfo = data[8].trim();

            Event event = null;
            if (eventTypeStr.equals("LECTURE")) {
                event = new Lecture(date, startTime, endTime, score, name, speakerInfo);
            } else if (eventTypeStr.equals("SEMINAR")) {
                String[] speakers = speakerInfo.split("#");
                event = new Seminar(date, startTime, endTime, score, name, speakers);
            } else if (eventTypeStr.equals("EXAM")) {
                event = new Exam(date, startTime, endTime, score, name);
            }

            if (event != null) {
                position.addEvent(event);
                this.allEvents.add(event);
                this.eventRows.add(row);
                this.eventCols.add(col);
            }
        }
    }

    private void loadSessionScores() throws IOException {
        FileHandler fileHandler = new FileHandler();
        ArrayList<Score> previousScores = fileHandler.readSessionScoresFile();
        
        if (!previousScores.isEmpty()) {
            this.sessionManager.addPreviousSessionScores(previousScores);
        }
    }

    private void printMap(Student student) {
        System.out.println("Map Name: " + this.mapName);
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                MapPosition currentPos = this.map.get(i).get(j);
                if (student != null && i == student.getRow() && j == student.getCol()) {
                    System.out.print(Constants.PLAYER_SYMBOL + " ");
                } else if (currentPos.getType() != MapPositionType.PLACE && currentPos.getType() != MapPositionType.RESTRICTED) {
                    char symbol = Constants.getSymbol(currentPos.getType());
                    System.out.print(symbol + " ");
                } else if (currentPos.getType() == MapPositionType.RESTRICTED) {
                    System.out.print(Constants.getSymbol(MapPositionType.RESTRICTED) + " ");
                } else {
                    Place place = currentPos.getPlace();
                    if (place.getPlaceType() == PlaceType.CAFETERIA) {
                        System.out.print(Constants.CAFETERIA_SYMBOL + " ");
                    } else if (place.getPlaceType() == PlaceType.LIBRARY) {
                        System.out.print(Constants.LIBRARY_SYMBOL + " ");
                    } else if (place.getPlaceType() == PlaceType.SPORTS_CENTRE) {
                        System.out.print(Constants.SPORTS_CENTRE_SYMBOL + " ");
                    } else if (place.getPlaceType() == PlaceType.LECTURE_HALL || place.getPlaceType() == PlaceType.EVENT_HALL) {
                        if (place.hasEvents()) {
                            System.out.print("@ ");
                        } else {
                            System.out.print(Constants.EMPTY_EVENT_SYMBOL + " ");
                        }
                    } else {
                        System.out.print(". ");
                    }
                }
            }
            System.out.println();
        }
    }

    private void handleVisit(Scanner scanner, Student student) {
        MapPosition current = this.map.get(student.getRow()).get(student.getCol());

        if (current.getType() != MapPositionType.PLACE) {
            return;
        }

        Place place = current.getPlace();
        
        if (!place.isVisited()) {
            this.numOfPlacesToVisit--;
        }
        
        current.markPlaceVisited();

        if (place.getPlaceType() == PlaceType.CAFETERIA) {
            System.out.println("You can eat here if you are hungry.");
            Score score = student.createPlaceScore(place);
            this.sessionManager.addScore(score);
        } else if (place.getPlaceType() == PlaceType.LIBRARY) {
            System.out.println("Study hard here.");
            Score score = student.createPlaceScore(place);
            this.sessionManager.addScore(score);
        } else if (place.getPlaceType() == PlaceType.SPORTS_CENTRE) {
            System.out.println("You can exercise here.");
            Score score = student.createPlaceScore(place);
            this.sessionManager.addScore(score);
        } else if (place.hasEvents()) {
            visitEvent(scanner, current, student);
        } else {
            System.out.println("Nothing happening here.");
        }
    }

    private void visitEvent(Scanner scanner, MapPosition position, Student student) {
        Place place = position.getPlace();
        place.printEventSchedule();
        
        System.out.println("Enter event ID to attend (or 0 to skip):");
        System.out.print("> ");
        String input = scanner.nextLine().trim();
        
        try {
            int eventId = Integer.parseInt(input);
            if (eventId == 0) {
                return;
            }

            Event event = place.getEventById(eventId);
            if (event == null) {
                System.out.println("Invalid event ID.");
                return;
            }

            System.out.println("Attending: " + event.getEventDetails());
            Score score = student.createEventScore(event, place);
            this.sessionManager.addScore(score);
            
            position.removeEventById(eventId);
            
            for (int i = 0; i < this.allEvents.size(); i++) {
                if (this.allEvents.get(i).getId() == eventId) {
                    this.allEvents.remove(i);
                    this.eventRows.remove(i);
                    this.eventCols.remove(i);
                    break;
                }
            }

        } catch (NumberFormatException e) {
            System.out.println("Invalid input.");
        }
    }

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
            } else {
                try {
                    boolean validMove = student.move(input, getMap());
                    if (validMove) {
                        handleVisit(scanner, student);
                    }
                    
                    if (this.allEvents.isEmpty() && this.numOfPlacesToVisit == 0) {
                        System.out.println("No more places left to visit on campus. Please visit another time.");
                        sessionOver = true;
                    } else {
                        printMap(student);
                    }
                } catch (MovementBlockedException e) {
                    System.out.println(e.getMessage());
                    printMap(student);
                }
            }
        }
    }

    public void printSchedule(Scanner scanner) {
        System.out.println("Select place type to view schedule:");
        System.out.println("1. Cafeteria");
        System.out.println("2. Library");
        System.out.println("3. Sports Centre");
        System.out.println("4. Lecture Hall");
        System.out.println("5. Event Hall");
        System.out.print("> ");
        
        String choice = scanner.nextLine().trim();
        
        PlaceType selectedType = null;
        switch (choice) {
            case "1":
                selectedType = PlaceType.CAFETERIA;
                break;
            case "2":
                selectedType = PlaceType.LIBRARY;
                System.out.println("Library has no specific schedule.");
                return;
            case "3":
                selectedType = PlaceType.SPORTS_CENTRE;
                break;
            case "4":
                selectedType = PlaceType.LECTURE_HALL;
                break;
            case "5":
                selectedType = PlaceType.EVENT_HALL;
                break;
            default:
                System.out.println("Invalid choice.");
                return;
        }

        if (selectedType == PlaceType.CAFETERIA) {
            Cafeteria cafeteria = new Cafeteria();
            cafeteria.printSchedule();
        } else if (selectedType == PlaceType.SPORTS_CENTRE) {
            SportsCentre sportsCentre = new SportsCentre("Sports Centre");
            sportsCentre.printSchedule();
        } else {
            boolean found = false;
            for (ArrayList<MapPosition> row : this.map) {
                for (MapPosition pos : row) {
                    if (pos.getType() == MapPositionType.PLACE) {
                        Place place = pos.getPlace();
                        if (place.getPlaceType() == selectedType && place.hasEvents()) {
                            place.printEventSchedule();
                            found = true;
                        }
                    }
                }
            }
            if (!found) {
                System.out.println("No events scheduled for this place type.");
            }
        }
    }

    public void bookPlace(Scanner scanner) {
        System.out.println("Select place type to book:");
        System.out.println("1. Sports Centre");
        System.out.println("2. Event Hall");
        System.out.print("> ");
        
        String choice = scanner.nextLine().trim();
        
        PlaceType selectedType = null;
        switch (choice) {
            case "1":
                selectedType = PlaceType.SPORTS_CENTRE;
                break;
            case "2":
                selectedType = PlaceType.EVENT_HALL;
                break;
            default:
                System.out.println("Invalid choice.");
                return;
        }

        ArrayList<Bookable> bookablePlaces = new ArrayList<>();
        for (ArrayList<MapPosition> row : this.map) {
            for (MapPosition pos : row) {
                if (pos.getType() == MapPositionType.PLACE) {
                    Place place = pos.getPlace();
                    if (place.getPlaceType() == selectedType) {
                        if (selectedType == PlaceType.SPORTS_CENTRE) {
                            bookablePlaces.add(new SportsCentre(place.getName()));
                        } else if (selectedType == PlaceType.EVENT_HALL) {
                            bookablePlaces.add(new EventHall(place.getName()));
                        }
                    }
                }
            }
        }

        if (bookablePlaces.isEmpty()) {
            System.out.println("No bookable places of this type found.");
            return;
        }

        System.out.println("Available places:");
        for (int i = 0; i < bookablePlaces.size(); i++) {
            System.out.println((i + 1) + ". " + bookablePlaces.get(i).getBookingDetails());
        }
        
        System.out.print("Select a place to book: ");
        try {
            int placeChoice = Integer.parseInt(scanner.nextLine().trim());
            if (placeChoice > 0 && placeChoice <= bookablePlaces.size()) {
                bookablePlaces.get(placeChoice - 1).book();
            } else {
                System.out.println("Invalid choice.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input.");
        }
    }

    public void viewCurrentScoreHistory() {
        this.sessionManager.printCurrentSessionScores();
    }

    public void viewPreviousSessionScores() {
        this.sessionManager.printAllSessionScores();
    }

    public void saveAndExit() throws IOException {
        FileHandler fileHandler = new FileHandler();
        
        ArrayList<Score> currentScores = this.sessionManager.getCurrentSessionScores();
        if (!currentScores.isEmpty()) {
            fileHandler.writeSessionScores(currentScores, this.mapName, this.sessionId);
        }

        if (!this.allEvents.isEmpty()) {
            fileHandler.writeEventsFile(this.eventsFile, this.allEvents, this.eventRows, this.eventCols);
        }
    }

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
