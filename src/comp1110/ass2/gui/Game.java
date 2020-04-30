package comp1110.ass2.gui;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.stage.Stage;

public class Game extends Application {
    private static final int SQUARE_SIZE = 70;
    private static final int VIEWER_WIDTH = 1024;
    private static final int VIEWER_HEIGHT = 768;
    private static final int DIM = 8;           //board is 8 by 8
    private static final String URI_BASE = "assets/";

    private final Group root = new Group();
    private final Group controls = new Group();
    private final Slider playerCount = new Slider();
    /**
     * slider and subsequent boxes to add number of player and their names
     * number of computer players
     * maybe difficulty in future (if playing with computer)
     */
    private void makeControls(){
        playerCount.setMin(1);
        playerCount.setMax(6);
        playerCount.setShowTickLabels(true);
        playerCount.setShowTickMarks(true);
        playerCount.setMajorTickUnit(1);
        playerCount.setMinorTickCount(1);
        controls.getChildren().add(playerCount);
    }
    /**
     * function to get empty board added on the root
     * @param root base group of the scene
     */
    private void showEmptyBoard(Group root){
        Viewer.showStations(root);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Metro Game");
        Scene scene = new Scene(root, VIEWER_WIDTH, VIEWER_HEIGHT);

        primaryStage.setScene(scene);
        makeControls();
        showEmptyBoard(root);

        primaryStage.show();
    }
}