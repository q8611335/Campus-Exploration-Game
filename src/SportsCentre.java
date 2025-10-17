import utils.Constants;

public class SportsCentre implements Bookable {
    private String name;
    
    public SportsCentre(String name) {
        this.name = name;
    }
    
    public void printSchedule() {
        System.out.println("Facilities:");
        System.out.println(Constants.SPORTCENTRE_FORMAT_LINE);
        System.out.println(String.format(Constants.SPORTCENTRE_FORMATTER, "Gymnasium"));
        System.out.println(String.format(Constants.SPORTCENTRE_FORMATTER, "Swimming Pool"));
        System.out.println(String.format(Constants.SPORTCENTRE_FORMATTER, "Basketball Court"));
        System.out.println(String.format(Constants.SPORTCENTRE_FORMATTER, "Tennis Court"));
        System.out.println(String.format(Constants.SPORTCENTRE_FORMATTER, "Fitness Studio"));
        System.out.println(Constants.SPORTCENTRE_FORMAT_LINE);
    }

    @Override
    public void book() {
        System.out.println("You have booked " + this.name + " for professional use.");
    }

    @Override
    public String getBookingDetails() {
        return "Sports Centre: " + this.name;
    }
}
