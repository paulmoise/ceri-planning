package fr.ceri.prototypeinterface.ceriplanning.helper;

import java.time.LocalTime;

public class Event {
    private LocalTime startTime;
    private LocalTime endTime;
    private String name;
    private String eventType;

    // Constructor
    public Event(LocalTime startTime, LocalTime endTime, String name, String eventType) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.name = name;
        this.eventType = eventType;
    }

    // Getters
    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public String getName() {
        return name;
    }

    public String getEventType() {
        return eventType;
    }
}
