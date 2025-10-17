package events;

public class Exam extends Event {
    private String courseCode;

    public Exam(String date, String startTime, String endTime, double score, String courseCode) {
        super(date, startTime, endTime, score, courseCode);
        this.courseCode = courseCode;
    }

    public Exam(Exam other) {
        super(other);
        if (other != null) {
            this.courseCode = other.courseCode;
        }
    }

    public String getCourseCode() {
        return this.courseCode;
    }

    @Override
    public String getEventDetails() {
        return "Exam: " + this.courseCode;
    }
}