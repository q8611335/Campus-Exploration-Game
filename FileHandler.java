import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import enums.PlaceType;
import events.*;

public class FileHandler {
    private static final String SESSION_SCORES_FILE = "data/session_scores.txt";

    public ArrayList<String[]> readMapFile(String mapFile, int rows, int cols) throws IOException, InvalidMapException {
        ArrayList<String[]> validLines = new ArrayList<>();
        File file = new File(mapFile);
        Scanner scanner = new Scanner(file);
        int lineNumber = 0;
        boolean hasHeader = false;

        while (scanner.hasNextLine()) {
            lineNumber++;
            String line = scanner.nextLine().trim();
            
            if (lineNumber == 1) {
                hasHeader = true;
                continue;
            }
            
            if (line.isEmpty()) {
                continue;
            }

            try {
                String[] parts = line.split(",", -1);
                
                if (parts.length < 6) {
                    throw new InvalidLineException("Invalid line for maps. Skipping this line " + lineNumber + " from the maps file.");
                }

                int row = Integer.parseInt(parts[0].trim());
                int col = Integer.parseInt(parts[1].trim());
                
                if (row <= 0 || row >= rows - 1) {
                    throw new InvalidFormatException("Invalid row format. Skipping this line " + lineNumber + " from the maps file.");
                }
                if (col <= 0 || col >= cols - 1) {
                    throw new InvalidFormatException("Invalid column format. Skipping this line " + lineNumber + " from the maps file.");
                }

                String placeTypeStr = parts[2].trim();
                if (placeTypeStr.isEmpty()) {
                    throw new DataNotFoundException("Place type cannot be empty. Skipping this line " + lineNumber + " from the maps file.");
                }

                PlaceType placeType;
                try {
                    placeType = PlaceType.valueOf(placeTypeStr.toUpperCase());
                } catch (IllegalArgumentException e) {
                    throw new InvalidFormatException("Invalid place type format. Skipping this line " + lineNumber + " from the maps file.");
                }

                String placeName = parts[3].trim();
                if (placeName.isEmpty()) {
                    throw new DataNotFoundException("Place name cannot be empty. Skipping this line " + lineNumber + " from the maps file.");
                }

                String scoreStr = parts[4].trim();
                if (scoreStr.isEmpty()) {
                    throw new DataNotFoundException("Score cannot be empty. Skipping this line " + lineNumber + " from the maps file.");
                }

                double score;
                try {
                    score = Double.parseDouble(scoreStr);
                    if (placeType == PlaceType.CAFETERIA && score >= 0) {
                        throw new InvalidFormatException("Invalid score format. Skipping this line " + lineNumber + " from the maps file.");
                    }
                } catch (NumberFormatException e) {
                    throw new InvalidFormatException("Invalid score format. Skipping this line " + lineNumber + " from the maps file.");
                }

                String restricted = parts[5].trim();
                if (restricted.isEmpty()) {
                    throw new DataNotFoundException("Restricted field cannot be empty. Skipping this line " + lineNumber + " from the maps file.");
                }

                validLines.add(parts);

            } catch (InvalidLineException | InvalidFormatException | DataNotFoundException e) {
                System.out.println(e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println("Invalid row format. Skipping this line " + lineNumber + " from the maps file.");
            }
        }
        scanner.close();

        if (validLines.isEmpty() && hasHeader) {
            throw new InvalidMapException("No places found in map file. Exiting program.");
        }

        return validLines;
    }

    public ArrayList<String[]> readEventsFile(String eventsFile, int rows, int cols) throws IOException {
        ArrayList<String[]> validLines = new ArrayList<>();
        File file = new File(eventsFile);
        Scanner scanner = new Scanner(file);
        int lineNumber = 0;

        while (scanner.hasNextLine()) {
            lineNumber++;
            String line = scanner.nextLine().trim();
            
            if (lineNumber == 1) {
                continue;
            }
            
            if (line.isEmpty()) {
                continue;
            }

            try {
                String[] parts = line.split(",", -1);
                
                if (parts.length < 9) {
                    throw new InvalidLineException("Invalid line for events. Skipping this line " + lineNumber + " from the events file.");
                }

                int row = Integer.parseInt(parts[0].trim());
                int col = Integer.parseInt(parts[1].trim());
                
                if (row <= 0 || row >= rows - 1) {
                    throw new InvalidFormatException("Invalid row format. Skipping this line " + lineNumber + " from the events file.");
                }
                if (col <= 0 || col >= cols - 1) {
                    throw new InvalidFormatException("Invalid column format. Skipping this line " + lineNumber + " from the events file.");
                }

                String eventTypeStr = parts[2].trim();
                if (eventTypeStr.isEmpty()) {
                    throw new DataNotFoundException("Event type cannot be empty. Skipping this line " + lineNumber + " from the events file.");
                }

                if (!eventTypeStr.equalsIgnoreCase("LECTURE") && !eventTypeStr.equalsIgnoreCase("SEMINAR") && !eventTypeStr.equalsIgnoreCase("EXAM")) {
                    throw new InvalidFormatException("Invalid event type format. Skipping this line " + lineNumber + " from the events file.");
                }

                String date = parts[3].trim();
                if (date.isEmpty()) {
                    throw new DataNotFoundException("Date cannot be empty. Skipping this line " + lineNumber + " from the events file.");
                }
                if (!isValidDate(date)) {
                    throw new InvalidFormatException("Invalid date format. Skipping this line " + lineNumber + " from the events file.");
                }

                String startTime = parts[4].trim();
                if (startTime.isEmpty()) {
                    throw new DataNotFoundException("Start time cannot be empty. Skipping this line " + lineNumber + " from the events file.");
                }
                if (!isValidTime(startTime)) {
                    throw new InvalidFormatException("Invalid start time format. Skipping this line " + lineNumber + " from the events file.");
                }

                String endTime = parts[5].trim();
                if (endTime.isEmpty()) {
                    throw new DataNotFoundException("End time cannot be empty. Skipping this line " + lineNumber + " from the events file.");
                }
                if (!isValidTime(endTime)) {
                    throw new InvalidFormatException("Invalid end time format. Skipping this line " + lineNumber + " from the events file.");
                }

                String scoreStr = parts[6].trim();
                if (scoreStr.isEmpty()) {
                    throw new DataNotFoundException("Score cannot be empty. Skipping this line " + lineNumber + " from the events file.");
                }

                try {
                    Double.parseDouble(scoreStr);
                } catch (NumberFormatException e) {
                    throw new InvalidFormatException("Invalid score format. Skipping this line " + lineNumber + " from the events file.");
                }

                String eventName = parts[7].trim();
                if (eventName.isEmpty()) {
                    throw new DataNotFoundException("Event name cannot be empty. Skipping this line " + lineNumber + " from the events file.");
                }

                validLines.add(parts);

            } catch (InvalidLineException | InvalidFormatException | DataNotFoundException e) {
                System.out.println(e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println("Invalid row format. Skipping this line " + lineNumber + " from the events file.");
            }
        }
        scanner.close();

        return validLines;
    }

    public ArrayList<Score> readSessionScoresFile() throws IOException {
        ArrayList<Score> scores = new ArrayList<>();
        File file = new File(SESSION_SCORES_FILE);
        
        if (!file.exists()) {
            return scores;
        }

        Scanner scanner = new Scanner(file);
        int lineNumber = 0;

        while (scanner.hasNextLine()) {
            lineNumber++;
            String line = scanner.nextLine().trim();
            
            if (lineNumber == 1) {
                continue;
            }
            
            if (line.isEmpty()) {
                continue;
            }

            try {
                String[] parts = line.split(",", -1);
                
                if (parts.length < 9) {
                    throw new InvalidLineException("Invalid line for session score. Skipping this line " + lineNumber + " from the session score file.");
                }

                String mapName = parts[0].trim();
                int sessionId = Integer.parseInt(parts[1].trim());
                String placeName = parts[2].trim();
                String eventName = parts[3].trim();
                String date = parts[4].trim();
                String timeRange = parts[5].trim();
                int moves = Integer.parseInt(parts[6].trim());
                int hits = Integer.parseInt(parts[7].trim());
                double score = Double.parseDouble(parts[8].trim());

                scores.add(new Score(mapName, sessionId, moves, hits, score, placeName, eventName, date, timeRange));

            } catch (InvalidLineException e) {
                System.out.println(e.getMessage());
            } catch (NumberFormatException e) {
            }
        }
        scanner.close();

        return scores;
    }

    public void writeSessionScores(ArrayList<Score> scores, String mapName, int sessionId) throws IOException {
        File file = new File(SESSION_SCORES_FILE);
        PrintWriter writer = new PrintWriter(new FileWriter(file, true));

        for (Score score : scores) {
            writer.println(score.toFileString(mapName, sessionId));
        }

        writer.flush();
        writer.close();
    }

    public void writeEventsFile(String eventsFile, ArrayList<Event> events, ArrayList<Integer> rows, ArrayList<Integer> cols) throws IOException {
        File file = new File(eventsFile);
        PrintWriter writer = new PrintWriter(new FileWriter(file));

        writer.println("row_id,col_id,event_type,date,start_time,end_time,score,name,speaker");

        for (int i = 0; i < events.size(); i++) {
            Event event = events.get(i);
            int row = rows.get(i);
            int col = cols.get(i);

            StringBuilder sb = new StringBuilder();
            sb.append(row).append(",");
            sb.append(col).append(",");

            if (event instanceof Lecture) {
                Lecture lecture = (Lecture) event;
                sb.append("lecture,");
                sb.append(event.getDate()).append(",");
                sb.append(event.getStartTime()).append(",");
                sb.append(event.getEndTime()).append(",");
                sb.append(event.getScore()).append(",");
                sb.append(lecture.getCourseCode()).append(",");
                sb.append(lecture.getLecturer());
            } else if (event instanceof Seminar) {
                Seminar seminar = (Seminar) event;
                sb.append("seminar,");
                sb.append(event.getDate()).append(",");
                sb.append(event.getStartTime()).append(",");
                sb.append(event.getEndTime()).append(",");
                sb.append(event.getScore()).append(",");
                sb.append(event.getName()).append(",");
                String[] speakers = seminar.getSpeakers();
                for (int j = 0; j < speakers.length; j++) {
                    sb.append(speakers[j]);
                    if (j < speakers.length - 1) {
                        sb.append("#");
                    }
                }
            } else if (event instanceof Exam) {
                Exam exam = (Exam) event;
                sb.append("exam,");
                sb.append(event.getDate()).append(",");
                sb.append(event.getStartTime()).append(",");
                sb.append(event.getEndTime()).append(",");
                sb.append(event.getScore()).append(",");
                sb.append(exam.getCourseCode()).append(",");
            }

            writer.println(sb.toString());
        }

        writer.flush();
        writer.close();
    }

    private boolean isValidDate(String date) {
        if (!date.matches("\\d{4}-\\d{2}-\\d{2}")) {
            return false;
        }
        return true;
    }

    private boolean isValidTime(String time) {
        if (!time.matches("\\d{2}:\\d{2}")) {
            return false;
        }
        String[] parts = time.split(":");
        try {
            int hour = Integer.parseInt(parts[0]);
            int minute = Integer.parseInt(parts[1]);
            return hour >= 0 && hour <= 23 && minute >= 0 && minute <= 59;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
