import utils.Constants;


public class SportsCentre{
    
    //TODO: add any extra code if needed
    
    public void printSchedule() {
        //TODO: add any extra code if needed
        System.out.println("Facilities:");
        System.out.println(Constants.SPORTCENTRE_FORMAT_LINE);
        System.out.println(String.format(Constants.SPORTCENTRE_FORMATTER, "Gymnasium"));
        System.out.println(String.format(Constants.SPORTCENTRE_FORMATTER, "Swimming Pool"));
        System.out.println(String.format(Constants.SPORTCENTRE_FORMATTER, "Basketball Court"));
        System.out.println(String.format(Constants.SPORTCENTRE_FORMATTER, "Tennis Court"));
        System.out.println(String.format(Constants.SPORTCENTRE_FORMATTER, "Fitness Studio"));
        System.out.println(Constants.SPORTCENTRE_FORMAT_LINE);
    }

}
