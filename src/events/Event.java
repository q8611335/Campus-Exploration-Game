package events;

public abstract class Event {
    private static int nextId = 1;
    protected int id;
    protected String date;
    protected String startTime;
    protected String endTime;
    protected double score;
    protected String name;

    public Event(String date, String startTime, String endTime, double score, String name) {
        this.id = nextId++;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.score = score;
        this.name = name;
    }

    public Event(Event other) {
        if (other != null) {
            this.id = other.id;
            this.date = other.date;
            this.startTime = other.startTime;
            this.endTime = other.endTime;
            this.score = other.score;
            this.name = other.name;
        }
    }

    public int getId() {
        return this.id;
    }

    public String getDate() {
        return this.date;
    }

    public String getStartTime() {
        return this.startTime;
    }

    public String getEndTime() {
        return this.endTime;
    }

    public double getScore() {
        return this.score;
    }

    public String getName() {
        return this.name;
    }

    public abstract String getEventDetails();
    
    public String getFormattedTime() {
        return this.startTime + "-" + this.endTime;
    }
}
