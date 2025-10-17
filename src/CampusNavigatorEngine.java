import java.io.*;
import utils.Constants;
import utils.Messages;

public class CampusNavigatorEngine {
    private final int NUM_ROWS = 4;
    private final int NUM_COLS = 4;
    private final int NUM_OF_CMD_ARGS = 5;

    public static void main(String[] args) {
        CampusNavigatorEngine nav = new CampusNavigatorEngine();
        if (nav.validateCmdArgs(args)) {
            Messages.printWelcome();
            try {
                nav.runMainMenuLoop(args);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private boolean validateCmdArgs(String[] args) {
        if (args.length != NUM_OF_CMD_ARGS) {
            System.out.println("Invalid number of Command Line Arguments. Usage: java CampusNavigatorEngine <rows> <cols> <session id> <location file> <events file>");
            return false;
        }

        try {
            int rows = Integer.parseInt(args[0]);
            int cols = Integer.parseInt(args[1]);
            
            if (rows < NUM_ROWS || cols < NUM_COLS) {
                System.out.println("Error: Rows and columns must be at least 4 to allow proper map layout.");
                return false;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid number of Command Line Arguments. Usage: java CampusNavigatorEngine <rows> <cols> <session id> <location file> <events file>");
            return false;
        }

        try {
            new FileInputStream(args[3]);
            new FileInputStream(args[4]);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to process file. Exiting program.");
            return false;
        }

        return true;
    }

    private void runMainMenuLoop(String[] args) throws Exception {
        int rows = Integer.parseInt(args[0]);
        int cols = Integer.parseInt(args[1]);
        int sessionID = Integer.parseInt(args[2]);
        String mapFile = args[3];
        String eventsFile = args[4];
        
        Student student = new Student();
        CampusMap campusMap = new CampusMap(rows, cols, sessionID, mapFile, eventsFile);

        boolean exit = false;
        while (!exit) {
            Messages.printMainMenuCommands();
            String choice = Constants.keyboard.nextLine();
            
            switch (choice) {
                case Constants.MENU_VISIT:
                    campusMap.visitCampus(Constants.keyboard, student);
                    break;
                    
                case Constants.MENU_PRINT_SCHEDULE:
                    campusMap.printSchedule(Constants.keyboard);
                    break;
                    
                case Constants.MENU_BOOK_PLACE:
                    campusMap.bookPlace(Constants.keyboard);
                    break;
                    
                case Constants.MENU_SCORE:
                    campusMap.viewCurrentScoreHistory();
                    break;
                    
                case Constants.MENU_VIEW_PREVIOUS_SESSIONS:
                    campusMap.viewPreviousSessionScores();
                    break;
                    
                case Constants.MENU_EXIT:
                    campusMap.saveAndExit();
                    System.out.println("Session abandoned.");
                    System.out.println("Goodbye for now. Visit Again.");
                    exit = true;
                    break;
                    
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }
        }
    }
}



