package sample;

import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

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

        moveTracker.getItems().add("Turn " + turn + ": P" + whoseTurn +
                board.getSquare(currSquareCoors).getPiece().getType() + ": (" + currSquareCoors[0] + "," +
                currSquareCoors[1] + "," + currSquareCoors[2] + "," + currSquareCoors[3] + ") -> (" + position[0] +
                "," + position[1] + "," + position[2] + "," + position[3] + ")");

        board.getSquare(position).setPiece(board.getSquare(currSquareCoors).getPiece());
        board.getSquare(position).getPiece().setPosition(position);
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
        leftPane = new VBox();

        leftPaneSection1 = new HBox();
        p1CapturedPiecesBox = new VBox();
        p1CapturedPiecesLabel = new Label(players[1].getName() + "'s Captured Pieces");
        p1CapturedPiecesScroller = new ScrollPane();
        p1CapturedPieces = new ListView<>();
        p1CapturedPiecesScroller.setContent(p1CapturedPieces);
        p1CapturedPiecesBox.getChildren().addAll(p1CapturedPiecesLabel, p1CapturedPiecesScroller);
        leftPaneSection1.getChildren().add(p1CapturedPiecesBox);

        leftPaneSection2 = new HBox();
        p2CapturedPiecesBox = new VBox();
        p2CapturedPiecesLabel = new Label(players[2].getName() + "'s Captured Pieces");
        p2CapturedPiecesScroller = new ScrollPane();
        p2CapturedPieces = new ListView<>();
        p2CapturedPiecesScroller.setContent(p2CapturedPieces);
        p2CapturedPiecesBox.getChildren().addAll(p2CapturedPiecesLabel, p2CapturedPiecesScroller);
        leftPaneSection2.getChildren().add(p2CapturedPiecesBox);

        leftPaneSection3 = new HBox();
        capturedPiecesUpdates = new Label();
        leftPaneSection3.getChildren().add(capturedPiecesUpdates);

        leftPane.getChildren().addAll(leftPaneSection1, leftPaneSection2, leftPaneSection3);


        rightPane = new VBox();

        banner = new ImageView();

        rightPaneSection1 = new HBox();
        turnCounterBox = new Label();
        rightPaneSection1.getChildren().add(turnCounterBox);

        rightPaneSection2 = new HBox();
        moveTrackerBox = new VBox();
        moveTrackerLabel = new Label("List of Moves Made");
        moveTrackerScroller = new ScrollPane();
        moveTracker = new ListView<>();
        moveTrackerScroller.setContent(moveTracker);
        moveTrackerBox.getChildren().addAll(moveTrackerLabel, moveTrackerScroller);
        rightPaneSection2.getChildren().add(moveTrackerBox);

        rightPaneSection3 = new VBox();
        resetButton = new Button("Reset");
        resetButton.setOnAction((event) -> reset(players[1], players[2]));
        mainMenuButton = new Button("Main Menu");
        mainMenuButton.setDisable(true);
        rightPaneSection3.getChildren().addAll(resetButton, mainMenuButton);

        rightPane.getChildren().addAll(banner, rightPaneSection1, rightPaneSection2, rightPaneSection3);

        this.getChildren().addAll(leftPane, board, rightPane);
    }

    public static void gameOver() {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setHeaderText("Game over!! " + players[whoseTurn].getName() + " won!");
        a.setContentText("Clear the board?");
        a.show();
        whoseTurn = 0;
    }
}
