import java.io.*;
import utils.Constants;
import utils.Messages;

public class CampusNavigatorEngine {
    private  final int NUM_ROWS = 4;
    private  final int NUM_COLS = 4;
    private final int NUM_OF_CMD_ARGS = 5; //rows, cols, sessionID, mapFile, eventsFile

    public static void main(String[] args) {
        try { CampusNavigatorEngine nav = new CampusNavigatorEngine();
        //invoke any method from this class as nav.Methodname();
        // Validate command-line arguments
        if (nav.validateCmdArgs(args)){
            Messages.printWelcome();
            nav.runMainMenuLoop(args);
        }
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

       
    }

    // validates the command line arguments
    private boolean validateCmdArgs(String[] args) {
        if (args.length != NUM_OF_CMD_ARGS) { 
            System.out.println("Invalid number of Command Line Arguments. Usage: java CampusNavigatorEngine <rows> <cols> <session id> <location file> <events file>");
            return false;            
        }
        
        int rows = Integer.parseInt(args[0]);
        int cols = Integer.parseInt(args[1]);
        if (rows < NUM_ROWS || cols < NUM_COLS) {
            System.out.println("Error: Rows and columns must be at least 4 to allow proper map layout.");
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

    //runs the main menu loop
    private  void runMainMenuLoop(String[] args) throws Exception {
        int rows = Integer.parseInt(args[0]); //Note:  array indices need not be constant. they dont hold any meaning. they are just oreder
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
            // each block inside switch case is a small method
            // and each method is delegated to respective classes/objects. no privacy leaks
            switch (choice) {
                case Constants.MENU_VISIT:  // Note: Switch case labels are constants and not enums, and meaningful. Do not name as OPTION_1 and so on.
                    campusMap.visitCampus(Constants.keyboard, student);
                    break;
                case Constants.MENU_PRINT_SCHEDULE: 
                    // if (student.isSessionPaused()) {
                    //     campusMap.visitCampus(Constants.keyboard, student);
                    // } else {
                    //     System.out.println("No session to resume.");
                    // }
                    // break;
                case Constants.MENU_SCORE://4. view score history
                    //student.printSummary();
                    break;
                case Constants.MENU_VIEW_PREVIOUS_SESSIONS: 
                    // student.reset();
                    // campusMap.reset();
                    // System.out.println("Session and map reset.");
                    // break;
                case Constants.MENU_EXIT:
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



