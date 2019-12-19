package backend.controllers.views;

import backend.controllers.Controller;
import backend.models.entities.Entity;
import backend.models.obstacles.Cuboid;
import backend.models.obstacles.Obstacle;
import backend.models.playground.Playground;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.beans.PropertyChangeEvent;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SimulationController implements Controller {

    @FXML private StackPane rootSimulation;
    @FXML private Pane playground;
    @FXML private Label genNumber;

    private Playground playgroundM;

    private List<Circle> entities;

    public SimulationController() {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Current default config
        playgroundM = new Playground(500, 500, 250, 490, 250, 10);
        playgroundM.addObstacle(new Cuboid(200, 245, 200, 10, playgroundM.getEnd()));
        playgroundM.addObstacle(new Cuboid(100, 100, 200, 10, playgroundM.getEnd()));
        playgroundM.addObstacle(new Cuboid(100, 400, 200, 10, playgroundM.getEnd()));

        initPlayground();
    }

    public void initPlayground() {
        try {
            entities = new ArrayList<>();

            Circle start = FXMLLoader.load(getClass().getResource("/ui/components/points/point.fxml"));
            Circle end = FXMLLoader.load(getClass().getResource("/ui/components/points/point.fxml"));

            start.setLayoutX(playgroundM.getStart().getLoc().x - 5);
            start.setLayoutY(playgroundM.getStart().getLoc().y - 5);
            start.setFill(Color.YELLOW);
            start.setStroke(Color.BLACK);
            start.setStrokeWidth(1);
            start.setRadius(playgroundM.getStart().getRadius());
            end.setLayoutX(playgroundM.getEnd().getLoc().x - 5);
            end.setLayoutY(playgroundM.getEnd().getLoc().y - 5);
            end.setFill(Color.RED);
            end.setStroke(Color.BLACK);
            end.setStrokeWidth(1);
            end.setRadius(playgroundM.getEnd().getRadius());

            playground.getChildren().addAll(start, end);

            for (Entity e : playgroundM.getEntities()) {
                Circle c = FXMLLoader.load(getClass().getResource("/ui/components/entities/ball.fxml"));
                c.setLayoutX(e.getLoc().x - e.getSize());
                c.setLayoutY(e.getLoc().y - e.getSize());
                c.setRadius(e.getSize());
                c.setStyle("-fx-background-color: " + e.getColor() + ";");
                entities.add(c);
                playground.getChildren().add(c);
            }

            for (Obstacle o : playgroundM.getObstacles()) {
                Rectangle r = FXMLLoader.load(getClass().getResource("/ui/components/obstacles/cuboid.fxml"));
                r.setLayoutX(o.getLoc().x);
                r.setLayoutY(o.getLoc().y);
                r.setWidth(o.sizeX());
                r.setHeight(o.sizeY());
                r.setFill(Color.BLUE);
                playground.getChildren().add(r);
            }

            playground.setStyle("-fx-border-color: black; -fx-border-width: 2px; -fx-border-style: solid;");
            playground.setPrefWidth(playgroundM.sizeX());
            playground.setPrefHeight(playgroundM.sizeY());
            playground.setMaxWidth(playgroundM.sizeX());
            playground.setMaxHeight(playgroundM.sizeY());

            genNumber.setText(Integer.toString(playgroundM.getGeneration()));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void resize() {
        rootSimulation.getScene().getWindow().setWidth(playgroundM.sizeX() + 300);
        rootSimulation.getScene().getWindow().setHeight(playgroundM.sizeY() + 100);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }

    public void updateEntitiesLocation() {
        for(int i = 0; i < playgroundM.getEntities().size(); i++) {
            Entity e = playgroundM.getEntities().get(i);
            Circle c = entities.get(i);
            c.setLayoutX(e.getLoc().x - e.getSize());
            c.setLayoutY(e.getLoc().y - e.getSize());
        }
    }

    public void start() {
        final ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                boolean allDone = playgroundM.moveAll();
                boolean allDead = playgroundM.checkCollision();
                Platform.runLater(() -> updateEntitiesLocation());

                if(allDone || allDead) {
                    restart();
                    ses.shutdown();
                }
            }
        };
        ses.scheduleWithFixedDelay(task, 0, 5, TimeUnit.MICROSECONDS);
    }

    public void restart() {
        playgroundM.respawnAll();
        Platform.runLater(() -> {
            updateEntitiesLocation();
            updateEntities();
        });

        Platform.runLater(() -> genNumber.setText(Integer.toString(playgroundM.getGeneration())));
        start();
    }

    private void updateEntities() {
        for(int i = 0; i < entities.size(); i++) {
            entities.get(i).setFill(Paint.valueOf(playgroundM.getEntities().get(i).getColor()));
            entities.get(i).setRadius(playgroundM.getEntities().get(i).getSize());
        }
    }
}
