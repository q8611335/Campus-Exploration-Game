package events;


public class Lecture extends Event {
    protected int id;
    protected String date;
    protected String startTime;
    protected String endTime;
    protected double score;

    public Lecture(String date, String startTime, String endTime, double score) {
        super(date, startTime, endTime, score);
    }
}