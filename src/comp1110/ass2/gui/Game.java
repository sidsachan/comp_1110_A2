package comp1110.ass2.gui;

import comp1110.ass2.Player;
import gittest.A;
import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
    private ArrayList<Player> playerArrayList = new ArrayList<>();

    /**
     * slider and subsequent boxes to add number of player and their names
     * number of computer players
     * maybe difficulty in future (if playing with computer)
     */
    private void makeControls(){
        Group initialControls =new Group();
        controls.getChildren().add(initialControls);
    }
    /**
     * function to get empty board added on the root
     * @param root base group of the scene
     */
    private void showEmptyBoard(Group root){
        Viewer.showStations(root);
    }


    /**
     * first function called to et player info, in turn calls
     * getComputerPlayer to find computer opponents
     */
    private void getPlayerInfo(){
        Integer [] playerCountData = {1, 2, 3, 4, 5, 6};
        List<Integer> playerCount;
        playerCount = Arrays.asList(playerCountData);
        String titleTxt = "Players";

        ChoiceDialog dialog = new ChoiceDialog(playerCount.get(0), playerCount);
        dialog.setTitle(titleTxt);
        dialog.setHeaderText("How many people are playing");
        Optional<Integer> result = dialog.showAndWait();

        if (result.isPresent()) {
            numberOfHumanPlayers = result.get();
            getComputerPlayers();
        }
    }

    /**
     * dialog box function for getting number of Computer players
     * if total number of players>6, then calls error dialog box
     * otherwise take in the name of players
     */
    private void getComputerPlayers(){
        Integer [] computerPlayerCountData = {0, 1, 2, 3, 4, 5};
        List<Integer> computerPlayerCount;
        computerPlayerCount = Arrays.asList(computerPlayerCountData);
        String titleTxt = "Computer opponents";

        ChoiceDialog dialog = new ChoiceDialog(computerPlayerCount.get(0), computerPlayerCount);
        dialog.setTitle(titleTxt);
        dialog.setHeaderText("How many computer opponents you want to go against?\n Choose \"Back\" to go to previous menu");
        ((Button) dialog.getDialogPane().lookupButton(ButtonType.CANCEL)).setText("Back");

        Optional<Integer> result = dialog.showAndWait();
        if (result.isPresent()) {
            if(numberOfHumanPlayers + result.get()>6)
            {
                playerCountError();
            }
            else
                numberOfComputerPlayers = result.get();
            getPlayerNames();
        }
        else
            getPlayerInfo();
    }

    /**
     * When total number of players>6, this is called.
     * gives a chance to go back to computerPlayer selection
     */
    private void playerCountError(){
        String titleTxt = "Oh No!!";
        Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(titleTxt);
            String header = "Maximum number of player allowed is 6.\n" +" Please reselect number of computer opponents or human players.";
            alert.setContentText(header);
            alert.showAndWait();
            getComputerPlayers();
    }

    /**
     * function to get player names, A text input dialog box
     */
    private void getPlayerNames() {
        Dialog<String> dialog = new Dialog<>();
        String titleTxt = "Names";
        dialog.setTitle(titleTxt);
        dialog.setHeaderText("Enter name of the players : ");
        dialog.setResizable(true);
        GridPane grid = new GridPane();

        Label[] labels = new Label[numberOfHumanPlayers];           //Lables array to show which player info is in the given text field
        TextField[] textFields = new TextField[numberOfHumanPlayers];
        for (int i = 0; i < labels.length; i++) {
            int j = i + 1;
            Label temp = new Label("Player " + j);
            labels[i] = temp;
            grid.add(labels[i], 1, i + 1);
            TextField text = new TextField();
            textFields[i] = text;
            grid.add(textFields[i], 2, i + 1);
        }
        dialog.getDialogPane().setContent(grid);

        ButtonType buttonTypeOk = new ButtonType("Proceed", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);

        Optional<String> result = dialog.showAndWait();

        if (result.isPresent()) {
            //add human player to the list
            for (int i = 0; i < numberOfHumanPlayers; i++) {
                String name;
                if (!textFields[i].getText().isEmpty()) {
                    name = textFields[i].getText();
                }
                else{
                    name = "P" + (i+1);         //default player name is P1, P2 etc.
                }
                playerArrayList.add(new Player(name));
            }
            //add computer players to the list
            for (int i=0;i<numberOfComputerPlayers;i++){
                playerArrayList.add(new Player("C" +(i+1)));
            }
        }
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
        getPlayerInfo();
    }
}