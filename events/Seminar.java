package events;

public class Seminar extends Event {
    private String[] speakers;

    public Seminar(String date, String startTime, String endTime, double score, String name, String[] speakers) {
        super(date, startTime, endTime, score, name);
        this.speakers = speakers;
    }

    public Seminar(Seminar other) {
        super(other);
        if (other != null && other.speakers != null) {
            this.speakers = new String[other.speakers.length];
            for (int i = 0; i < other.speakers.length; i++) {
                this.speakers[i] = other.speakers[i];
            }
        }
    }

    public String[] getSpeakers() {
        if (this.speakers == null) {
            return null;
        }
        String[] copy = new String[this.speakers.length];
        for (int i = 0; i < this.speakers.length; i++) {
            copy[i] = this.speakers[i];
        }
        return copy;
    }

    @Override
    public String getEventDetails() {
        StringBuilder sb = new StringBuilder("Seminar: " + this.name + " by ");
        if (this.speakers != null && this.speakers.length > 0) {
            for (int i = 0; i < this.speakers.length; i++) {
                sb.append(this.speakers[i]);
                if (i < this.speakers.length - 1) {
                    sb.append(", ");
                }
            }
        }
        return sb.toString();
    }
}