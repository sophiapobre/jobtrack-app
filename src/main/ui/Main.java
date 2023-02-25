package ui;

import java.io.FileNotFoundException;

// Represents the class containing the main method
public class Main {

    /*
     * EFFECTS: creates a new JobTrackApp, prints error message if file requested by user cannot be found
     */
    public static void main(String[] args) {
        try {
            new JobTrackApp();
        } catch (FileNotFoundException fnfe) {
            System.out.println("ERROR: Cannot load application because the file was not found.");
        }
    }
}
