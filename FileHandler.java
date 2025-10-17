

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class FileHandler{

    //write a method to read from files
    public ArrayList<String> readFile(String mapName) throws IOException {
        ArrayList<String> fileContent = new ArrayList<>();
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File(mapName));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                fileContent.add(line);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Unable to process file. Exiting program.");
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
        return fileContent;
    }

    //write a method to write into files.
    // public void writeFile(String fileName, ArrayList<String> fileContent) throws IOException {
    //     Constants.keyboard = null;
    //     try{
    //         Constants.keyboard = new Scanner(new FileWriter(fileName));
    //         for(String line : fileContent){
    //             Constants.keyboard .write(line);
    //         }
    //         Constants.keyboard.flush();
    //     } finally {
    //         if(Constants.keyboard != null){
    //             Constants.keyboard.close();
    //         }
    //     }
    // }
}