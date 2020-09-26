package sample;

import javafx.geometry.Pos;
import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;

public class Board extends VBox {

    private final int BS1 = 8;
    private final int BS2 = BS1 * BS1;
    private final int BS3 = BS1 * BS1 * BS1;
    private final int BS4 = BS1 * BS1 * BS1 * BS1;

    private final Background[][] BGS = {{Backgrounds.tan, Backgrounds.saddlebrown},
            {Backgrounds.tan, Backgrounds.peru}};

    public GridSquare[] squares;
    private HBox[] innerHBoxes;
    private VBox[] innerVBoxes;
    private HBox[] outerHBoxes;


    public Board() {
        setAlignment(Pos.CENTER);

        squares = new GridSquare[BS4];
        innerHBoxes = new HBox[BS3];
        innerVBoxes = new VBox[BS2];
        outerHBoxes = new HBox[BS1];

        for (int i = 0; i < BS4; i++) {
            if (i % BS1 == 0) {
                if (i % (BS2) == 0) {
                    if (i % (BS3) == 0) {
                        outerHBoxes[i / BS3] = new HBox();
                        this.getChildren().add(outerHBoxes[i / BS3]);
                    }
                    innerVBoxes[i / BS2] = new VBox();
                    outerHBoxes[i / BS3].getChildren().add(innerVBoxes[i / BS2]);
                }
                innerHBoxes[i / BS1] = new HBox();
                innerVBoxes[i / BS2].getChildren().add(innerHBoxes[i / BS1]);
            }
            squares[i] = new GridSquare(i / BS3, (i % BS3) / BS2, (i % BS2) / BS1, i % BS1, i,
                    BGS[(i / BS3 + i / BS2) % 2][(i + i / BS1) % 2]);
            innerHBoxes[i / BS1].getChildren().add(squares[i]);
        }
    }

    public void addPiece(Piece piece, int[] position) {
        getSquare(position).setPiece(piece);
        piece.setPosition(position);
    }

    public void setUpPieces(Player[] players) {
        addPiece(new Rook(players[2]), new int[]{0,0,0,0});
        addPiece(new Knight(players[2]), new int[]{0,0,0,1});
        addPiece(new Bishop(players[2]), new int[]{0,0,0,2});
        addPiece(new Queen(players[2]), new int[]{0,0,0,3});
        addPiece(new King(players[2]), new int[]{0,0,0,4});
        addPiece(new Bishop(players[2]), new int[]{0,0,0,5});
        addPiece(new Knight(players[2]), new int[]{0,0,0,6});
        addPiece(new Rook(players[2]), new int[]{0,0,0,7});
        for (int w = 0; w < Game.board.getBS(); w++) {
            addPiece(new Pawn(players[2]), new int[]{0, 0, 1, w});
        }

        addPiece(new Rook(players[1]), new int[]{7,7,7,0});
        addPiece(new Knight(players[1]), new int[]{7,7,7,1});
        addPiece(new Bishop(players[1]), new int[]{7,7,7,2});
        addPiece(new Queen(players[1]), new int[]{7,7,7,3});
        addPiece(new King(players[1]), new int[]{7,7,7,4});
        addPiece(new Bishop(players[1]), new int[]{7,7,7,5});
        addPiece(new Knight(players[1]), new int[]{7,7,7,6});
        addPiece(new Rook(players[1]), new int[]{7,7,7,7});
        for (int w = 0; w < Game.board.getBS(); w++) {
            addPiece(new Pawn(players[1]), new int[]{7, 7, 6, w});
        }
    }

    public void reset() {
        for (int i = 0; i < BS4; i++) {
            if (!squares[i].isEmpty()) {
                squares[i].setPiece(null);
            }
        }
    }

    public int getBS() {
        return BS1;
    }

    public GridSquare getSquare(int x, int y, int z, int w) {
        return squares[w + BS1 * z + BS2 * y + BS3 * x];
    }

    public GridSquare getSquare(int[] x) {
        return getSquare(x[0], x[1], x[2], x[3]);
    }
}
