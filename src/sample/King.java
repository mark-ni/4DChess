package sample;

public class King extends Piece {

    public King(Player player) {
        this.type = "K";
        this.fullName = "King";
        this.player = player;
    }

    public void getMoves() {
        for (int i = 0; i < 6; i++) {
            getMovesTwoD(groups[i][0], groups[i][1]);
        }
    }

    private void getMovesTwoD(int dimIndex1, int dimIndex2) {
        int[] tempCoors = position.clone();

        for (int dist1 = -1; dist1 < 2; dist1++) {
            tempCoors[dimIndex1] = position[dimIndex1] + dist1;
            if (tempCoors[dimIndex1] < BS && tempCoors[dimIndex1] >= 0) {
                for (int dist2 = -1; dist2 < 2; dist2++) {
                    tempCoors[dimIndex2] = position[dimIndex2] + dist2;
                    if (tempCoors[dimIndex2] < BS && tempCoors[dimIndex2] >= 0) {
                        if (!Game.board.getSquare(tempCoors).isFriendly(player.num)) {
                            Game.board.getSquare(tempCoors).setLegalOption();
                        }
                    }
                }
            }
        }
    }
}
