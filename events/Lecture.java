package events;

public class Lecture extends Event {
    private String courseCode;
    private String lecturer;

    public Lecture(String date, String startTime, String endTime, double score, String courseCode, String lecturer) {
        super(date, startTime, endTime, score, courseCode);
        this.courseCode = courseCode;
        this.lecturer = lecturer;
    }

    public Lecture(Lecture other) {
        super(other);
        if (other != null) {
            this.courseCode = other.courseCode;
            this.lecturer = other.lecturer;
        }
    }

    public String getCourseCode() {
        return this.courseCode;
    }

    public String getLecturer() {
        return this.lecturer;
    }

    @Override
    public String getEventDetails() {
        return "Lecture: " + this.courseCode + " by " + this.lecturer;
    }
}