package agh.ics.oop.gui;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class Settings {
    Settings(){
    }

    private GridPane createSettings(){
        GridPane gridPane = new GridPane();
        TextField width = new TextField();
        Label widthLabel = new Label("Width: ");
        TextField height = new TextField();
        Label heightLabel = new Label("Height: ");
        TextField startEnergy = new TextField();
        Label startEnergyLabel = new Label("Start energy: ");
        TextField moveEnergy = new TextField();
        Label moveEnergyLabel = new Label("Move energy: ");
        TextField plantEnergy = new TextField();
        Label plantEnergyLabel = new Label("Plant energy: ");
        TextField jungleWidth = new TextField();
        Label jungleWidthLabel = new Label("Jungle width: ");
        TextField jungleHeight = new TextField();
        Label jungleHeightLabel = new Label("Jungle height: ");
        gridPane.add(widthLabel, 0, 0);
        gridPane.add(width, 1, 0);
        gridPane.add(heightLabel, 0, 1);
        gridPane.add(height, 1, 1);
        gridPane.add(startEnergyLabel, 0, 2);
        gridPane.add(startEnergy, 1, 2);
        gridPane.add(moveEnergyLabel, 0, 3);
        gridPane.add(moveEnergy, 1, 3);
        gridPane.add(plantEnergyLabel, 0, 4);
        gridPane.add(plantEnergy, 1, 4);
        gridPane.add(jungleWidthLabel, 0, 5);
        gridPane.add(jungleWidth, 1, 5);
        gridPane.add(jungleHeightLabel, 0, 6);
        gridPane.add(jungleHeight, 1, 6);
        gridPane.setAlignment(Pos.CENTER);
        return gridPane;
    }
}
