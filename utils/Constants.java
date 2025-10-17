package utils;

import enums.MapPositionType;
import java.util.Scanner;

public final class Constants {

    public static final char PLAYER_SYMBOL = 'P';
    public static final char CAFETERIA_SYMBOL = 'C';   
    public static final char LIBRARY_SYMBOL = 'L';   
    public static final char SPORTS_CENTRE_SYMBOL = 'G';
    public static final char EMPTY_EVENT_SYMBOL = 'E';   // Used when PlaceType is EVENT but no events remain
    public static final char DEFAULT_SYMBOL = ' ';
    public static final String SCORE_SUMMARY_FORMATTER = "| %-15s | %-15s | %-30s | %-30s | %-10s | %-12s | %-6d | %-7d | %7.2f |\n";
    public static final String SCORE_HEADER_FORMATTER = "| %-15s | %-15s | %-30s | %-30s | %-10s | %-12s | %-6s | %-7s | %-7s |\n";
    public static final String SCORE_HEADER_LINE = "|" + "=".repeat(17) +"|" + "=".repeat(17) + "|" + "=".repeat(32) + "|"+ "=".repeat(32) + "|" + "=".repeat(12) + "|" + "=".repeat(14) + "|" + "=".repeat(8) + "|" + "=".repeat(9) + "|" + "=".repeat(9) + "|";

   
    public static final String SCHEDULE_FORMAT_LINE = "-".repeat(66);
    public static final String EVENT_SCHEDULE_FORMATTER = "| %-7s | %10s | %5s-%5s | %25s |";
    public static final String MENU_FORMATTER = "| %10s | %6s |";
    public static final String MENU_FORMAT_LINE = "-".repeat(23);
    public static final String SPORTCENTRE_FORMAT_LINE = "-".repeat(23);
    public static final String SPORTCENTRE_FORMATTER = "| %-19s |";
    
    public static final String MOVE_UP = "U";
    public static final String MOVE_DOWN = "D";
    public static final String MOVE_LEFT = "L";
    public static final String MOVE_RIGHT = "R";
    
    //need to change 3 to 5
    public  static  final String MENU_VISIT = "1";
    public  static  final String MENU_PRINT_SCHEDULE = "2";
    public  static  final String MENU_BOOK_PLACE = "3";
    public  static  final String MENU_SCORE = "4";
    public  static  final String MENU_VIEW_PREVIOUS_SESSIONS = "5";
    public  static  final String MENU_EXIT = "6";


    public static final Scanner keyboard = new Scanner(System.in);
    
    public static char getSymbol(MapPositionType type) {
        switch (type) {
            case BOUNDARY: return '#';
            case OPEN: return '.';
            case RESTRICTED: return 'X';  // Previously was '-'
            case PLACE: return '@';       
            case START: return 'S';
            default: return DEFAULT_SYMBOL;
        }
    }
}
