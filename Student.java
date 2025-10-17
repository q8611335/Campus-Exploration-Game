import enums.MapPositionType;
import events.Event;
import utils.Constants;
import java.util.ArrayList;

public class Student {

    public static final String HYPHEN = "-";
    private int row;
    private int col;
    private int hits;
    private int moves;
    private boolean sessionPaused;
    private double totalScore;

    public Student() {
        reset();
    }

    public Score createPlaceScore(Place place) {
        double combinedScore = place.getScore() + this.totalScore;
        this.totalScore = combinedScore;
        return new Score(this.moves, this.hits, combinedScore, place.getName(), HYPHEN, HYPHEN, HYPHEN);
    }

    public Score createEventScore(Event event, Place place) {
        String timeRange = event.getStartTime() + HYPHEN + event.getEndTime();
        double combinedScore = event.getScore() + place.getScore() + this.totalScore;
        this.totalScore = combinedScore;
        return new Score(this.moves, this.hits, combinedScore, place.getName(), event.getName(), event.getDate(), timeRange);
    }

    public boolean move(String direction, ArrayList<ArrayList<MapPosition>> map) throws MovementBlockedException {
        int newRow = this.row;
        int newCol = this.col;

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

        return movePlayerToNewPosition(map, newRow, newCol);
    }

    public void reset() {
        this.row = 1;
        this.col = 1;
        this.moves = 0;
        this.hits = 0;
        this.sessionPaused = false;
        this.totalScore = 0;
    }

    public int getRow() {
        return this.row;
    }

    public int getCol() {
        return this.col;
    }

    public int getMoves() {
        return this.moves;
    }

    public int getHits() {
        return this.hits;
    }

    public double getTotalScore() {
        return this.totalScore;
    }

    public boolean isSessionPaused() {
        return this.sessionPaused;
    }

    public void setSessionPaused(boolean val) {
        this.sessionPaused = val;
    }

    private boolean movePlayerToNewPosition(ArrayList<ArrayList<MapPosition>> map, int newRow, int newCol) throws MovementBlockedException {
        if (newRow <= 0 || newRow >= map.size() - 1 || newCol <= 0 || newCol >= map.get(0).size() - 1) {
            this.hits++;
            throw new MovementBlockedException("You have hit the edge of the map.");
        }

        MapPosition target = map.get(newRow).get(newCol);
        MapPositionType type = target.getType();

        if (type == MapPositionType.BOUNDARY) {
            this.hits++;
            throw new MovementBlockedException("You have hit the edge of the map.");
        } else if (type == MapPositionType.RESTRICTED) {
            this.hits++;
            throw new MovementBlockedException("You cannot enter that area.");
        } else {
            this.row = newRow;
            this.col = newCol;
            this.moves++;
            return true;
        }
    }
}

