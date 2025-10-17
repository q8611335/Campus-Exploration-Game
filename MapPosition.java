import enums.MapPositionType;


public class MapPosition {
    private MapPositionType type;
    private Place place;

    /**
     * Creates a map position
     * @param type type of the map position
     */
    public MapPosition(MapPositionType type) {
        this.type = type;
    }

    /**
     * Creates a map position and adds a place to the  map.
     * @param type type of the map position
     * @param place place to be added at the map position
     */
    public MapPosition(MapPositionType type, Place place) {
        this.type = type;
        this.place = place;
    }

    /**
     * Copy constructor for the map position, returns a new object by copying from the old one.
     * @param position position to be copied.
     */
    public MapPosition(MapPosition position) {
        if (position != null) {
            this.type = position.type;
            this.place = new Place(position.place);
        }
    }

    public MapPositionType getType() {
        return this.type;
    } //Notes: enums need not be deep copied

    public Place getPlace() {

        return new Place(this.place);
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public void setType(MapPositionType type) {
        this.type = type;
    }

    public void markPlaceVisited() {
        this.place.setVisited(true);
    }

    // public void removeEventById(int id) {
    //     this.place.removeEventById(id);
    // }
}
