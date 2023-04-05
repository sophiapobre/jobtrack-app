package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

// Represents a log of job application tracker events.
// Based on AlarmSystem project provided by the CPSC 210 teaching team
public class EventLog implements Iterable<Event> {
    private static EventLog theLog; // the only event log in the system
    private Collection<Event> events; // the collection of events in the event log

    /*
     * EFFECTS: creates an event log with an empty list of events
     */
    private EventLog() {
        events = new ArrayList<Event>();
    }

    /*
     * MODIFIES: this
     * EFFECTS: creates an event log if it doesn't already exist, returns the event log
     */
    public static EventLog getInstance() {
        if (theLog == null) {
            theLog = new EventLog();
        }

        return theLog;
    }

    /*
     * MODIFIES: this
     * EFFECTS: adds the given e to the event log
     */
    public void logEvent(Event e) {
        events.add(e);
    }

    /*
     * MODIFIES: this
     * EFFECTS: clears the event log and logs the clearing as an event
     */
    public void clear() {
        events.clear();
        logEvent(new Event("Event log cleared."));
    }

    /*
     * EFFECTS: returns an iterator over the events in the event log
     */
    @Override
    public Iterator<Event> iterator() {
        return events.iterator();
    }
}
