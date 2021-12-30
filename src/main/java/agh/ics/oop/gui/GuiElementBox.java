package agh.ics.oop.gui;


import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;


public class GuiElementBox {
    private final VBox box;

    public GuiElementBox(Image image, int width, boolean isJungle){
        ImageView view = new ImageView(image);
        view.setFitHeight(150/width);
        view.setFitWidth(150/width);
        this.box = new VBox(view);
        this.box.setAlignment(Pos.CENTER);
        if (isJungle){
            String cssLayout = "-fx-background-color: yellowgreen;\n";
            box.setStyle(cssLayout);
        }
    }

    public GuiElementBox(boolean isJungle){
        this.box = new VBox(new Label(""));
        this.box.setAlignment(Pos.CENTER);
        if (isJungle){
            String cssLayout = "-fx-background-color: yellowgreen;\n";
            box.setStyle(cssLayout);
        }
    }


    public GuiElementBox(int energy, boolean bool, int width, boolean isJungle) {
        Circle circle = new Circle();
        circle.setRadius(90/width);
        circle.setFill(Color.BROWN);
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
        if (isJungle){
            String cssLayout = "-fx-background-color: yellowgreen;\n";
            box.setStyle(cssLayout);
        }
        box.setAlignment(Pos.CENTER);
    }

    public VBox getImage(){
        return box;
    }


}
