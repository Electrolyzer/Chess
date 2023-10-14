package chess.pieces;
import chess.Piece;
import chess.Square;

public class Knight extends Piece {
    
    public Knight(Square position, boolean isWhite){
        super(position, isWhite);
    }

    public String getType(){ return "N"; }

    public void updateValidMoves(){

        //Initialize all moves to invalid
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                validMoves.setPosition(i, j, moveType.INVALID);
            }    
        }

        int file = 0;
        int rank = 0;

        //The two for loops iterate the 8 possible directions
        for (int up = -2; up <= 2; up ++) {
            for (int right = -2; right <= 2; right ++) {

                //Only check for valid moves if the taxicab distance is 3
                if((Math.abs(right)+Math.abs(up)!=3)){ continue; }

                //Ensures knight does not move out of bounds
                file = getFile() + up;
                rank = getRank() + right;
                if(file<0 || file>7 || rank<0 || rank>7){ continue; }

                //Checks whether the space is occupied or has a capturable piece
                if (null == Board.getPosition(file, rank) || Board.getPosition(file, rank) instanceof PhantomPawn) {
                    validMoves.setPosition(file, rank, moveType.VALID);
                } else if (!isSameColor(Board.getPosition(file, rank))) {
                    validMoves.setPosition(file, rank, moveType.VALID);
                }

            }
        }
        updateValidMovesCheck();
    }
}
