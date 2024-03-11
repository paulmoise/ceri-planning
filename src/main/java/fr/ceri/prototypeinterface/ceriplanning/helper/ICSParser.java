package fr.ceri.prototypeinterface.ceriplanning.helper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ICSParser {

    public static void main(String[] args) {

        String filePath = "data/calendar.ics";
        String calendarData = null;
        try {
            // Read the content of the file into a String
            calendarData = Files.readString(Path.of(filePath), StandardCharsets.UTF_8);
            List<Map<String, String>> events = parseCalendar(calendarData);

            for (Map<String, String> event : events) {
                System.out.println("Event:");
                for (Map.Entry<String, String> entry : event.entrySet()) {
                    System.out.println(entry.getKey() + ": " + entry.getValue());
                }
                System.out.println("-----------");
            }
        } catch (IOException e) {
            // Handle the situation when the file can't be read (e.g., file not found, access denied)
            System.err.println("Error reading the file: " + e.getMessage());
        }

    }

    private static List<Map<String, String>> parseCalendar(String data) {
        List<Map<String, String>> events = new ArrayList<>();
        String[] lines = data.split("\n");
        boolean inEvent = false;
        Map<String, String> currentEvent = null;

        for (String line : lines) {
            if (line.startsWith("BEGIN:VEVENT")) {
                inEvent = true;
                currentEvent = new HashMap<>();
            } else if (line.startsWith("END:VEVENT")) {
                inEvent = false;
                events.add(currentEvent);
            } else if (inEvent) {
                String[] parts = line.split(":", 2);
                if (parts.length == 2) {
                    String key = parts[0];
                    String value = parts[1];
                    // Handling multi-line values (continuation lines)
                    if (key.startsWith(" ") || key.startsWith("\t")) {
                        // It's a continuation line; append value to the last key
                        String lastKey = new ArrayList<>(currentEvent.keySet()).get(currentEvent.size() - 1);
                        currentEvent.put(lastKey, currentEvent.get(lastKey) + value.trim());
                    } else {
                        currentEvent.put(key, value);
                    }
                }
            }
        }

        return events;
    }
}
