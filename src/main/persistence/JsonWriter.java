package persistence;

import model.JobApplicationTracker;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

// Represents a writer that writes the JSON representation of a job application tracker to file
// Based on JsonSerializationDemo-master project provided by the CPSC 210 teaching team
public class JsonWriter {
    private static final int TAB = 4; // the number of spaces in a tab
    private PrintWriter writer; // the writer object
    private String destination; // the destination file

    /*
     * EFFECTS: constructs a writer to write to the given destination file
     */
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    /*
     * MODIFIES: this
     * EFFECTS: opens the writer, throws FileNotFoundException if an error occurs while the destination file is
     *          being opened
     */
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    /*
     * MODIFIES: this
     * EFFECTS: writes the JSON representation of the given job application tracker to the destination file
     */
    public void write(JobApplicationTracker tracker) {
        JSONObject json = tracker.toJson();
        saveToFile(json.toString(TAB));
    }

    /*
     * MODIFIES: this
     * EFFECTS: closes the writer
     */
    public void close() {
        writer.close();
    }

    /*
     * MODIFIES: this
     * EFFECTS: writes the given string to the destination file
     */
    private void saveToFile(String json) {
        writer.print(json);
    }
}
