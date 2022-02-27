package agh.ics.oop.gui.buttons;

import agh.ics.oop.SimulationEngine;
import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class StopButton {

    private final Button button;

    public StopButton(SimulationEngine engine){
        this.button = new Button("Stop");
        GridPane.setHalignment(button, HPos.CENTER);
        button.setOnAction(event -> engine.pause());
    }

    public Button getButton() {
        return this.button;
    }
}
