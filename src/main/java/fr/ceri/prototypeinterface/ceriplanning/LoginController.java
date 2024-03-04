package fr.ceri.prototypeinterface.ceriplanning;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

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

    @FXML
    void handleLogin(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        boolean isStudent = studentCheckbox.isSelected();
        boolean isProfessor = professorCheckbox.isSelected();

        // Vous pouvez implémenter ici votre logique de vérification des informations de connexion
        // Par exemple, vérifier les identifiants et mots de passe dans une base de données

        if (isStudent) {
            statusLabel.setText("Connexion en tant qu'étudiant : " + username);
        } else if (isProfessor) {
            statusLabel.setText("Connexion en tant que professeur : " + username);
        } else {
            statusLabel.setText("Veuillez sélectionner un type de compte.");
        }

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 320, 240);
            Stage stage = new Stage();
            stage.setTitle("Hello!");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
