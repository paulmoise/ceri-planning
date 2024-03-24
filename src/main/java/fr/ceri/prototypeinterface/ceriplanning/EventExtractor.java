package fr.ceri.prototypeinterface.ceriplanning;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EventExtractor {
    public static List<Event> extractEvents(String filePath) {
        List<Event> events = new ArrayList<>();
        boolean inEvent = false;
        StringBuilder currentEvent = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("BEGIN:VEVENT")) {
                    inEvent = true;
                    currentEvent = new StringBuilder();
                } else if (line.startsWith("END:VEVENT")) {
                    inEvent = false;
                    events.add(parseEvent(currentEvent.toString()));
                }

                if (inEvent) {
                    currentEvent.append(line.trim()).append("\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return events;
    }

    private static Event parseEvent(String eventContent ) {
        Event event = new Event();

        String dateDebut = extractInformation(eventContent, "DTSTART:(.*?)T");
        String dateFin = extractInformation(eventContent, "DTEND:(.*?)T");
        String location = extractInformation(eventContent, "LOCATION;LANGUAGE=fr:(.*?)\\n");


        // Appel d'une méthode pour analyser le bloc SUMMARY et récupérer les détails de l'événement
        // parseSummaryBlock(summaryBlock, event);

        event.setDateDebut(dateDebut);
        event.setDateFin(dateFin);
        event.setSalle(location);
        return event;
    }

    public static List<String> extractDescriptions(String filePath) {
        List<String> descriptions = new ArrayList<>();
        boolean inEvent = false;
        StringBuilder currentDescription = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("X-ALT-DESC;FMTTYPE=text/html:Matière :")) {
                    inEvent = true;
                    currentDescription = new StringBuilder();
                } else if (line.startsWith("END:VEVENT")) {
                    inEvent = false;
                    descriptions.add(currentDescription.toString());
                }

                if (inEvent) {
                    int index = line.indexOf("Matière");
                    if (index != -1) {
                        currentDescription.append(line.substring(index)).append("\n");
                    }
                   else  currentDescription.append(line).append("\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return descriptions;
    }


    /*private static void parseSummaryBlock(String summaryBlock, Event event) {
        if (summaryBlock != null) {
            String[] lines = summaryBlock.split("<br/>");
            for (String line : lines) {
                if (line.startsWith("Enseignant")) {
                    event.setEnseignant(line.substring(line.indexOf(":") + 2).trim());
                } else if (line.startsWith("Matieres")) {
                    event.setMatiere(line.substring(line.indexOf(":") + 2).trim());
                } else if (line.startsWith("Type")) {
                    event.setTypeCours(line.substring(line.indexOf(":") + 2).trim());
                }
                // Vous pouvez ajouter d'autres conditions pour extraire d'autres informations si nécessaire
            }
        }
    }*/

    private static String extractInformation(String text, String patternString) {
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }
    public  static List<String> lunch(){
       List<String> listdescription;

        String filePath = "data/calendar.ics"; // Chemin vers le fichier de calendrier
       // List<Event> extractedEvents = extractEvents(filePath);
        listdescription = extractDescriptions(filePath);
        return listdescription;

    }
      private static  ArrayList<String> parsedescription (String descriptionbloc) {
          ArrayList<String> listed = new ArrayList<>();
          if (descriptionbloc != null) {
              String[] lines = descriptionbloc.split("<br/>");
              for (String line : lines) {
                  if (line.contains(":")) {
                      String[] v = line.split(":");
                     // listed.add(v[1].trim());
                      listed.add(v[1]);

                     // System.out.println(line + "-------" + v[1]);
                  }
                  else if (line.contains("Matière :")){
                      String[] v2 = line.split("Matière :");
                      listed.add(v2[1]);
                  }
              }

          }
         return  listed;
      }
    public static void main(String[] args) {

        String filePath = "data/calendar.ics"; // Chemin vers le fichier de calendrier
        List<Event> extractedEvents = extractEvents(filePath);
          List<String> liste1 =    lunch();

          for (String s:liste1){

       ArrayList<String >  l =      parsedescription (s.trim());
              System.out.println("----------------");
        for (String l1: l){
            System.out.println(l1);
        }
          }


    }
}
    class Event {
        private String salle;
        private String enseignant;
        private String dateDebut;
        private String dateFin;
        private String matiere;
        private String typeCours;

        // Getters and setters
        public String getSalle() {
            return salle;
        }

        public void setSalle(String salle) {
            this.salle = salle;
        }

        public String getEnseignant() {
            return enseignant;
        }

        public void setEnseignant(String enseignant) {
            this.enseignant = enseignant;
        }

        public String getDateDebut() {
            return dateDebut;
        }

        public void setDateDebut(String dateDebut) {
            this.dateDebut = dateDebut;
        }

        public String getDateFin() {
            return dateFin;
        }

        public void setDateFin(String dateFin) {
            this.dateFin = dateFin;
        }

        public String getMatiere() {
            return matiere;
        }

        public void setMatiere(String matiere) {
            this.matiere = matiere;
        }

        public String getTypeCours() {
            return typeCours;
        }

        public void setTypeCours(String typeCours) {
            this.typeCours = typeCours;
        }
    }
