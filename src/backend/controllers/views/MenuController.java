package backend.controllers.views;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class MenuController {

    @FXML private StackPane rootMenu;

    @FXML public void startSimulation() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/views/simulation.fxml"));
            StackPane pane = loader.load();
            rootMenu.getScene().setRoot(pane);
            pane.getScene().getWindow().centerOnScreen();
            SimulationController sc = loader.getController();
            sc.resize();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
