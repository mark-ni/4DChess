package sample;

public class Pawn extends Piece {

    private static int[][] pawnMovesP2 = {{2, 0}, {1, 0}, {1, 1}, {0, 1}, {0, 2}};
    private static int[][] pawnMovesP1 = {{-2, 0}, {-1, 0}, {-1, -1}, {0, -1}, {0, -2}};
    private static int[][] pawnConditionals = {{-1, 0}, {1, 0}, {0, 1}, {0, -1}};
    private int[][] pawnMoves;

    public Pawn(Player player) {
        this.player = player;
        this.type = "P";
        this.fullName = "Pawn";

        if (player.num == 1) {
            pawnMoves = pawnMovesP1;
        } else {
            pawnMoves = pawnMovesP2;
        }
    }

    public void getMoves() {
        int tX, tY, tZ, tW;

        //Normal moves
        for (int i = 0; i < 5; i++) {
            tX = x + pawnMoves[i][0];
            tY = y;
            tZ = z + pawnMoves[i][1];
            tW = w;
            if ((player.num == 2 && tX < BS && tZ < BS) || (player.num == 1 && tX >= 0 && tZ >= 0)) {
                if (Game.board.getSquare(tX,tY,tZ,tW).isEmpty()) {
                    Game.board.getSquare(tX,tY,tZ,tW).setLegalOption();
                }
                for (int j = 0; j < 4; j++) {
                    tY = y + pawnConditionals[j][0];
                    tW = w + pawnConditionals[j][1];
                    if (tY < BS && tY >= 0 && tW < BS && tW >= 0) {
                        if (Game.board.getSquare(tX,tY,tZ,tW).isHostile(player.num)) {
                            Game.board.getSquare(tX,tY,tZ,tW).setLegalOption();
                        }
                    }
                }
            }
        }

    }
}
