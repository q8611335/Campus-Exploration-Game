import java.util.ArrayList;

public class SessionManager {
    private int currentSessionId;
    private String mapName;
    private ArrayList<Score> currentSessionScores;
    private ArrayList<ArrayList<Score>> previousSessions;

    public SessionManager(int sessionId, String mapName) {
        this.currentSessionId = sessionId;
        this.mapName = mapName;
        this.currentSessionScores = new ArrayList<>();
        this.previousSessions = new ArrayList<>();
    }

    public void addScore(Score score) {
        this.currentSessionScores.add(score);
    }

    public void addPreviousSessionScores(ArrayList<Score> scores) {
        if (scores != null && !scores.isEmpty()) {
            this.previousSessions.add(new ArrayList<>(scores));
        }
    }

    public ArrayList<Score> getCurrentSessionScores() {
        return new ArrayList<>(this.currentSessionScores);
    }

    public ArrayList<ArrayList<Score>> getPreviousSessionScores() {
        return new ArrayList<>(this.previousSessions);
    }

    public int getCurrentSessionId() {
        return this.currentSessionId;
    }

    public String getMapName() {
        return this.mapName;
    }

    public void printCurrentSessionScores() {
        if (this.currentSessionScores.isEmpty()) {
            System.out.println("No scores in the current session.");
        } else {
            System.out.println("Current Session Score History:");
            System.out.printf(utils.Constants.SCORE_HEADER_FORMATTER, "Map Name", "Session ID", "Place Name", "Event Name", "Date", "Time Range", "Moves", "Hits", "Score");
            System.out.println(utils.Constants.SCORE_HEADER_LINE);
            for (Score score : this.currentSessionScores) {
                score.printSummary(this.mapName, this.currentSessionId);
            }
        }
    }

    public void printAllSessionScores() {
        if (this.previousSessions.isEmpty()) {
            System.out.println("No previous session scores found.");
        } else {
            System.out.println("Previous Session Score History:");
            System.out.printf(utils.Constants.SCORE_HEADER_FORMATTER, "Map Name", "Session ID", "Place Name", "Event Name", "Date", "Time Range", "Moves", "Hits", "Score");
            System.out.println(utils.Constants.SCORE_HEADER_LINE);
            for (ArrayList<Score> sessionScores : this.previousSessions) {
                for (Score score : sessionScores) {
                    score.printSummary();
                }
            }
        }
    }
}
