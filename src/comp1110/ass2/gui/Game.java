package comp1110.ass2.gui;

import comp1110.ass2.Board;
import comp1110.ass2.Metro;
import comp1110.ass2.Player;
import comp1110.ass2.Tile;
import javafx.animation.KeyFrame;
import javafx.animation.PathTransition;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.*;

public class Game extends Application {

    private static final int SQUARE_SIZE = 70;
    private static final int VIEWER_WIDTH = 1024;
    private static final int VIEWER_HEIGHT = 768;

    //board is 8 by 8
    private static final int DIM = 8;
    private static final String URI_BASE = "assets/";

    private final Group root = new Group();
    private final Group controls = new Group();
    private final Group scoreBoard = new Group();
    private final Group emptyBoard = new Group();
    private final Group stations = new Group();
    private final GridPane nameAndScore = new GridPane();
    private final VBox tileHandling = new VBox();
    private Text nameOfPlayersTurn = new Text();

    //存放 computer 的 发牌记录
//    private final HashMap<Integer>
    private int numberOfHumanPlayers;
    private int numberOfComputerPlayers;
    private int numberOfPlayers;
    //玩家列表
    private ArrayList<Player> playerArrayList = new ArrayList<>();
    //
    private ArrayList<String> deck = new ArrayList<>();

    private ArrayList<Rectangle> emptyBoardSquares = new ArrayList<>();

    private HashMap<Integer, DraggableImage> tileInHandImages = new HashMap<>();

    private Rectangle highlighted = null;

    private StringBuilder placementSequence = new StringBuilder();
    //是否显示card
    private boolean isCardShowing = false;
    //判断是否该 玩家发牌
    private int indexOfPlayersTurn = 0;

    /**
     * a class for making a draggable image
     */

    class DraggableImage extends ImageView {
        double mouseXOffset;
        double mouseYOffset;
        boolean isDraggable = true;

