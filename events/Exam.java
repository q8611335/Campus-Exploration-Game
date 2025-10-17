package events;

public class Exam extends Event {
    protected int id;
    protected String date;
    protected String startTime;
    protected String endTime;
    protected double score;

    public Exam(String date, String startTime, String endTime, double score) {
        super(date, startTime, endTime, score);
    }
}