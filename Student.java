

import enums.MapPositionType;
import utils.Constants;


public class Student {

    public static final String HYPHEN = "-";
    private static int currentSessionId;
    private int row;
    private int col;
    private int hits;
    private int moves;
    private boolean sessionPaused;
    //private final ScoreHistory history;

    /**
     * Constructs an object of Student. Resets and initialises the score history and other details.
     */
    public Student() {
        //this.history = new ScoreHistory();
        reset();
    }

    /**
     * Adds a score with place details
     * @param place details of the place to be added to score
     */
    // public void addScore(Place place) {
    //     this.history.addScore(new Score(currentSessionId, this.moves, this.hits, place.getScore(), HYPHEN, place.getName(), HYPHEN, HYPHEN));
    // }

    // /**
    //  * Adds a score with place and events' details
    //  * @param event event details to be added
    //  * @param place place details to be added
    //  */
    // public void addScore(Event event, Place place) {
    //     String timeRange = event.getStartTime() + HYPHEN + event.getEndTime();
    //     this.history.addScore(new Score(currentSessionId, this.moves, this.hits, event.getScore(), event.getName(), place.getName(), event.getDate(), timeRange));
    // }

    /**
     * move the student on the campus map based on direction
     * @param direction direction where the student to be moved.
     * @param map copy of campus map to validate if the move is valid.
     * @return whether a valid move has been made.
     */
    public boolean move(String direction, MapPosition[][] map) {
        int newRow = this.row;
        int newCol = this.col;
        boolean valid ;

        switch (direction) {
            case Constants.MOVE_UP:
                newRow--;
                break;
            case Constants.MOVE_DOWN:
                newRow++;
                break;
            case Constants.MOVE_LEFT:
                newCol--;
                break;
            case Constants.MOVE_RIGHT:
                newCol++;
                break;
            default:
                System.out.println("Invalid direction to move.");
                return false;
        }

        valid = movePlayerToNewPosition(map, newRow, newCol);
        return valid;
    }

    /**
     * Prints score history for the student
    public void printSummary() {
        this.history.printSummary();
    }
     */

    /**
     * resets the student's position on the map and other details.
     */
    public void reset() {
        this.row = 1;
        this.col = 1;
        this.moves = 0;
        this.hits = 0;
        this.sessionPaused = false;
        currentSessionId++;
    }

    public int getRow() {
        return this.row;
    }

    public int getCol() {
        return this.col;
    }

    public boolean isSessionPaused() {
        return this.sessionPaused;
    }

    public void setSessionPaused(boolean val) {
        this.sessionPaused = val;
    }

    public int getCurrentSessionId() {
        return currentSessionId;
    }

    //Note: the private method is at the end of the file after the public methods.
    // moves the player to a new position on the map.
    private boolean movePlayerToNewPosition(MapPosition[][] map, int newRow, int newCol) {
        boolean valid;
        if (newRow >= 1 && newRow < map.length - 1 && newCol >= 1 && newCol < map[0].length - 1) {
            MapPosition target = map[newRow][newCol];
            MapPositionType type = target.getType();

            if (type == MapPositionType.BOUNDARY || type == MapPositionType.RESTRICTED) {
                System.out.println("You cannot enter that area.");
                this.hits++;
                valid = false;
            } else {
                this.row = newRow;
                this.col = newCol;
                this.moves++;
                valid = true;
            }
        } else {
            System.out.println("You have hit the edge of the map!");
            this.hits++;
            valid = false;
        }
        return valid;
    }

}