        DraggableImage(double size, String tileType, double initialX, double initialY) {
            super();
            this.setImage(new Image(this.getClass().getResource(URI_BASE + tileType + ".jpg").toString()));
            this.setFitWidth(size);
            this.setFitHeight(size);
            this.setLayoutX(initialX);
            this.setLayoutY(initialY);
            // press the
            this.setOnMousePressed(event -> {
                if (isDraggable) {
                    mouseXOffset = this.getLayoutX() - event.getSceneX();
                    mouseYOffset = this.getLayoutY() - event.getSceneY();
                }
            });

            this.setOnMouseDragged(event -> {
                if (isDraggable) {
                    this.setLayoutX(event.getSceneX() + mouseXOffset);
                    this.setLayoutY(event.getSceneY() + mouseYOffset);
                    Rectangle closest = findClosestRectangle(this.getLayoutX(), this.getLayoutY());

                    if (highlighted != null) {
                        highlighted.setFill(Color.LIGHTGRAY);
                    }

                    int row = (int) closest.getLayoutY() / SQUARE_SIZE;
                    int column = (int) closest.getLayoutX() / SQUARE_SIZE;
                    String possiblePlacement = placementSequence + tileType + row + column;
                    double closestDistance = distanceToRectangle(closest, this.getLayoutX(), this.getLayoutY());

                    if (closestDistance > 2 * SQUARE_SIZE) {

                        //System.out.println(closestDistance);
                    } else if (Metro.isPlacementSequenceValid(possiblePlacement)) {
                        closest.setFill(Color.GREEN);
                    } else {
                        closest.setFill(Color.RED);
                    }

                    highlighted = closest;
                }
            });

            this.setOnMouseReleased(event -> {
                if (emptyBoardSquares.size() > 0 && isDraggable) {
                    Rectangle closest = findClosestRectangle(this.getLayoutX(), this.getLayoutY());
                    int row = (int) closest.getLayoutY() / SQUARE_SIZE;
                    int column = (int) closest.getLayoutX() / SQUARE_SIZE;
                    String possiblePlacement = placementSequence + tileType + row + column;
                    double closestDistance = distanceToRectangle(closest, this.getLayoutX(), this.getLayoutY());

                    if (closestDistance > 2 * SQUARE_SIZE && !playerArrayList.get(indexOfPlayersTurn).isHolding()) {
                        if (distanceToRectangle(tileHandling.getChildren().get(4), this.getLayoutX(), this.getLayoutY()) < 1.5 * SQUARE_SIZE) {
                            this.setLayoutX(tileHandling.getLayoutX() + tileHandling.getChildren().get(4).getLayoutX());
                            this.setLayoutY(tileHandling.getLayoutY() + tileHandling.getChildren().get(4).getLayoutY());
                            playerArrayList.get(indexOfPlayersTurn).updateTileHolding(tileType);
                            this.isDraggable = false;
                            tileInHandImages.put(indexOfPlayersTurn, this);
                            isCardShowing = false;
                            deck.remove(0);
                            autoDraw();

                        } else {
                            this.setLayoutX(initialX);
                            this.setLayoutY(initialY);
                        }

                    } else if (closestDistance > 2 * SQUARE_SIZE && playerArrayList.get(indexOfPlayersTurn).isHolding()) {
                        if (this == tileInHandImages.get(indexOfPlayersTurn)) {
                            this.setLayoutX(tileHandling.getLayoutX() + tileHandling.getChildren().get(4).getLayoutX());
                            this.setLayoutY(tileHandling.getLayoutY() + tileHandling.getChildren().get(4).getLayoutY());
                        } else {
                            this.setLayoutX(initialX);
                            this.setLayoutY(initialY);
                        }

                    } else if (Metro.isPlacementSequenceValid(possiblePlacement)) {
                        this.setLayoutX(closest.getLayoutX() + emptyBoard.getLayoutX());
                        this.setLayoutY(closest.getLayoutY() + emptyBoard.getLayoutY());
                        placementSequence.append(tileType).append(row).append(column);
                        this.isDraggable = false;
                        isCardShowing = false;
                        emptyBoardSquares.remove(closest);


                        updateScoreBoard(placementSequence.toString());
                        if (this == tileInHandImages.get(indexOfPlayersTurn)) {
                            playerArrayList.get(indexOfPlayersTurn).updateTileHolding("");
                            tileInHandImages.put(indexOfPlayersTurn, null);
                        } else {
                            deck.remove(0);
                        }

                        if (indexOfPlayersTurn == playerArrayList.size() - 1) {
                            indexOfPlayersTurn = 0;
                        } else {
                            indexOfPlayersTurn++;
                        }
                        updateTileHandling();

                    } else if (isCardShowing) {
                        //snap back to the deck position
                        this.setLayoutX(initialX);
                        this.setLayoutY(initialY);
                    } else if (this == tileInHandImages.get(indexOfPlayersTurn)) {
                        //snap back to the tile in hand position
                        this.setLayoutX(tileHandling.getLayoutX() + tileHandling.getChildren().get(4).getLayoutX());
                        this.setLayoutY(tileHandling.getLayoutY() + tileHandling.getChildren().get(4).getLayoutY());
                    }
                    highlighted.setFill(Color.LIGHTGRAY);
                    highlighted = null;
                }
            });
        }


        public void moveImageToBoardWithoutTransition(String piecePlacement) {
            int row = piecePlacement.charAt(4) - '0';
            int column = piecePlacement.charAt(5) - '0';
            Rectangle r = findClosestRectangle(emptyBoard.getLayoutX() + SQUARE_SIZE * column, emptyBoard.getLayoutY() + SQUARE_SIZE * row);
            this.setLayoutX(emptyBoard.getLayoutX() + r.getLayoutX());
            this.setLayoutY(emptyBoard.getLayoutX() + r.getLayoutY());
            //remove the rectangle from the empty board squares array list
            emptyBoardSquares.remove(r);
            //update the placement sequence
            placementSequence.append(piecePlacement);
        }

