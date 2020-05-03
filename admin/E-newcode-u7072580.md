# New Code for Deliverable D2E

## < u7072580 > < Siddharth Sachan >

For Deliverable D2E, I contributed the following new statements of original code:
SortedOccurrenceTest class, randomFullBoardString() function, lines 94-115.
-       public void randomFullBoardString(){
                Tile[] tilesExpected = Tile.getStartingTiles();
                //forming random full board string
                StringBuilder boardString = new StringBuilder();
                ArrayList<Tile> tileArrayList = new ArrayList<>();
                for (Tile tile : tilesExpected) {
                    for (int i = 0; i < tile.getNumber();++i) {
                        Tile t = new Tile(tile.getType(),1);
                        tileArrayList.add(t);
                    }
                }
                Collections.shuffle(tileArrayList);
                for (Tile tile:tileArrayList){
                    boardString.append(tile.getType()).append("00");
                }
                Tile[] functionOutput = Metro.sortedOccurrence(String.valueOf(boardString));
                //Comparison of tile type and count
                for (int i = 0; i<tilesExpected.length; i++) {
                    assertTrue("Expected: " + tilesExpected[i].getType() +"\nBut got: "+ functionOutput[i].getType(), tilesExpected[i].getType().equals(functionOutput[i].getType()));
                    assertTrue("Expected: " + tilesExpected[i].getNumber() +"\nBut got: "+ functionOutput[i].getNumber(), tilesExpected[i].getType().equals(functionOutput[i].getType()));
                    }
            }
Game class, initiateScoreBoard() function, lines 180-233(a lot of it is formatting)

-       private void initiateScoreBoard() {
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

Game Class, getPlayerName() function, lines 130-173
-           private void getPlayersName() {
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