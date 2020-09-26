package sample;

public class Knight extends Piece {

    private static int[][] knightsMoves = {{-2,1},{-2,-1},{-1, 2}, {-1, -2}, {1, 2}, {1, -2}, {2, 1}, {2, -1}};

    public Knight(Player player) {
        this.type = "N";
        this.fullName = "Knight";
        this.player = player;
    }

    public void getMoves() {
        for (int i = 0; i < 6; i++) {
            getMovesTwoD(groups[i][0], groups[i][1]);
        }
    }

    private void getMovesTwoD(int dimIndex1, int dimIndex2) {
        int[] tempCoors = position.clone();

        for (int j = 0; j < 8; j++) {
            tempCoors[dimIndex1] = position[dimIndex1] + knightsMoves[j][0];
            if (tempCoors[dimIndex1] <= BS && tempCoors[dimIndex1] >= 0) {
                tempCoors[dimIndex2] = position[dimIndex2] +  knightsMoves[j][1];
                if (tempCoors[dimIndex2] <= BS && tempCoors[dimIndex2] >= 0) {
                    if (!Game.board.getSquare(tempCoors).isFriendly(player.num)) {
                        Game.board.getSquare(tempCoors).setLegalOption();
                    }
                }
            }
        }
    }
}