import utils.Constants;

public class Score {
    private final int moves;
    private final int hits;
    private final double score;
    private final String eventName;
    private final String placeName;
    private final String date;
    private final String timeRange;
    private String mapName;
    private int sessionId;

    public Score(int moves, int hits, double score, String placeName, String eventName, String date, String timeRange) {
        this.moves = moves;
        this.hits = hits;
        this.score = score;
        this.placeName = placeName;
        this.eventName = eventName;
        this.date = date;
        this.timeRange = timeRange;
        this.mapName = "";
        this.sessionId = 0;
    }

    public Score(String mapName, int sessionId, int moves, int hits, double score, String placeName, String eventName, String date, String timeRange) {
        this.mapName = mapName;
        this.sessionId = sessionId;
        this.moves = moves;
        this.hits = hits;
        this.score = score;
        this.placeName = placeName;
        this.eventName = eventName;
        this.date = date;
        this.timeRange = timeRange;
    }

    public void printSummary(String mapName, int sessionId) {
        System.out.printf(Constants.SCORE_SUMMARY_FORMATTER, mapName, sessionId, this.placeName, this.eventName, this.date, this.timeRange, this.moves, this.hits, this.score);
    }

    public void printSummary() {
        System.out.printf(Constants.SCORE_SUMMARY_FORMATTER, this.mapName, this.sessionId, this.placeName, this.eventName, this.date, this.timeRange, this.moves, this.hits, this.score);
    }

    public int getMoves() {
        return this.moves;
    }

    public int getHits() {
        return this.hits;
    }

    public double getScore() {
        return this.score;
    }

    public String getEventName() {
        return this.eventName;
    }

    public String getPlaceName() {
        return this.placeName;
    }

    public String getDate() {
        return this.date;
    }

    public String getTimeRange() {
        return this.timeRange;
    }

    public String getMapName() {
        return this.mapName;
    }

    public int getSessionId() {
        return this.sessionId;
    }

    public String toFileString(String mapName, int sessionId) {
        return mapName + "," + sessionId + "," + this.placeName + "," + this.eventName + "," + this.date + "," + this.timeRange + "," + this.moves + "," + this.hits + "," + String.format("%.2f", this.score);
    }
}
