package agh.ics.oop.gui;

import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class CenteredLabel extends Label{
    private final Label label;

    public CenteredLabel(String text){
        this.label = new Label(text);
        GridPane.setHalignment(label, HPos.CENTER);
    }

    public Label getLabel() {
        return label;
    }
}