        public void moveImageToBoardWithTransition(String piecePlacement) {
            int row = piecePlacement.charAt(4) - '0';
            int column = piecePlacement.charAt(5) - '0';
            Rectangle r = findClosestRectangle(emptyBoard.getLayoutX() + SQUARE_SIZE * column, emptyBoard.getLayoutY() + SQUARE_SIZE * row);
            double startX = SQUARE_SIZE / 2;
            double startY = SQUARE_SIZE / 2;
            double endX = (emptyBoard.getLayoutX() + r.getLayoutX()) - this.getLayoutX() + SQUARE_SIZE / 2;
            double endY = (emptyBoard.getLayoutY() + r.getLayoutY()) - this.getLayoutY() + SQUARE_SIZE / 2;
            //creating path
            PathElement[] pathElement = {
                    new MoveTo(startX, startY),
                    new LineTo(endX, endY)
            };

            Path line = new Path();
            line.getElements().addAll(pathElement);
            //path transition animation
            PathTransition travel = new PathTransition();
            travel.setNode(this);
            travel.setPath(line);
            travel.setDuration(new Duration(1500));
            travel.setCycleCount(1);
            //puase transition
            PauseTransition delay = new PauseTransition(Duration.millis(1000));
            Timeline playtime = new Timeline(
                    new KeyFrame(Duration.seconds(0), event -> delay.play()),
                    new KeyFrame(Duration.seconds(1), event -> travel.play())
            );
            playtime.play();

            //remove the rectangle from the empty board squares array list
            emptyBoardSquares.remove(r);
            //update the placement sequence
            placementSequence.append(piecePlacement);
        }
    }


    /**
     * @param r the rectangle
     * @param x x coordinate of the point
     * @param y y coordinate of the point
     * @return distance between layouts of a rectangle and another point
     */

    private double distanceToRectangle(Node r, double x, double y) {
        double dx = x - r.getLayoutX() - r.getParent().getLayoutX();
        double dy = y - r.getLayoutY() - r.getParent().getLayoutY();
        return Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
    }

    /**
     * finding closest rectangle (in the array list) to a given location
     *
     * @param x x coordinates of the location
     * @param y y coordinates of the location
     * @return the closest rectangle
     */
    private Rectangle findClosestRectangle(double x, double y) {
        Rectangle closest = null;
        double closestDistance = Double.MAX_VALUE;
        for (Rectangle r : emptyBoardSquares) {
            double distance = distanceToRectangle(r, x, y);
            if (distance < closestDistance) {
                closestDistance = distance;
                closest = r;
            }
        }
        return closest;
    }

    /**
     * Indicate which players' turn
     * Restart button
     * maybe difficulty in future (if playing with computer)
     */

    private void makeControls() {
        Label l1 = new Label("Difficulty");
        Slider slider = new Slider();
        slider.setMin(1);
        slider.setMax(5);
        slider.setValue(3);
        slider.setMajorTickUnit(1);
        slider.setMinorTickCount(0);
        slider.setSnapToTicks(true);
        slider.setShowTickMarks(true);
        slider.setShowTickLabels(true);
        Button st = new Button("START");

        st.setOnAction(event -> {
            //pressed for first time
            if (stations.getChildren().size() == 0) {
                showEmptyBoard();
                getPlayerInfo();
                initiateScoreBoard();
                //洗牌
                deck = Metro.getFreshDeck();
                initializeTileHandling();
            } else if (emptyBoardSquares.isEmpty()) {
                //pressed after completing the game
                clearAll();
                showEmptyBoard();
                getPlayerInfo();
                initiateScoreBoard();
                deck = Metro.getFreshDeck();
                initializeTileHandling();
            } else {
                //when pressed in middle of the game
                //show them an alert or option to confirm if they really want to restart
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Exit and Restart");
                alert.setContentText("Do you really want to exit?");
                ButtonType okButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
                ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);
                alert.getButtonTypes().setAll(okButton, noButton);
                Optional<ButtonType> type = alert.showAndWait();

                if (type.isPresent()) {
                    System.out.println("result is recorded " + type.get());
                    if (type.get().getText() == "Yes") {
                        //if they confirm, then restart
                        System.out.println("reaching restart");
                        clearAll();
                        showEmptyBoard();
                        getPlayerInfo();
                        initiateScoreBoard();
                        deck = Metro.getFreshDeck();
                        initializeTileHandling();
                    }
                }
            }
        });

