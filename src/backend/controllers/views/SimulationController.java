package backend.controllers.views;

import backend.controllers.Controller;
import backend.models.entities.Ball;
import backend.models.entities.Entity;
import backend.models.obstacles.Cuboid;
import backend.models.obstacles.Obstacle;
import backend.models.playground.Playground;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.beans.PropertyChangeEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SimulationController implements Controller {

    @FXML private StackPane rootSimulation;
    @FXML private Pane playground;

    private Playground playgroundM;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Current default config
        playgroundM = new Playground(500,500,250,490,250,10);
        playgroundM.addObstacle(new Cuboid(200, 245, 100,10));
        playgroundM.addEntity(new Ball(playgroundM.getStart().getLoc().x, playgroundM.getStart().getLoc().y, Color.BLACK));

        for(Entity e : playgroundM.getEntities()) {
            try {
                Circle c = FXMLLoader.load(getClass().getResource("/ui/components/entities/ball.fxml"));
                c.setLayoutX(e.getLoc().x);
                c.setLayoutY(e.getLoc().y);
                c.setRadius(e.getSize());
                c.setStyle("-fx-background-color: " + e.getColor() + ";");
                playground.getChildren().add(c);
            } catch(IOException ex) {
                ex.printStackTrace();
            }
        }

        for(Obstacle o : playgroundM.getObstacles()) {
            try {
                Rectangle r = FXMLLoader.load(getClass().getResource("/ui/components/obstacles/cuboid.fxml"));
                r.setLayoutX(o.getLoc().x);
                r.setLayoutY(o.getLoc().y);
                r.setWidth(o.sizeX());
                r.setHeight(o.sizeY());
                r.setFill(Paint.valueOf("blue"));
                playground.getChildren().add(r);
            } catch(IOException ex) {
                ex.printStackTrace();
            }
        }

        playground.setStyle("-fx-border-color: black; -fx-border-width: 2px; -fx-border-style: solid;");
        playground.setPrefWidth(playgroundM.sizeX());
        playground.setPrefHeight(playgroundM.sizeY());
        playground.setMaxWidth(playgroundM.sizeX());
        playground.setMaxHeight(playgroundM.sizeY());
    }

    public void rezise() {
        rootSimulation.getScene().getWindow().setWidth(playgroundM.sizeX() + 300);
        rootSimulation.getScene().getWindow().setHeight(playgroundM.sizeY() + 100);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }
}
