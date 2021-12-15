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
import javafx.scene.layout.*;
import javafx.stage.Stage;


public class App extends Application{

    private GridPane gridPane;
    private final IWorldMap map = new GrassField(10);
    private final AppUpdater appUpdater = new AppUpdater(this);
    private Stage primaryStage;
    private SimulationEngine engine;
    private OptionsParser parser;


    @Override
    public void start(Stage primaryStage){
        this.primaryStage = primaryStage;
        Vector2d[] positions = {new Vector2d(1, 2), new Vector2d(2, 2), new Vector2d(3, 2),new Vector2d(4, 2)};
        this.engine = new SimulationEngine(map, positions, appUpdater);
        this.parser = new OptionsParser();
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
            gridPane.getColumnConstraints().add(new ColumnConstraints(40));
        }
        for (int y=lowLeft.y; y<= upRight.y; y++){
            Label label = new Label(((Integer) y).toString());
            gridPane.add(label, 0, upRight.y-y+1, 1, 1);
            GridPane.setHalignment(label, HPos.CENTER);
            gridPane.getRowConstraints().add(new RowConstraints(40));
        }
        for (int i=upRight.x; i>=lowLeft.x; i--){
            for (int j=lowLeft.y; j<= upRight.y; j++){
                Vector2d vector = new Vector2d(i, j);
                if(map.objectAt(vector) != null){
                    Label label = new Label(map.objectAt(vector).toString());
                    GuiElementBox box = new GuiElementBox((IMapElement) map.objectAt(vector));
                    gridPane.add(box.getImage(), i-lowLeft.x+1, upRight.y-j+1, 1, 1);
                    GridPane.setHalignment(label, HPos.CENTER);
                }
                else{
                    Label label = new Label("");
                    gridPane.add(label, i-lowLeft.x+1, upRight.y-j+1, 1, 1);
                }
            }
        }
        gridPane.getColumnConstraints().add(new ColumnConstraints(40));
        gridPane.getRowConstraints().add(new RowConstraints(40));
        Label label = new Label("y/x");
        gridPane.add(label, 0, 0, 1, 1);
        GridPane.setHalignment(label, HPos.CENTER);
        return gridPane;
    }

    public void updateGridPane(){
        Platform.runLater(() -> {
            gridPane.getChildren().clear();
            Vector2d lowLeft = map.getLowerLeft();
            Vector2d upRight = map.getUpperRight();
            gridPane = draw(map, upRight, lowLeft);
            Scene scene = createScene(gridPane);
            primaryStage.setScene(scene);
            primaryStage.show();
        });
    }


    private Scene createScene(GridPane gridPane){
        TextField text = new TextField();
        Button button = new Button("Start");
        button.setOnAction(event -> {
            engine.setDirections(parser.parse(text.getText().split(" ")));
            Thread engineThread = new Thread(engine);
            engineThread.start();
        });
        VBox box = new VBox(gridPane, button, text);
        box.setAlignment(Pos.CENTER);
        return new Scene(box, 500, 500);
    }
}
