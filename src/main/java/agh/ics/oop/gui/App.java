package agh.ics.oop.gui;

import agh.ics.oop.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;


public class App extends Application{

    private GridPane gridPane;
    private final AbstractWorldMap map = new FlatWorld(30, 30, 5,5,40,15,1);
    private final AppUpdater appUpdater = new AppUpdater(this);
    private Stage primaryStage;
    private SimulationEngine engine;
    private final Image grassImage = new Image("grass.png");
    private final ImageView grassView = new ImageView(grassImage);
    private final Image animalImage = new Image("up.png");
    private final ImageView animalView = new ImageView(animalImage);



    @Override
    public void start(Stage primaryStage){
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Settings");
        this.engine = new SimulationEngine(map, appUpdater, 20);
        this.gridPane = new GridPane();
        Scene scene = createScene(gridPane);
        primaryStage.setScene(scene);
        primaryStage.show();
}



    public GridPane draw(IWorldMap map, Vector2d upRight, Vector2d lowLeft){
        GridPane gridPane = new GridPane();
        gridPane.setGridLinesVisible(true);
        for (int x=lowLeft.x; x<= upRight.x; x++){
            Label label = new Label(((Integer) x).toString());
            gridPane.add(label, x-lowLeft.x+1, 0, 1, 1);
            GridPane.setHalignment(label, HPos.CENTER);
            gridPane.getColumnConstraints().add(new ColumnConstraints(10));
        }
        for (int y=lowLeft.y; y<= upRight.y; y++){
            Label label = new Label(((Integer) y).toString());
            gridPane.add(label, 0, upRight.y-y+1, 1, 1);
            GridPane.setHalignment(label, HPos.CENTER);
            gridPane.getRowConstraints().add(new RowConstraints(10));
        }
        for (int i=upRight.x; i>=lowLeft.x; i--){
            for (int j=lowLeft.y; j<= upRight.y; j++){
                Vector2d vector = new Vector2d(i, j);
                Object object = map.objectAt(vector);
                if(object != null){
                    Label label = new Label();
                    if (object instanceof Grass){
                        gridPane.add(grassView, i-lowLeft.x+1, upRight.y-j+1, 1, 1);
                    }
                    else{
                        ArrayList<Animal> tempAnimals = ((ArrayList<Animal>) object);
                        if(tempAnimals.size()>0){
                            gridPane.add(animalView, i-lowLeft.x+1, upRight.y-j+1, 1, 1);
                        }
                    }
                    GridPane.setHalignment(label, HPos.CENTER);
                }
                else{
                    Label label = new Label("");
                    gridPane.add(label, i-lowLeft.x+1, upRight.y-j+1, 1, 1);
                }
            }
        }
        gridPane.getColumnConstraints().add(new ColumnConstraints(10));
        gridPane.getRowConstraints().add(new RowConstraints(10));
        Label label = new Label("y/x");
        gridPane.add(label, 0, 0, 1, 1);
        GridPane.setHalignment(label, HPos.CENTER);
        return gridPane;
    }

    public void updateGridPane(){
        Platform.runLater(() -> {
            gridPane.getChildren().clear();
            gridPane = draw(map, new Vector2d(map.getWidth(), map.getHeight()), new Vector2d(0,0));
            Scene scene = new Scene(gridPane, 800, 800);
            primaryStage.setScene(scene);
            primaryStage.show();
        });
    }


    private Scene createScene(GridPane gridPane){
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
        Button button = new Button("Start");
        button.setOnAction(event -> {
            Thread engineThread = new Thread(engine);
            engineThread.start();
        });
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
        VBox box = new VBox(gridPane, button);
        box.setAlignment(Pos.CENTER);
        return new Scene(box, 300, 300);
    }
}
