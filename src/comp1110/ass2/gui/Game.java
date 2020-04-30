package comp1110.ass2.gui;

import comp1110.ass2.Player;
import gittest.B;
import javafx.animation.ScaleTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Game extends Application {
    private static final int SQUARE_SIZE = 70;
    private static final int VIEWER_WIDTH = 1024;
    private static final int VIEWER_HEIGHT = 768;
    private static final int DIM = 8;           //board is 8 by 8
    private static final String URI_BASE = "assets/";

    private final Group root = new Group();
    private final Group controls = new Group();

    private int numberOfHumanPlayers ;
    private int numberOfComputerPlayers;
    private int numberOfPlayers;

    /**
     * slider and subsequent boxes to add number of player and their names
     * number of computer players
     * maybe difficulty in future (if playing with computer)
     */
    private void makeControls(){
        Group initialControls =new Group();
        Slider playerCount = new Slider();
        Slider computerPlayerCount = new Slider();
        double sliderWidth = 30;
        double sliderHeight = 200;
        double buttonWidth = 70;
        double buttonHeight = 30;
        double sliderLayoutX = sliderHeight/2;
        double buttonLayoutY = sliderHeight + 20;

        playerCount.setMin(1);
        playerCount.setMax(6);
        playerCount.setShowTickLabels(true);
        playerCount.setShowTickMarks(true);
        playerCount.setMajorTickUnit(1);
        playerCount.setMinorTickCount(0);
        playerCount.setBlockIncrement(1);
        playerCount.setSnapToTicks(true);
        playerCount.setOrientation(Orientation.VERTICAL);
        playerCount.setPrefSize(sliderWidth,sliderHeight);

        computerPlayerCount.setMin(0);
        computerPlayerCount.setMax(5);
        computerPlayerCount.setShowTickLabels(true);
        computerPlayerCount.setShowTickMarks(true);
        computerPlayerCount.setMajorTickUnit(1);
        computerPlayerCount.setMinorTickCount(0);
        computerPlayerCount.setBlockIncrement(1);
        computerPlayerCount.setSnapToTicks(true);
        computerPlayerCount.setOrientation(Orientation.VERTICAL);
        computerPlayerCount.setPrefSize(sliderWidth,sliderHeight);
        computerPlayerCount.setLayoutX(sliderLayoutX);

        Button b = new Button("Proceed");
        b.setPrefSize(buttonWidth,buttonHeight);
        b.setLayoutX((sliderLayoutX + sliderWidth - buttonWidth)/2);
        b.setLayoutY(buttonLayoutY);
        b.setOnAction(actionEvent -> {
            numberOfHumanPlayers = (int) playerCount.getValue();
            numberOfComputerPlayers = (int) computerPlayerCount.getValue();
                    if(numberOfHumanPlayers + numberOfComputerPlayers>6){
                        Text errorLine1 = new Text("Total Number Of Players cannot be more than 6.");
                        Text errorLine2 = new Text("Please try again!");
                        initialControls.getChildren().addAll(errorLine1,errorLine2);
                        //formatting remains, somehow remove these two lines after sometime
                    }
                    else{
                        numberOfPlayers = numberOfComputerPlayers + numberOfHumanPlayers;
                        //call a function which takes input of players' name according to numberOfHumanPlayers
                        //and give compute player some names
                    }
        }
        );
        initialControls.getChildren().addAll(playerCount, computerPlayerCount, b);
        initialControls.setLayoutX((VIEWER_WIDTH - (sliderLayoutX + sliderWidth))/2);
        initialControls.setLayoutY((VIEWER_HEIGHT- (buttonLayoutY+ buttonWidth))/2);

        controls.getChildren().add(initialControls);
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
//        showEmptyBoard(root);
        root.getChildren().add(controls);
        primaryStage.show();
    }
}