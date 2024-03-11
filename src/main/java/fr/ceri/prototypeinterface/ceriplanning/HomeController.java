package fr.ceri.prototypeinterface.ceriplanning;

import fr.ceri.prototypeinterface.ceriplanning.helper.Event;
import fr.ceri.prototypeinterface.ceriplanning.model.CalendarActivity;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class HomeController implements Initializable {

    @FXML
    private AnchorPane weekcalendar;

    // Initialize the GridPane
    private GridPane gridPane = new GridPane();

    // Array for weekdays starting from Monday
    String[] weekDays = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};


    ZonedDateTime dateFocus;
    ZonedDateTime today;

    @FXML
    private Text year;

    @FXML
    private Text month;

    @FXML
    private FlowPane monthCalendar;

    @FXML
    private GridPane weekCalendarGrid;

    @FXML
    private ImageView burgerImageView;

    @FXML
    private ImageView homeImageView;

    @FXML
    private ImageView addImageView;

    @FXML
    private AnchorPane contentAnchorPane;

    private void fillGridWithTimeSlotsAndEvents() {
        // Define the start and end times
        LocalTime startTime = LocalTime.of(8, 0); // 08:00
        LocalTime endTime = LocalTime.of(19, 0); // 19:00
        Duration slotDuration = Duration.ofMinutes(30); // 30 minutes slots

        int numCols = weekCalendarGrid.getColumnConstraints().size();
        List<LocalTime> timeSlots = new ArrayList<>();

        LocalTime currentTime = startTime;
        while (currentTime.isBefore(endTime.plusSeconds(1))) { // Include 19:00
            timeSlots.add(currentTime);
            // Add time slot as the first column in each row
            Text timeSlotText = new Text(currentTime.toString());
            weekCalendarGrid.add(timeSlotText, 0, timeSlots.size() - 1);
            currentTime = currentTime.plus(slotDuration);
        }
    }

    private void fillGridWithTimeSlots() {
        LocalTime time = LocalTime.of(8, 0); // Starting time
        for (int row = 0; row < 24; row++) { // Adjust the number of rows based on your time slots
            Text timeSlot = new Text(time.toString());
            weekCalendarGrid.add(timeSlot, 0, row); // First column for time slots
            time = time.plusMinutes(30); // Increment by 30 minutes for the next slot
        }
    }

    private void addEventToGrid(Event event) {
        // Calculate the start row and row span for the event
        int startHour = event.getStartTime().getHour();
        int startMinute = event.getStartTime().getMinute();
        int endHour = event.getEndTime().getHour();
        int endMinute = event.getEndTime().getMinute();

        // Assuming your grid starts at 8AM and has 30-minute intervals
        int startRow = (startHour - 8) * 2 + (startMinute / 30);
        long durationInMinutes = ChronoUnit.MINUTES.between(event.getStartTime(), event.getEndTime());
        int rowSpan = (int) (durationInMinutes / 30);

        // Create the event text or more complex node
        Text eventText = new Text("Event: "+event.getName() + "\n\n Type: " + event.getEventType());
        eventText.setWrappingWidth(100); // Ensure the text wraps if needed

        // Merge cells and add the event to the grid
        GridPane.setRowIndex(eventText, startRow);
        GridPane.setColumnIndex(eventText, 1); // Assuming events are in the second column
        GridPane.setRowSpan(eventText, rowSpan);

        weekCalendarGrid.getChildren().add(eventText);
    }



    @FXML
    void homeClicked(MouseEvent event) {
        // Gestionnaire d'événement pour l'image de la maison
        System.out.println("Home image clicked!");
        loadContent("hello-view.fxml");
    }

    @FXML
    void addClicked(MouseEvent event) {
        // Gestionnaire d'événement pour l'image d'ajout
        loadContent("Calendargridpane-view.fxml");
        System.out.println("Add image clicked!");
    }

    private void loadContent(String fxmlFile) {
        try {
            // Charger le fichier FXML
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFile));
            Node newContent = fxmlLoader.load();

            // Remplacer le contenu actuel par le contenu chargé depuis le fichier FXML
            contentAnchorPane.getChildren().setAll(newContent);
        } catch (IOException e) {
            e.printStackTrace();
            // Gérer l'erreur lors du chargement du fichier FXML
        }
    }


    private void initializeDynamicGridPane() {
        // Set properties for GridPane
        gridPane.setGridLinesVisible(true);
        gridPane.setPrefHeight(520.0);
        gridPane.setPrefWidth(720.0);

        // Add ColumnConstraints
        for (int i = 0; i < 6; i++) { // Assuming 6 columns as in your example
            ColumnConstraints column = new ColumnConstraints();
            column.setHgrow(Priority.SOMETIMES);
            column.setMinWidth(10);
            column.setPrefWidth(100);
            gridPane.getColumnConstraints().add(column);
        }

        // Add RowConstraints
        for (int i = 0; i < 24; i++) { // Assuming 24 rows as in your example
            RowConstraints row = new RowConstraints();
            row.setVgrow(Priority.SOMETIMES);
            row.setMinHeight(10);
            row.setPrefHeight(30);
            gridPane.getRowConstraints().add(row);
        }

        // Add the GridPane to your layout, for example to an AnchorPane or another container
        // For example, if adding to an AnchorPane:
        AnchorPane.setTopAnchor(gridPane, 0.0);
        AnchorPane.setBottomAnchor(gridPane, 0.0);
        AnchorPane.setLeftAnchor(gridPane, 0.0);
        AnchorPane.setRightAnchor(gridPane, 0.0);
        contentAnchorPane.getChildren().add(gridPane); // Assuming you want to add it to 'contentAnchorPane'
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dateFocus = ZonedDateTime.now();
        System.out.println(dateFocus.getYear());
        today = ZonedDateTime.now();

        fillGridWithTimeSlotsAndEvents();
        fillGridWithTimeSlots();
        addEventToGrid(new Event(LocalTime.of(8, 0), LocalTime.of(9, 30), "Java Class", "Cours"));
        drawCalendar();
    }

    @FXML
    void backOneMonth(ActionEvent event) {
        dateFocus = dateFocus.minusMonths(1);
        monthCalendar.getChildren().clear();
        drawCalendar();
    }

    @FXML
    void forwardOneMonth(ActionEvent event) {
        dateFocus = dateFocus.plusMonths(1);
        monthCalendar.getChildren().clear();
        drawCalendar();
    }


    private void drawCalendar() {
        System.out.println(dateFocus.getYear());
        year.setText(String.valueOf(dateFocus.getYear()));
        month.setText(String.valueOf(dateFocus.getMonth()));
        double calendarWidth = monthCalendar.getPrefWidth();
        double calendarHeight = monthCalendar.getPrefHeight();
        double strokeWidth = 1;
        double spacingH = monthCalendar.getHgap();
        double spacingV = monthCalendar.getVgap();

        //List of activities for a given month
        Map<Integer, List<CalendarActivity>> calendarActivityMap = getCalendarActivitiesMonth(dateFocus);

        int monthMaxDate = dateFocus.getMonth().maxLength();
        //Check for leap year
        if (dateFocus.getYear() % 4 != 0 && monthMaxDate == 29) {
            monthMaxDate = 28;
        }
        int dateOffset = ZonedDateTime.of(dateFocus.getYear(), dateFocus.getMonthValue(), 1, 0, 0, 0, 0, dateFocus.getZone()).getDayOfWeek().getValue();

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                StackPane stackPane = new StackPane();

                Rectangle rectangle = new Rectangle();
                rectangle.setFill(Color.TRANSPARENT);
                rectangle.setStroke(Color.BLACK);
                rectangle.setStrokeWidth(strokeWidth);
                double rectangleWidth = (calendarWidth / 7) - strokeWidth - spacingH;
                rectangle.setWidth(rectangleWidth);
                double rectangleHeight = (calendarHeight / 6) - strokeWidth - spacingV;
                rectangle.setHeight(rectangleHeight);
                stackPane.getChildren().add(rectangle);

                int calculatedDate = (j + 1) + (7 * i);
                if (calculatedDate > dateOffset) {
                    int currentDate = calculatedDate - dateOffset;
                    if (currentDate <= monthMaxDate) {
                        Text date = new Text(String.valueOf(currentDate));
                        double textTranslationY = -(rectangleHeight / 2) * 0.75;
                        date.setTranslateY(textTranslationY);
                        stackPane.getChildren().add(date);

                        List<CalendarActivity> calendarActivities = calendarActivityMap.get(currentDate);
                        if (calendarActivities != null) {
                            createCalendarActivity(calendarActivities, rectangleHeight, rectangleWidth, stackPane);
                        }
                    }
                    if (today.getYear() == dateFocus.getYear() && today.getMonth() == dateFocus.getMonth() && today.getDayOfMonth() == currentDate) {
                        rectangle.setStroke(Color.BLUE);
                    }
                }
                monthCalendar.getChildren().add(stackPane);
            }
        }
    }


    private void createCalendarActivity(List<CalendarActivity> calendarActivities, double rectangleHeight, double rectangleWidth, StackPane stackPane) {
        VBox calendarActivityBox = new VBox();
        for (int k = 0; k < calendarActivities.size(); k++) {
            if (k >= 2) {
                Text moreActivities = new Text("...");
                calendarActivityBox.getChildren().add(moreActivities);
                moreActivities.setOnMouseClicked(mouseEvent -> {
                    //On ... click print all activities for given date
                    System.out.println(calendarActivities);
                });
                break;
            }
            Text text = new Text(calendarActivities.get(k).getClientName() + ", " + calendarActivities.get(k).getDate().toLocalTime());
            calendarActivityBox.getChildren().add(text);
            text.setOnMouseClicked(mouseEvent -> {
                //On Text clicked
                System.out.println(text.getText());
            });
        }
        calendarActivityBox.setTranslateY((rectangleHeight / 2) * 0.20);
        calendarActivityBox.setMaxWidth(rectangleWidth * 0.8);
        calendarActivityBox.setMaxHeight(rectangleHeight * 0.65);
        calendarActivityBox.setStyle("-fx-background-color:GRAY");
        stackPane.getChildren().add(calendarActivityBox);
    }


    private Map<Integer, List<CalendarActivity>> createCalendarMap(List<CalendarActivity> calendarActivities) {
        Map<Integer, List<CalendarActivity>> calendarActivityMap = new HashMap<>();

        for (CalendarActivity activity : calendarActivities) {
            int activityDate = activity.getDate().getDayOfMonth();
            if (!calendarActivityMap.containsKey(activityDate)) {
                calendarActivityMap.put(activityDate, List.of(activity));
            } else {
                List<CalendarActivity> OldListByDate = calendarActivityMap.get(activityDate);

                List<CalendarActivity> newList = new ArrayList<>(OldListByDate);
                newList.add(activity);
                calendarActivityMap.put(activityDate, newList);
            }
        }
        return calendarActivityMap;
    }

    private Map<Integer, List<CalendarActivity>> getCalendarActivitiesMonth(ZonedDateTime dateFocus) {
        List<CalendarActivity> calendarActivities = new ArrayList<>();
        int year = dateFocus.getYear();
        int month = dateFocus.getMonth().getValue();

        Random random = new Random();
        for (int i = 0; i < 50; i++) {
            ZonedDateTime time = ZonedDateTime.of(year, month, random.nextInt(27) + 1, 16, 0, 0, 0, dateFocus.getZone());
            calendarActivities.add(new CalendarActivity(time, "Hans", 111111));
        }

        return createCalendarMap(calendarActivities);
    }

}
