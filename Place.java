
import enums.PlaceType;
import events.Event;

public class Place {
    private PlaceType placeType;       // LIBRARY, LECTURE_HALL, etc.
    private String name;               // Human-readable name
    private Event[] events;        // List of events happening at this place
    private double score;              // Score for visiting this place
    private int numOfEvents;
    private boolean visited;

    /**
     * Constructs a place object.
     * @param placeType Type of place
     * @param name name of the place
     * @param score score associated with the place.
     */
    public Place(PlaceType placeType, String name, double score) {
        this.placeType = placeType;
        this.name = name;
        this.score = score;
        this.events = new events.Event[2];
    }

    /**
     * Copy constructor for Place
     * @param place place to be copied
     */
    public Place(Place place) {
        if(place != null) {
            this.placeType = place.getPlaceType();
            this.name = place.getName();
            this.score = place.getScore();
            this.visited = place.isVisited();
            // if (place.getEvents() != null) {
            //     this.events = new Event[place.getEvents().length];
            //     for (int i = 0; i < place.getEvents().length; i++) {
            //         if( place.getEvents()[i]!= null) {
            //             this.events[i] = new Event(place.getEvents()[i]);
            //         }
            //     }
            // }
        }
    }

    /**
     * Adds an event to the list of events.
     * @param event new event to be added.
     */
    public void addEvent(Event event) {
        if(this.numOfEvents == this.events.length){
            this.events = resizeArray(this.events, this.events.length* 2);
        }
        this.events[numOfEvents++] = event;
    }

    /**
     * gets the details of the events by event id
     * @param id event id
     * @return copy of Event object
     */
    // public Event getEventById(int id) {
    //     if(this.events != null && this.events.length > 0 ){
    //         for(int i = 0; i< this.events.length; i++){
    //             if(this.events[i] != null && this.events[i].getId() == id){
    //                 return new Event(this.events[i]); // use copy constructor to return the object to avoid privacy leak
    //             }
    //         }
    //     }
    //     return null;
    // }

    /**
     * checks if the events array still has events to be visited.
     * @return true if events array had one or more events.
     */
    public boolean hasEvents() {
        if(this.events != null ){
            for(Event event: this.events){
                if (event != null)
                    return true;
            }
        }
        return false;
    }

    /**
     * Removes an event from a list of events by event id.
     * @param id event id to be removed.
     * @return whether the event has been removed.
     */
    // public boolean removeEventById(int id) {
    //     boolean removed = false;
    //     if(this.events != null && this.events.length > 0 ){
    //         for(int i = 0; i< this.events.length; i++){
    //             if(this.events[i] != null && this.events[i].getId() == id){
    //                 this.events[i] = null;
    //                 removed = true;
    //             }
    //             if(removed && i< this.events.length -1){ // if removed move the rest of elements one up by copying i+1 to ith position
    //                 this.events[i] = this.events[i+1];
    //             }
    //         }
    //         if(removed){
    //             this.events[this.events.length -1] = null; // if removed (and copied) set the last position in the array to null.
    //         }
    //     }
    //     return removed;
    // }

    public PlaceType getPlaceType() {
        return this.placeType;
    }

    public String getName() {
        return this.name;
    }

    public double getScore() {
        return this.score;
    }

    // public Event[] getEvents() {
    //     //Note: return a copy of events
    //     if(this.events != null) {
    //         Event[] newEvents = new Event[this.events.length];
    //         for (int i = 0; i < this.events.length; i++) {
    //             if(this.events[i] != null) {
    //                 newEvents[i] = new Event(this.events[i]); //Note: use copy constructor
    //             }
    //         }
    //         return newEvents;
    //     }
    //     return null;
    // }

    public boolean isVisited() {
        return this.visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    @Override
    public String toString() {
        return this.name + " (" + this.placeType + "), Score: " + this.score + ", Events: " + this.events.length;
    }

    //resize array based on the new size.
    private Event[] resizeArray(Event[] oldArray, int newSize ) {
        Event[] newArray = new Event[newSize];
        for (int i = 0; i < oldArray.length; i++) {
            newArray[i] = oldArray[i]; // no deep copying required here as it is used internally in this class.
        }

        return newArray;
    }



}
