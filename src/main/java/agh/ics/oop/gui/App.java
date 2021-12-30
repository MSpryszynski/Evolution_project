package agh.ics.oop.gui;

import agh.ics.oop.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class App extends Application{

    private GridPane gridPaneFlat = new GridPane();
    private GridPane gridPaneRound = new GridPane();
    private int width;
    private int height;
    private int jungleWidth;
    private int jungleHeight;
    private int plantEnergy;
    private int startEnergy;
    private int moveEnergy;
    private IWorldMap map;
    private IWorldMap map2;
    private final AppUpdater appUpdater = new AppUpdater(this);
    private Stage primaryStage;
    private SimulationEngine engine;
    private SimulationEngine engine2;
    private final Image grassImage = new Image("grass.png");
    private Thread engineThread;
    private Thread engine2Thread;
    private final NumberAxis xAxisAnimals = new NumberAxis();
    private final NumberAxis yAxisAnimals = new NumberAxis();
    private final NumberAxis xAxisGrass = new NumberAxis();
    private final NumberAxis yAxisGrass = new NumberAxis();
    private final XYChart.Series animals = new XYChart.Series();
    private final XYChart.Series animals2 = new XYChart.Series();
    private final XYChart.Series grass = new XYChart.Series();
    private final XYChart.Series grass2 = new XYChart.Series();
    private final LineChart lineChartAnimals = new LineChart(xAxisAnimals, yAxisAnimals);
    private final LineChart lineChartGrass = new LineChart(xAxisGrass, yAxisGrass);
    private Animal trackedAnimal = null;
    private boolean showBestGenotype = false;
    private boolean isMagic1 = false;
    private boolean isMagic2 = false;



    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Settings");
        Scene scene = createScene();
        primaryStage.setScene(scene);
        primaryStage.show();
}



    public GridPane draw(IWorldMap worldMap, Vector2d upRight, Vector2d lowLeft){
        primaryStage.setTitle("Ewolucja!");
        GridPane gridPane = new GridPane();
        for (int x=lowLeft.x; x<= upRight.x; x++){
            Label label = new Label(((Integer) x).toString());
            gridPane.add(label, x-lowLeft.x+1, 0, 1, 1);
            GridPane.setHalignment(label, HPos.CENTER);
            gridPane.getColumnConstraints().add(new ColumnConstraints(200/width));
        }
        for (int y=lowLeft.y; y<= upRight.y; y++){
            Label label = new Label(((Integer) y).toString());
            gridPane.add(label, 0, upRight.y-y+1, 1, 1);
            GridPane.setHalignment(label, HPos.CENTER);
            gridPane.getRowConstraints().add(new RowConstraints(200/width));
        }
        for (int i=upRight.x; i>=lowLeft.x; i--){
            for (int j=lowLeft.y; j<= upRight.y; j++){
                Vector2d vector = new Vector2d(i, j);
                boolean isJungle = vector.follows(worldMap.getJungleStartPoint()) && vector.precedes(worldMap.getJungleEndPoint());
                Object object = worldMap.objectAt(vector);
                if(object != null){
                    Label label = new Label();
                    if (object instanceof Grass){
                        GuiElementBox box = new GuiElementBox(grassImage, width, isJungle);
                        gridPane.add(box.getImage(), i-lowLeft.x+1, upRight.y-j+1, 1, 1);
                    }
                    else{
                        ArrayList<Animal> tempAnimals = ((ArrayList<Animal>) object);
                        if(tempAnimals.size()>0){
                            Animal animal = tempAnimals.get(0);
                            if(showBestGenotype && animal.getGenotype().equals(worldMap.getBestGenotype())){
                                GuiElementBox box = new GuiElementBox(animal.getEnergy(), true, width, isJungle);
                                VBox guiBox = box.getImage();
                                guiBox.setOnMouseClicked((action) -> {
                                    if(trackedAnimal != null){
                                        trackedAnimal.unsetTracked();
                                        ArrayList<Animal> animalArrayList = worldMap.getAnimalList();
                                        for (Animal worldAnimal : animalArrayList){
                                            worldAnimal.setTrackedAncestor(false);
                                        }
                                    }
                                    worldMap.setTrackedAnimal(animal);
                                    trackedAnimal = animal;
                                    trackedAnimal.setTracked();
                                });
                                gridPane.add(guiBox, i-lowLeft.x+1, upRight.y-j+1, 1, 1);
                            }
                            else{
                                GuiElementBox box = new GuiElementBox(animal.getEnergy(), false, width, isJungle);
                                VBox guiBox = box.getImage();
                                guiBox.setOnMouseClicked((action) -> {
                                    if(trackedAnimal != null){
                                        trackedAnimal.unsetTracked();
                                        ArrayList<Animal> animalArrayList = worldMap.getAnimalList();
                                        for (Animal worldAnimal : animalArrayList){
                                            worldAnimal.setTrackedAncestor(false);
                                        }
                                    }
                                    worldMap.setTrackedAnimal(animal);
                                    trackedAnimal = animal;
                                    trackedAnimal.setTracked();
                                });
                                gridPane.add(guiBox, i-lowLeft.x+1, upRight.y-j+1, 1, 1);
                            }
                        }
                    }
                    GridPane.setHalignment(label, HPos.CENTER);
                }
                else{
                    GuiElementBox box = new GuiElementBox(isJungle);
                    gridPane.add(box.getImage(), i-lowLeft.x+1, upRight.y-j+1, 1, 1);
                }
            }
        }
        gridPane.getColumnConstraints().add(new ColumnConstraints(200/width));
        gridPane.getRowConstraints().add(new RowConstraints(200/width));
        Label label = new Label("y/x");
        gridPane.add(label, 0, 0, 1, 1);
        GridPane.setHalignment(label, HPos.CENTER);
        return gridPane;
    }

    public void updateGridPane(){
        Platform.runLater(() -> {
            gridPaneFlat.getChildren().clear();
            gridPaneFlat = draw(map, new Vector2d(width, height), new Vector2d(0,0));
            gridPaneRound.getChildren().clear();
            gridPaneRound = draw(map2, new Vector2d(width, height), new Vector2d(0,0));
            showBestGenotype = false;
            Button buttonStop = new Button("Stop");
            GridPane.setHalignment(buttonStop, HPos.CENTER);
            Button buttonStart = new Button("Start");
            GridPane.setHalignment(buttonStart,HPos.CENTER);
            buttonStop.setOnAction(event -> engine.pause());
            buttonStart.setOnAction(event -> engine.resume());
            Button buttonStop2 = new Button("Stop");
            GridPane.setHalignment(buttonStop2,HPos.CENTER);
            Button buttonStart2 = new Button("Start");
            GridPane.setHalignment(buttonStart2,HPos.CENTER);
            buttonStop2.setOnAction(event -> engine2.pause());
            buttonStart2.setOnAction(event -> engine2.resume());
            Integer numberOfDays = engine.getNumberOfDays();
            Integer numberOfDays2 = engine2.getNumberOfDays();
            Label days = new Label(numberOfDays.toString());
            Label days2 = new Label(numberOfDays2.toString());
            GridPane.setHalignment(days, HPos.CENTER);
            GridPane.setHalignment(days2, HPos.CENTER);
            Label genotype = new Label(map.getStrongestGenotype());
            Label genotype2 = new Label(map2.getStrongestGenotype());
            GridPane.setHalignment(genotype, HPos.CENTER);
            GridPane.setHalignment(genotype2, HPos.CENTER);
            Label energy = new Label(map.getAverageEnergy().toString());
            Label energy2 = new Label(map2.getAverageEnergy().toString());
            GridPane.setHalignment(energy, HPos.CENTER);
            GridPane.setHalignment(energy2, HPos.CENTER);
            Label lifeTime = new Label(map.averageLifeTime().toString());
            Label lifeTime2 = new Label(map2.averageLifeTime().toString());
            GridPane.setHalignment(lifeTime, HPos.CENTER);
            GridPane.setHalignment(lifeTime2, HPos.CENTER);
            Label children = new Label(map.averageChildren().toString());
            Label children2 = new Label(map2.averageChildren().toString());
            GridPane.setHalignment(children, HPos.CENTER);
            GridPane.setHalignment(children2, HPos.CENTER);
            Label magicChances = new Label(map.sendMagicInfo());
            Label magicChances2 = new Label(map2.sendMagicInfo());
            GridPane.setHalignment(magicChances, HPos.CENTER);
            GridPane.setHalignment(magicChances2, HPos.CENTER);
            this.animals.getData().add(new XYChart.Data(numberOfDays, map.getNumberOfAnimals()));
            this.animals2.getData().add(new XYChart.Data(numberOfDays2, map2.getNumberOfAnimals()));
            this.grass.getData().add(new XYChart.Data(numberOfDays, map.getNumberOfPlants()));
            this.grass2.getData().add(new XYChart.Data(numberOfDays2, map2.getNumberOfPlants()));
            GridPane gridBox = new GridPane();
            Label flatMap = new Label("Flat map");
            GridPane.setHalignment(flatMap, HPos.CENTER);
            Label roundMap = new Label("Round map");
            GridPane.setHalignment(roundMap, HPos.CENTER);
            gridPaneFlat.setAlignment(Pos.CENTER);
            gridPaneRound.setAlignment(Pos.CENTER);
            gridBox.add(flatMap,1,0);
            gridBox.add(roundMap,2,0);
            gridBox.add(gridPaneFlat,1,1);
            gridBox.add(gridPaneRound,2,1);
            gridBox.add(buttonStop, 1, 2);
            gridBox.add(buttonStart, 1, 3);
            gridBox.add(buttonStop2, 2, 2);
            gridBox.add(buttonStart2, 2, 3);
            gridBox.add(new Label("Number od days: "),0,4);
            gridBox.add(days, 1, 4);
            gridBox.add(days2, 2, 4);
            gridBox.add(new Label("Best genotype: "), 0, 5);
            gridBox.add(genotype,1,5);
            gridBox.add(genotype2,2,5);
            gridBox.add(new Label("Average energy: "), 0 , 6);
            gridBox.add(energy, 1, 6);
            gridBox.add(energy2, 2, 6);
            gridBox.add(new Label("Average lifetime: "),0,7);
            gridBox.add(lifeTime, 1, 7);
            gridBox.add(lifeTime2, 2, 7);
            gridBox.add(new Label("Average children: "),0, 8);
            gridBox.add(children,1, 8);
            gridBox.add(children2, 2, 8);
            gridBox.add(new Label("Magic chances left: "), 0, 9);
            gridBox.add(magicChances,1,9);
            gridBox.add(magicChances2,2,9);
            gridBox.add(lineChartAnimals, 1, 10);
            GridPane.setHalignment(lineChartAnimals, HPos.CENTER);
            gridBox.add(lineChartGrass, 2, 10);
            GridPane.setHalignment(lineChartGrass, HPos.CENTER);
            if(trackedAnimal != null){
                GridPane trackBox = new GridPane();
                trackBox.add(new Label("Tracked genotype: "), 0, 0);
                Label trackedGenotype = new Label(trackedAnimal.getGenotype().toString());
                trackBox.add(trackedGenotype, 0, 1);
                trackBox.add(new Label("Children: "), 0, 2);
                Label trackedChildren = new Label(trackedAnimal.getTrackedChildren().toString());
                trackBox.add(trackedChildren, 0, 3);
                trackBox.add(new Label("Descendants:"),0,4);
                Label trackedDescendants = new Label(trackedAnimal.getTrackedDescendants().toString());
                trackBox.add(trackedDescendants, 0, 5);
                trackBox.add(new Label("Description: "),0,6);
                if(trackedAnimal.getDeathDay() != 0){
                    Label trackedDeathDay = new Label("Died in day " + trackedAnimal.getDeathDay().toString());
                    trackBox.add(trackedDeathDay, 0, 7);
                }
                else{
                    Label trackedDeathDay = new Label("Still alive");
                    trackBox.add(trackedDeathDay, 0, 7);
                }
                gridBox.add(trackBox, 3, 1);
            }
            gridBox.setHgap(40);
            Button showGenotype = new Button("Show best genes");
            showGenotype.setOnAction(event -> {
                showBestGenotype = true;
                updateGridPane();
            });
            Button saveToCSV = new Button("Create CSV");
            saveToCSV.setOnAction(event ->{
                List<String[]> csvData1 = engine.getCSV();
                csvData1.add(engine.average());
                List<String[]> csvData2 = engine2.getCSV();
                csvData2.add(engine2.average());
                try {
                    writeToCsvFile(csvData1, new File( "flatmap.csv"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    writeToCsvFile(csvData2, new File("roundmap.csv"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            gridBox.add(showGenotype, 3, 4);
            gridBox.add(saveToCSV, 3, 5);
            Scene scene = new Scene(gridBox, 1200, 800);
            primaryStage.setScene(scene);
            primaryStage.show();
        });
    }

    private Scene createScene(){
        GridPane gridPane = new GridPane();
        TextField widthText = new TextField();
        Label widthLabel = new Label("Width: ");
        TextField heightText = new TextField();
        Label heightLabel = new Label("Height: ");
        TextField startEnergyText = new TextField();
        Label startEnergyLabel = new Label("Start energy: ");
        TextField moveEnergyText = new TextField();
        Label moveEnergyLabel = new Label("Move energy: ");
        TextField plantEnergyText = new TextField();
        Label plantEnergyLabel = new Label("Plant energy: ");
        TextField jungleWidthText = new TextField();
        Label jungleWidthLabel = new Label("Jungle width: ");
        TextField jungleHeightText = new TextField();
        Label jungleHeightLabel = new Label("Jungle height: ");
        Button magicMap1 = new Button("Set Map1 magic");
        magicMap1.setOnAction(event -> {
            isMagic1 = true;
            magicMap1.setVisible(false);
        });
        Button magicMap2 = new Button("Set Map2 magic");
        magicMap2.setOnAction(event -> {
            isMagic2 = true;
            magicMap2.setVisible(false);
        });
        Button button = new Button("Start");
        button.setOnAction(event ->  {
            this.width = Integer.parseInt(widthText.getText());
            this.height = Integer.parseInt(heightText.getText());
            this.jungleWidth = Integer.parseInt(jungleWidthText.getText());
            this.jungleHeight = Integer.parseInt(jungleHeightText.getText());
            this.plantEnergy = Integer.parseInt(plantEnergyText.getText());
            this.startEnergy = Integer.parseInt(startEnergyText.getText());
            this.moveEnergy = Integer.parseInt(moveEnergyText.getText());
            this.map = new FlatWorld(width, height, jungleWidth, jungleHeight, plantEnergy, startEnergy, moveEnergy, isMagic1);
            this.map2 = new RoundWorld(width, height, jungleWidth, jungleHeight, plantEnergy, startEnergy, moveEnergy, isMagic2);
            this.engine = new SimulationEngine(map, appUpdater, 10);
            this.engine2 = new SimulationEngine(map2, appUpdater, 10);
            engineThread = new Thread(engine);
            engine2Thread = new Thread(engine2);
            engineThread.start();
            engine2Thread.start();
            lineChartAnimals.getData().add(animals);
            animals.setName("Flat");
            lineChartAnimals.getData().add(animals2);
            animals2.setName("Round");
            lineChartGrass.getData().add(grass);
            grass.setName("Flat");
            lineChartGrass.getData().add(grass2);
            grass2.setName("Round");
            lineChartAnimals.setPrefSize(200, 200);
            lineChartGrass.setPrefSize(200, 200);
            xAxisAnimals.setLabel("Days");
            yAxisAnimals.setLabel("Animals");
            xAxisGrass.setLabel("Days");
            yAxisGrass.setLabel("Grass");
        });
        gridPane.add(widthLabel, 0, 0);
        gridPane.add(widthText, 1, 0);
        gridPane.add(heightLabel, 0, 1);
        gridPane.add(heightText, 1, 1);
        gridPane.add(startEnergyLabel, 0, 2);
        gridPane.add(startEnergyText, 1, 2);
        gridPane.add(moveEnergyLabel, 0, 3);
        gridPane.add(moveEnergyText, 1, 3);
        gridPane.add(plantEnergyLabel, 0, 4);
        gridPane.add(plantEnergyText, 1, 4);
        gridPane.add(jungleWidthLabel, 0, 5);
        gridPane.add(jungleWidthText, 1, 5);
        gridPane.add(jungleHeightLabel, 0, 6);
        gridPane.add(jungleHeightText, 1, 6);
        gridPane.setAlignment(Pos.CENTER);
        VBox box = new VBox(gridPane, magicMap1, magicMap2, button);
        box.setAlignment(Pos.CENTER);
        return new Scene(box, 300, 300);
    }

    //https://mkyong.com/java/how-to-export-data-to-csv-file-java/
    public String convertToCsvFormat(final String[] line) {
        return convertToCsvFormat(line, ",");
    }

    public String convertToCsvFormat(final String[] line, final String separator) {
        return convertToCsvFormat(line, separator, true);
    }


    public String convertToCsvFormat(
            final String[] line,
        final String separator,
        final boolean quote) {

            return Stream.of(line)                              // convert String[] to stream
                    .map(l -> formatCsvField(l, quote))         // format CSV field
                    .collect(Collectors.joining(separator));    // join with a separator

        }

        private String formatCsvField(final String field, final boolean quote) {

            String result = field;

            if (result.contains(",")
                    || result.contains("\"")
                    || result.contains("\n")
                    || result.contains("\r\n")) {

                result = result.replace("\"", "\"\"");

                result = "\"" + result + "\"";

            } else {
                if (quote) {
                    result ="\""  + result + "\"";
                }
            }
            return result;
        }
        private void writeToCsvFile(List<String[]> list, File file) throws IOException {

            List<String> collect = list.stream()
                    .map(this::convertToCsvFormat)
                    .collect(Collectors.toList());

            try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
                for (String line : collect) {
                    bw.write(line);
                    bw.newLine();
                }
            }
    }
}
