public class EventHall implements Bookable {
    private String name;
    
    public EventHall(String name) {
        this.name = name;
    }

    @Override
    public void book() {
        System.out.println("You have booked " + this.name + " for professional use.");
    }

    @Override
    public String getBookingDetails() {
        return "Event Hall: " + this.name;
    }
}
