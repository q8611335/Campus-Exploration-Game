

import java.util.ArrayList;
import utils.Constants;

public class ScoreHistory {

    private ArrayList<Score> scores;

    /**
     * constructs a new score history object and initialises the scores array with an initial size.
     */
    public ScoreHistory() {
        this.scores = new ArrayList<>();
    }

    /**
     * add a new score to the score history
     *
     * @param scoresToAdd score(s) to be added to the history
     */
    public void addScore(ArrayList<Score> scoresToAdd) {
        this.scores.addAll(scoresToAdd);
    }

    /**
     * prints the score history summary.
     */
    public void printSummary() {
        if (this.scores.isEmpty()) {
            System.out.println("No scores yet."); // Notes: Single strings need not be constants unless reused
        } else {
            System.out.printf(Constants.SCORE_HEADER_FORMATTER, "Map ID", "Place Name", "Event Name", "Date", "Time", "Moves", "Hits", "Score");
            System.out.println(Constants.SCORE_HEADER_LINE);

            for (Score s : this.scores) {
                s.printSummary();
            }
        }
    }

    /**
     * gets the list of scores
     * @return list of scores
     */
    public ArrayList<Score> getScores() {
        return new ArrayList<>(this.scores);
    }

    // Minimal inner Score class to ensure compilation; replace with the real Score implementation when available.
    // public static class Score {
    //     public void printSummary() {
    //         // placeholder implementation
    //         System.out.println("Score summary");
    //     }
    // }
}