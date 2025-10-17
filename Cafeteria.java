import utils.Constants;

public class Cafeteria {
    
    public void printSchedule() {
        System.out.println("Menu:");
        System.out.println(Constants.MENU_FORMAT_LINE);
        System.out.println(String.format(Constants.MENU_FORMATTER, "coffee", "$2.50"));
        System.out.println(String.format(Constants.MENU_FORMATTER, "tea", "$2.00"));
        System.out.println(String.format(Constants.MENU_FORMATTER, "sandwich", "$4.50"));
        System.out.println(String.format(Constants.MENU_FORMATTER, "muffin", "$2.75"));
        System.out.println(Constants.MENU_FORMAT_LINE);
    }
}

