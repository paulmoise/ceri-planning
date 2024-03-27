package fr.ceri.prototypeinterface.ceriplanning;

import fr.ceri.prototypeinterface.ceriplanning.helper.Filter;
import fr.ceri.prototypeinterface.ceriplanning.model.Event;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HelloApplication extends Application {
    Filter filter =new Filter();
    public static final List<Event> listefiltredm = new ArrayList<>();
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("home-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        seachformation("M1-IA-IL-ALT");
        stage.show();
    }
    public void seachformation(String seachdtring){

        ArrayList<Event> l= filter.getFormationSchedule(seachdtring);
        for (Event e : l){
            //System.out.println("-------------------------");
           // System.out.println(e.getDescriptionDetails().toString());
            listefiltredm .add(e);
        }
    }
    public static void main(String[] args) {
        launch();
    }
}