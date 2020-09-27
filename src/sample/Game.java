package sample;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;

public class Game extends HBox {

    //Interface-related
    private static VBox leftPane;

    private static HBox leftPaneSection1;
    private static VBox p1CapturedPiecesBox;
    private static Label p1CapturedPiecesLabel;
    private static ScrollPane p1CapturedPiecesScroller;
    private static ListView<String> p1CapturedPieces;

    private static HBox leftPaneSection2;
    private static VBox p2CapturedPiecesBox;
    private static Label p2CapturedPiecesLabel;
    private static ScrollPane p2CapturedPiecesScroller;
    private static ListView<String> p2CapturedPieces;

    private static HBox leftPaneSection3;
    private static Label capturedPiecesUpdates;


    private static VBox rightPane;

    private static ImageView banner;

    private static HBox rightPaneSection1;
    private static Label turnCounterBox;

    private static HBox rightPaneSection2;
    private static VBox moveTrackerBox;
    private static Label moveTrackerLabel;
    private static ScrollPane moveTrackerScroller;
    private static ListView<String> moveTracker;

    private static VBox rightPaneSection3;
    private static Button resetButton;
    private static Button mainMenuButton;

    //Game-logic related
    public static Board board;
    public static int[] currSquareCoors;
    public static ArrayList<Integer> highlightedSquares;
    public static int whoseTurn;
    public static int turn;
    private static Player[] players;

    public Game(Player p1, Player p2) {
        players = new Player[3];
        players[1] = p1;
        players[2] = p2;
        highlightedSquares = new ArrayList<>();
        board = new Board();

        setUpInterface();

        reset(p1, p2);
    }

    public void reset(Player p1, Player p2) {
        //Logic
        highlightedSquares.clear();
        whoseTurn = 1;
        turn = 1;
        board.reset();
        board.setUpPieces(players);

        //Interface
        p1CapturedPieces.getItems().clear();
        p2CapturedPieces.getItems().clear();
        capturedPiecesUpdates.setText(" ");
        turnCounterBox.setText("Turn 1: It is " + players[1].getName() + "'s turn.");
        moveTracker.getItems().clear();
    }

    public static void movePiece(int[] position) {
        //clear the old square, fill the new square, unhighlight all highlighted squares
        boolean isGameOver = false;
        if (!board.getSquare(position).isEmpty()) {
            Piece lostPiece = board.getSquare(position).getPiece();
            if (lostPiece.getType().equals("K")) {
                isGameOver = true;
            } else {
                capturedPiecesUpdates.setText(players[3 - whoseTurn].getName() + " lost: " + lostPiece.getFullName());
                if (lostPiece.getPlayer().num == 1) {
                    p2CapturedPieces.getItems().add(lostPiece.getFullName());
                } else {
                    p1CapturedPieces.getItems().add(lostPiece.getFullName());
                }
            }
        } else {
            capturedPiecesUpdates.setText(" ");
        }

        moveTracker.getItems().add("Turn " + turn + " P" + whoseTurn + ": " +
                board.getSquare(currSquareCoors).getPiece().getType() + " (" + currSquareCoors[0] + "," +
                currSquareCoors[1] + "," + currSquareCoors[2] + "," + currSquareCoors[3] + ") -> (" + position[0] +
                "," + position[1] + "," + position[2] + "," + position[3] + ")");

        if (board.getSquare(currSquareCoors).getPiece().type.equals("P") && position[0]%7 == 0 && position[2]%7 == 0) {
            board.addPiece(new Queen(players[whoseTurn]), position);
            moveTracker.getItems().add("P" + whoseTurn + ": P -> Q");
        } else {
            board.getSquare(position).setPiece(board.getSquare(currSquareCoors).getPiece());
            board.getSquare(position).getPiece().setPosition(position);
        }
        board.getSquare(currSquareCoors).setPiece(null);
        clearHighlights();

        if (isGameOver) {
            gameOver();
            return;
        }

        whoseTurn = 3 - whoseTurn;
        if (whoseTurn == 1) {
            turn += 1;
        }
        turnCounterBox.setText("Turn " + turn + ": It is " + players[whoseTurn].getName() + "'s turn.");
    }

    public static void clearHighlights() {
        for (int x : highlightedSquares) {
            board.squares[x].setBaseState();
        }
        highlightedSquares.clear();
    }

