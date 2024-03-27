package fr.ceri.prototypeinterface.ceriplanning;

import fr.ceri.prototypeinterface.ceriplanning.helper.Conexion;
import fr.ceri.prototypeinterface.ceriplanning.helper.Filter;
import fr.ceri.prototypeinterface.ceriplanning.model.CreateConnexion;
import fr.ceri.prototypeinterface.ceriplanning.model.Event;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LoginController {
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private CheckBox studentCheckbox;

    @FXML
    private CheckBox professorCheckbox;

    @FXML
    private Label statusLabel;
    Filter filter =new Filter();
    public static final List<Event> listefiltredm = new ArrayList<>();

    Conexion c = null;

    @FXML
    public void initialize() {
        c = new Conexion();
    }

    @FXML
    void handleLogin(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        boolean isStudent = studentCheckbox.isSelected();
        boolean isProfessor = professorCheckbox.isSelected();
        CreateConnexion individu = new CreateConnexion(username, password);

        if (isStudent) {
            statusLabel.setText("Connexion en tant qu'étudiant : " + username);
            for (CreateConnexion e : c.getStudentList()) {
                if (e.equals(individu)) {
                    switchToAcueil(event);
                }
            }
        } else if (isProfessor) {
            for (CreateConnexion e : c.getTeacherList()) {
                if (e.equals(individu)) {
                    switchToAcueil(event);
                }
            }
        } else {
            statusLabel.setText("Veuillez sélectionner un type de compte.");
        }
    }

    void switchToAcueil(ActionEvent event) {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("home-view.fxml"));
            Parent root = fxmlLoader.load();
            Scene newScene = new Scene(root, 320, 240);
            seachformation("M1-IA-IL-ALT");
            // Récupérer la scène actuelle
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene currentScene = stage.getScene();

            // Remplacer la scène actuelle par la nouvelle scène
            stage.setScene(newScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void seachformation(String seachdtring){

        ArrayList<Event> l= filter.getFormationSchedule(seachdtring);
        for (Event e : l){
            //System.out.println("-------------------------");
            // System.out.println(e.getDescriptionDetails().toString());
            listefiltredm .add(e);
        }
    }
}
