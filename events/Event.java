package events;

public abstract class Event {
    protected int id;
    protected String date;
    protected String startTime;
    protected String endTime;
    protected double score;

    public Event(String date, String startTime, String endTime, double score) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.score = score;
    }

    //ADD Abstract events here

}
