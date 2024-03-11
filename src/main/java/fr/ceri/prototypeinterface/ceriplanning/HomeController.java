package fr.ceri.prototypeinterface.ceriplanning;

import javafx.fxml.FXML;
import javafx.scene.control.TabPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import java.io.IOException;
public class HomeController {

    @FXML
    private AnchorPane contentAnchorpan;
    @FXML
    private AnchorPane exit;

    @FXML
    private TabPane tabPane;

    @FXML
    private AnchorPane jourAnchorPane;

    @FXML
    private AnchorPane semaineAnchorPane;

    @FXML
    private AnchorPane moisAnchorPane;





    @FXML
    private ImageView burgerImageView;

    @FXML
    private ImageView homeImageView;

    @FXML
    private ImageView addImageView;

    @FXML
    private ImageView backupImageView;

    @FXML
    private ImageView dataImageView;

    @FXML
    private ImageView userImageView;

    @FXML
    private void initialize() {
        // Code d'initialisation ici
    }

    @FXML
    void burgerClicked(MouseEvent event) {
        // Gestionnaire d'événement pour l'image du burger

    }

    @FXML
    void homeClicked(MouseEvent event) {
        // Gestionnaire d'événement pour l'image de la maison
        System.out.println("Home image clicked!");
        loadcontent("hello-view.fxml");
    }

    @FXML
    void addClicked(MouseEvent event) {
        // Gestionnaire d'événement pour l'image d'ajout
        loadcontent("Calendargridpane-view.fxml");
        System.out.println("Add image clicked!");
    }

    @FXML
    void backupClicked(MouseEvent event) {
        // Gestionnaire d'événement pour l'image de sauvegarde
        System.out.println("Backup image clicked!");
    }

    @FXML
    void dataClicked(MouseEvent event) {
        // Gestionnaire d'événement pour l'image de données
        System.out.println("Data image clicked!");
    }

    @FXML
    void userClicked(MouseEvent event) {
        // Gestionnaire d'événement pour l'image de l'utilisateur
        System.out.println("User image clicked!");
    }
    private void loadcontent(String fxmlFile) {
        try {
            // Charger le fichier FXML
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFile));
            Node newContent = fxmlLoader.load();

            // Remplacer le contenu actuel par le contenu chargé depuis le fichier FXML
            contentAnchorpan.getChildren().setAll(newContent);
        } catch (IOException e) {
            e.printStackTrace();
            // Gérer l'erreur lors du chargement du fichier FXML
        }
    }
}

