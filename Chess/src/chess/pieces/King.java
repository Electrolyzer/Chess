package chess.pieces;
import chess.Piece;
import chess.Square;

public class King extends Piece {

    public King(Square position, boolean isWhite){
        super(position, isWhite);
    }

    public void updateValidMoves(){

        //Initialize all moves to invalid
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                validMoves.setPosition(i, j, moveType.INVALID);
            }    
        }

        int file;
        int rank;

        // The two for loops iterate the 8 possible directions
        for (int up = -1; up <= 1; up++) {
            for (int right = -1; right <= 1; right++) {

                // Don't check for valid move when there is no direction
                if (!((right == 0) && (up == 0))) {
                    continue;
                }

                //Ensures king does not move out of bounds
                file = getFile() + up;
                rank = getRank() + right;
                if(file<0 || file>7 || rank<0 || rank>7){ continue; }

                // Checks each direction for valid movement
                if (null == Board.Board[getFile() + up][getRank() + right]) {
                    validMoves.setPosition(getFile() + up, getRank() + right, moveType.VALID);
                    continue;
                } else if (!isSameColor(Board.Board[getFile() + up][getRank() + right])) {
                    validMoves.setPosition(getFile() + up, getRank() + right, moveType.VALID);
                }

            }
        }

    }
}