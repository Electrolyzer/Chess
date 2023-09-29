package chess.pieces;
import chess.Piece;
import chess.Square;

public class Knight extends Piece {
    
    public Knight(Square position, boolean isWhite){
        super(position, isWhite);
    }

    public void updateValidMoves(){

        //Initialize all moves to invalid
        validMoves = new boolean[8][8];

        //The two for loops iterate the 8 possible directions
        for (int up = -2; up <= 2; up ++) {
            for (int right = -2; right <= 2; right ++) {

                //Only check for valid moves if the taxicab distance is 3
                if(!(Math.abs(right)+Math.abs(up)==3)){ continue; }

                //Checks whether the space is occupied or has a capturable piece
                if (null == Board.Board[getFile() + up][getRank() + right]) {
                    validMoves[getFile() + up][getRank() + right] = true;
                    continue;
                } else if (!isSameColor(Board.Board[getFile() + up][getRank() + right])) {
                    validMoves[getFile() + up][getRank() + right] = true;
                    break;
                }

            }
        }

    }
}