        HBox bottomControls = new HBox();
        bottomControls.getChildren().addAll(l1, slider, st);
        bottomControls.setLayoutX(100);
        bottomControls.setLayoutY(VIEWER_HEIGHT - 50);
        bottomControls.setSpacing(30);
        controls.getChildren().add(bottomControls);
    }

    /**
     * function to get empty board added on the root
     */
    private void showEmptyBoard() {
        Viewer.showStations(stations);

        root.getChildren().add(stations);
        //making light gray rectangles for the empty positions on the board
        for (int i = 0; i < DIM; i++) {

            for (int j = 0; j < DIM; j++) {
                if ((i == 3 || i == 4) && (j == 3 || j == 4)) {
                    continue;
                }
                Rectangle r = new Rectangle();
                r.setWidth(SQUARE_SIZE);
                r.setHeight(SQUARE_SIZE);
                r.setArcWidth(3);
                r.setArcHeight(3);
                r.setFill(Color.LIGHTGRAY);
                emptyBoardSquares.add(r);
                r.setLayoutX(i * SQUARE_SIZE);
                r.setLayoutY(j * SQUARE_SIZE);
                emptyBoard.getChildren().add(r);
            }
        }
        //emptyBoard.setGridLinesVisible(true);
        emptyBoard.setLayoutX(SQUARE_SIZE);
        emptyBoard.setLayoutY(SQUARE_SIZE);
        root.getChildren().add(emptyBoard);
    }

    /**
     * function to update the player name in tile handling VBox
     */

    private void updateTileHandling() {
        if (!emptyBoardSquares.isEmpty() && !deck.isEmpty()) {
            //removing the tile in hind of the previous player
            int previousPlayer = indexOfPlayersTurn - 1;
            if (indexOfPlayersTurn == 0) {
                previousPlayer = numberOfPlayers - 1;
            }
            if (playerArrayList.get(previousPlayer).isHolding()) {
                root.getChildren().remove(tileInHandImages.get(previousPlayer));
            }

            //if human player has to play
            if (indexOfPlayersTurn < numberOfHumanPlayers) {

                if (indexOfPlayersTurn == 0 && numberOfComputerPlayers != 0) {
                    //wait for some time, for last move transition to finish, do the same thing as done for others
                    Task<Void> sleeper = new Task<>() {
                        @Override
                        protected Void call() {
                            try {
                                Thread.sleep(2500);
                            } catch (InterruptedException ignored) {
                            }
                            return null;
                        }
                    };
                    sleeper.setOnSucceeded(event -> {
                        //update name info
                        nameOfPlayersTurn.setText(playerArrayList.get(indexOfPlayersTurn).getName() + "'s turn");
                        nameOfPlayersTurn.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
                        nameOfPlayersTurn.setLayoutX(800 - (nameOfPlayersTurn.getText().length() - 7) * 2);
                        //if holding a tile, show it on tile in hand position
                        if (playerArrayList.get(indexOfPlayersTurn).isHolding()) {
                            root.getChildren().add(tileInHandImages.get(indexOfPlayersTurn));
                            tileInHandImages.get(indexOfPlayersTurn).isDraggable = true;
                        } else {
                            autoDraw();
                        }
                    });
                    new Thread(sleeper).start();

                } else {
                    nameOfPlayersTurn.setText(playerArrayList.get(indexOfPlayersTurn).getName() + "'s turn");
                    nameOfPlayersTurn.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
                    nameOfPlayersTurn.setLayoutX(800 - (nameOfPlayersTurn.getText().length() - 7) * 2);

                    if (playerArrayList.get(indexOfPlayersTurn).isHolding()) {
                        root.getChildren().add(tileInHandImages.get(indexOfPlayersTurn));
                        tileInHandImages.get(indexOfPlayersTurn).isDraggable = true;
                    } else {
                        autoDraw();
                    }
                }
            }
            //if this player is a computer, make a move
            else {
                if (indexOfPlayersTurn == numberOfHumanPlayers) {
                    //update name info
                    nameOfPlayersTurn.setText(playerArrayList.get(indexOfPlayersTurn).getName() + "'s turn");
                    nameOfPlayersTurn.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
                    nameOfPlayersTurn.setLayoutX(800 - (nameOfPlayersTurn.getText().length() - 7) * 2);
                    makeComputerMove();
                } else {
                    Task<Void> sleeper = new Task<>() {
                        @Override
                        protected Void call() {
                            try {
                                Thread.sleep(2500);
                            } catch (InterruptedException ignored) {
                            }
                            return null;
                        }
                    };
                    sleeper.setOnSucceeded(event -> {
                        //update name info
                        nameOfPlayersTurn.setText(playerArrayList.get(indexOfPlayersTurn).getName() + "'s turn");
                        nameOfPlayersTurn.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
                        nameOfPlayersTurn.setLayoutX(800 - (nameOfPlayersTurn.getText().length() - 7) * 2);

                        makeComputerMove();
                    });
                    new Thread(sleeper).start();
                }
            }
        }
        //if someone has a tile in hand and deck is finished, we have to play is somehow
        //as the game runs now, this will only be human players turn
        else if (deck.isEmpty() && !emptyBoardSquares.isEmpty()) {
            if (playerArrayList.get(indexOfPlayersTurn).isHolding()) {
                //update name info
                nameOfPlayersTurn.setText(playerArrayList.get(indexOfPlayersTurn).getName() + "'s turn");
                nameOfPlayersTurn.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
                nameOfPlayersTurn.setLayoutX(800 - (nameOfPlayersTurn.getText().length() - 7) * 2);
                root.getChildren().add(tileInHandImages.get(indexOfPlayersTurn));
                tileInHandImages.get(indexOfPlayersTurn).isDraggable = true;
            }
            else {
                indexOfPlayersTurn++;
                if(indexOfPlayersTurn==numberOfPlayers)
                    indexOfPlayersTurn=0;
                updateTileHandling();
            }
        }
        //the game is finished, all tiles have been placed
        //show who won and give info, how to play again
        else {
            Alert results = new Alert(Alert.AlertType.INFORMATION);
            String titleTxt = "Game Over";
            results.setTitle(titleTxt);
            results.setHeaderText("Press OK -> START to play again. Scores are: ");
            results.setResizable(true);
            GridPane grid = new GridPane();
            grid.setHgap(70);
            for (int i = 0; i < numberOfPlayers; i++) {
                grid.add(new Label(playerArrayList.get(i).getName()), 1, i + 1);
                grid.add(new Label(String.valueOf(playerArrayList.get(i).getScore())), 2, i + 1);
            }
            grid.setLayoutX((results.getWidth() - grid.getWidth()) / 2);
            results.getDialogPane().setContent(grid);

            ButtonType buttonTypeExit = new ButtonType("EXIT", ButtonBar.ButtonData.CANCEL_CLOSE);
            results.getDialogPane().getButtonTypes().add(buttonTypeExit);

            //wait for a bit for last computer transition move to finish
            if (numberOfComputerPlayers != 0) {
                Task<Void> sleeper = new Task<>() {
                    @Override
                    protected Void call() {
                        try {
                            Thread.sleep(2500);
                        } catch (InterruptedException ignored) {
                        }
                        return null;
                    }
                };
                sleeper.setOnSucceeded(event -> results.showAndWait().ifPresent(type -> {
                    if (type.getText().equals("EXIT")) {
                        System.exit(0);
                    }
                }));
                new Thread(sleeper).start();
            } else {
                results.showAndWait().ifPresent(type -> {
                    if (type.getText().equals("EXIT")) {
                        System.exit(0);
                    }
                });
            }
        }
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
        } else {
            System.exit(0);
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
            if (numberOfHumanPlayers + result.get() > 6 || numberOfHumanPlayers + result.get() < 2) {
                playerCountError();
            } else {
                numberOfComputerPlayers = result.get();
                numberOfPlayers = numberOfHumanPlayers + numberOfComputerPlayers;
                getPlayersName();
            }
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
        String header = "Total number of players must be between 2 and 6.\n" + " Please reselect number of computer opponents or human players.";
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
//获取 玩家名字
    private void getPlayersName() {
        Dialog<String> dialog = new Dialog<>();
        String titleTxt = "Names";
        dialog.setTitle(titleTxt);
        dialog.setHeaderText("Enter name of the players : ");
        dialog.setResizable(true);
        GridPane grid = new GridPane();
        //Lables array to show which player info is in the given text field
        Label[] labels = new Label[numberOfHumanPlayers];
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
                    //default player name
                    name = "P" + (i + 1);
                }
                playerArrayList.add(new Player(name));
            }

            //add computer players to the list
            for (int i = 0; i < numberOfComputerPlayers; i++) {
                playerArrayList.add(new Player("C" + (i + 1)));
            }
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
        Text heading = new Text("SCORE BOARD");
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
    }

    /**
     * a function to initialize tile panel
     */
    private void initializeTileHandling() {
        Rectangle emptyFaceUpDeck = new Rectangle();
        Rectangle emptyTileInHand = new Rectangle();

        nameOfPlayersTurn.setText(playerArrayList.get(indexOfPlayersTurn).getName() + "'s turn");
        nameOfPlayersTurn.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));

        Text deckHeading = new Text("Deck");
        deckHeading.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));

        Text tileInHandHeading = new Text("Tile In Hand");
        tileInHandHeading.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));

        emptyFaceUpDeck.setWidth(SQUARE_SIZE);
        emptyFaceUpDeck.setHeight(SQUARE_SIZE);
        emptyFaceUpDeck.setArcWidth(3);
        emptyFaceUpDeck.setArcHeight(3);
        emptyFaceUpDeck.setFill(Color.LIGHTGRAY);
        Button draw = new Button("Draw");
        //autoDraw();
        draw.setOnAction(actionEvent -> {
            //when draw is pressed by player to draw a card, thre is no card showing and deck is not empty
            if (deck.size() != 0 && !isCardShowing && (indexOfPlayersTurn < numberOfHumanPlayers)) {
                String tileType = deck.get(0);
                DraggableImage draggableImage =
                        new DraggableImage(SQUARE_SIZE, tileType, tileHandling.getLayoutX() + tileHandling.getChildren().get(1).getLayoutX(), tileHandling.getLayoutY() + tileHandling.getChildren().get(1).getLayoutY());
                root.getChildren().add(draggableImage);
                isCardShowing = true;
                //if you have tile in hand and draw, the draggability of tile in hand is gone
                if (playerArrayList.get(indexOfPlayersTurn).isHolding()) {
                    tileInHandImages.get(indexOfPlayersTurn).isDraggable = false;
                }
            }
            //draw is pressed and deck is empty, it means someone has a tile in hand and have not place it
            else if (deck.isEmpty() && !emptyBoardSquares.isEmpty()) {
                //game over
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Deck Empty");
                alert.setHeaderText("No more Tiles to Draw!");
                alert.setContentText("Someone has a tile in hand. Ask them to place it!!!");
                alert.showAndWait();
            }
        });

        emptyTileInHand.setWidth(SQUARE_SIZE);
        emptyTileInHand.setHeight(SQUARE_SIZE);
        emptyTileInHand.setArcWidth(3);
        emptyTileInHand.setArcHeight(3);
        emptyTileInHand.setFill(Color.LIGHTGRAY);

        nameOfPlayersTurn.setLayoutX(800 - (nameOfPlayersTurn.getText().length() - 7) * 2);
        nameOfPlayersTurn.setLayoutY(370);
        root.getChildren().add(nameOfPlayersTurn);

        tileHandling.getChildren().addAll(deckHeading, emptyFaceUpDeck, draw, tileInHandHeading, emptyTileInHand);
        tileHandling.setLayoutY(400);
        tileHandling.setLayoutX(800);
        tileHandling.setSpacing(15);
        tileHandling.setAlignment(Pos.CENTER);
        root.getChildren().add(tileHandling);
    }

    /**
     * function for auto drawing from deck if no tile in hand
     * ond when tile is place in hand
     */

    private void autoDraw() {
        String tileType = deck.get(0);
        DraggableImage draggableImage = new DraggableImage(SQUARE_SIZE, tileType, tileHandling.getLayoutX() + tileHandling.getChildren().get(1).getLayoutX(), tileHandling.getLayoutY() + tileHandling.getChildren().get(1).getLayoutY());
        root.getChildren().add(draggableImage);
        isCardShowing = true;
    }

    /**
     * function for making/showing a computer move
     * draggable property of images is  set to off so that humans may not cause any interference
     */

    private void makeComputerMove() {

        //if computer player has a in hand card
        if (playerArrayList.get(indexOfPlayersTurn).isHolding()) {
            //adding the tile at tile in hand location
            root.getChildren().add(tileInHandImages.get(indexOfPlayersTurn));
            tileInHandImages.get(indexOfPlayersTurn).isDraggable = false;
            //if we draw and play this card
            if (Metro.shouldDraw(placementSequence.toString(), playerArrayList.get(indexOfPlayersTurn).getTileInHand())) {
                //draw
                String tileType = deck.get(0);
                DraggableImage draggableImage = new DraggableImage(SQUARE_SIZE, tileType, tileHandling.getLayoutX() + tileHandling.getChildren().get(1).getLayoutX(), tileHandling.getLayoutY() + tileHandling.getChildren().get(1).getLayoutY());
                draggableImage.isDraggable = false;

                root.getChildren().add(draggableImage);
                //play
                String piecePlacement = Metro.generateMove(placementSequence.toString(), tileType, numberOfPlayers);
                draggableImage.moveImageToBoardWithoutTransition(piecePlacement);
                //update the deck and empty board
                deck.remove(0);
            }
            //if we don't draw and play the in hand card
            else {
                String piecePlacement = Metro.generateMove(placementSequence.toString(), playerArrayList.get(indexOfPlayersTurn).getTileInHand(), numberOfPlayers);
                tileInHandImages.get(indexOfPlayersTurn).moveImageToBoardWithoutTransition(piecePlacement);
                //update players info and the hash map
                playerArrayList.get(indexOfPlayersTurn).updateTileHolding("");

                tileInHandImages.put(indexOfPlayersTurn, null);

            }
        }
        //if computer player doesn't have a in hand card
        else if (deck.size() != 0) {
            //have to draw
            String tileType = deck.get(0);
            DraggableImage draggableImage = new DraggableImage(SQUARE_SIZE, tileType, tileHandling.getLayoutX() + tileHandling.getChildren().get(1).getLayoutX(), tileHandling.getLayoutY() + tileHandling.getChildren().get(1).getLayoutY());
            draggableImage.isDraggable = false;         //human player should not be able to move this
            root.getChildren().add(draggableImage);
            //if we keep this in hand and then draw a new one and play that one
            if (Metro.shouldKeepInHand(placementSequence.toString(), tileType)) {
                //update deck, player info and hash map
                deck.remove(0);

                playerArrayList.get(indexOfPlayersTurn).updateTileHolding(tileType);
                tileInHandImages.put(indexOfPlayersTurn, draggableImage);
                //position this one in the tile in hand place
                draggableImage.setLayoutX(tileHandling.getLayoutX() + tileHandling.getChildren().get(4).getLayoutX());
                draggableImage.setLayoutY(tileHandling.getLayoutY() + tileHandling.getChildren().get(4).getLayoutY());
                //draw another one and play
                String tileType1 = deck.get(0);
                DraggableImage draggableImage1 = new DraggableImage(SQUARE_SIZE, tileType1, tileHandling.getLayoutX() + tileHandling.getChildren().get(1).getLayoutX(), tileHandling.getLayoutY() + tileHandling.getChildren().get(1).getLayoutY());
                draggableImage1.isDraggable = false;
                root.getChildren().add(draggableImage1);
                String piecePlacement = Metro.generateMove(placementSequence.toString(), tileType1, numberOfPlayers);
                draggableImage1.moveImageToBoardWithoutTransition(piecePlacement);
                //update the deck
                deck.remove(0);
            }
            //if we play this card
            else {
                String piecePlacement = Metro.generateMove(placementSequence.toString(), tileType, numberOfPlayers);
                //playing

                draggableImage.moveImageToBoardWithTransition(piecePlacement);
                //update the deck and empty board
                deck.remove(0);
            }

        }
        //update the player turn index
        if (indexOfPlayersTurn == numberOfPlayers - 1) {
            indexOfPlayersTurn = 0;
        } else {
            indexOfPlayersTurn++;
        }
        updateTileHandling();
    }


    /**
     * during restart, clean up the nodes
     */
    //清除 面板所有组件
    private void clearAll() {
        root.getChildren().clear();
        controls.getChildren().clear();
        stations.getChildren().clear();
        scoreBoard.getChildren().clear();
        emptyBoard.getChildren().clear();
        nameAndScore.getChildren().clear();
        tileHandling.getChildren().clear();

        tileInHandImages = new HashMap<>();
        this.isCardShowing = false;
        placementSequence = new StringBuilder();
        playerArrayList = new ArrayList<>();
        deck = new ArrayList<>();
        highlighted = null;
        emptyBoardSquares = new ArrayList<>();
        indexOfPlayersTurn = 0;
        nameOfPlayersTurn = new Text();

        makeControls();
        root.getChildren().add(controls);

    }

    /**
     * function to update the text belonging to nameAndScore grid pane in the scoreBoard group.
     */
    private void updateScoreBoard(String placementSequence) {
        //get updated player list
        Metro.assignScore(placementSequence, playerArrayList);
        //removing the scores nodes first
        for (int i = 0; i < playerArrayList.size(); i++) {
            //since the index gets updated on removal we will remove the same index multiple times
            nameAndScore.getChildren().remove(playerArrayList.size());
        }

        //adding new nodes for scores
        for (int i = 0; i < playerArrayList.size(); i++) {
            //since the index gets updated on removal we will remove the same index multiple times
            Text t = new Text(String.valueOf(playerArrayList.get(i).getScore()));
            t.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 15));
            nameAndScore.add(t, 1, i);
        }
    }

    /**
     * private void updateComputerDifficult(int difficultyValue) {
     * Random random=new Random();
     * //所有 score 对应的  placement sequences
     * TreeMap<Integer, String> allPlacementSequences = Metro.getAllPlacementSequences();
     * if(allPlacementSequences.size()==0){
     * return;
     * }
     * if(difficultyValue>=0&&difficultyValue<=20){
     * String placements = allPlacementSequences.get(random.nextInt(difficultyValue));
     * <p>
     * Metro.sortedOccurrence(placements);
     * }
     * else if(difficultyValue>20&&difficultyValue<=40){
     * String placements = allPlacementSequences.get(random.nextInt(difficultyValue));
     * Metro.sortedOccurrence(placements);
     * }
     * else if(difficultyValue>40&&difficultyValue<60){
     * String placements = allPlacementSequences.get(random.nextInt(difficultyValue));
     * Metro.sortedOccurrence(placements);
     * }
     * else if(difficultyValue>60&&difficultyValue<80){
     * String placements = allPlacementSequences.get(random.nextInt(difficultyValue));
     * Metro.sortedOccurrence(placements);
     * }
     * else {
     * String placements = allPlacementSequences.get(random.nextInt(difficultyValue));
     * Metro.sortedOccurrence(placements);
     * }
     * }
     **/

    //程序启动
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Metro Game");
        Scene scene = new Scene(root, VIEWER_WIDTH, VIEWER_HEIGHT);
        primaryStage.setScene(scene);
        makeControls();
        root.getChildren().add(controls);
        primaryStage.show();
    }
}