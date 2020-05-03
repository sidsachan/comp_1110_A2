package comp1110.ass2.gui;

import comp1110.ass2.Metro;
import comp1110.ass2.Player;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
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
    private final Group scoreBoard = new Group();
    private final Group stations = new Group();
    private final GridPane nameAndScore = new GridPane();

    private int numberOfHumanPlayers;
    private int numberOfComputerPlayers;
    private int numberOfPlayers;
    private ArrayList<Player> playerArrayList = new ArrayList<>();
    private ArrayList<String> deck = Metro.getFreshDeck();

    /**
     * slider and subsequent boxes to add number of player and their names
     * number of computer players
     * maybe difficulty in future (if playing with computer)
     */
    private void makeControls() {
        Group initialControls = new Group();
        controls.getChildren().add(initialControls);
    }

    /**
     * function to get empty board added on the root
     */
    private void showEmptyBoard() {
        Viewer.showStations(stations);
        root.getChildren().add(stations);
    }

    /**
     * first function called to et player info, in turn calls
     * getComputerPlayer to find computer opponents
     */
    private void getPlayerInfo() {
        Integer[] playerCountData = {1, 2, 3, 4, 5, 6};
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
    private void getComputerPlayers() {
        Integer[] computerPlayerCountData = {0, 1, 2, 3, 4, 5};
        List<Integer> computerPlayerCount;
        computerPlayerCount = Arrays.asList(computerPlayerCountData);
        String titleTxt = "Computer opponents";

        ChoiceDialog dialog = new ChoiceDialog(computerPlayerCount.get(0), computerPlayerCount);
        dialog.setTitle(titleTxt);
        dialog.setHeaderText("How many computer opponents you want to go against?\n Choose \"Back\" to go to previous menu");
        ((Button) dialog.getDialogPane().lookupButton(ButtonType.CANCEL)).setText("Back");

        Optional<Integer> result = dialog.showAndWait();
        if (result.isPresent()) {
            if (numberOfHumanPlayers + result.get() > 6) {
                playerCountError();
            } else
                numberOfComputerPlayers = result.get();
            numberOfPlayers = numberOfHumanPlayers + numberOfComputerPlayers;
            getPlayersName();
        } else {
            getPlayerInfo();
        }
    }

    /**
     * When total number of players>6, this is called.
     * gives a chance to go back to computerPlayer selection
     */
    private void playerCountError() {
        String titleTxt = "Oh No!!";
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titleTxt);
        String header = "Maximum number of player allowed is 6.\n" + " Please reselect number of computer opponents or human players.";
        alert.setContentText(header);
        alert.showAndWait();
        getComputerPlayers();
    }

    /**
     * function to get player names, A text input dialog box
     * And add those name to the player array list
     * default player name are P1, P2, ...
     * default computer player name are C1, C2, ...
     */
    private void getPlayersName() {
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
                } else {
                    name = "P" + (i + 1);         //default player name
                }
                playerArrayList.add(new Player(name));
            }
            //add computer players to the list
            for (int i = 0; i < numberOfComputerPlayers; i++) {
                playerArrayList.add(new Player("C" + (i + 1)));
            }
            initiateScoreBoard();
        }
    }

    /**
     * function to initiate the scoreboard
     * includes the names and scores of the players
     * can replace heading with a custom image
     */
    private void initiateScoreBoard() {
        ArrayList<Text> playerName = new ArrayList<>();
        ArrayList<Text> playerScore = new ArrayList<>();
        //heading of the scoreboard
        Text heading = new Text("SCOREBOARD");
        heading.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 30));
        double headingWidth = 250;
        double headingHeight = 30;
        //left sub heading
        Text subHeadingLeft = new Text("NAME");
        subHeadingLeft.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        //Right subheading
        Text subHeadingRight = new Text("SCORE");
        subHeadingRight.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        double subHeadingWidth = headingWidth / 2;
        double subHeadingHeight = 20;
        subHeadingLeft.setX(20);
        subHeadingLeft.setY(headingHeight);
        subHeadingRight.setX(subHeadingWidth - 10);
        subHeadingRight.setY(headingHeight);

        //Name and score of the players
        for (int i = 0; i < numberOfPlayers; i++) {
            playerName.add(new Text(playerArrayList.get(i).getName()));
            playerScore.add(new Text(String.valueOf(playerArrayList.get(i).getScore())));
        }
        //adding nodes sequentially to help in removal later on
        //so player names come first and then do scores
        for (int i = 0; i < numberOfPlayers; i++) {
            playerName.get(i).setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 15));
            nameAndScore.add(playerName.get(i), 0, i);
        }
        for (int i = 0; i < numberOfPlayers; i++) {
            playerScore.get(i).setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 15));
            nameAndScore.add(playerScore.get(i), 1, i);
        }
        //Formatting the grid pane
        nameAndScore.setLayoutX(0);
        nameAndScore.setLayoutY(headingHeight + subHeadingHeight);
        nameAndScore.setGridLinesVisible(false);
        nameAndScore.setHgap(5);
        nameAndScore.setVgap(5);
        nameAndScore.getColumnConstraints().add(0, new ColumnConstraints(110));
        nameAndScore.getColumnConstraints().add(1, new ColumnConstraints(70));
        nameAndScore.setPadding(new Insets(5, 5, 5, 30));

        scoreBoard.getChildren().addAll(heading, subHeadingLeft, subHeadingRight, nameAndScore);
        scoreBoard.setLayoutX(VIEWER_WIDTH - headingWidth);
        scoreBoard.setLayoutY(50);
        root.getChildren().add(scoreBoard);
        showEmptyBoard();
        //for checking the working of updateScore function
//        updateScoreBoard("");
    }

    /**
     * function to update the text belonging to nameAndScore grid pane in the scoreBoard group.
     */
    private void updateScoreBoard(String placementSequence) {
        //get updated player list
        Metro.assignScore(placementSequence, playerArrayList);
        //removing the scores nodes first
        for (int i=0;i<playerArrayList.size();i++){
            //since the index gets updated on removal we will remove the same index multiple times
            nameAndScore.getChildren().remove(playerArrayList.size());
        }
        //adding new nodes for scores
        for (int i=0;i<playerArrayList.size();i++){
            //since the index gets updated on removal we will remove the same index multiple times
            Text t = new Text(String.valueOf(playerArrayList.get(i).getScore()));
            t.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 15));
            nameAndScore.add(t,1,i);
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Metro Game");
        Scene scene = new Scene(root, VIEWER_WIDTH, VIEWER_HEIGHT);

        primaryStage.setScene(scene);
        makeControls();
        root.getChildren().add(controls);
        primaryStage.show();
        getPlayerInfo();
    }
}