package fr.ceri.prototypeinterface.ceriplanning;

import fr.ceri.prototypeinterface.ceriplanning.helper.Filter;
import fr.ceri.prototypeinterface.ceriplanning.helper.Utils;
import fr.ceri.prototypeinterface.ceriplanning.model.Event;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.net.URL;
import java.time.*;
import java.time.temporal.WeekFields;
import java.util.*;

import static fr.ceri.prototypeinterface.ceriplanning.helper.ICSFileParser.parseIcsFile;
import static fr.ceri.prototypeinterface.ceriplanning.helper.Utils.*;

public class SearchController implements Initializable {
    @FXML
    public AnchorPane contentAnchorPane;
    @FXML
    public TextField searchField;
    String[] weekDays = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};

   // LocalDate currentDate = LocalDate.now();

    // Calculez le numéro de la semaine en cours
  //  int activeWeekOfYear = currentDate.get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear());
    private int activeWeekOfYear = 12;
    ZonedDateTime dateFocus;
    ZonedDateTime today;

    static  boolean isalle=false;
    static boolean isformation=false ;
    static boolean isschedule=false ;

    Map<String, Integer> timeSlots = Utils.generateTimeSlots(LocalTime.of(8, 0), LocalTime.of(19, 0), Duration.ofMinutes(30));





    private ObservableList<Event> observableEvents = FXCollections.observableArrayList();



    List<Event> events=HomeController.listefiltred;



    public void displayMonthGridPane() {

        GridPane gridPane = new GridPane();

        // header
        Label allDays = new Label("");
        StackPane allDaysPane = new StackPane(allDays);
        allDaysPane.setAlignment(Pos.CENTER); // Center the label within the stack pane

        String headerWeekTitleStyle = "-fx-font-weight: bold; -fx-font-size: 15px;";
        Label monday = new Label("Lundi");
        monday.setStyle(headerWeekTitleStyle);
        StackPane mondayPane = new StackPane(monday);
        mondayPane.setAlignment(Pos.CENTER);

        Label tuesday = new Label("Mardi");
        tuesday.setStyle(headerWeekTitleStyle);
        StackPane tuesdayPane = new StackPane(tuesday);
        tuesdayPane.setAlignment(Pos.CENTER);

        Label wednesday = new Label("Mercredi");
        wednesday.setStyle(headerWeekTitleStyle);
        StackPane wednesdayPane = new StackPane(wednesday);
        wednesdayPane.setAlignment(Pos.CENTER);

        Label thursday = new Label("Jeudi");
        thursday.setStyle(headerWeekTitleStyle);
        StackPane thursdayPane = new StackPane(thursday);
        thursdayPane.setAlignment(Pos.CENTER);

        Label friday = new Label("Vendredi");
        friday.setStyle(headerWeekTitleStyle);
        StackPane fridayPane = new StackPane(friday);
        fridayPane.setAlignment(Pos.CENTER);


        gridPane.add(allDaysPane, 0, 0);
        gridPane.add(mondayPane, 1, 0);
        gridPane.add(tuesdayPane, 2, 0);
        gridPane.add(wednesdayPane, 3, 0);
        gridPane.add(thursdayPane, 4, 0);
        gridPane.add(fridayPane, 5, 0);

//        gridPane.setHgap(5);
        gridPane.setVgap(0.5);

        gridPane.setPadding(new Insets(10, 10, 10, 10));


        ColumnConstraints column1 = new ColumnConstraints();
        ColumnConstraints column2 = new ColumnConstraints();
        ColumnConstraints column3 = new ColumnConstraints();
        ColumnConstraints column4 = new ColumnConstraints();
        ColumnConstraints column5 = new ColumnConstraints();
        ColumnConstraints column6 = new ColumnConstraints();

        gridPane.getColumnConstraints().addAll(column1, column2, column3, column4, column5, column6);

        column1.setPrefWidth(200);
        column2.setPrefWidth(200);
        column3.setPrefWidth(200);
        column4.setPrefWidth(200);
        column5.setPrefWidth(200);
        column6.setPrefWidth(200);

        double percentWidth = 100.0 / 6; // This divides the grid equally among the 6 columns


        column1.setPercentWidth(percentWidth);
        column2.setPercentWidth(percentWidth);
        column3.setPercentWidth(percentWidth);
        column4.setPercentWidth(percentWidth);
        column5.setPercentWidth(percentWidth);
        column6.setPercentWidth(percentWidth);


        for (int col = 0; col < 6; col++) {
            for (int row = 1; row < 24; row++) {
                if (col == 0) {
                    Label slot = new Label(timeSlots.keySet().toArray()[row - 1].toString());
                    slot.setStyle("-fx-font-size: 12px; -fx-font-weight: bold;");
                    StackPane slotPane = new StackPane(slot);
                    slotPane.setStyle("-fx-background-color: #f4f4f4;" +
                            "-fx-border-color: #e1e1e1; " +
                            "-fx-border-width: 1; " +
                            "-fx-border-style: dashed;"
                    );
                    slotPane.setAlignment(Pos.CENTER); // Center the label within the stack pane
                    gridPane.add(slotPane, col, row);

                } else {
                    Button button = getButton(row, col);

                    gridPane.add(button, col, row);
                }


            }
        }
        gridPane.setId("weekCalendarGrid2");
        contentAnchorPane.getChildren().add(gridPane);
    }

    private Button getButton(int row, int col) {
        Button button = new Button("Row: " + row + " Col: " + col);
        button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        button.setStyle("-fx-background-color: #fafafa;" +
                "-fx-border-color: #e1e1e1; " +
                "-fx-border-width: 1; " +
                "-fx-border-style: dashed;");


        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: #cccccc; " +
                "-fx-border-color: black; " +
                "-fx-border-width: 1; " +
                "-fx-border-style: dashed; " +
                "-fx-background-insets: 0;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #fafafa;" +
                "-fx-border-color: #e1e1e1; " +
                "-fx-border-width: 1; " +
                "-fx-border-style: dashed;"));

        return button;
    }

    private void updateActiveMonthLabel() {
        String monthName = getMonthFromWeek(2024, activeWeekOfYear);
        String capitalizedMonthName = monthName.substring(0, 1).toUpperCase() + monthName.substring(1).toLowerCase();


    }
    public void initialize(URL url, ResourceBundle resourceBundle) {

        showcalendar ( );


    }

    public  void  showcalendar () {
        dateFocus = ZonedDateTime.now();
        today = ZonedDateTime.now();

        observableEvents.addListener((ListChangeListener<Event>) change -> {
             while (change.next()) {
                 if (change.wasAdded() || change.wasRemoved()) {
                     // Clear the existing grid and redraw based on observableEvents
                     updateActiveMonthLabel();

                     contentAnchorPane.getChildren().clear();
                     displayMonthGridPane(); // Assuming this method now uses observableEvents
                     displayEventOnGridPane(observableEvents); // Modify this method to accept a List or ObservableList
                 }
             }
         });

        displayMonthGridPane();

        List<Event> events;
        events = getAllEventOfActiveWeek(activeWeekOfYear);
        displayEventOnGridPane(events);
    }

    private void applyFilterToListOfAllEvents(int activeWeekOfYear, List<Event> observableEvents) {
        getutils(activeWeekOfYear, observableEvents, events);
    }

    static void getutils(int activeWeekOfYear, List<Event> observableEvents, List<Event> events ) {
        for (Event event : events) { // Assuming 'events' is your master list of all events
            if (isValidDateFormat(event.getDtStart()) && isValidDateFormat(event.getDtEnd()) &&
                    isActiveWeekOfYearEqualToEventStartWeekOfYear(event.getDtStart(), activeWeekOfYear)) {

                observableEvents.add(event);

            }
        }
    }

    private List<Event> getAllEventOfActiveWeek(int activeWeekOfYear ) {
        List<Event> filteredEvents = new ArrayList<>();
        applyFilterToListOfAllEvents(activeWeekOfYear, filteredEvents);
        return filteredEvents;
    }


    private void displayEventOnGridPane(List<Event> events) {
        for (Event event : events) {

            LocalDateTime updatedStartTime = addOneHourToDate(event.getDtStart());
            LocalDateTime updatedEndTime = addOneHourToDate(event.getDtEnd());


            int numberOf30MinutesSlots = calculateNumberOf30MinIntervals(updatedStartTime, updatedEndTime);
            String stringEvent = "Debut: " + event.getDtStart() + "\nFin: " + event.getDtEnd() + "\nSalle: " + event.getDescriptionDetails().getSalle() + "\nEnseignant : " + event.getDescriptionDetails().getEnseignant() + "\nType: " + event.getDescriptionDetails().getType() + "\nMatiere:" + event.getDescriptionDetails().getMatiere() + "\n Formation: " + event.getDescriptionDetails().getTd();

            int startCol = getDayOfWeek(updatedStartTime);

           // System.out.println("start = " + event.getDtStart());
           // System.out.println("End = " + event.getDtEnd());

            String startTime = extractTime(updatedStartTime);
            System.out.println(startTime);

            if (timeSlots.containsKey(startTime) && startCol != -1) {
                int startRow = timeSlots.get(startTime);
               // System.out.println("Nrow: " + numberOf30MinutesSlots);
               // System.out.println("StartCol: " + startCol);
               // System.out.println("startRow: " + startRow);

                Button buttonEvent = new Button(stringEvent);
                buttonEvent.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

                GridPane gridPane = (GridPane)contentAnchorPane.lookup("#weekCalendarGrid2");
                gridPane.add(buttonEvent, startCol, startRow);
                gridPane.setRowSpan(buttonEvent, numberOf30MinutesSlots + 1);
            }
        }
    }

    private void updateEventsForActiveWeek(int activeWeekOfYear) {
        observableEvents.clear();
        applyFilterToListOfAllEvents(activeWeekOfYear, observableEvents);
    }
  /*  private void updateActiveMonthLabel() {
        String monthName = getMonthFromWeek(2024, activeWeekOfYear);
        String capitalizedMonthName = monthName.substring(0, 1).toUpperCase() + monthName.substring(1).toLowerCase();

        activeMonth.setText(capitalizedMonthName+ " " + 2024);
    }*/
    public void onNextWeek(MouseEvent mouseEvent) {
        activeWeekOfYear++;
        updateEventsForActiveWeek(activeWeekOfYear);
    }

    public void onPrevWeek(MouseEvent mouseEvent) {
        activeWeekOfYear--;
        updateEventsForActiveWeek(activeWeekOfYear);
    }

    public void onTodayDateClick(MouseEvent mouseEvent) {
        Calendar now = Calendar.getInstance(); // Gets the current date and time
        this.activeWeekOfYear = now.get(Calendar.WEEK_OF_YEAR);
        updateEventsForActiveWeek(activeWeekOfYear);
    }

    public void showSearchFieldForReservation(MouseEvent mouseEvent) {
        searchField.setVisible(true);
        isschedule= true ;
        searchField.setPromptText("Rechercher une salle...");
    }

    public void showSearchFieldForSalle(MouseEvent mouseEvent) {
        searchField.setVisible(true);
        isalle=true;
        searchField.setPromptText("Rechercher une salle...");
    }

    public void showSearchFieldForFormationss(MouseEvent mouseEvent) {
        searchField.setVisible(true);
        isformation=true;
        searchField.setPromptText("Rechercher une salle...");
    }

  /*  public void onSearchEnterPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.ENTER)) {
            String searchString = searchField.getText();
            searchField.clear();
            searchField.setVisible(false);
            Filter f ; new Filter();
            ArrayList<Event> listeUpdated = new ArrayList<>();
            System.out.println( "BLALLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLL");

            if (isalle) {
               f= new Filter();
                isalle = false;
                System.out.println(searchString);
                listeUpdated = f.getSalleSchedule(searchString);
                //ArrayList<Event > listesalle =f.getSalleSchedule(searchString);
;
               // System.out.println(listeUpdated.size()+" je suis la taille du liste");
              /// System.out.println(listesalle.size()+" je suis la taille du liste 22222");
                System.out.println("test is salle passed");

               // ArrayList<Event > listesalle =f.getSalleSchedule("S5 = C 024");


            } else if (isformation) {
                f= new Filter();
                listeUpdated = f.getFormationSchedule(searchString);
                isformation = false;
                isschedule = false;
                System.out.println(searchString + " formation");

                System.out.println(listeUpdated.size()+" je suis la taille du liste");

            } else if (isschedule) {
                f= new Filter();
                //ESTEVE Yannick
                listeUpdated = f.getPersonnesSchedule(searchString);
                isschedule = false;

                System.out.println(" je suis lenseignat "+ searchString);
                //ArrayList<Event > listeEnseignat  =f.getPersonnesSchedule("ESTEVE Yannick");
              //  ArrayList<Event > listesalle =f.getSalleSchedule("S5 = C 024");
                System.out.println(listeUpdated.size()+" je suis la taille du liste");
              //  System.out.println(listesalle.size()+" je suis la taille du liste 22222");
            }
            if(!listeUpdated.isEmpty()){
                observableEvents.removeAll();
                System.out.println(observableEvents.size() + "taille actullle");
                System.out.println(" je mets a jourss la liste "+ listeUpdated.size());
                System.out.println(events.size()+ " taille avant ");
                events.addAll(listeUpdated);
                System.out.println( events.size()+ "taille apres ");
                updateEventsForActiveWeek(activeWeekOfYear);

               getutils(activeWeekOfYear, observableEvents, listeUpdated);
               System.out.println(observableEvents.size() + "taille finales");
            }

        }
    }*/

    public void onSearchEnterPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.ENTER)) {
            String searchString = searchField.getText();
            searchField.clear();
            searchField.setVisible(false);
            Filter f = new Filter();
            ArrayList<Event> listeUpdated = new ArrayList<>();
            activeWeekOfYear=12;
            if (isalle) {
                f = new Filter();
                isalle = false;
                System.out.println(searchString);
                listeUpdated = f.getSalleSchedule(searchString);
                System.out.println("test is salle passed");
            } else if (isformation) {
                f = new Filter();
                listeUpdated = f.getFormationSchedule(searchString);
                isformation = false;
                isschedule = false;
                System.out.println(searchString + " formation");
            } else if (isschedule) {
                f = new Filter();
                listeUpdated = f.getPersonnesSchedule(searchString);
                isschedule = false;
                System.out.println(" je suis lenseignat " + searchString);
            }

            if (!listeUpdated.isEmpty()) {
                System.out.println(" je mets a jourss la liste " + listeUpdated.size());
                System.out.println(events.size() + " taille avant ");
                events.clear();
                events.addAll(listeUpdated);
                System.out.println(events.size() + " taille apres ");
                updateEventsForActiveWeek(activeWeekOfYear);
                getutils(activeWeekOfYear, observableEvents, events);

                // Mettre à jour le GridPane après avoir modifié les événements
                contentAnchorPane.getChildren().clear();
                displayMonthGridPane();
                displayEventOnGridPane(observableEvents);
            }
        }
    }

}
