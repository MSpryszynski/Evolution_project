package agh.ics.oop.gui.buttons;

import agh.ics.oop.SimulationEngine;
import agh.ics.oop.gui.CsvWriter;
import javafx.scene.control.Button;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class CSVButton {
    private final Button saveToCSV;
    private final CsvWriter csvWriter = new CsvWriter();

    public CSVButton(SimulationEngine engine, SimulationEngine engine2){
        this.saveToCSV = new Button("Create CSV");
        saveToCSV.setOnAction(event ->{
            List<String[]> csvData1 = engine.getCSV();
            csvData1.add(engine.average());
            List<String[]> csvData2 = engine2.getCSV();
            csvData2.add(engine2.average());
            try {
                this.csvWriter.writeToCsvFile(csvData1, new File( "flatmap.csv"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                this.csvWriter.writeToCsvFile(csvData2, new File("roundmap.csv"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public Button getButton() {
        return saveToCSV;
    }
}
