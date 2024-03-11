package fr.ceri.prototypeinterface.ceriplanning;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
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

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

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
            return; // Quitter la méthode si aucun type de compte n'est sélectionné
        }

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login-view.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root, 320, 240);

            Stage currentStage = (stage != null) ? stage : new Stage();
            currentStage.setTitle("Votre titre ici");
            currentStage.setScene(scene);
            currentStage.show();

            // Fermer la fenêtre actuelle
            ((Stage) usernameField.getScene().getWindow()).close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
