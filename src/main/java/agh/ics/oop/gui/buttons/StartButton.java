package agh.ics.oop.gui.buttons;

import agh.ics.oop.SimulationEngine;
import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class StartButton {
    private final Button button;

    public StartButton(SimulationEngine engine){
        this.button = new Button("Start");
        GridPane.setHalignment(button, HPos.CENTER);
        button.setOnAction(event -> engine.resume());
    }

    public Button getButton() {
        return this.button;
    }
}
