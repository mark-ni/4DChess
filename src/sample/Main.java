package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
//import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;
//import javafx.scene.media.Media;
//import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.Screen;

public class Main extends Application {

    private Label piece1;
    private VBox box1;

    public static int width = (int) Screen.getPrimary().getBounds().getMaxX();
    public static int height = (int) Screen.getPrimary().getBounds().getMaxY();

    @Override
    public void start(Stage primaryStage) {
        Player p1 = new Player("Player 1");
        Player p2 = new Player("Player 2");

        Game game = new Game(p1, p2);

        StackPane root = new StackPane();
        root.getChildren().add(game);
        primaryStage.setTitle("4D Chess");
        primaryStage.setScene(new Scene(root, width, height));
        primaryStage.setFullScreen(true);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
