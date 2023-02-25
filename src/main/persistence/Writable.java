package persistence;

import org.json.JSONObject;

// Represents the framework for Writable objects
// Based on JsonSerializationDemo-master project provided by the CPSC 210 teaching team
public interface Writable {
    /*
     * EFFECTS: returns this as a JSON object
     */
    JSONObject toJson();
}
