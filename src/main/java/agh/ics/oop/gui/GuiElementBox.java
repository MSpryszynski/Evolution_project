package agh.ics.oop.gui;

import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;


public class GuiElementBox {
    private final VBox box;

    public GuiElementBox(ImageView image){
        image.setFitHeight(8);
        image.setFitWidth(8);
        this.box = new VBox(image);
        this.box.setAlignment(Pos.CENTER);
    }

    public VBox getImage(){
        return box;
    }




}
