package model;

import java.util.Calendar;
import java.util.Date;


// Represents a job application tracker event
// Based on AlarmSystem project provided by the CPSC 210 teaching team
public class Event {
    private static final int HASH_CONSTANT = 13; // the integer for calculating this event's hashcode
    private Date dateLogged; // the date this event was logged
    private String description; // the description of this event

    /*
     * EFFECTS: creates an event with given description and the current date and time stamp
     */
    public Event(String description) {
        dateLogged = Calendar.getInstance().getTime();
        this.description = description;
    }

    /*
     * EFFECTS: returns the event date
     */
    public Date getDate() {
        return dateLogged;
    }

    /*
     * EFFECTS: returns the event description
     */
    public String getDescription() {
        return description;
    }

    /*
     * EFFECTS: returns true if the given other is an instance of an event and has the same date logged and description
     */
    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }

        if (other.getClass() != this.getClass()) {
            return false;
        }

        Event otherEvent = (Event) other;

        return (this.dateLogged.equals(otherEvent.dateLogged) && this.description.equals(otherEvent.description));
    }

    /*
     * EFFECTS: returns an integer hashcode for this event
     */
    @Override
    public int hashCode() {
        return (HASH_CONSTANT * dateLogged.hashCode() + description.hashCode());
    }

    /*
     * EFFECTS: returns a string representation for this event, including a date and description
     */
    @Override
    public String toString() {
        return dateLogged.toString() + "\n" + description;
    }
}
