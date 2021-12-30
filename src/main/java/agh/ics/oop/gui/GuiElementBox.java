package agh.ics.oop.gui;

import agh.ics.oop.Animal;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;


public class GuiElementBox {
    private final VBox box;

    public GuiElementBox(Image image){
        ImageView view = new ImageView(image);
        view.setFitHeight(15);
        view.setFitWidth(15);
        this.box = new VBox(view);
        this.box.setAlignment(Pos.CENTER);
    }

    public GuiElementBox (Circle circle){
        circle.setRadius(7);
        circle.setFill(Color.BLACK);
        this.box = new VBox(circle);
    }

    public GuiElementBox(int energy, boolean bool) {
        Circle circle = new Circle();
        circle.setRadius(9);
        circle.setFill(Color.BROWN);
        circle.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

            }
        });
        if(energy>10){
            circle.setFill(Color.RED);
        }
        if (energy>50){
            circle.setFill(Color.YELLOW);
        }
        if(energy>100) {
            circle.setFill(Color.GREEN);
        }
        this.box = new VBox(circle);
        if (bool){
            String cssLayout = "-fx-border-color: blue;\n" +
                    "-fx-border-width: 2;\n";
            box.setStyle(cssLayout);

        }
        box.setAlignment(Pos.CENTER);
    }

    public VBox getImage(){
        return box;
    }


}