    public void setUpInterface() {
        int paneWidth = (Main.width - Main.height)/2;
        System.out.println(paneWidth);

        leftPane = new VBox();

        leftPaneSection1 = new HBox();
        p1CapturedPiecesBox = new VBox();
        p1CapturedPiecesLabel = new Label(players[1].getName() + "'s Captured Pieces");
        p1CapturedPiecesLabel.setPrefSize(paneWidth - 20, Main.height * 0.03);
        p1CapturedPiecesLabel.setTextAlignment(TextAlignment.CENTER);
        p1CapturedPiecesLabel.setFont(Font.font("Futura", 20));
        for (String x:Font.getFamilies()) {
            System.out.println(x);
        }
        p1CapturedPiecesScroller = new ScrollPane();
        p1CapturedPieces = new ListView<>();
        p1CapturedPiecesScroller.setContent(p1CapturedPieces);
        p1CapturedPiecesScroller.setPrefHeight(Main.height * 0.4);
        p1CapturedPiecesBox.getChildren().addAll(p1CapturedPiecesLabel, p1CapturedPiecesScroller);
        p1CapturedPiecesBox.setSpacing(5);
        leftPaneSection1.getChildren().add(p1CapturedPiecesBox);

        leftPaneSection2 = new HBox();
        p2CapturedPiecesBox = new VBox();
        p2CapturedPiecesLabel = new Label(players[2].getName() + "'s Captured Pieces");
        p2CapturedPiecesLabel.setPrefSize(paneWidth - 20, Main.height * 0.03);
        p2CapturedPiecesLabel.setFont(Font.font("Futura", 20));
        p2CapturedPiecesLabel.setTextAlignment(TextAlignment.CENTER);

        p2CapturedPiecesScroller = new ScrollPane();
        p2CapturedPieces = new ListView<>();
        p2CapturedPiecesScroller.setContent(p2CapturedPieces);
        p2CapturedPiecesScroller.setPrefHeight(Main.height * 0.4);
        p2CapturedPiecesBox.getChildren().addAll(p2CapturedPiecesLabel, p2CapturedPiecesScroller);
        p2CapturedPiecesBox.setSpacing(5);
        leftPaneSection2.getChildren().add(p2CapturedPiecesBox);

        leftPaneSection3 = new HBox();
        capturedPiecesUpdates = new Label();
        capturedPiecesUpdates.setTextAlignment(TextAlignment.CENTER);
        capturedPiecesUpdates.setAlignment(Pos.CENTER);
        capturedPiecesUpdates.setFont(Font.font("Futura", 15));
        leftPaneSection3.getChildren().add(capturedPiecesUpdates);

        leftPane.getChildren().addAll(leftPaneSection1, leftPaneSection2, leftPaneSection3);
        leftPane.setAlignment(Pos.CENTER);
        leftPane.setPadding(new Insets(10, 10, 10, 10));
        leftPane.setSpacing(10);
        leftPane.setPrefSize(paneWidth, Main.height);


        rightPane = new VBox();

        banner = new ImageView("sample/bannertext.png");

        rightPaneSection1 = new HBox();
        turnCounterBox = new Label();
        turnCounterBox.setPrefSize(paneWidth - 20, Main.height * 0.05);
        turnCounterBox.setFont(Font.font("Futura", 20));
        turnCounterBox.setTextAlignment(TextAlignment.CENTER);
        rightPaneSection1.getChildren().add(turnCounterBox);

        rightPaneSection2 = new HBox();
        moveTrackerBox = new VBox();
        moveTrackerLabel = new Label("List of Moves Made");
        moveTrackerLabel.setPrefSize(paneWidth - 20, Main.height * 0.05);
        moveTrackerLabel.setFont(Font.font("Futura", 20));
        moveTrackerLabel.setTextAlignment(TextAlignment.CENTER);
        moveTrackerScroller = new ScrollPane();
        moveTracker = new ListView<>();
        moveTrackerScroller.setContent(moveTracker);
        moveTrackerScroller.setMinHeight(Main.height * 0.5);
        moveTrackerBox.getChildren().addAll(moveTrackerLabel, moveTrackerScroller);
        rightPaneSection2.getChildren().add(moveTrackerBox);

        rightPaneSection3 = new VBox();
        resetButton = new Button("Reset");
        resetButton.setOnAction((event) -> reset(players[1], players[2]));
        resetButton.setPrefSize(paneWidth - 20, Main.height / 9);
        resetButton.setAlignment(Pos.CENTER);
        mainMenuButton = new Button("Main Menu");
        mainMenuButton.setPrefSize(paneWidth - 20, Main.height / 9);
        mainMenuButton.setDisable(true);
        mainMenuButton.setAlignment(Pos.CENTER);
        rightPaneSection3.getChildren().addAll(resetButton, mainMenuButton);
        rightPaneSection3.setSpacing(15);

        rightPane.getChildren().addAll(banner, rightPaneSection1, rightPaneSection2, rightPaneSection3);
        rightPane.setAlignment(Pos.CENTER);
        rightPane.setPadding(new Insets(10, 10, 10, 10));
        rightPane.setSpacing(10);
        rightPane.setPrefSize(paneWidth, Main.height);

        this.getChildren().addAll(leftPane, board, rightPane);
        this.setAlignment(Pos.CENTER);
        this.setSpacing(10);
    }

    public static void gameOver() {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setHeaderText("Game over!! " + players[whoseTurn].getName() + " won!");
        a.setContentText("Clear the board?");
        a.show();
        whoseTurn = 0;
    }
}
