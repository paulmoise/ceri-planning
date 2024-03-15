package fr.ceri.prototypeinterface.ceriplanning.helper;

import fr.ceri.prototypeinterface.ceriplanning.model.DescriptionDetails;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.IsoFields;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

//    public static List<String> generateTimeSlots(LocalTime startTime, LocalTime endTime, Duration slotDuration) {
//        List<String> timeSlots = new ArrayList<>();
//        LocalTime currentTime = startTime;
//
//        while (!currentTime.isAfter(endTime)) {
//            timeSlots.add(currentTime.toString());
//            currentTime = currentTime.plus(slotDuration);
//        }
//
//        // Formatting to "HHHMM" pattern
//        List<String> formattedTimeSlots = new ArrayList<>();
//        for (String time : timeSlots) {
//            String formattedTime = time.replace(":", "H");
//            System.out.println(formattedTime);
//            formattedTimeSlots.add(formattedTime);
//        }
//
//
//        return formattedTimeSlots;
//    }


    public static DescriptionDetails getDescriptionDetailsFromHTML(String summary) {
        DescriptionDetails details = new DescriptionDetails();

        // Extract the X-ALT-DESC part
        String startIndicator = "X-ALT-DESC;FMTTYPE=text/html:";

        if (!summary.contains(startIndicator)) {
            return details;
        }
        String htmlPart = summary.substring(summary.indexOf(startIndicator) + startIndicator.length());

        // Split by <br/> to get each detail

        String regex = "<\\s*br\\s*/?>|<\\s*b\\s*r\\s*/>";


        String[] lines = htmlPart.replace("\n ", "").split(regex);
        for (String line : lines) {
            line = line.trim();
            if (line.contains(":")) {
                String[] parts = line.split(":", 2);
                if (parts.length < 2) continue; // Skip if there's no ":" in the line

                String key = parts[0].trim();
                String value = parts[1].trim();


                if (key.equals("Matière")) {
                    details.setMatiere(value);
                } else if (key.replace(" ", "").equals("Enseignants") || key.replace(" ", "").equals("Enseignant")) {
                    details.setEnseignant(value);
                } else if (key.replace(" ", "").equals("Salle")) {
                    details.setSalle(value);
                } else if (key.replace(" ", "").equals("Promotions") || key.replace(" ", "").equals("Promotion")) {
                    details.setTd(value);
                } else if (key.replace(" ", "").equals("Type")) {
                    details.setType(value);
                } else if (key.strip().replace(" ", "").equals("TD")) {
                    details.setTd(value);
                }
            }
        }

        return details;
    }


    public String parseDateIntoString(String s) {
        String dateString = "20240313T100900Z";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmssX").withZone(java.time.ZoneId.of("UTC"));
        Instant instant = Instant.from(formatter.parse(dateString));

        System.out.println(instant);
        return instant.toString();
    }


    public static int calculateNumberOf30MinIntervals(LocalDateTime startDate, LocalDateTime endDate) {

        // Calculate duration between the two instants
        Duration duration = Duration.between(startDate, endDate);
        // Convert duration to minutes and calculate the number of 30-minute intervals
        long totalMinutes = duration.toMinutes();
        return (int) (totalMinutes / 30);
    }

    public static String extractTime(LocalDateTime date) {
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("HH'H'mm");
        return date.format(outputFormatter);
    }

    public static boolean getWeekOfYear(String givenDateString) {

        Calendar now = Calendar.getInstance(); // Gets the current date and time
        int weekOfYear = now.get(Calendar.WEEK_OF_YEAR);


        // Create a formatter with the specified pattern
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss'Z'");

        // Parse the date string using the formatter
        LocalDateTime date = LocalDateTime.parse(givenDateString, formatter);

        // Get the week of the year
        int dateWeekOfYear = date.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR);

        System.out.println("Actual Week of year: " + weekOfYear);
        System.out.println("week of year: " + dateWeekOfYear);
        return weekOfYear == dateWeekOfYear;
    }

    public static boolean isValidDateFormat(String dateStr) {
        String regex = "^\\d{8}T\\d{6}Z$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(dateStr);
        return matcher.matches();
    }

    public static int getDayOfWeek(LocalDateTime date) {
        Map<DayOfWeek, Integer> dayOfWeekMap = Map.of(
                DayOfWeek.MONDAY, 1,
                DayOfWeek.TUESDAY, 2,
                DayOfWeek.WEDNESDAY, 3,
                DayOfWeek.THURSDAY, 4,
                DayOfWeek.FRIDAY, 5);



        // Extract the day of the week from the ZonedDateTime
        DayOfWeek dayOfWeek = date.getDayOfWeek();

        // Use the day of the week to find the corresponding number from the map
        return dayOfWeekMap.getOrDefault(dayOfWeek, -1);
    }


    public static Map<String, Integer> generateTimeSlots(LocalTime startTime, LocalTime endTime, Duration slotDuration) {
        List<String> timeSlots = new ArrayList<>();
        LocalTime currentTime = startTime;
        int counter = 1; // Initialize counter to map slots to integers
        Map<String, Integer> formattedTimeSlotsWithCounter = new LinkedHashMap<>(); // Use LinkedHashMap to maintain order

        while (!currentTime.isAfter(endTime)) {
            timeSlots.add(currentTime.toString());
            currentTime = currentTime.plus(slotDuration);
        }

        for (String time : timeSlots) {
            String formattedTime = time.replace(":", "H"); // Adjusting format to include trailing "00" for minutes
            formattedTimeSlotsWithCounter.put(formattedTime, counter++);
        }

        return formattedTimeSlotsWithCounter;
    }


    public static LocalDateTime addOneHourToDate(String dateStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss'Z'");
        LocalDateTime dateTime = LocalDateTime.parse(dateStr, formatter);
        return dateTime.plusHours(1);
    }

    public static void main(String[] args) {
//        Map<String, Integer> timeSlots = generateTimeSlots(LocalTime.of(8, 0), LocalTime.of(19, 0), Duration.ofMinutes(30));
//        timeSlots.forEach((key, value) -> System.out.println(key + " => " + value));

        String originalDate = "20231018T123000Z";
        LocalDateTime newDate = addOneHourToDate(originalDate);
        int n  = calculateNumberOf30MinIntervals(LocalDateTime.now(), LocalDateTime.now().plusHours(1));
        System.out.println("Original date: " + originalDate);
        System.out.println("New date after adding 1 hour: " + newDate);
        System.out.println("number of 30 min: " + n);
    }
}
