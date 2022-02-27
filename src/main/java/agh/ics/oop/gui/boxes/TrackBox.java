package agh.ics.oop.gui.boxes;

import agh.ics.oop.Animal;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class TrackBox {

    private final GridPane trackBox;
    public TrackBox(Animal trackedAnimal){
        this.trackBox = new GridPane();
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
    }

    public GridPane getTrackBox() {
        return trackBox;
    }
}
