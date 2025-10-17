package events;


public class Seminar extends Event {
    protected int id;
    protected String date;
    protected String startTime;
    protected String endTime;
    protected double score;

    public Seminar(String date, String startTime, String endTime, double score) {
        super(date, startTime, endTime, score);
    }
}