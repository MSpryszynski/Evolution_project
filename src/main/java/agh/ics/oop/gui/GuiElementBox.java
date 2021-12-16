package agh.ics.oop.gui;

import agh.ics.oop.IMapElement;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;


public class GuiElementBox {
    private final VBox box;

    public GuiElementBox(IMapElement mapElement){
        Image grassImage = new Image(mapElement.getImageUrl());
        ImageView grassView = new ImageView(grassImage);
        grassView.setFitHeight(15);
        grassView.setFitWidth(15);
        Label positionLabel = new Label(mapElement.getPosition().toString());
        this.box = new VBox(grassView, positionLabel);
        this.box.setAlignment(Pos.CENTER);
    }

    public VBox getImage(){
        return box;
    }




}
