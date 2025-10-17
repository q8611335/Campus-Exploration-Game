import enums.PlaceType;
import events.Event;
import java.util.ArrayList;

public class Place {
    private PlaceType placeType;
    private String name;
    private ArrayList<Event> events;
    private double score;
    private boolean visited;

    public Place(PlaceType placeType, String name, double score) {
        this.placeType = placeType;
        this.name = name;
        this.score = score;
        this.events = new ArrayList<>();
        this.visited = false;
    }

    public Place(Place place) {
        if (place != null) {
            this.placeType = place.getPlaceType();
            this.name = place.getName();
            this.score = place.getScore();
            this.visited = place.isVisited();
            this.events = new ArrayList<>();
            if (place.events != null) {
                this.events.addAll(place.events);
            }
        }
    }

    public void addEvent(Event event) {
        if (event != null) {
            this.events.add(event);
        }
    }

    public Event getEventById(int id) {
        for (Event event : this.events) {
            if (event != null && event.getId() == id) {
                return event;
            }
        }
        return null;
    }

    public boolean hasEvents() {
        return !this.events.isEmpty();
    }

    public boolean removeEventById(int id) {
        for (int i = 0; i < this.events.size(); i++) {
            if (this.events.get(i) != null && this.events.get(i).getId() == id) {
                this.events.remove(i);
                return true;
            }
        }
        return false;
    }

    public PlaceType getPlaceType() {
        return this.placeType;
    }

    public String getName() {
        return this.name;
    }

    public double getScore() {
        return this.score;
    }

    public ArrayList<Event> getEvents() {
        return new ArrayList<>(this.events);
    }

    public boolean isVisited() {
        return this.visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public void printEventSchedule() {
        if (this.events.isEmpty()) {
            System.out.println("No events scheduled at this location.");
            return;
        }

        System.out.println("Event Schedule:");
        System.out.println(utils.Constants.SCHEDULE_FORMAT_LINE);
        for (Event event : this.events) {
            if (event != null) {
                System.out.println(String.format(utils.Constants.EVENT_SCHEDULE_FORMATTER, 
                    "ID: " + event.getId(), 
                    event.getDate(), 
                    event.getStartTime(), 
                    event.getEndTime(), 
                    event.getEventDetails()));
            }
        }
        System.out.println(utils.Constants.SCHEDULE_FORMAT_LINE);
    }

    @Override
    public String toString() {
        return this.name + " (" + this.placeType + "), Score: " + this.score + ", Events: " + this.events.size();
    }
}

