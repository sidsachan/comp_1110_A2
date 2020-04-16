package comp1110.ass2.gui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * A very simple viewer for piece placements in the Metro game.
 * <p>
 * NOTE: This class is separate from your main game class.  This
 * class does not play a game, it just illustrates various piece
 * placements.
 */
public class Viewer extends Application {
    /* board layout */
    private static final int SQUARE_SIZE = 70;
    private static final int VIEWER_WIDTH = 1024;
    private static final int VIEWER_HEIGHT = 768;
    private static final int dim = 8;           //board is 8 by 8

    private static final String URI_BASE = "assets/";

    private final Group root = new Group();
    private final Group controls = new Group();
    private TextField textField;

    /**
     * Draw a placement in the window, removing any previously drawn one
     *
     * @param placement A valid placement string
     */
    void makePlacement(String placement) {
        // FIXME Task 4: implement the simple placement viewer
        if(placement.length()%6!=0)
            return;

        Group tiles = new Group();

        root.getChildren().add(tiles);
        //for clearing previously drawn tiles
        if(root.getChildren().indexOf(tiles)==3)
            root.getChildren().remove(2);

        int number = placement.length()/6;

        for (int i = 0; i<number; ++i){
            //the placement piece -> tile type and its coordinate
            String s = placement.substring(6*i,6*(i+1));
            //tile type is the first 4 characters
            String s1 = s.substring(0,4);
            ImageView tile = new ImageView();
            tile.setImage(new Image(this.getClass().getResource(URI_BASE + s1 + ".jpg").toString()));
            tile.setFitHeight(SQUARE_SIZE);
            tile.setFitWidth(SQUARE_SIZE);
            //x and y coordinates of the placement piece
            int x = s.charAt(4) - 48;
            int y = s.charAt(5) - 48;
            tile.setLayoutX((x+1)*SQUARE_SIZE);
            tile.setLayoutY((y+1)*SQUARE_SIZE);
            tiles.getChildren().add(tile);
        }
    }

    /**
     * Draw the stations
     */
    void showStations(){
        Group stations = new Group();
        // top represents all the stations on the top(1-8)
        Group top = new Group();
        for (int i=1; i<dim+1;++i){
            ImageView im = new ImageView();
            im.setImage(new Image(this.getClass().getResource(URI_BASE + "station" + i +".jpg").toString()));
            im.setRotate(180);
            im.setFitHeight(SQUARE_SIZE);
            im.setFitWidth(SQUARE_SIZE);
            im.setLayoutY(0);
            im.setLayoutX((dim - i)*SQUARE_SIZE);
            top.getChildren().add(im);
        }
        top.setLayoutX(SQUARE_SIZE);
        top.setLayoutY(0);
        //left represents all stations on left side (9-16)
        Group left = new Group();
        for (int i=dim+1;i<=dim*2;++i){
            ImageView im = new ImageView();
            im.setImage(new Image(this.getClass().getResource(URI_BASE + "station" + i +".jpg").toString()));
            im.setRotate(90);
            im.setFitHeight(SQUARE_SIZE);
            im.setFitWidth(SQUARE_SIZE);
            im.setLayoutX(0);
            im.setLayoutY((i-(dim+1))*SQUARE_SIZE);
            left.getChildren().add(im);
        }
        left.setLayoutX(0);
        left.setLayoutY(SQUARE_SIZE);
        //bottom represents all stations on bottom(17-24)
        Group bottom = new Group();
        for (int i=dim*2+1;i<=dim*3;++i){
            ImageView im = new ImageView();
            im.setImage(new Image(this.getClass().getResource(URI_BASE + "station" + i +".jpg").toString()));
            im.setFitHeight(SQUARE_SIZE);
            im.setFitWidth(SQUARE_SIZE);
            im.setLayoutY(0);
            im.setLayoutX((i - (dim*2+1))*SQUARE_SIZE);
            bottom.getChildren().add(im);
        }
        bottom.setLayoutX(SQUARE_SIZE);
        bottom.setLayoutY(SQUARE_SIZE+dim*SQUARE_SIZE);
        //right is for all the stations on the right (17-24)
        Group right = new Group();
        for (int i=dim*3+1;i<=dim*4;++i){
            ImageView im = new ImageView();
            im.setImage(new Image(this.getClass().getResource(URI_BASE + "station" + i +".jpg").toString()));
            im.setRotate(270);
            im.setFitHeight(SQUARE_SIZE);
            im.setFitWidth(SQUARE_SIZE);
            im.setLayoutX(0);
            im.setLayoutY((dim*4 - i)*SQUARE_SIZE);
            right.getChildren().add(im);
        }
        right.setLayoutX(SQUARE_SIZE + dim*SQUARE_SIZE);
        right.setLayoutY(SQUARE_SIZE);
        //corner represents the corner of the board, may be add something in future
        Group corner = new Group();
        //center represents center stations, first layout is set amongst them and then whole centre block is placed
        Group center = new Group();
        Image im = new Image(this.getClass().getResource(URI_BASE+"centre_station.jpg").toString());
        ImageView c1 = new ImageView(im);
        c1.setFitWidth(SQUARE_SIZE);
        c1.setFitHeight(SQUARE_SIZE);
        c1.setLayoutX(SQUARE_SIZE);
        c1.setLayoutY(0);
        center.getChildren().add(c1);

        ImageView c2 = new ImageView(im);
        c2.setFitWidth(SQUARE_SIZE);
        c2.setFitHeight(SQUARE_SIZE);
        c2.setRotate(90);
        c2.setLayoutX(SQUARE_SIZE);
        c2.setLayoutY(SQUARE_SIZE);
        center.getChildren().add(c2);

        ImageView c3 = new ImageView(im);
        c3.setFitWidth(SQUARE_SIZE);
        c3.setFitHeight(SQUARE_SIZE);
        c3.setRotate(180);
        c3.setLayoutX(0);
        c3.setLayoutY(SQUARE_SIZE);
        center.getChildren().add(c3);

        ImageView c4 = new ImageView(im);
        c4.setFitWidth(SQUARE_SIZE);
        c4.setFitHeight(SQUARE_SIZE);
        c4.setRotate(270);
        c4.setLayoutX(0);
        c4.setLayoutY(0);
        center.getChildren().add(c4);

        center.setLayoutX(dim*SQUARE_SIZE/2);
        center.setLayoutY(dim*SQUARE_SIZE/2);

        //adding all the sections of the stations
        stations.getChildren().add(top);
        stations.getChildren().add(left);
        stations.getChildren().add(bottom);
        stations.getChildren().add(right);
        stations.getChildren().add(center);
        stations.getChildren().add(corner);

        root.getChildren().add(stations);
    }

    /**
     * Create a basic text field for input and a refresh button.
     */
    private void makeControls() {
        Label label1 = new Label("Placement:");
        textField = new TextField();
        textField.setPrefWidth(300);
        Button button = new Button("Refresh");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                makePlacement(textField.getText());
                textField.clear();
            }
        });
        HBox hb = new HBox();
        hb.getChildren().addAll(label1, textField, button);
        hb.setSpacing(10);
        hb.setLayoutX(130);
        hb.setLayoutY(VIEWER_HEIGHT - 50);
        controls.getChildren().add(hb);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Metro Game Viewer");
        Scene scene = new Scene(root, VIEWER_WIDTH, VIEWER_HEIGHT);
        root.getChildren().add(controls);
        showStations();
        makeControls();

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
