

import utils.Constants;
public class Score {
    private final int moves;
    private final int hits;
    private final double score;
    private final String eventName;
    private final String placeName;
    private final String date;
    private final String timeRange;
    private final int visitNumber;
    

    /**
     * Constructs a score object
     * @param visit number of visit
     * @param moves number of moves made on the map
     * @param hits number of hits on the map
     * @param score score for the place/event
     * @param eventName name of the event visited
     * @param placeName name of the place visited
     * @param date date of the event visited
     * @param timeRange time of the event visited
     */
    public Score(int visit, int moves, int hits, double score, String eventName, String placeName, String date, String timeRange) {
        this.visitNumber = visit;
        this.moves = moves;
        this.hits = hits;
        this.score = score;
        this.placeName = placeName;
        this.eventName = eventName;
        this.date = date;
        this.timeRange = timeRange;
    }

    /**
     * prints the score summary
     */
    public void printSummary() {
        System.out.printf(Constants.SCORE_SUMMARY_FORMATTER, this.visitNumber, this.placeName, this.eventName, this.date, this.timeRange, this.moves, this.hits, this.score);
    }
}
