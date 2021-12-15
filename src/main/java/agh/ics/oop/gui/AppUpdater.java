package agh.ics.oop.gui;


public class AppUpdater{

    private final App app;
    public AppUpdater(App app){
        this.app = app;
    }

    public void mapChanged() {
        app.updateGridPane();
    }
}
