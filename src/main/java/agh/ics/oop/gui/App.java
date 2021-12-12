package agh.ics.oop.gui;

import agh.ics.oop.*;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage primaryStage){
        GridPane gridPane = new GridPane();
        gridPane.setGridLinesVisible(true);
        MoveDirection[] directions1 = {MoveDirection.FORWARD, MoveDirection.FORWARD, MoveDirection.FORWARD,MoveDirection.FORWARD};
        AbstractWorldMap map = new GrassField(10);
        Vector2d[] positions = {new Vector2d(1, 2), new Vector2d(2, 2), new Vector2d(3, 2),new Vector2d(4, 2)};
        System.out.println(map);
        IEngine engine = new SimulationEngine(directions1, map, positions);
        engine.run();
        System.out.println(map);
        map.place(new Animal(map, new Vector2d(10,10)));
        Vector2d lowLeft = map.getLowerLeft();
        Vector2d upRight = map.getUpperRight();
        for (int x=lowLeft.x; x<= upRight.x; x++){
            Label label = new Label(((Integer) x).toString());
            gridPane.add(label, x-lowLeft.x+1, 0, 1, 1);
            GridPane.setHalignment(label, HPos.CENTER);
        }
        for (int y=lowLeft.y; y<= upRight.y; y++){
            Label label = new Label(((Integer) y).toString());
            gridPane.add(label, 0, upRight.y-(y-lowLeft.y)+1, 1, 1);
            GridPane.setHalignment(label, HPos.CENTER);
        }
        for (int i=upRight.x; i>=lowLeft.x; i--){
            for (int j=lowLeft.y; j<= upRight.y; j++){
                Vector2d vector = new Vector2d(i, j);
                if(map.objectAt(vector) != null){
                    Label label = new Label(map.objectAt(vector).toString());
                    GuiElementBox box = new GuiElementBox((IMapElement) map.objectAt(vector));
                    gridPane.add(box.getImage(), i-lowLeft.x+1, upRight.y-(j-lowLeft.y)+1, 1, 1);
                    GridPane.setHalignment(label, HPos.CENTER);
                }
                else{
                    Label label = new Label("");
                    gridPane.add(label, i-lowLeft.x+1, upRight.y-(j-lowLeft.y)+1, 1, 1);
                }
            }
            gridPane.getColumnConstraints().add(new ColumnConstraints(40));
            gridPane.getRowConstraints().add(new RowConstraints(40));
        }
        gridPane.getColumnConstraints().add(new ColumnConstraints(40));
        gridPane.getRowConstraints().add(new RowConstraints(40));
        Label label = new Label("y/x");
        gridPane.add(label, 0, 0, 1, 1);
        GridPane.setHalignment(label, HPos.CENTER);
        Scene scene = new Scene(gridPane, 600, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
