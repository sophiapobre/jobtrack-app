package persistence;

import org.json.JSONObject;

// Represents the framework for Writable objects
public interface Writable {
    /*
     * EFFECTS: returns this as a JSON object
     */
    JSONObject toJson();
}
